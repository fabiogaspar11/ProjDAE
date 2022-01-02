package pt.ipleiria.estg.dei.ei.dae.prc.ws;

import pt.ipleiria.estg.dei.ei.dae.prc.dtos.*;
import pt.ipleiria.estg.dei.ei.dae.prc.ejbs.HealthcareProfessionalBean;
import pt.ipleiria.estg.dei.ei.dae.prc.ejbs.PatientBean;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.*;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.*;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.mail.MessagingException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Path("patients")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class PatientService {
    @EJB
    private PatientBean patientBean;
    @EJB
    private HealthcareProfessionalBean healthcareProfessionalBean;
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

    private PatientDTO toDTOBasic(Patient patient) {
        return new PatientDTO(
                patient.getName(),
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
                prescription.getHealthcareProfessional().getName()
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
    public List<PatientDTO> getAllPatientsWS() throws MyEntityNotFoundException {

        Principal principal = securityContext.getUserPrincipal();
        List<Patient> patients = patientBean.getAllPatients();
        List<PatientDTO> patientDTOS = new LinkedList<>();
        PatientDTO patientDTO;
        for (Patient p:patients) {
            boolean hasHealthcareProfessionalAssociated = healthcareProfIsAssociatedToPatient(principal.getName(), p.getUsername());
            if(hasHealthcareProfessionalAssociated){
                patientDTO = toDTOnoDetails(p);
            }else{
                patientDTO = toDTOBasic(p);
            }
            patientDTOS.add(patientDTO);
        }
        return patientDTOS;
    }

    @POST
    @Path("/")
    @RolesAllowed({"HealthcareProfessional"})
    public Response createNewPatient(PatientDTO patientDTO) throws MyEntityExistsException, MyEntityNotFoundException, MessagingException {
        String username = patientBean.create(
                patientDTO.getName(),
                patientDTO.getEmail(),
                patientDTO.getBirthDate(),
                patientDTO.getContact(),
                patientDTO.getHealthNumber()
        );
         Patient patient = patientBean.findPatient(username);
        return Response.status(Response.Status.CREATED)
                .entity(toDTOnoDetails(patient))
                .build();
    }

    
    @GET
    @Path("/{username}")
    @RolesAllowed({"HealthcareProfessional","Patient"})
    public Response getPatientDetails(@PathParam("username") String username) throws MyEntityNotFoundException {
        Principal principal = securityContext.getUserPrincipal();

        if(securityContext.isUserInRole("Patient") && !principal.getName().equals(username)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        Patient patient  = patientBean.findPatient(username);
        if(securityContext.isUserInRole("HealthcareProfessional")){
            boolean hasHealthcareProfessionalAssociated = healthcareProfIsAssociatedToPatient(principal.getName(), username);
            if(hasHealthcareProfessionalAssociated){
                return Response.status(Response.Status.OK)
                        .entity(toDTOnoDetails(patient))
                        .build();
            }else{
                return Response.status(Response.Status.OK)
                        .entity(toDTOBasic(patient))
                        .build();
            }
        }

        return Response.status(Response.Status.OK)
                .entity(toDTOnoDetails(patient))
                .build();
    }

    @DELETE
    @Path("/{username}")
    @RolesAllowed({"HealthcareProfessional"})
    public Response deletePatient(@PathParam("username") String username) throws MyEntityNotFoundException {
        Principal principal = securityContext.getUserPrincipal();
        Patient patient  = patientBean.findPatient(username);
        boolean hasHealthcareProfessionalAssociated = healthcareProfIsAssociatedToPatient(principal.getName(), username);
        if (!hasHealthcareProfessionalAssociated){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        patientBean.remove(patient);
        return Response.status(Response.Status.OK)
                .entity(toDTOnoDetails(patient))
                .build();
    }

    @PUT
    @Path("/{username}")
    @RolesAllowed({"HealthcareProfessional","Patient"})
    public Response updatePatient(@PathParam("username") String username, PatientDTO patientDTO) throws MyEntityNotFoundException {

        Principal principal = securityContext.getUserPrincipal();

        if(securityContext.isUserInRole("Patient") && !principal.getName().equals(username)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        if(securityContext.isUserInRole("HealthcareProfessional")){
            boolean hasHealthcareProfessionalAssociated = healthcareProfIsAssociatedToPatient(principal.getName(), username);
            if (!hasHealthcareProfessionalAssociated){
                return Response.status(Response.Status.FORBIDDEN).build();
            }
        }
        Patient patient  = patientBean.findPatient(username);
        patientBean.update(patient, patientDTO);
        return Response.status(Response.Status.OK)
                .entity(toDTOnoDetails(patient))
                .build();
    }

    @GET
    @Path("{username}/healthcareProfessionals")
    @RolesAllowed({"HealthcareProfessional","Patient"})
    public Response getPatientHealthCareProfessionals(@PathParam("username") String username) throws MyEntityNotFoundException {

        Principal principal = securityContext.getUserPrincipal();
        if(securityContext.isUserInRole("Patient") && !principal.getName().equals(username)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        Patient patient = patientBean.findPatient(username);
        return Response.ok(healthCareProfessionalsToDTOs(patient.getHealthcareProfessionals())).build();

    }

    @GET
    @Path("{username}/biomedicMeasures")
    @RolesAllowed({"HealthcareProfessional","Patient"})
    public Response getBiomedicMeasures(@PathParam("username") String username) throws MyEntityNotFoundException {

        Principal principal = securityContext.getUserPrincipal();
        if(securityContext.isUserInRole("Patient") && !principal.getName().equals(username)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        Patient patient = patientBean.findPatient(username);
        return Response.ok(biomedicMeasuresToDTOs(patient.getBiomedicDataMeasures())).build();
    }

    @GET
    @Path("{username}/diseases")
    @RolesAllowed({"HealthcareProfessional","Patient"})
    public Response getPatientDiseases(@PathParam("username") String username) throws MyEntityNotFoundException {

        Principal principal = securityContext.getUserPrincipal();

        if(securityContext.isUserInRole("Patient") && !principal.getName().equals(username)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        Patient patient = patientBean.findPatient(username);
        if(securityContext.isUserInRole("HealthcareProfessional")){
            boolean hasHealthcareProfessionalAssociated = healthcareProfIsAssociatedToPatient(principal.getName(), username);
            if (!hasHealthcareProfessionalAssociated){
                return Response.status(Response.Status.FORBIDDEN).build();
            }
        }

        return Response.ok(diseasesToDTOs(patient.getDiseases())).build();
    }

    @PUT
    @Path("/{username}/addDisease/{disease}")
    @RolesAllowed({"HealthcareProfessional"})
    public Response addDisease(@PathParam("username") String username, @PathParam("disease") int code) throws MyEntityNotFoundException {
        Principal principal = securityContext.getUserPrincipal();
        boolean hasHealthcareProfessionalAssociated = healthcareProfIsAssociatedToPatient(principal.getName(), username);

        if (!hasHealthcareProfessionalAssociated){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        patientBean.addDiseaseToPatient(username, code);
        Patient patient  = patientBean.findPatient(username);
        return Response.ok(diseasesToDTOs(patient.getDiseases())).build();
    }

    @PUT
    @Path("/{username}/removeDisease/{disease}")
    @RolesAllowed({"HealthcareProfessional"})
    public Response removeDisease(@PathParam("username") String username, @PathParam("disease") int code) throws MyEntityNotFoundException {
        Principal principal = securityContext.getUserPrincipal();

        boolean hasHealthcareProfessionalAssociated = healthcareProfIsAssociatedToPatient(principal.getName(), username);
        if (!hasHealthcareProfessionalAssociated){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        patientBean.removeDiseaseFromPatient(username, code);
        Patient patient  = patientBean.findPatient(username);
        return Response.ok(diseasesToDTOs(patient.getDiseases())).build();
    }

    @GET
    @Path("{username}/prescriptions")
    @RolesAllowed({"HealthcareProfessional","Patient"})
    public Response getPatientPrescriptions(@PathParam("username") String username) throws MyEntityNotFoundException {

        Principal principal = securityContext.getUserPrincipal();
        if(securityContext.isUserInRole("Patient") && !principal.getName().equals(username)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        Patient patient = patientBean.findPatient(username);

        if(securityContext.isUserInRole("HealthcareProfessional")){
            boolean hasHealthcareProfessionalAssociated = healthcareProfIsAssociatedToPatient(principal.getName(), username);
            if (!hasHealthcareProfessionalAssociated){
                return Response.status(Response.Status.FORBIDDEN).build();
            }
                return Response.ok(prescriptionsToDTOs(healthcareProfessionalBean.getHealthcareProfessionalPatientPrescriptions(principal.getName(),username))).build();
        }

        return Response.ok(prescriptionsToDTOs(patient.getPrescriptions())).build();

    }

    @PUT
    @Path("/{username}/addPrescription/{code}")
    @RolesAllowed({"HealthcareProfessional"})
    public Response addPrescription(@PathParam("username") String username, @PathParam("code") long code) throws MyEntityNotFoundException, MyIllegalArgumentException {
        Principal principal = securityContext.getUserPrincipal();
        boolean hasHealthcareProfessionalAssociated = healthcareProfIsAssociatedToPatient(principal.getName(), username);
        if (!hasHealthcareProfessionalAssociated){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        patientBean.addPrescription(username, code);
        Patient patient  = patientBean.findPatient(username);
        return Response.ok(prescriptionToDTOs(patient.getPrescriptions())).build();
    }

    @PUT
    @Path("/{username}/removePrescription/{code}")
    @RolesAllowed({"HealthcareProfessional"})
    public Response removePrescription(@PathParam("username") String username, @PathParam("code") long code) throws MyEntityNotFoundException {
        Principal principal = securityContext.getUserPrincipal();
        boolean hasHealthcareProfessionalAssociated = healthcareProfIsAssociatedToPatient(principal.getName(), username);
        if (!hasHealthcareProfessionalAssociated){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        patientBean.removePrescription(username, code);
        Patient patient  = patientBean.findPatient(username);
        return Response.ok(prescriptionToDTOs(patient.getPrescriptions())).build();
    }

    @PUT
    @Path("/{username}/password")
    @RolesAllowed({"Patient"})
    public Response updatePasswordPatient(@PathParam("username") String username, PatientDTO patientDTO) throws MyEntityNotFoundException, MyIllegalArgumentException {
        Principal principal = securityContext.getUserPrincipal();
        if(!principal.getName().equals(username)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        Patient patient = patientBean.findPatient(username);
        patientBean.updatePassword(patient, patientDTO);
        return Response.status(Response.Status.OK)
                .entity(toDTOnoDetails(patient))
                .build();
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
