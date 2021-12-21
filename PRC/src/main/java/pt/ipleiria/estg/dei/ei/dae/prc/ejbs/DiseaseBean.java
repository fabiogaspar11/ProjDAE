package pt.ipleiria.estg.dei.ei.dae.prc.ejbs;

import pt.ipleiria.estg.dei.ei.dae.prc.dtos.DiseaseDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Disease;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.DiseaseType;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Patient;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class DiseaseBean {
    @PersistenceContext
    private EntityManager entityManager;

    public int create(String name, int codeDiseaseType) throws MyEntityExistsException, MyEntityNotFoundException {
        DiseaseType diseaseType = entityManager.find(DiseaseType.class,codeDiseaseType);
        if(diseaseType == null) {
            throw new MyEntityNotFoundException("There is no Disease Type with the code \'" + codeDiseaseType + "\'");
        }
        Disease disease = new Disease(name, diseaseType);
        diseaseType.addDisease(disease);
        entityManager.persist(disease);
        entityManager.merge(diseaseType);
        entityManager.flush();
        return disease.getCode();
    }

    public void delete(int code) throws MyEntityNotFoundException {
        Disease disease = findDisease(code);
        entityManager.remove(entityManager.merge(disease));
    }

    public void update(int code, DiseaseDTO diseaseDTO) throws MyEntityNotFoundException {
        Disease disease = findDisease(code);



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
