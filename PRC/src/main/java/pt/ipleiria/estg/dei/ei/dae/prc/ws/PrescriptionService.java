package pt.ipleiria.estg.dei.ei.dae.prc.ws;


import pt.ipleiria.estg.dei.ei.dae.prc.dtos.PrescriptionDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.ejbs.HealthcareProfessionalBean;
import pt.ipleiria.estg.dei.ei.dae.prc.ejbs.PatientBean;
import pt.ipleiria.estg.dei.ei.dae.prc.ejbs.PrescriptionBean;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.HealthcareProfessional;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Patient;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Prescription;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.MyEntityNotFoundException;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.mail.MessagingException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("prescriptions")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class PrescriptionService {
    @EJB
    private PrescriptionBean prescriptionBean;

    @EJB
    private HealthcareProfessionalBean healthcareProfessionalBean;
    @Context
    private SecurityContext securityContext;
    @EJB
    private PatientBean patientBean;
    private PrescriptionDTO toDTO(Prescription prescription) {
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

    private List<PrescriptionDTO> toDTOs(List<Prescription>  prescriptions) {
        return prescriptions.stream().map(this::toDTO).collect(Collectors.toList());
    }


    @POST
    @Path("/")
    @RolesAllowed({"HealthcareProfessional"})
    public Response createNewPrescription(PrescriptionDTO prescriptionDTO) throws MyEntityExistsException, MyEntityNotFoundException, MessagingException {
        Principal principal = securityContext.getUserPrincipal();
        boolean hasHealthcareProfessionalAssociated = healthcareProfIsAssociatedToPatient(principal.getName(), prescriptionDTO.getUsernamePatient());
        if (!hasHealthcareProfessionalAssociated){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime emissionDate = LocalDateTime.now();

        long code = prescriptionBean.create(
                prescriptionDTO.getTitle(),
                prescriptionDTO.getObservations(),
                prescriptionDTO.getIsPharmacological(),
                prescriptionDTO.getTreatmentInfo(),
                dtf.format(emissionDate),
                prescriptionDTO.getExpireDate(),
                prescriptionDTO.getUsernamePatient(),
                prescriptionDTO.getUsernameHealthcareProfessional());
        Prescription prescription = prescriptionBean.findPrescription(code);
        return Response.status(Response.Status.CREATED)
                .entity(toDTO(prescription))
                .build();
    }

    @GET
    @Path("/{code}")
    @RolesAllowed({"HealthcareProfessional","Patient"})
    public Response getPrescriptionDetails(@PathParam("code") long code) throws MyEntityNotFoundException {
        Prescription prescription = prescriptionBean.findPrescription(code);
        if (!verifyCanSeePrescription(prescription)) return Response.status(Response.Status.FORBIDDEN).build();

        return Response.status(Response.Status.OK)
                .entity(toDTO(prescription))
                .build();
    }

    @DELETE
    @Path("/{code}")
    @RolesAllowed({"HealthcareProfessional"})
    public Response deletePrescription(@PathParam("code") long code) throws MyEntityNotFoundException {
        Prescription prescription = prescriptionBean.findPrescription(code);
        if (verifyRoles(prescription)) return Response.status(Response.Status.FORBIDDEN).build();

        prescriptionBean.remove(prescription);
        return Response.status(Response.Status.OK)
                .entity(toDTO(prescription))
                .build();
    }


    @PUT
    @Path("/{code}")
    @RolesAllowed({"HealthcareProfessional"})
    public Response updatePrescription(@PathParam("code") long code, PrescriptionDTO prescriptionDTO) throws MyEntityNotFoundException {
        Prescription prescription = prescriptionBean.findPrescription(code);
        if (verifyRoles(prescription)) return Response.status(Response.Status.FORBIDDEN).build();

        prescriptionBean.update(prescription, prescriptionDTO);
        return Response.status(Response.Status.OK)
                .entity(toDTO(prescription))
                .build();
    }

    private boolean verifyRoles(Prescription prescription) {
        Principal principal = securityContext.getUserPrincipal();

        if(securityContext.isUserInRole("Patient") && !principal.getName().equals(prescription.getPatient().getUsername())){
            return true;
        }
        return securityContext.isUserInRole("HealthcareProfessional") && !principal.getName().equals(prescription.getHealthcareProfessional().getUsername());
    }

    private boolean verifyCanSeePrescription(Prescription prescription) throws MyEntityNotFoundException {
        Principal principal = securityContext.getUserPrincipal();

        if(securityContext.isUserInRole("Patient") && principal.getName().equals(prescription.getPatient().getUsername())){
            return true;
        }
        if(securityContext.isUserInRole("HealthcareProfessional")){
            HealthcareProfessional healthcareProfessional = healthcareProfessionalBean.findHealthcareProfessional(principal.getName());
          String usernamePatient =  prescription.getPatient().getUsername();
            for (Patient p:healthcareProfessional.getPatients()) {
                if(p.getUsername().equals(usernamePatient)){
                    return true;
                }
            }
        }
        return false;
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
