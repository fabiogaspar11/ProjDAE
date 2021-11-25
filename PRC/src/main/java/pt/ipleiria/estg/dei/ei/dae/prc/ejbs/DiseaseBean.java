package pt.ipleiria.estg.dei.ei.dae.prc.ejbs;

import pt.ipleiria.estg.dei.ei.dae.prc.entities.Administrator;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Disease;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.HealthcareProfessional;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Patient;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class    DiseaseBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(int code, String name, String type) throws MyEntityExistsException {
        Disease diseaseExists = entityManager.find(Disease.class,code);
        if (diseaseExists == null){
            Disease disease = new Disease(code, name, type);
            entityManager.persist(disease);
        }
        else{
            throw new MyEntityExistsException("Disease with code: " + code + "already exists");
        }
    }

    public void delete(int code) throws MyEntityNotFoundException {
        Disease disease = findDisease(code);
        entityManager.remove(entityManager.merge(disease));
    }

    public void update(int code, String name, String type) throws MyEntityNotFoundException {
        Disease disease = findDisease(code);
        disease.setCode(code);
        disease.setName(name);
        disease.setType(type);
        entityManager.merge(disease);
    }

    public List<Disease> getAllDiseases() {
        return (List<Disease>) entityManager.createNamedQuery("getAllDiseases").getResultList();
    }

    public Disease findDisease(int code) throws MyEntityNotFoundException {
        Disease disease = entityManager.find(Disease.class, code);
        if (disease ==null) throw new MyEntityNotFoundException("There is no Disease with code: " + code);
        return disease;
    }

    public void addDiseaseToPatient(int code, String username) throws MyEntityNotFoundException {
        Disease disease = findDisease(code);
        Patient patient = entityManager.find(Patient.class, username);
        if (patient != null) {
            if (!patient.getDiseases().contains(disease)){
                disease.addPatient(patient);
                patient.addDisease(disease);
                entityManager.merge(disease);
                entityManager.merge(patient);
            }
        }
    }

    public void removeDiseaseFromPatient(int code, String username) throws MyEntityNotFoundException {
        Disease disease = findDisease(code);
        Patient patient = entityManager.find(Patient.class, username);
        if (patient != null) {
            if (patient.getDiseases().contains(disease)){
                disease.removePatient(patient);
                patient.removeDisease(disease);
                entityManager.merge(disease);
                entityManager.merge(patient);
            }
        }
        else{
            throw new MyEntityNotFoundException("Patient with username: " + username + " not found.");
        }
    }
}
