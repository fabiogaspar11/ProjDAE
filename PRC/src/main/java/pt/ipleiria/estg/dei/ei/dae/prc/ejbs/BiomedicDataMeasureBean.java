package pt.ipleiria.estg.dei.ei.dae.prc.ejbs;

import pt.ipleiria.estg.dei.ei.dae.prc.dtos.BiomedicDataMeasureDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.*;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.Calendar;
import java.util.List;

@Stateless
public class BiomedicDataMeasureBean {

    @PersistenceContext
    private EntityManager entityManager;

    public long create(double value, String date, String hour, String username, long biomedicDataTypeCode, String usernameRegister) throws MyEntityExistsException, MyEntityNotFoundException {
        Patient patient = entityManager.find(Patient.class,username);
        if(patient == null) {
            throw new MyEntityNotFoundException("There is no Patient with the username \'" + username + "\'");
        }
        BiomedicDataType biomedicDataType = entityManager.find(BiomedicDataType.class,biomedicDataTypeCode);
        if(biomedicDataType == null) {
            throw new MyEntityNotFoundException("There is no Biomedic Data Type with the code \'" + biomedicDataTypeCode + "\'");
        }
        User userRegister = entityManager.find(User.class,usernameRegister);
        if(userRegister == null) {
            throw new MyEntityNotFoundException("There is no User with the username \'" + usernameRegister + "\'");
        }

        String classification = "Undefined";

        float genderDiff = biomedicDataType.getGenderValuedifferentiation();
        float ageDiff = biomedicDataType.getAgeValuedifferentiation();
        float minimumValueMeasure = biomedicDataType.getNormalMinValue();
        float maximumValueMeasure = biomedicDataType.getNormalMaxValue();

        if(patient.getGender().equals("Feminino")){
            minimumValueMeasure -= genderDiff;
            maximumValueMeasure -= genderDiff;
        }

        int age = this.getAge(patient.getBirthDate());
        if(age<=2){
            minimumValueMeasure += ageDiff*4;
            maximumValueMeasure += ageDiff*4;
        }
        else if(age<=10){
            minimumValueMeasure += ageDiff*3;
            maximumValueMeasure += ageDiff*3;
        }
        else if(age<19){
            minimumValueMeasure += ageDiff*2;
            maximumValueMeasure += ageDiff*2;

        }
        else if(age>=65){
            minimumValueMeasure -= ageDiff;
            maximumValueMeasure -= ageDiff;

        }
        //intervalo de 19 a 65 é o normal


        if(minimumValueMeasure <= biomedicDataType.getMinValue() || minimumValueMeasure >= biomedicDataType.getMaxValue()){
            minimumValueMeasure = biomedicDataType.getMinValue();
        }

        if(maximumValueMeasure >= biomedicDataType.getMaxValue() || minimumValueMeasure <= biomedicDataType.getMinValue()){
            maximumValueMeasure = biomedicDataType.getMaxValue();
        }

        if(value < minimumValueMeasure){
            classification = "Inferior";
        }else if(value >= minimumValueMeasure && value <=maximumValueMeasure){
            classification = "Normal";
        }else if(value > minimumValueMeasure){
            classification = "Superior";
        }


        BiomedicDataMeasure biomedicDataMeasure = new BiomedicDataMeasure(value, date, hour, patient, biomedicDataType, userRegister,classification,minimumValueMeasure,maximumValueMeasure);
        entityManager.persist(biomedicDataMeasure);
        entityManager.flush();
        patient.addBiomedicDataMeasure(biomedicDataMeasure);
        entityManager.merge(patient);

        return biomedicDataMeasure.getCode();
    }

    public int getAge(String birthdate){
        String[] birthdateArray =birthdate.split("/");
        int day = Integer.parseInt(birthdateArray[0]);
        int month = Integer.parseInt(birthdateArray[1]);
        int year = Integer.parseInt(birthdateArray[2]);

        Calendar today = Calendar.getInstance();

        int curYear = today.get(Calendar.YEAR);

        int age = curYear - year;


        int curMonth = today.get(Calendar.MONTH);
        if (month > curMonth) { // this year can't be counted!
            age--;
        } else if (month == curMonth) { // same month? check for day
            int curDay = today.get(Calendar.DAY_OF_MONTH);
            if (day > curDay) { // this year can't be counted!
                age--;
            }
        }
        return age;
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

        if (biomedicDataMeasureDTO.getValue() > biomedicDataMeasure.getNormalMaxValue()){
            biomedicDataMeasure.setClassification("Superior");
        }
        else if (biomedicDataMeasureDTO.getValue() < biomedicDataMeasure.getNormalMinValue()){
            biomedicDataMeasure.setClassification("Inferior");
        }
        else{
            biomedicDataMeasure.setClassification("Normal");
        }

        biomedicDataMeasure.setClassification(biomedicDataMeasure.getClassification());

        entityManager.merge(biomedicDataMeasure);
    }

}
