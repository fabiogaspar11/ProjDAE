package pt.ipleiria.estg.dei.ei.dae.prc.ws;

import pt.ipleiria.estg.dei.ei.dae.prc.dtos.HealthcareProfessionalDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.dtos.PatientDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.dtos.PrescriptionDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.ejbs.HealthcareProfessionalBean;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.HealthcareProfessional;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Patient;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Prescription;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.*;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("healthcareProfessionals")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})

public class HealthcareprofessionalService {
    @EJB
    private HealthcareProfessionalBean healthcareProfessionalBean;

    private HealthcareProfessionalDTO toDTONoPatients(HealthcareProfessional healthcareProfessional){
        return new HealthcareProfessionalDTO(
                healthcareProfessional.getName(),
                healthcareProfessional.getEmail(),
                healthcareProfessional.getPassword(),
                healthcareProfessional.getBirthDate(),
                healthcareProfessional.getContact(),
                healthcareProfessional.getHealthNumber(),
                healthcareProfessional.getType()
        );
    }

    private HealthcareProfessionalDTO toDTO(HealthcareProfessional healthcareProfessional) {
        return new HealthcareProfessionalDTO(
                healthcareProfessional.getName(),
                healthcareProfessional.getEmail(),
                healthcareProfessional.getPassword(),
                healthcareProfessional.getBirthDate(),
                healthcareProfessional.getContact(),
                healthcareProfessional.getHealthNumber(),
                healthcareProfessional.getType(),
                healthcareProfessional.getPatients(),
                healthcareProfessional.getPrescriptions()
        );
    }

    private PrescriptionDTO prescriptionToDTO(Prescription prescription) {
        return new PrescriptionDTO(
                prescription.getCode(),
                prescription.getTitle(),
                prescription.getObservations(),
                prescription.getEmissionDate(),
                prescription.getExpireDate()
        );
    }

    private PatientDTO patientToDTO(Patient patient) {
        return new PatientDTO(
                patient.getName(),
                patient.getEmail(),
                patient.getPassword(),
                patient.getBirthDate(),
                patient.getContact(),
                patient.getHealthNumber()
        );
    }

