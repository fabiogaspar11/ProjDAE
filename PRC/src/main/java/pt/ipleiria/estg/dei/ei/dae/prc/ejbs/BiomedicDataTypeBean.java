package pt.ipleiria.estg.dei.ei.dae.prc.ejbs;

import pt.ipleiria.estg.dei.ei.dae.prc.dtos.BiomedicDataTypeDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.BiomedicDataType;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.MyIllegalArgumentException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.RollbackException;
import java.util.List;

@Stateless
public class BiomedicDataTypeBean {
    @PersistenceContext
    private EntityManager entityManager;

    public long create(String name,String unitMeasure, float normalMinValue, float normalMaxValue, float minValue, float maxValue,float genderValuedifferentiation,float ageValuedifferentiation) throws MyEntityExistsException, MyIllegalArgumentException {
        if(normalMinValue < minValue){
            throw new MyIllegalArgumentException("Datatype normal minimum value should be bigger than the minimum datatype value");
        }
        if(normalMaxValue > maxValue){
            throw new MyIllegalArgumentException("Datatype normal maximum value should be bigger than the maximum datatype value");
        }

        BiomedicDataType biomedicDataType = new BiomedicDataType(name,unitMeasure,normalMinValue,normalMaxValue,minValue,maxValue,genderValuedifferentiation,ageValuedifferentiation);
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

    public void delete(long code) throws MyEntityNotFoundException, MyConstraintViolationException {
        BiomedicDataType biomedicDataType = findBiomedicDataType(code);
        try{
            entityManager.remove(biomedicDataType);
        }catch(RollbackException e){
            throw new MyConstraintViolationException("Cannot delete Biomedic Type - There are Biomedic Measures associated with this Biomedic Type");
        }
    }

    public void update(BiomedicDataType biomedicDataType, BiomedicDataTypeDTO biomedicDataTypeDTO) throws MyEntityNotFoundException {
        entityManager.lock(entityManager.merge(biomedicDataType), LockModeType.PESSIMISTIC_WRITE);
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
        if(biomedicDataTypeDTO.getNormalMinValue() < biomedicDataTypeDTO.getNormalMaxValue())
        {
            if(biomedicDataTypeDTO.getNormalMinValue() < Float.MAX_VALUE && biomedicDataTypeDTO.getNormalMinValue() > Float.MIN_VALUE  && biomedicDataType.getNormalMinValue() != (biomedicDataTypeDTO.getNormalMinValue())){
                biomedicDataType.setNormalMinValue(biomedicDataTypeDTO.getNormalMinValue());
            }
            if(biomedicDataTypeDTO.getNormalMaxValue() < Float.MAX_VALUE && biomedicDataTypeDTO.getNormalMaxValue() > Float.MIN_VALUE && biomedicDataType.getNormalMaxValue() != (biomedicDataTypeDTO.getNormalMaxValue())){
                biomedicDataType.setNormalMaxValue(biomedicDataTypeDTO.getNormalMaxValue());
            }
        }
        if(biomedicDataType.getAgeValuedifferentiation() != (biomedicDataTypeDTO.getAgeValuedifferentiation())){
            biomedicDataType.setAgeValuedifferentiation(biomedicDataTypeDTO.getAgeValuedifferentiation());
        }
        if(biomedicDataType.getGenderValuedifferentiation() != (biomedicDataTypeDTO.getGenderValuedifferentiation())){
            biomedicDataType.setGenderValuedifferentiation(biomedicDataTypeDTO.getGenderValuedifferentiation());
        }

        entityManager.merge(biomedicDataType);
    }
}
