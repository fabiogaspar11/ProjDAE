package pt.ipleiria.estg.dei.ei.dae.prc.ws;


import pt.ipleiria.estg.dei.ei.dae.prc.dtos.PrescriptionDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.ejbs.PrescriptionBean;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Prescription;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("prescriptions")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class PrescriptionService {
    @EJB
    private PrescriptionBean prescriptionBean;


    private PrescriptionDTO toDTO(Prescription prescription) {
        return new PrescriptionDTO(
                prescription.getCode(),
                prescription.getTitle(),
                prescription.getObservations(),
                prescription.getEmissionDate(),
                prescription.getExpireDate(),
                prescription.getPatient().getUsername()
        );
    }

    private List<PrescriptionDTO> toDTOs(List<Prescription>  prescriptions) {
        return prescriptions.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<PrescriptionDTO> getAllPrescriptionsWS() {
        return toDTOs(prescriptionBean.getAllPrescriptions());
    }

    @POST
    @Path("/")
    public Response createNewPrescription(PrescriptionDTO prescriptionDTO) throws MyEntityExistsException, MyEntityNotFoundException {
        prescriptionBean.create(
                prescriptionDTO.getCode(),
                prescriptionDTO.getTitle(),
                prescriptionDTO.getObservations(),
                prescriptionDTO.getEmissionDate(),
                prescriptionDTO.getExpireDate(),
                prescriptionDTO.getUsernamePatient());
        Prescription prescription = prescriptionBean.findPrescription(prescriptionDTO.getCode());
        return Response.status(Response.Status.CREATED)
                .entity(toDTO(prescription))
                .build();
    }

    @GET
    @Path("{code}")
    public Response getPrescriptionDetails(@PathParam("code") long code) throws MyEntityNotFoundException {
        Prescription prescription = prescriptionBean.findPrescription(code);
        return Response.status(Response.Status.OK)
                .entity(toDTO(prescription))
                .build();
    }

    @DELETE
    @Path("/{code}")
    public Response deletePrescription(@PathParam("code") long code) throws MyEntityNotFoundException {
        Prescription prescription = prescriptionBean.findPrescription(code);
        prescriptionBean.remove(prescription);
        return Response.status(Response.Status.OK)
                .entity(toDTO(prescription))
                .build();
    }

    @PUT
    @Path("/{code}")
    public Response updatePrescription(@PathParam("code") long code, PrescriptionDTO prescriptionDTO) throws MyEntityNotFoundException {
        Prescription prescription = prescriptionBean.findPrescription(code);
        prescriptionBean.update(prescription, prescriptionDTO);
        return Response.status(Response.Status.OK)
                .entity(toDTO(prescription))
                .build();
    }

}
