package pt.ipleiria.estg.dei.ei.dae.prc.ejbs;

import pt.ipleiria.estg.dei.ei.dae.prc.dtos.DiseaseTypeDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Administrator;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Disease;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.DiseaseType;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class DiseaseTypeBean {
    @PersistenceContext
    private EntityManager entityManager;

    public int create(String name) throws MyEntityExistsException {
        DiseaseType diseaseType = new DiseaseType(name);
        entityManager.persist(diseaseType);
        entityManager.flush();
        return diseaseType.getCode();
    }
    public List<DiseaseType> getAllDiseaseTypes() {
        return (List<DiseaseType>) entityManager.createNamedQuery("getAllDiseaseTypes").getResultList();
    }

    public DiseaseType findDiseaseType(int code) throws MyEntityNotFoundException {
        DiseaseType diseaseType = entityManager.find(DiseaseType.class, code);
        if (diseaseType ==null) throw new MyEntityNotFoundException("There is no Disease Type with code: " + code);
        return diseaseType;
    }
    public void update(int code, DiseaseTypeDTO diseaseTypeDTO) throws MyEntityNotFoundException {
        DiseaseType diseaseType =  findDiseaseType(code);
        if(diseaseTypeDTO.getName() != null && !diseaseType.getName().equals(diseaseTypeDTO.getName())){
            diseaseType.setName(diseaseTypeDTO.getName());
        }
        entityManager.merge(diseaseType);
    }

    public void delete(int code) throws MyEntityNotFoundException {
        DiseaseType diseaseType =  findDiseaseType(code);
        entityManager.remove(diseaseType);
    }
}
