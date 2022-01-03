package pt.ipleiria.estg.dei.ei.dae.prc.ws;

import pt.ipleiria.estg.dei.ei.dae.prc.dtos.BiomedicDataMeasureDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.dtos.HealthcareProfessionalDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.dtos.PatientDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.dtos.PrescriptionDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.ejbs.HealthcareProfessionalBean;
import pt.ipleiria.estg.dei.ei.dae.prc.ejbs.PatientBean;
import pt.ipleiria.estg.dei.ei.dae.prc.ejbs.PrescriptionBean;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.BiomedicDataMeasure;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.HealthcareProfessional;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Patient;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Prescription;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.*;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("healthcareProfessionals")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})

public class HealthcareprofessionalService {
    @EJB
    private HealthcareProfessionalBean healthcareProfessionalBean;
    @EJB
    private PrescriptionBean prescriptionBean;
    @EJB
    private PatientBean patientBean;


    @Context
    private SecurityContext securityContext;


    private HealthcareProfessionalDTO toDTO(HealthcareProfessional healthcareProfessional){
        return new HealthcareProfessionalDTO(
                healthcareProfessional.getName(),
                healthcareProfessional.getEmail(),
                healthcareProfessional.getBirthDate(),
                healthcareProfessional.getContact(),
                healthcareProfessional.getHealthNumber(),
                healthcareProfessional.getType()
        );
    }