    private List<HealthcareProfessionalDTO> toDTOs(List<HealthcareProfessional> healthcareProfessionals) {
        return healthcareProfessionals.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private List<PrescriptionDTO> prescriptionstoDTOs(List<Prescription> prescriptions) {
        return prescriptions.stream().map(this::prescriptionToDTO).collect(Collectors.toList());
    }

    private List<PatientDTO> patientstoDTOs(List<Patient> patients) {
        return patients.stream().map(this::patientToDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<HealthcareProfessionalDTO> getAllHealthcareProfessionalsWS() {
        return toDTOs(healthcareProfessionalBean.getAllHealthcareProfessionals());
    }

    @POST
    @Path("/")
    public Response createNewHealthcareprofessional(HealthcareProfessionalDTO healthcareProfessionalDTO) throws MyEntityExistsException, MyEntityNotFoundException {
        String username = healthcareProfessionalBean.create(
                healthcareProfessionalDTO.getHealthNumber(),
                healthcareProfessionalDTO.getName(),
                healthcareProfessionalDTO.getEmail(),
                healthcareProfessionalDTO.getPassword(),
                healthcareProfessionalDTO.getBirthDate(),
                healthcareProfessionalDTO.getContact(),
                healthcareProfessionalDTO.getType()
        );
        HealthcareProfessional healthcareProfessional = healthcareProfessionalBean.findHealthcareProfessional(username);
        return Response.status(Response.Status.CREATED)
                .entity(toDTONoPatients(healthcareProfessional))
                .build();
    }

    @GET
    @Path("{username}")
    public Response getHealthcareprofessionalDetails(@PathParam("username") String username) throws MyEntityNotFoundException {
        HealthcareProfessional healthcareProfessional = healthcareProfessionalBean.findHealthcareProfessional(username);
        return Response.status(Response.Status.OK)
                .entity(toDTO(healthcareProfessional))
                .build();
    }

    @DELETE
    @Path("/{username}")
    public Response deleteHealthcareprofessional(@PathParam("username") String username) throws MyEntityNotFoundException {
        HealthcareProfessional healthcareProfessional = healthcareProfessionalBean.findHealthcareProfessional(username);
        healthcareProfessionalBean.remove(username);
        return Response.status(Response.Status.OK)
                .entity(toDTONoPatients(healthcareProfessional))
                .build();
    }

    @PUT
    @Path("/{username}")
    public Response updateHealthcareprofessional(@PathParam("username") String username, HealthcareProfessionalDTO healthcareProfessionalDTO) throws MyEntityNotFoundException {
        HealthcareProfessional healthcareProfessional = healthcareProfessionalBean.findHealthcareProfessional(username);
        healthcareProfessionalBean.update(healthcareProfessional, healthcareProfessionalDTO);
        return Response.status(Response.Status.OK)
                .entity(toDTO(healthcareProfessional))
                .build();
    }
    @PUT
    @Path("/{username}/password")
    public Response updatePasswordHealthcareprofessional(@PathParam("username") String username, HealthcareProfessionalDTO healthcareProfessionalDTO) throws MyEntityNotFoundException, MyIllegalArgumentException {

        HealthcareProfessional healthcareProfessional = healthcareProfessionalBean.findHealthcareProfessional(username);
        healthcareProfessionalBean.updatePassword(healthcareProfessional, healthcareProfessionalDTO);
        return Response.status(Response.Status.OK)
                .entity(toDTO(healthcareProfessional))
                .build();
    }

    @GET
    @Path("{username}/patients")
    public Response getHealthcareprofessionalPatients(@PathParam("username") String username) throws MyEntityNotFoundException {
        HealthcareProfessional healthcareProfessional = healthcareProfessionalBean.findHealthcareProfessional(username);
        return Response.status(Response.Status.OK)
                .entity(patientstoDTOs(healthcareProfessional.getPatients()))
                .build();
    }

    @GET
    @Path("{username}/prescriptions")
    public Response getHealthcareprofessionalPrescriptions(@PathParam("username") String username) throws MyEntityNotFoundException {
        HealthcareProfessional healthcareProfessional = healthcareProfessionalBean.findHealthcareProfessional(username);
        return Response.status(Response.Status.OK)
                .entity(prescriptionstoDTOs(healthcareProfessional.getPrescriptions()))
                .build();
    }

    @POST
    @Path("/{username}/AddPatient/{usernamePatient}")
    public Response addPatient(@PathParam("username") String username, @PathParam("usernamePatient") String usernamePatient) throws MyEntityNotFoundException {
        healthcareProfessionalBean.addPatientFromHealthcareprofessional(usernamePatient, username);
        HealthcareProfessional healthcareProfessional = healthcareProfessionalBean.findHealthcareProfessional(username);
        return Response.ok(patientstoDTOs(healthcareProfessional.getPatients())).build();

    }

    @POST
    @Path("/{username}/RemovePatient/{usernamePatient}")
    public Response removePatient(@PathParam("username") String username, @PathParam("usernamePatient") String usernamePatient) throws MyEntityNotFoundException {
        healthcareProfessionalBean.removePatientFromHealthcareprofessional(usernamePatient, username);
        HealthcareProfessional healthcareProfessional = healthcareProfessionalBean.findHealthcareProfessional(username);
        return Response.ok(patientstoDTOs(healthcareProfessional.getPatients())).build();
    }

    @POST
    @Path("/{username}/AddPrescription/{codePrescription}")
    public Response addPatient(@PathParam("username") String username, @PathParam("codePrescription") Integer codePrescription) throws MyEntityNotFoundException {
        healthcareProfessionalBean.addPrescriptionFromHealthcareprofessional(codePrescription, username);
        HealthcareProfessional healthcareProfessional = healthcareProfessionalBean.findHealthcareProfessional(username);
        return Response.ok(patientstoDTOs(healthcareProfessional.getPatients())).build();

    }

    @POST
    @Path("/{username}/RemovePrescription/{codePrescription}")
    public Response removePatient(@PathParam("username") String username, @PathParam("codePrescription") Integer codePrescription) throws MyEntityNotFoundException {
        healthcareProfessionalBean.removePrescriptionFromHealthcareprofessional(codePrescription, username);
        HealthcareProfessional healthcareProfessional = healthcareProfessionalBean.findHealthcareProfessional(username);
        return Response.ok(patientstoDTOs(healthcareProfessional.getPatients())).build();
    }

}
