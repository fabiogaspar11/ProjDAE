package pt.ipleiria.estg.dei.ei.dae.prc.ws;

import pt.ipleiria.estg.dei.ei.dae.prc.dtos.*;
import pt.ipleiria.estg.dei.ei.dae.prc.ejbs.PatientBean;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.*;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.*;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Path("patients")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class PatientService {
    @EJB
    private PatientBean patientBean;
    @Context
    private SecurityContext securityContext;
    //Patient with everything
    private PatientDTO toDTO(Patient patient) {
        return new PatientDTO(
                patient.getName(),
                patient.getEmail(),
                patient.getPassword(),
                patient.getBirthDate(),
                patient.getContact(),
                patient.getHealthNumber(),
                patient.getPrescriptions(),
                patient.getBiomedicDataMeasures(),
                patient.getHealthcareProfessionals()
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

    private List<PatientDTO> toDTOs(List<Patient> patients) {
        return patients.stream().map(this::toDTO).collect(Collectors.toList());
    }

  private List<PrescriptionDTO> prescriptionToDTOs(List<Prescription>  prescriptions) {
        return prescriptions.stream().map(this::prescriptionToDTO).collect(Collectors.toList());
    }

    private PatientDTO toDTOnoDetails(Patient patient) {
        return new PatientDTO(
                patient.getName(),
                patient.getEmail(),
                patient.getBirthDate(),
                patient.getContact(),
                patient.getHealthNumber()
        );
    }

    private List<PatientDTO> toDTOsNoDetails(List<Patient> patients) {
        return patients.stream().map(this::toDTOnoDetails).collect(Collectors.toList());
    }

    private DiseaseDTO toDTO(Disease disease) {
        return new DiseaseDTO(
                disease.getCode(),
                disease.getName()
        );
    }

    private PrescriptionDTO toDTOPrescription(Prescription prescription) {
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
    private HealthcareProfessionalDTO toDTOHealthCareProfessional(HealthcareProfessional healthcareProfessional){
        return new HealthcareProfessionalDTO(
                healthcareProfessional.getName(),
                healthcareProfessional.getEmail(),
                healthcareProfessional.getContact()
        );
    }
    private List<DiseaseDTO> diseasesToDTOs(List<Disease> diseases) {
        return diseases.stream().map(this::toDTO).collect(Collectors.toList());
    }
    private List<PrescriptionDTO> prescriptionsToDTOs(List<Prescription> prescriptions) {
        return prescriptions.stream().map(this::toDTOPrescription).collect(Collectors.toList());
    }

    private List<HealthcareProfessionalDTO> healthCareProfessionalsToDTOs(List<HealthcareProfessional> healthcareProfessionals){
        return healthcareProfessionals.stream().map(this::toDTOHealthCareProfessional).collect(Collectors.toList());
    }


    private BiomedicDataMeasureDTO biomedicMeasuretoDTO(BiomedicDataMeasure biomedicDataMeasure){
        return new BiomedicDataMeasureDTO(
                biomedicDataMeasure.getCode(),
                biomedicDataMeasure.getValue(),
                biomedicDataMeasure.getDate(),
                biomedicDataMeasure.getHour(),
                biomedicDataMeasure.getPatient().getUsername(),
                biomedicDataMeasure.getBiomedicDataType().getCode()
        );
    }

    private List<BiomedicDataMeasureDTO> biomedicMeasuresToDTOs(List<BiomedicDataMeasure> biomedicDataMeasures) {
        return biomedicDataMeasures.stream().map(this::biomedicMeasuretoDTO).collect(Collectors.toList());
    }


    @GET
    @Path("/")
    @RolesAllowed({"HealthcareProfessional"})
    public List<PatientDTO> getAllPatientsWS() {
        return toDTOsNoDetails(patientBean.getAllPatients());
    }

    @POST
    @Path("/")
    public Response createNewPatient(PatientDTO patientDTO) throws MyEntityExistsException, MyEntityNotFoundException {
        if(!securityContext.isUserInRole("HealthcareProfessional")){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        String username = patientBean.create(
                patientDTO.getName(),
                patientDTO.getEmail(),
                patientDTO.getPassword(),
                patientDTO.getBirthDate(),
                patientDTO.getContact(),
                patientDTO.getHealthNumber()
        );
        Patient patient = patientBean.findPatient(username);
        return Response.status(Response.Status.CREATED)
                .entity(toDTO(patient))
                .build();
    }

    
    @GET
    @Path("/{username}")
    public Response getPatientDetails(@PathParam("username") String username) throws MyEntityNotFoundException {
        Principal principal = securityContext.getUserPrincipal();
        if(securityContext.isUserInRole("Patient") && !principal.getName().equals(username)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        if(!securityContext.isUserInRole("HealthcareProfessional") && !securityContext.isUserInRole("Patient")){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        Patient patient  = patientBean.findPatient(username);
        return Response.status(Response.Status.OK)
                .entity(toDTO(patient))
                .build();
    }

    @DELETE
    @Path("/{username}")
    public Response deletePatient(@PathParam("username") String username) throws MyEntityNotFoundException {
        if(!securityContext.isUserInRole("HealthcareProfessional")){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        Patient patient  = patientBean.findPatient(username);
        patientBean.remove(patient);
        return Response.status(Response.Status.OK)
                .entity(toDTO(patient))
                .build();
    }

    @PUT
    @Path("/{username}")
    public Response updatePatient(@PathParam("username") String username, PatientDTO patientDTO) throws MyEntityNotFoundException {
        Principal principal = securityContext.getUserPrincipal();
        if(securityContext.isUserInRole("Patient") && !principal.getName().equals(username)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        Patient patient  = patientBean.findPatient(username);
        patientBean.update(patient, patientDTO);
        return Response.status(Response.Status.OK)
                .entity(toDTO(patient))
                .build();
    }

    @GET
    @Path("{username}/healthcareProfessionals")
    public Response getPatientHealthCareProfessionals(@PathParam("username") String username) throws MyEntityNotFoundException {
        Principal principal = securityContext.getUserPrincipal();
        if(securityContext.isUserInRole("Patient") && !principal.getName().equals(username)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        if(!securityContext.isUserInRole("HealthcareProfessional") && !securityContext.isUserInRole("Patient")){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        Patient patient = patientBean.findPatient(username);
        return Response.ok(healthCareProfessionalsToDTOs(patient.getHealthcareProfessionals())).build();

    }

    @GET
    @Path("{username}/biomedicMeasures")
    public Response getBiomedicMeasures(@PathParam("username") String username) throws MyEntityNotFoundException {
        Principal principal = securityContext.getUserPrincipal();
        if(securityContext.isUserInRole("Patient") && !principal.getName().equals(username)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        if(!securityContext.isUserInRole("HealthcareProfessional") && !securityContext.isUserInRole("Patient")){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        Patient patient = patientBean.findPatient(username);
        return Response.ok(biomedicMeasuresToDTOs(patient.getBiomedicDataMeasures())).build();
    }

    @GET
    @Path("{username}/diseases")
    public Response getPatientDiseases(@PathParam("username") String username) throws MyEntityNotFoundException {
        Principal principal = securityContext.getUserPrincipal();
        if(securityContext.isUserInRole("Patient") && !principal.getName().equals(username)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        if(!securityContext.isUserInRole("HealthcareProfessional") && !securityContext.isUserInRole("Patient")){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        Patient patient = patientBean.findPatient(username);
        return Response.ok(diseasesToDTOs(patient.getDiseases())).build();
    }

    @POST
    @Path("/{username}/addDisease/{disease}")
    public Response addDisease(@PathParam("username") String username, @PathParam("disease") int code) throws MyEntityNotFoundException {
        if(!securityContext.isUserInRole("HealthcareProfessional")){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        patientBean.addDiseaseToPatient(username, code);
        Patient patient  = patientBean.findPatient(username);
        return Response.ok(diseasesToDTOs(patient.getDiseases())).build();
    }

    @POST
    @Path("/{username}/removeDisease/{disease}")
    public Response removeDisease(@PathParam("username") String username, @PathParam("disease") int code) throws MyEntityNotFoundException {
        if(!securityContext.isUserInRole("HealthcareProfessional")){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        patientBean.removeDiseaseFromPatient(username, code);
        Patient patient  = patientBean.findPatient(username);
        return Response.ok(diseasesToDTOs(patient.getDiseases())).build();
    }

    @GET
    @Path("{username}/prescriptions")
    public Response getPatientPrescriptions(@PathParam("username") String username) throws MyEntityNotFoundException {
        Principal principal = securityContext.getUserPrincipal();
        if(securityContext.isUserInRole("Patient") && !principal.getName().equals(username)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        if(!securityContext.isUserInRole("HealthcareProfessional") && !securityContext.isUserInRole("Patient")){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        Patient patient = patientBean.findPatient(username);
        return Response.ok(prescriptionsToDTOs(patient.getPrescriptions())).build();

    }


    //TODO Duvida 1 - seria necessário estes dos metodos abaixo?
    @POST
    @Path("/{username}/addPrescription/{code}")
    public Response addPrescription(@PathParam("username") String username, @PathParam("code") long code) throws MyEntityNotFoundException, MyIllegalArgumentException {
        if(!securityContext.isUserInRole("HealthcareProfessional")){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        patientBean.addPrescription(username, code);
        Patient patient  = patientBean.findPatient(username);
        return Response.ok(prescriptionToDTOs(patient.getPrescriptions())).build();
    }

    @POST
    @Path("/{username}/removePrescription/{code}")
    public Response removePrescription(@PathParam("username") String username, @PathParam("code") long code) throws MyEntityNotFoundException {
        if(!securityContext.isUserInRole("HealthcareProfessional")){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        patientBean.removePrescription(username, code);
        Patient patient  = patientBean.findPatient(username);
        return Response.ok(prescriptionToDTOs(patient.getPrescriptions())).build();
    }

}
