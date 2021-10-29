package pt.ipleiria.estg.dei.ei.dae.prc.ws;

import pt.ipleiria.estg.dei.ei.dae.prc.dtos.HealthcareProfessionalDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.dtos.PatientDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.ejbs.HealthcareProfessionalBean;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.HealthcareProfessional;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Patient;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("healthcareprofessionals")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
//TODO
public class HealthcareprofessionalService {
    @EJB
    private HealthcareProfessionalBean healthcareProfessionalBean;

    private HealthcareProfessionalDTO toDTO(HealthcareProfessional healthcareProfessional){
        return new HealthcareProfessionalDTO(
                healthcareProfessional.getUsername(),
                healthcareProfessional.getName(),
                healthcareProfessional.getEmail(),
                healthcareProfessional.getPassword(),
                healthcareProfessional.getBirthDate(),
                healthcareProfessional.getContact(),
                healthcareProfessional.getHealthcareProfessionalNumber(),
                healthcareProfessional.getType()
        );
    }
    private HealthcareProfessionalDTO toDTONoPatients(HealthcareProfessional healthcareProfessional) {
        return new HealthcareProfessionalDTO(
                healthcareProfessional.getUsername(),
                healthcareProfessional.getName(),
                healthcareProfessional.getEmail(),
                healthcareProfessional.getPassword(),
                healthcareProfessional.getBirthDate(),
                healthcareProfessional.getContact(),
                healthcareProfessional.getHealthcareProfessionalNumber(),
                healthcareProfessional.getType(),
                healthcareProfessional.getPatients()
        );
    }
    private List<HealthcareProfessionalDTO> toDTOs(List<HealthcareProfessional> healthcareProfessionals) {
        return healthcareProfessionals.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private List<HealthcareProfessionalDTO> toDTOsNoPatients(List<HealthcareProfessional> students) {
        return students.stream().map(this::toDTONoPatients).collect(Collectors.toList());
    }

    private PatientDTO patientToDTO(Patient patient) {
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

    // converts an entire list of entities into a list of DTOs
    private List<PatientDTO> patientsoDTOs(List<Patient> patients) {
        return patients.stream().map(this::patientToDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<HealthcareProfessionalDTO> getAllHealthcareProfessionalsWS() {
        return toDTOs(healthcareProfessionalBean.getAllHealthcareProfessionals());
    }

    @POST
    @Path("/")
    public Response createNewHealthcareprofessional(HealthcareProfessionalDTO healthcareProfessionalDTO) {
        healthcareProfessionalBean.create(
                healthcareProfessionalDTO.getUsername(),
                healthcareProfessionalDTO.getHealthcareProfessionalNumber(),
                healthcareProfessionalDTO.getName(),
                healthcareProfessionalDTO.getEmail(),
                healthcareProfessionalDTO.getPassword(),
                healthcareProfessionalDTO.getBirthDate(),
                healthcareProfessionalDTO.getContact(),
                healthcareProfessionalDTO.getType()
        );
        HealthcareProfessional healthcareProfessional = healthcareProfessionalBean.findHealthcareProfessional(healthcareProfessionalDTO.getUsername());
        return Response.status(Response.Status.CREATED)
                .entity(toDTO(healthcareProfessional))
                .build();
    }

    @GET
    @Path("{username}")
    public Response getHealthcareprofessionalDetails(@PathParam("username") String username){
        HealthcareProfessional healthcareProfessional = healthcareProfessionalBean.findHealthcareProfessional(username);
        return Response.status(Response.Status.OK)
                .entity(toDTO(healthcareProfessional))
                .build();
    }

    @DELETE
    @Path("/{username}")
    public Response deleteHealthcareprofessional(@PathParam("username") String username)  {
        HealthcareProfessional healthcareProfessional = healthcareProfessionalBean.findHealthcareProfessional(username);
        healthcareProfessionalBean.remove(healthcareProfessional);
        return Response.status(Response.Status.OK)
                .entity(toDTO(healthcareProfessional))
                .build();
    }

    @PUT
    @Path("/{username}")
    public Response updateHealthcareprofessional(@PathParam("username") String username, HealthcareProfessionalDTO healthcareProfessionalDTO)  {

        HealthcareProfessional healthcareProfessional = healthcareProfessionalBean.findHealthcareProfessional(username);
        healthcareProfessionalBean.update(healthcareProfessional, healthcareProfessionalDTO);
        return Response.status(Response.Status.OK)
                .entity(toDTO(healthcareProfessional))
                .build();
    }

    @Path("/{username}/add/{usernamePatient}")
    public Response addPatient(@PathParam("username") String username, @PathParam("usernamePatient") String usernamePatient) {
        healthcareProfessionalBean.addPatientFromHealthcareprofessional(usernamePatient, username);
        HealthcareProfessional healthcareProfessional = healthcareProfessionalBean.findHealthcareProfessional(username);
        return Response.ok(patientsoDTOs(healthcareProfessional.getPatients())).build();

    }

    @POST
    @Path("/{username}/remove/{usernamePatient}")
    public Response removePatient(@PathParam("username") String username, @PathParam("usernamePatient") String usernamePatient) {
        healthcareProfessionalBean.removePatientFromHealthcareprofessional(usernamePatient, username);
        HealthcareProfessional healthcareProfessional = healthcareProfessionalBean.findHealthcareProfessional(username);
        return Response.ok(patientsoDTOs(healthcareProfessional.getPatients())).build();
    }
    }
