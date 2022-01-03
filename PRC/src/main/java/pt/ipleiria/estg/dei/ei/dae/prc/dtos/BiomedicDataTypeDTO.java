package pt.ipleiria.estg.dei.ei.dae.prc.dtos;

import java.io.Serializable;

public class BiomedicDataTypeDTO implements Serializable {
    private long code;
    private String name;
    private String unitMeasure;
    private float minValue;
    private float maxValue;
    private float normalMinValue;
    private float normalMaxValue;
    private float genderValuedifferentiation;
    private float ageValuedifferentiation;

    public BiomedicDataTypeDTO() {
    }

    public BiomedicDataTypeDTO(long code, String name, String unitMeasure,float normalMinValue,float normalMaxValue, float minValue, float maxValue,float genderValuedifferentiation, float ageValuedifferentiation) {
        this.code = code;
        this.name = name;
        this.unitMeasure = unitMeasure;
        this.normalMinValue = normalMinValue;
        this.normalMaxValue = normalMaxValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.genderValuedifferentiation = genderValuedifferentiation;
        this.ageValuedifferentiation = ageValuedifferentiation;
    }



    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public float getMinValue() {
        return minValue;
    }

    public void setMinValue(float minValue) {
        this.minValue = minValue;
    }

    public float getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    public float getNormalMinValue() {
        return normalMinValue;
    }

    public void setNormalMinValue(float normalMinValue) {
        this.normalMinValue = normalMinValue;
    }

    public float getNormalMaxValue() {
        return normalMaxValue;
    }

    public void setNormalMaxValue(float normalMaxValue) {
        this.normalMaxValue = normalMaxValue;
    }

    public float getGenderValuedifferentiation() {
        return genderValuedifferentiation;
    }

    public void setGenderValuedifferentiation(float genderValuedifferentiation) {
        this.genderValuedifferentiation = genderValuedifferentiation;
    }

    public float getAgeValuedifferentiation() {
        return ageValuedifferentiation;
    }

    public void setAgeValuedifferentiation(float ageValuedifferentiation) {
        this.ageValuedifferentiation = ageValuedifferentiation;
    }
}
