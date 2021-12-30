package pt.ipleiria.estg.dei.ei.dae.prc.ejbs;

import pt.ipleiria.estg.dei.ei.dae.prc.entities.Prescription;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.*;
import pt.ipleiria.estg.dei.ei.dae.prc.dtos.HealthcareProfessionalDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.HealthcareProfessional;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Patient;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class HealthcareProfessionalBean {
    @PersistenceContext
    private EntityManager entityManager;

    public String create(long healthNumber, String name, String email, String password, String birthDate, String contact, String type) throws MyEntityExistsException, MyEntityNotFoundException {
        String username = "H"+healthNumber;
        HealthcareProfessional healthcareProfessional = entityManager.find(HealthcareProfessional.class,username);
        if(healthcareProfessional != null) throw new MyEntityExistsException("A healthcare professional with the username \'" + username + "\' already exists");
        healthcareProfessional = new HealthcareProfessional(username,healthNumber, name, email, password, birthDate, contact, type);
        entityManager.persist(healthcareProfessional);
        return healthcareProfessional.getUsername();
    }

    public void remove(String username) throws MyEntityNotFoundException {
        HealthcareProfessional healthcareProfessional = entityManager.find(HealthcareProfessional.class, username);
        for (Patient p: healthcareProfessional.getPatients()) {
            removePatientFromHealthcareprofessional(p.getUsername(), healthcareProfessional.getUsername());
        }
        for (Prescription p: healthcareProfessional.getPrescriptions()) {
            removePrescriptionFromHealthcareprofessional(p.getCode(), healthcareProfessional.getUsername());
            entityManager.remove(entityManager.merge(p));
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

    public boolean addPrescriptionFromHealthcareprofessional(long code, String usernameHealthcareprofessional) throws MyEntityNotFoundException {
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

    public boolean addPatientFromHealthcareprofessional(String usernamePatient, String usernameHealthcareprofessional) throws MyEntityNotFoundException {
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
            healthcareProfessional.setContact(healthcareProfessionalDTO.getContact());
        }

        if(healthcareProfessionalDTO.getType() != null && !healthcareProfessional.getType().equals(healthcareProfessionalDTO.getType())){
            healthcareProfessional.setType(healthcareProfessionalDTO.getType());
        }
        entityManager.merge(healthcareProfessional);
    }

    public void updatePassword(HealthcareProfessional healthcareProfessional, HealthcareProfessionalDTO healthcareProfessionalDTO) throws MyIllegalArgumentException {
        if (!healthcareProfessional.getPassword().equals(healthcareProfessionalDTO.getPasswordOld())){
            throw new MyIllegalArgumentException("Password field must be the same as current password");
        }
        if (healthcareProfessionalDTO.getPassword().equals(healthcareProfessionalDTO.getPasswordOld())){
            throw new MyIllegalArgumentException("PasswordOld and passwordNew cannot be the same");
        }

        healthcareProfessional.setPassword(healthcareProfessionalDTO.getPassword());
        entityManager.merge(healthcareProfessional);
    }

}