    private PrescriptionDTO prescriptionToDTO(Prescription prescription) {
        return new PrescriptionDTO(
                prescription.getCode(),
                prescription.getTitle(),
                prescription.getObservations(),
                prescription.getIsPharmacological(),
                prescription.getTreatmentInfo(),
                prescription.getEmissionDate(),
                prescription.getExpireDate(),
                prescription.getPatient().getUsername(),
                prescription.getHealthcareProfessional().getUsername()
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

    private BiomedicDataMeasureDTO biomedicDataMeasureToDTO(BiomedicDataMeasure biomedicDataMeasure){
        return new BiomedicDataMeasureDTO(
                biomedicDataMeasure.getCode(),
                biomedicDataMeasure.getValue(),
                biomedicDataMeasure.getDate(),
                biomedicDataMeasure.getHour(),
                biomedicDataMeasure.getPatient().getUsername(),
                biomedicDataMeasure.getBiomedicDataType().getCode(),
                biomedicDataMeasure.getUserRegister().getUsername()
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

    private List<BiomedicDataMeasureDTO> biomedicDataMeasuresToDTOs(List<BiomedicDataMeasure> biomedicDataMeasures){
        return biomedicDataMeasures.stream().map(this::biomedicDataMeasureToDTO).collect(Collectors.toList());
    }
    @GET
    @Path("/")
    @RolesAllowed({"Administrator"})
    public List<HealthcareProfessionalDTO> getAllHealthcareProfessionalsWS() {
        return toDTOs(healthcareProfessionalBean.getAllHealthcareProfessionals());
    }

    @POST
    @Path("/")
    @RolesAllowed({"Administrator"})
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
                .entity(toDTO(healthcareProfessional))
                .build();
    }

    @GET
    @Path("{username}")
    @RolesAllowed({"Administrator", "HealthcareProfessional"})
    public Response getHealthcareprofessionalDetails(@PathParam("username") String username) throws MyEntityNotFoundException {
        Principal principal = securityContext.getUserPrincipal();
        HealthcareProfessional healthcareProfessional = healthcareProfessionalBean.findHealthcareProfessional(username);
        if(securityContext.isUserInRole("HealthcareProfessional") && !principal.getName().equals(healthcareProfessional.getUsername())){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        return Response.status(Response.Status.OK)
                .entity(toDTO(healthcareProfessional))
                .build();
    }

    @DELETE
    @Path("/{username}")
    @RolesAllowed({"Administrator"})
    public Response deleteHealthcareprofessional(@PathParam("username") String username) throws MyEntityNotFoundException, MyConstraintViolationException {
        HealthcareProfessional healthcareProfessional = healthcareProfessionalBean.findHealthcareProfessional(username);
        healthcareProfessionalBean.remove(username);
        return Response.status(Response.Status.OK)
                .entity(toDTO(healthcareProfessional))
                .build();
    }

    @PUT
    @Path("/{username}")
    @RolesAllowed({"Administrator", "HealthcareProfessional"})
    public Response updateHealthcareprofessional(@PathParam("username") String username, HealthcareProfessionalDTO healthcareProfessionalDTO) throws MyEntityNotFoundException {
        Principal principal = securityContext.getUserPrincipal();
        HealthcareProfessional healthcareProfessional = healthcareProfessionalBean.findHealthcareProfessional(username);
        if(securityContext.isUserInRole("HealthcareProfessional") && !principal.getName().equals(healthcareProfessional.getUsername())){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        healthcareProfessionalBean.update(healthcareProfessional, healthcareProfessionalDTO);
        return Response.status(Response.Status.OK)
                .entity(toDTO(healthcareProfessional))
                .build();
    }

    @PUT
    @Path("/{username}/password")
    @RolesAllowed({"HealthcareProfessional"})
    public Response updatePasswordHealthcareprofessional(@PathParam("username") String username, HealthcareProfessionalDTO healthcareProfessionalDTO) throws MyEntityNotFoundException, MyIllegalArgumentException {
        Principal principal = securityContext.getUserPrincipal();
        HealthcareProfessional healthcareProfessional = healthcareProfessionalBean.findHealthcareProfessional(username);
        if( !principal.getName().equals(healthcareProfessional.getUsername())){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        healthcareProfessionalBean.updatePassword(healthcareProfessional, healthcareProfessionalDTO);
        return Response.status(Response.Status.OK)
                .entity(toDTO(healthcareProfessional))
                .build();
    }

    @GET
    @Path("{username}/patients")
    @RolesAllowed({"HealthcareProfessional"})
    public Response getHealthcareprofessionalPatients(@PathParam("username") String username) throws MyEntityNotFoundException {
        Principal principal = securityContext.getUserPrincipal();
        HealthcareProfessional healthcareProfessional = healthcareProfessionalBean.findHealthcareProfessional(username);
        if( !principal.getName().equals(healthcareProfessional.getUsername())){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        return Response.status(Response.Status.OK)
                .entity(patientstoDTOs(healthcareProfessional.getPatients()))
                .build();
    }


   @GET
    @Path("{username}/biomedicDataMeasures")
    @RolesAllowed({"HealthcareProfessional"})
    public Response getHealthcareprofessionalBiomedicMeasures(@PathParam("username") String username) throws MyEntityNotFoundException {
        Principal principal = securityContext.getUserPrincipal();
        HealthcareProfessional healthcareProfessional = healthcareProfessionalBean.findHealthcareProfessional(username);
        if( !principal.getName().equals(healthcareProfessional.getUsername())){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        List<BiomedicDataMeasure> biomedicDataMeasures = new ArrayList<>();
        for (Patient p: healthcareProfessional.getPatients()
             ) {
            for (BiomedicDataMeasure b: p.getBiomedicDataMeasures()) {
                biomedicDataMeasures.add(b);
            }
        }
        return Response.status(Response.Status.OK)
                .entity(biomedicDataMeasuresToDTOs(biomedicDataMeasures))
                .build();
    }


    @GET
    @Path("{username}/prescriptions")
    @RolesAllowed({"HealthcareProfessional"})
    public Response getHealthcareprofessionalPrescriptions(@PathParam("username") String username) throws MyEntityNotFoundException {
        Principal principal = securityContext.getUserPrincipal();
        HealthcareProfessional healthcareProfessional = healthcareProfessionalBean.findHealthcareProfessional(username);
        if(!principal.getName().equals(healthcareProfessional.getUsername())){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        return Response.status(Response.Status.OK)
                .entity(prescriptionstoDTOs(healthcareProfessionalBean.getPrescriptions(username)))
                .build();
    }

    @PUT
    @Path("/{username}/AddPatient/{usernamePatient}")
    @RolesAllowed({"HealthcareProfessional"})
    public Response addPatient(@PathParam("username") String username, @PathParam("usernamePatient") String usernamePatient) throws MyEntityNotFoundException {
        Principal principal = securityContext.getUserPrincipal();
        HealthcareProfessional healthcareProfessional = healthcareProfessionalBean.findHealthcareProfessional(username);
        if(!principal.getName().equals(healthcareProfessional.getUsername())){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        healthcareProfessionalBean.addPatientToHealthcareprofessional(usernamePatient, username);
        return Response.ok(patientstoDTOs(healthcareProfessional.getPatients())).build();
    }

    @PUT
    @Path("/{username}/RemovePatient/{usernamePatient}")
    @RolesAllowed({"HealthcareProfessional"})
    public Response removePatient(@PathParam("username") String username, @PathParam("usernamePatient") String usernamePatient) throws MyEntityNotFoundException {
        Principal principal = securityContext.getUserPrincipal();
        HealthcareProfessional healthcareProfessional = healthcareProfessionalBean.findHealthcareProfessional(username);
        if(!principal.getName().equals(healthcareProfessional.getUsername())){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        boolean hasHealthcareProfessionalAssociated = healthcareProfIsAssociatedToPatient(username, usernamePatient);
        if (!hasHealthcareProfessionalAssociated){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        healthcareProfessionalBean.removePatientFromHealthcareprofessional(usernamePatient, username);
        return Response.ok(patientstoDTOs(healthcareProfessional.getPatients())).build();
    }

    @PUT
    @Path("/{username}/AddPrescription/{codePrescription}")
    @RolesAllowed({"HealthcareProfessional"})
    public Response addPrescription(@PathParam("username") String username, @PathParam("codePrescription") Integer codePrescription) throws MyEntityNotFoundException {
        Principal principal = securityContext.getUserPrincipal();
        HealthcareProfessional healthcareProfessional = healthcareProfessionalBean.findHealthcareProfessional(username);
        if(!principal.getName().equals(healthcareProfessional.getUsername())){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        Prescription prescription = prescriptionBean.findPrescription(codePrescription);
        Patient patient = prescription.getPatient();
        boolean hasHealthcareProfessionalAssociated = healthcareProfIsAssociatedToPatient(username, patient.getUsername());

        if (!hasHealthcareProfessionalAssociated){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        healthcareProfessionalBean.addPrescriptionToHealthcareprofessional(codePrescription, username);
        return Response.ok(patientstoDTOs(healthcareProfessional.getPatients())).build();

    }

    @PUT
    @Path("/{username}/RemovePrescription/{codePrescription}")
    @RolesAllowed({"HealthcareProfessional"})
    public Response removePrescription(@PathParam("username") String username, @PathParam("codePrescription") Integer codePrescription) throws MyEntityNotFoundException {
        Principal principal = securityContext.getUserPrincipal();
        HealthcareProfessional healthcareProfessional = healthcareProfessionalBean.findHealthcareProfessional(username);
        if(!principal.getName().equals(healthcareProfessional.getUsername())){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        Prescription prescription = prescriptionBean.findPrescription(codePrescription);
        Patient patient = prescription.getPatient();
        boolean hasHealthcareProfessionalAssociated = healthcareProfIsAssociatedToPatient(username, patient.getUsername());
        if (!hasHealthcareProfessionalAssociated){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        healthcareProfessionalBean.removePrescriptionFromHealthcareprofessional(codePrescription, username);
        return Response.ok(patientstoDTOs(healthcareProfessional.getPatients())).build();
    }

    private boolean healthcareProfIsAssociatedToPatient(String usernameHealthcareProf, String usernamePatient) throws MyEntityNotFoundException {
        boolean hasHealthcareProfessionalAssociated = false;
        Patient patient  = patientBean.findPatient(usernamePatient);
        for (HealthcareProfessional healthcareProfessional: patient.getHealthcareProfessionals()) {
            if (healthcareProfessional.getUsername().equals(usernameHealthcareProf)){
                hasHealthcareProfessionalAssociated = true;
                break;
            }
        }
        return hasHealthcareProfessionalAssociated;

    }
}
