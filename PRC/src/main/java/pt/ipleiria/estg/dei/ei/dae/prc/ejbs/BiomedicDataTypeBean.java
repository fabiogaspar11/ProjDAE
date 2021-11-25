package pt.ipleiria.estg.dei.ei.dae.prc.ejbs;

import pt.ipleiria.estg.dei.ei.dae.prc.entities.Administrator;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.BiomedicDataType;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Patient;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class BiomedicDataTypeBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(long code, String name, String unitMeasure, float minValue, float maxValue){
        BiomedicDataType biomedicDataType = new BiomedicDataType(code,name,unitMeasure,minValue,maxValue);
        entityManager.persist(biomedicDataType);
    }

    public List<BiomedicDataType> getAllBiomedicDataType() {
        return (List<BiomedicDataType>) entityManager.createNamedQuery("getAllBiomedicDataType").getResultList();
    }

    public BiomedicDataType findBiomedicDataType(long code) throws MyEntityNotFoundException {
        BiomedicDataType biomedicDataType = entityManager.find(BiomedicDataType.class, code);
        if (biomedicDataType != null){
            return biomedicDataType;
        }
        else{
            throw new MyEntityNotFoundException("Biomedic data type with code: " + code + " not found.");
        }
    }

    public void delete(long code) throws MyEntityNotFoundException {
        BiomedicDataType biomedicDataType = findBiomedicDataType(code);
        entityManager.remove(biomedicDataType);
    }
}
