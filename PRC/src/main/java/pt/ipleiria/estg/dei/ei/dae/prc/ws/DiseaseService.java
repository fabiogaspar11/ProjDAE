package pt.ipleiria.estg.dei.ei.dae.prc.ws;

import pt.ipleiria.estg.dei.ei.dae.prc.dtos.DiseaseDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.dtos.PatientDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.ejbs.DiseaseBean;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Disease;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Patient;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.*;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("diseases")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class DiseaseService {
    @EJB
    DiseaseBean diseaseBean;

    private DiseaseDTO toDTO(Disease disease){
        return new DiseaseDTO(
                disease.getCode(),
                disease.getName()
        );
    }

    private List<DiseaseDTO> toDTOs(List<Disease> diseases) {
        return diseases.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private DiseaseDTO toDTONoPatients(Disease disease) {
        return new DiseaseDTO(
                disease.getCode(),
                disease.getName()
        );
    }
    private List<DiseaseDTO> toDTOsNoPatients(List<Disease> diseases) {
        return diseases.stream().map(this::toDTONoPatients).collect(Collectors.toList());
    }

    private PatientDTO toDTO(Patient patient) {
        return new PatientDTO(
                patient.getName(),
                patient.getEmail(),
                patient.getPassword(),
                patient.getBirthDate(),
                patient.getContact(),
                patient.getHealthNumber(),
                patient.getGender()
        );
    }

    private List<PatientDTO> patientsToDTOs(List<Patient> patients) {
        return patients.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/diseases/”
    @RolesAllowed({"Administrator","HealthcareProfessional"})
    public List<DiseaseDTO> getAllDiseasesWS() {
        return toDTOsNoPatients(diseaseBean.getAllDiseases());
    }


    @POST
    @Path("/")
    @RolesAllowed({"Administrator"})
    public Response createNewDisease (DiseaseDTO diseaseDTO) throws MyEntityExistsException, MyEntityNotFoundException {
        long diseaseCode = diseaseBean.create(diseaseDTO.getName());
        Disease newDisease = diseaseBean.findDisease(diseaseCode);
        return Response.status(Response.Status.CREATED)
                .entity(toDTONoPatients(newDisease))
                .build();
    }

    @GET
    @Path("/{code}")
    @RolesAllowed({"Administrator","HealthcareProfessional"})
    public Response getDiseaseDetails(@PathParam("code") int code) throws MyEntityNotFoundException {
        Disease disease = diseaseBean.findDisease(code);
        return Response.status(Response.Status.OK)
                .entity(toDTO(disease))
                .build();
    }


    @PUT
    @Path("/{code}")
    @RolesAllowed({"Administrator"})
    public Response updateDiseaseWS(@PathParam("code") int code, DiseaseDTO diseaseDTO) throws MyEntityNotFoundException {
        Disease disease = diseaseBean.findDisease(code);
        diseaseBean.update(disease, diseaseDTO);
        return Response.status(Response.Status.OK)
                .entity(toDTO(disease))
                .build();
    }

    @DELETE
    @Path("/{code}")
    @RolesAllowed({"Administrator"})
    public Response deleteDiseaseWS(@PathParam("code") int code) throws MyEntityNotFoundException, MyConstraintViolationException {
        Disease disease = diseaseBean.findDisease(code);
        diseaseBean.delete(code);
        return Response.status(Response.Status.OK)
                .entity(toDTO(disease))
                .build();
    }

}
