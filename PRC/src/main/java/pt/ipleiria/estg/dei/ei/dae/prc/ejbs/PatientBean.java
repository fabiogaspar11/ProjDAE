package pt.ipleiria.estg.dei.ei.dae.prc.ejbs;

import pt.ipleiria.estg.dei.ei.dae.prc.dtos.HealthcareProfessionalDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.dtos.PatientDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.*;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.MyIllegalArgumentException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class PatientBean {

    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    EmailBean emailBean;

    public String create(String name, String email, String birthDate, String contact, long healthNumber) throws MyEntityExistsException, MessagingException {
        String username = "P"+healthNumber;
        String password = generatePassword(); //"12345"; //generatePassword();
        Patient patient = entityManager.find(Patient.class, username);
        if(patient != null) throw new MyEntityExistsException("A patient with the username \'" + username + "\' already exists");
        patient = new Patient(username,name,email,password,birthDate,contact,healthNumber);
        entityManager.persist(patient);
        entityManager.flush();

        emailBean.send(email, "PRC Register - Welcome!", "Thank you for joining our healthcare platform!\n The Credentials for your account are as follows.\nUsername: " + username + "\nPassword: " + password);

        return patient.getUsername();
    }

    public static String generatePassword() {
        String lettersSymbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*_=+-/";
        SecureRandom random = new SecureRandom();
        char[] characters = (lettersSymbols).toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append(characters[random.nextInt(characters.length)]);
        }
        return sb.toString();
    }

    public List<Patient> getAllPatients() {
        return (List<Patient>) entityManager.createNamedQuery("getAllPatients").getResultList();
    }

    public Patient findPatient(String username) throws MyEntityNotFoundException {
        Patient patient = entityManager.find(Patient.class, username);
        if(patient == null) throw new MyEntityNotFoundException("There is no Patient with the username: \'"+username+"\'");
        return patient;
    }

    public void addDiseaseToPatient(String username, long code) throws MyEntityNotFoundException {
        Patient patient = findPatient(username);
        Disease disease = entityManager.find(Disease.class, code);
        if (disease != null) {
            if (!disease.getPatients().contains(patient)){
                patient.addDisease(disease);
                disease.addPatient(patient);
                entityManager.merge(patient);
                entityManager.merge(disease);
            }
        }
        else{
            throw new MyEntityNotFoundException("Disease with code: " + code + " not found.");
        }

    }
    public boolean removeDiseaseFromPatient(String usernamePatient, long code) throws MyEntityNotFoundException {
        Patient patient = findPatient(usernamePatient);
        Disease disease = entityManager.find(Disease.class, code);
        if (disease == null){
            throw new MyEntityNotFoundException("There is no Disease with the code: \'"+code+"\'");
        }
        patient.removeDisease(disease);
        disease.removePatient(patient);
        entityManager.merge(patient);
        entityManager.merge(disease);
        return true;
    }

    public void remove(Patient patient) throws MyEntityNotFoundException, MyConstraintViolationException {
        if(!patient.getPrescriptions().isEmpty()){
            throw new MyConstraintViolationException("Cannot delete Patient - There are Prescriptions associated with this Patient");
        }
        if(!patient.getHealthcareProfessionals().isEmpty()){
            throw new MyConstraintViolationException("Cannot delete Patient - This Patient is associated with Healthcare Professionals");
        }
        if(!patient.getDiseases().isEmpty()){
            throw new MyConstraintViolationException("Cannot delete Patient - This Patient is diagnosed with diseases");
        }
        if(!patient.getBiomedicDataMeasures().isEmpty()){
            throw new MyConstraintViolationException("Cannot delete Patient - There are Biomedic Measures associated with this Patient");
        }
        entityManager.remove(entityManager.merge(patient));
    }

    public void update(Patient patient, PatientDTO patientDTO) {
        entityManager.lock(entityManager.merge(patient), LockModeType.PESSIMISTIC_READ);
        if(patientDTO.getName() != null && !patient.getName().equals(patientDTO.getName())){
            patient.setName(patientDTO.getName());
        }

        if(patientDTO.getEmail() != null && !patient.getEmail().equals(patientDTO.getEmail())){
            patient.setEmail(patientDTO.getEmail());
        }

        if(patientDTO.getBirthDate() != null && !patient.getBirthDate().equals(patientDTO.getBirthDate())){
            patient.setBirthDate(patientDTO.getBirthDate());
        }

        if(patientDTO.getContact() != null && !patient.getContact().equals(patientDTO.getContact())){
            patient.setContact(patientDTO.getContact());
        }

        entityManager.merge(patient);
    }


    public boolean addPrescription(String username, long code) throws MyIllegalArgumentException, MyEntityNotFoundException {
        Patient patient = findPatient(username);
        Prescription prescription = entityManager.find(Prescription.class, code);
        if (prescription == null){
            throw new MyEntityNotFoundException("There is no Prescription with the code: \'"+code+"\'");
        }
        if(prescription.getPatient() != null){
            throw new MyIllegalArgumentException("This prescription is already prescribed to the Patient with the username: \'"+prescription.getPatient().getUsername()+"\'");
        }
        patient.addPrescription(prescription);
        prescription.setPatient(patient);
        entityManager.merge(patient);
        entityManager.merge(prescription);
        return true;
    }

    public boolean removePrescription(String username, long code) throws MyEntityNotFoundException {
        Patient patient = findPatient(username);
        Prescription prescription = entityManager.find(Prescription.class, code);
        if (prescription == null){
            throw new MyEntityNotFoundException("There is no Prescription with the code: \'"+code+"\'");
        }
        prescription.setPatient(null);
        patient.removePrescription(prescription);
        entityManager.merge(patient);
        entityManager.merge(prescription);
        return true;
    }

    public void updatePassword(Patient patient, PatientDTO patientDTO) throws MyIllegalArgumentException {
        if (!patient.getPassword().equals(hashPassword(patientDTO.getPasswordOld()))){
            throw new MyIllegalArgumentException("Password field must be the same as current password");
        }
        if (hashPassword(patientDTO.getPassword()).equals(hashPassword(patientDTO.getPasswordOld()))){
            throw new MyIllegalArgumentException("Old Password and New password cannot be the same");
        }
        patient.setPassword(patientDTO.getPassword());
        entityManager.merge(patient);
    }
    private static String hashPassword(String password) {
        char[] encoded = null;
        try {
            ByteBuffer passwdBuffer =
                    Charset.defaultCharset().encode(CharBuffer.wrap(password));
            byte[] passwdBytes = passwdBuffer.array();
            MessageDigest mdEnc = MessageDigest.getInstance("SHA-256");
            mdEnc.update(passwdBytes, 0, password.toCharArray().length);
            encoded = new BigInteger(1, mdEnc.digest()).toString(16).toCharArray();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new String(encoded);
    }
}
