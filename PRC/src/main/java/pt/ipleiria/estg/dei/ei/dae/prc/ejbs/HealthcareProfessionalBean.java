package pt.ipleiria.estg.dei.ei.dae.prc.ejbs;

import pt.ipleiria.estg.dei.ei.dae.prc.entities.Prescription;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.User;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.*;
import pt.ipleiria.estg.dei.ei.dae.prc.dtos.HealthcareProfessionalDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.HealthcareProfessional;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Patient;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class HealthcareProfessionalBean {
    @PersistenceContext
    private EntityManager entityManager;

    public String create(long healthNumber, String name, String email, String password, String birthDate, String contact) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        if(Long.toString(healthNumber).length() != 9){
            throw new MyConstraintViolationException("Health Number can only have 9 digits");
        }
        if(contact.length() != 9 || !contact.startsWith("9")){
            throw new MyConstraintViolationException("Contact can only have 9 digits and must be according to PT format");
        }
        String username = "H"+healthNumber;
        HealthcareProfessional healthcareProfessional = entityManager.find(HealthcareProfessional.class,username);
        if(healthcareProfessional != null) throw new MyEntityExistsException("A healthcare professional with the username \'" + username + "\' already exists");
        healthcareProfessional = new HealthcareProfessional(username,healthNumber, name, email, password, birthDate, contact);
        entityManager.persist(healthcareProfessional);
        return healthcareProfessional.getUsername();
    }

    public void remove(String username) throws MyEntityNotFoundException, MyConstraintViolationException {
        HealthcareProfessional healthcareProfessional = entityManager.find(HealthcareProfessional.class, username);
        if(!healthcareProfessional.getPatients().isEmpty()){
            throw new MyConstraintViolationException("Cannot delete Healthcare Professional - There are Patients associated with this Healthcare Professional");
        }
        if(!healthcareProfessional.getPrescriptions().isEmpty()){
            throw new MyConstraintViolationException("Cannot delete Healthcare Professional - There are Prescription associated with this Healthcare Professional");
        }
        entityManager.remove(entityManager.merge(healthcareProfessional));
    }

    public List<HealthcareProfessional> getAllHealthcareProfessionals(){
        return (List<HealthcareProfessional>) entityManager.createNamedQuery("getAllHealthcareProfessionals").getResultList();
    }

    public HealthcareProfessional findHealthcareProfessional(String username) throws MyEntityNotFoundException {
        HealthcareProfessional healthcareProfessional = entityManager.find(HealthcareProfessional.class, username);
        if(healthcareProfessional == null) throw new MyEntityNotFoundException("There is no Healthcare professional with the username: \'"+username+"\'");
        return healthcareProfessional;
    }

    public boolean removePrescriptionFromHealthcareprofessional(long code, String usernameHealthcareprofessional) throws MyEntityNotFoundException {
        HealthcareProfessional healthcareProfessional = findHealthcareProfessional(usernameHealthcareprofessional);
        Prescription prescription = entityManager.find(Prescription.class, code);
        if (prescription == null){
            throw new MyEntityNotFoundException("There is no Prescription with the code: \'"+prescription.getCode()+"\'");
        }

        healthcareProfessional.removePrescription(prescription);
        entityManager.merge(healthcareProfessional);
        return true;
    }

    public boolean addPrescriptionToHealthcareprofessional(long code, String usernameHealthcareprofessional) throws MyEntityNotFoundException {
        HealthcareProfessional healthcareProfessional = findHealthcareProfessional(usernameHealthcareprofessional);
        Prescription prescription = entityManager.find(Prescription.class, code);
        if (prescription == null){
            throw new MyEntityNotFoundException("There is no Prescription with the code: \'"+prescription.getCode()+"\'");
        }

        healthcareProfessional.addPrescription(prescription);
        entityManager.merge(healthcareProfessional);
        return true;
    }


    public boolean removePatientFromHealthcareprofessional(String usernamePatient, String usernameHealthcareprofessional) throws MyEntityNotFoundException {
        HealthcareProfessional healthcareProfessional = findHealthcareProfessional(usernameHealthcareprofessional);
        Patient patient = entityManager.find(Patient.class, usernamePatient);
        if (patient == null){
           throw new MyEntityNotFoundException("There is no Patient with the username: \'"+usernamePatient+"\'");
        }
        patient.removeHealthcareProfessional(healthcareProfessional);
        healthcareProfessional.removePatient(patient);
        entityManager.merge(patient);
        entityManager.merge(healthcareProfessional);
        return true;
    }

    public boolean addPatientToHealthcareprofessional(String usernamePatient, String usernameHealthcareprofessional) throws MyEntityNotFoundException {
        HealthcareProfessional healthcareProfessional = findHealthcareProfessional(usernameHealthcareprofessional);
        Patient patient = entityManager.find(Patient.class, usernamePatient);
        if (patient == null){
            throw new MyEntityNotFoundException("There is no Patient with the username: \'"+usernamePatient+"\'");
        }
        patient.addHealthcareProfessional(healthcareProfessional);
        healthcareProfessional.addPatient(patient);
        entityManager.merge(patient);
        entityManager.merge(healthcareProfessional);
        return true;
    }

    public void update(HealthcareProfessional healthcareProfessional, HealthcareProfessionalDTO healthcareProfessionalDTO) {
        entityManager.lock(entityManager.merge(healthcareProfessional), LockModeType.OPTIMISTIC);
        if(healthcareProfessionalDTO.getName() != null && !healthcareProfessional.getName().equals(healthcareProfessionalDTO.getName())){
            healthcareProfessional.setName(healthcareProfessionalDTO.getName());
        }
        if(healthcareProfessionalDTO.getPassword() != null && !healthcareProfessional.getPassword().equals(healthcareProfessionalDTO.getPassword())){
            healthcareProfessional.setPassword(healthcareProfessionalDTO.getPassword());
        }
        if(healthcareProfessionalDTO.getEmail() != null && !healthcareProfessional.getEmail().equals(healthcareProfessionalDTO.getEmail())){
            healthcareProfessional.setEmail(healthcareProfessionalDTO.getEmail());
        }

        if(healthcareProfessionalDTO.getBirthDate() != null && !healthcareProfessional.getBirthDate().equals(healthcareProfessionalDTO.getBirthDate())){
            healthcareProfessional.setBirthDate(healthcareProfessionalDTO.getBirthDate());
        }

        if(healthcareProfessionalDTO.getContact() != null && !healthcareProfessional.getContact().equals(healthcareProfessionalDTO.getContact())){
            if(healthcareProfessionalDTO.getContact().length() == 9 && healthcareProfessionalDTO.getContact().startsWith("9")){
                healthcareProfessional.setContact(healthcareProfessionalDTO.getContact());
            }
        }

        entityManager.merge(healthcareProfessional);
    }

    public void updatePassword(HealthcareProfessional healthcareProfessional, HealthcareProfessionalDTO healthcareProfessionalDTO) throws MyIllegalArgumentException {
        if (!healthcareProfessional.getPassword().equals(hashPassword(healthcareProfessionalDTO.getPasswordOld()))){
            throw new MyIllegalArgumentException("Password field must be the same as current password");
        }
        if (hashPassword(healthcareProfessionalDTO.getPassword()).equals(hashPassword(healthcareProfessionalDTO.getPasswordOld()))){
            throw new MyIllegalArgumentException("Old Password and New password cannot be the same");
        }
        healthcareProfessional.setPassword(healthcareProfessionalDTO.getPassword());
        entityManager.merge(healthcareProfessional);
    }

    public List<Prescription> getHealthcareProfessionalPatientPrescriptions(String healthcareUsername, String patientUsername ) throws MyEntityNotFoundException {
        HealthcareProfessional healthcareProfessional = findHealthcareProfessional(healthcareUsername);
        Patient patient = entityManager.find(Patient.class, patientUsername);
        if(patient == null){
            throw new MyEntityNotFoundException("There is no Patient with the username: \'"+patientUsername+"\'");
        }
        List<Prescription> prescriptionList = new LinkedList<>();
        for (Prescription prescription : patient.getPrescriptions()) {
            if(prescription.getHealthcareProfessional().getUsername().equals(healthcareProfessional.getUsername()))
                prescriptionList.add(prescription);
        }
        return prescriptionList;
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
    public List<Prescription> getPrescriptions(String username) {
        List<Prescription> prescriptions = new LinkedList<>();
        HealthcareProfessional healthcareProfessional = entityManager.find(HealthcareProfessional.class,username);

        for (Patient patient:healthcareProfessional.getPatients()) {
          for(Prescription prescription : patient.getPrescriptions()){
              if(prescription.getHealthcareProfessional().getUsername() == healthcareProfessional.getUsername())
                  prescriptions.add(prescription);
          }
        }
        return prescriptions;
    }
}
