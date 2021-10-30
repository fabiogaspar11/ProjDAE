package pt.ipleiria.estg.dei.ei.dae.prc.ws;

import pt.ipleiria.estg.dei.ei.dae.prc.dtos.DiseaseDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.dtos.HealthcareProfessionalDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.dtos.PatientDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.ejbs.DiseaseBean;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Disease;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.HealthcareProfessional;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Patient;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.*;

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

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/diseases/”
    public List<DiseaseDTO> getAllDiseasesWS() {
        return toDTOsNoPatients(diseaseBean.getAllDiseases());
    }

    private DiseaseDTO toDTO(Disease disease){
        return new DiseaseDTO(
                disease.getCode(),
                disease.getName(),
                disease.getType(),
                patientsToDTOs(disease.getPatients())
        );
    }
    private List<DiseaseDTO> toDTOs(List<Disease> diseases) {
        return diseases.stream().map(this::toDTO).collect(Collectors.toList());
    }


    private DiseaseDTO toDTONoPatients(Disease disease) {
        return new DiseaseDTO(
                disease.getCode(),
                disease.getName(),
                disease.getType()
        );
    }
    private List<DiseaseDTO> toDTOsNoPatients(List<Disease> diseases) {
        return diseases.stream().map(this::toDTONoPatients).collect(Collectors.toList());
    }


    private PatientDTO toDTO(Patient patient) {
        return new PatientDTO(
                patient.getUsername(),
                patient.getName(),
                patient.getEmail(),
                patient.getPassword(),
                patient.getBirthDate(),
                patient.getContact(),
                patient.getHealthUserNumber(),
                patient.getWeight(),
                patient.getHeight()
        );
    }
    private List<PatientDTO> patientsToDTOs(List<Patient> patients) {
        return patients.stream().map(this::toDTO).collect(Collectors.toList());
    }


    @POST
    @Path("/")
    public Response createNewDisease (DiseaseDTO diseaseDTO) throws MyEntityExistsException {
        diseaseBean.create(diseaseDTO.getCode(), diseaseDTO.getName(), diseaseDTO.getType());
        Disease newDisease = diseaseBean.findDisease(diseaseDTO.getCode());
        if(newDisease == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        return Response.status(Response.Status.CREATED)
                .entity(toDTONoPatients(newDisease))
                .build();
    }

    @GET
    @Path("{code}/patients")
    public Response getDiseasePatients(@PathParam("code") int code) {
        Disease disease = diseaseBean.findDisease(code);
        if (disease != null) {
            return Response.ok(patientsToDTOs(disease.getPatients())).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_PATIENT's")
                .build();
    }

    @GET
    @Path("/{code}")
    public Response getDiseaseDetails(@PathParam("code") int code) {
        Disease disease = diseaseBean.findDisease(code);
        if (disease != null) {
            return Response.ok(toDTO(disease)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_DISEASE")
                .build();
    }



    @PUT
    @Path("/{code}")
    public Response updateDiseaseWS(@PathParam("code") int code, DiseaseDTO diseaseDTO) {
        diseaseBean.update(code, diseaseDTO.getName(), diseaseDTO.getType());

        Disease disease = diseaseBean.findDisease(code);
        if(!disease.getName().equals(disease.getName()))
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        return Response.status(Response.Status.OK)
                .entity(toDTO(disease))
                .build();
    }

    @DELETE
    @Path("/{code}")
    public Response deleteDiseaseWS(@PathParam("code") int code) {
        diseaseBean.delete(code);

        Disease disease = diseaseBean.findDisease(code);
        if(disease != null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        return Response.status(Response.Status.OK)
                .build();
    }

    @POST
    @Path("/{code}/{patient}")
    public Response enrollInPatients(@PathParam("code") int code, @PathParam("patient") String username) throws MyEntityNotFoundException {

        diseaseBean.enrollDiseaseInPatient(code, username);

        return Response.status(Response.Status.CREATED)
                .build();
    }

    @DELETE
    @Path("/{code}/{patient}")
    public Response unrollInPatients(@PathParam("code") int code, @PathParam("patient") String username) throws MyEntityNotFoundException {

        diseaseBean.unrollDiseaseInPatient(code, username);

        return Response.status(Response.Status.CREATED)
                .build();
    }


}
