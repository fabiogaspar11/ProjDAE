package pt.ipleiria.estg.dei.ei.dae.prc.ejbs;

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

    public void remove(HealthcareProfessional healthcareProfessional) throws MyEntityNotFoundException {
        for (Patient p:
                healthcareProfessional.getPatients()) {
            removePatientFromHealthcareprofessional(p.getUsername(), healthcareProfessional.getUsername());
            healthcareProfessional=entityManager.find(HealthcareProfessional.class,healthcareProfessional.getUsername());
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

}
