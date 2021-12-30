package pt.ipleiria.estg.dei.ei.dae.prc.ejbs;

import pt.ipleiria.estg.dei.ei.dae.prc.dtos.BiomedicDataMeasureDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.*;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class BiomedicDataMeasureBean {

    @PersistenceContext
    private EntityManager entityManager;

    public long create(double value, String date, String hour, String usernamePatient, long biomedicDataTypeCode) throws MyEntityExistsException, MyEntityNotFoundException {
        Patient patient = entityManager.find(Patient.class,usernamePatient);
        if(patient == null) {
            throw new MyEntityNotFoundException("There is no Patient with the username \'" + usernamePatient + "\'");
        }
        BiomedicDataType biomedicDataType = entityManager.find(BiomedicDataType.class,biomedicDataTypeCode);
        if(biomedicDataType == null) {
            throw new MyEntityNotFoundException("There is no Biomedic Data Type with the code \'" + biomedicDataTypeCode + "\'");
        }
        BiomedicDataMeasure biomedicDataMeasure = new BiomedicDataMeasure(value, date, hour, patient, biomedicDataType);
        entityManager.persist(biomedicDataMeasure);
        entityManager.flush();
        patient.addBiomedicDataMeasure(biomedicDataMeasure);
        entityManager.merge(patient);

        return biomedicDataMeasure.getCode();
    }

    public List<BiomedicDataMeasure> getAllBiomedicDataMeasures() {
        return (List<BiomedicDataMeasure>) entityManager.createNamedQuery("getAllBiomedicDataMeasures").getResultList();
    }

    public BiomedicDataMeasure findBiomedicDataMeasure(long code) throws MyEntityNotFoundException {
        BiomedicDataMeasure biomedicDataMeasure = entityManager.find(BiomedicDataMeasure.class, code);
        if(biomedicDataMeasure == null) throw new MyEntityNotFoundException("There is no Biomedic Data Measure with the code: \'"+code+"\'");
        return biomedicDataMeasure;
    }

    public void remove(BiomedicDataMeasure biomedicDataMeasure){
        biomedicDataMeasure.getPatient().removeBiomedicDataMeasure(biomedicDataMeasure);
        entityManager.merge(biomedicDataMeasure.getPatient());
        entityManager.remove(entityManager.merge(biomedicDataMeasure));
    }
    public void update(BiomedicDataMeasure biomedicDataMeasure, BiomedicDataMeasureDTO biomedicDataMeasureDTO) throws MyEntityNotFoundException {
        entityManager.lock(entityManager.merge(biomedicDataMeasure), LockModeType.PESSIMISTIC_WRITE);
        if(biomedicDataMeasureDTO.getValue() != 0 && biomedicDataMeasure.getValue() != biomedicDataMeasureDTO.getValue() && biomedicDataMeasureDTO.getValue() >= biomedicDataMeasure.getBiomedicDataType().getMinValue() && biomedicDataMeasureDTO.getValue() <= biomedicDataMeasure.getBiomedicDataType().getMaxValue()){
            biomedicDataMeasure.setValue(biomedicDataMeasureDTO.getValue());
        }
        if(biomedicDataMeasureDTO.getDate() != null && !biomedicDataMeasure.getDate().equals(biomedicDataMeasureDTO.getDate())){
            biomedicDataMeasure.setDate(biomedicDataMeasureDTO.getDate());
        }
        if(biomedicDataMeasureDTO.getHour() != null && !biomedicDataMeasure.getDate().equals(biomedicDataMeasureDTO.getHour())){
            biomedicDataMeasure.setHour(biomedicDataMeasureDTO.getHour());
        }
        entityManager.merge(biomedicDataMeasure);
    }

}
