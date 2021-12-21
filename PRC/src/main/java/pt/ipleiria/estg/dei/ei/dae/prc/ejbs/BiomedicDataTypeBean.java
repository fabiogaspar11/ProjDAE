package pt.ipleiria.estg.dei.ei.dae.prc.ejbs;

import pt.ipleiria.estg.dei.ei.dae.prc.dtos.AdministratorDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.dtos.BiomedicDataTypeDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Administrator;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.BiomedicDataType;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Patient;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class BiomedicDataTypeBean {
    @PersistenceContext
    private EntityManager entityManager;

    public long create(String name,String unitMeasure, float minValue, float maxValue) throws MyEntityExistsException {
        BiomedicDataType biomedicDataType = new BiomedicDataType(name,unitMeasure,minValue,maxValue);
        entityManager.persist(biomedicDataType);
        entityManager.flush();
        return biomedicDataType.getCode();
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


    public void update(BiomedicDataType biomedicDataType, BiomedicDataTypeDTO biomedicDataTypeDTO) throws MyEntityNotFoundException {
        if(biomedicDataTypeDTO.getName() != null && !biomedicDataType.getName().equals(biomedicDataTypeDTO.getName())){
            biomedicDataType.setName(biomedicDataTypeDTO.getName());
        }
        if(biomedicDataTypeDTO.getUnitMeasure() != null && !biomedicDataType.getUnitMeasure().equals(biomedicDataTypeDTO.getUnitMeasure())){
            biomedicDataType.setUnitMeasure(biomedicDataTypeDTO.getUnitMeasure());
        }
        if(biomedicDataTypeDTO.getMinValue() < biomedicDataTypeDTO.getMaxValue())
        {
            if(biomedicDataTypeDTO.getMinValue() < Float.MAX_VALUE && biomedicDataTypeDTO.getMinValue() > Float.MIN_VALUE  && biomedicDataType.getMinValue() != (biomedicDataTypeDTO.getMinValue())){
                biomedicDataType.setMinValue(biomedicDataTypeDTO.getMinValue());
            }
            if(biomedicDataTypeDTO.getMaxValue() < Float.MAX_VALUE && biomedicDataTypeDTO.getMaxValue() > Float.MIN_VALUE && biomedicDataType.getMaxValue() != (biomedicDataTypeDTO.getMaxValue())){
                biomedicDataType.setMaxValue(biomedicDataTypeDTO.getMaxValue());
            }
        }

        entityManager.merge(biomedicDataType);
    }
}
