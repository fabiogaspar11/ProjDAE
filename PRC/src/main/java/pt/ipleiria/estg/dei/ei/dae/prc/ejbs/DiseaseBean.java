package pt.ipleiria.estg.dei.ei.dae.prc.ejbs;

import pt.ipleiria.estg.dei.ei.dae.prc.entities.Administrator;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Disease;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.HealthcareProfessional;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class DiseaseBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(int code, String name, String type) throws MyEntityExistsException {
        Disease diseaseExists = findDisease(code);
        if (diseaseExists == null){
            Disease disease = new Disease(code, name, type);
            entityManager.persist(disease);
        }
        else{
            throw new MyEntityExistsException("Disease with code: " + code + "already exists");
        }
    }

    public void delete(int code)  throws MyEntityNotFoundException {
        Disease disease = findDisease(code);
        if (disease != null) {
            entityManager.remove(disease);
        }
        else{
            throw new MyEntityNotFoundException("Disease with code: " + code + " not found.");
        }
    }

    public void update(int code, String name, String type)  throws MyEntityNotFoundException {
        Disease disease = findDisease(code);
        if (disease != null){
            disease.setCode(code);
            disease.setName(name);
            disease.setType(type);
        }
        else{
            throw new MyEntityNotFoundException("Disease with code: " + code + " not found.");
        }
    }

    public List<Disease> getAllDiseases() {
        return (List<Disease>) entityManager.createNamedQuery("getAllDiseases").getResultList();
    }

    public Disease findDisease(int code){
        return entityManager.find(Disease.class, code);
    }
}
