package pt.ipleiria.estg.dei.ei.dae.prc.ejbs;

import pt.ipleiria.estg.dei.ei.dae.prc.entities.BiomedicDataType;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Patient;

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

    public void findBiomedicDataType(long code){
        entityManager.find(BiomedicDataType.class, code);
    }
}
