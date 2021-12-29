package pt.ipleiria.estg.dei.ei.dae.prc.ejbs;

import pt.ipleiria.estg.dei.ei.dae.prc.dtos.DiseaseDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Disease;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Patient;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class DiseaseBean {
    @PersistenceContext
    private EntityManager entityManager;

    public long create(String name) throws MyEntityExistsException, MyEntityNotFoundException {

        Disease disease = new Disease(name);
        entityManager.persist(disease);
        entityManager.flush();
        return disease.getCode();
    }

    public void delete(int code) throws MyEntityNotFoundException {
        Disease disease = findDisease(code);
        entityManager.remove(entityManager.merge(disease));
    }

    public void update(Disease disease, DiseaseDTO diseaseDTO) throws MyEntityNotFoundException {
        entityManager.lock(disease, LockModeType.OPTIMISTIC);
        if(diseaseDTO.getName() != null && !disease.getName().equals(diseaseDTO.getName())){
            disease.setName(diseaseDTO.getName());
        }
        entityManager.merge(disease);
    }

    public List<Disease> getAllDiseases() {
        return (List<Disease>) entityManager.createNamedQuery("getAllDiseases").getResultList();
    }

    public Disease findDisease(long code) throws MyEntityNotFoundException {
        Disease disease = entityManager.find(Disease.class, code);
        if (disease ==null) throw new MyEntityNotFoundException("There is no Disease with code: " + code);
        return disease;
    }

    public void addDiseaseToPatient(long code, String username) throws MyEntityNotFoundException {
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

    public void removeDiseaseFromPatient(long code, String username) throws MyEntityNotFoundException {
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
