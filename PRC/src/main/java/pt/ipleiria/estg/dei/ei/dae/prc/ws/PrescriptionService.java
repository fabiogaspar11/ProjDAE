package pt.ipleiria.estg.dei.ei.dae.prc.ws;


import pt.ipleiria.estg.dei.ei.dae.prc.dtos.PrescriptionDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.ejbs.PrescriptionBean;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.HealthcareProfessional;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Patient;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Prescription;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.MyEntityNotFoundException;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
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
    @Context
    private SecurityContext securityContext;

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

    @GET
    @Path("/")
    @RolesAllowed({"HealthcareProfessional"})
    public Response getAllPrescriptionsWS() {

        if(!securityContext.isUserInRole("HealthcareProfessional")){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        Principal principal = securityContext.getUserPrincipal();
        List<Prescription> prescriptionsH = new ArrayList<>();
        for (Prescription prescription: prescriptionBean.getAllPrescriptions()) {
            if (prescription.getHealthcareProfessional().getUsername().equals(principal.getName())){
                prescriptionsH.add(prescription);
            }
        }

        return Response.status(Response.Status.CREATED)
                .entity(toDTOs(prescriptionsH))
                .build();
    }

    @POST
    @Path("/")
    @RolesAllowed({"HealthcareProfessional"})
    public Response createNewPrescription(PrescriptionDTO prescriptionDTO) throws MyEntityExistsException, MyEntityNotFoundException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime emissionDate = LocalDateTime.now();

        if(!securityContext.isUserInRole("HealthcareProfessional")){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

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
    @Path("{code}")
    public Response getPrescriptionDetails(@PathParam("code") long code) throws MyEntityNotFoundException {
        Prescription prescription = prescriptionBean.findPrescription(code);

        if (verifyRoles(prescription)) return Response.status(Response.Status.FORBIDDEN).build();

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
        if(!securityContext.isUserInRole("Patient") && !securityContext.isUserInRole("HealthcareProfessional")){
            return true;
        }

        if(securityContext.isUserInRole("Patient") && !principal.getName().equals(prescription.getPatient().getUsername())){
            return true;
        }
        return securityContext.isUserInRole("HealthcareProfessional") && !principal.getName().equals(prescription.getHealthcareProfessional().getUsername());
    }

}
