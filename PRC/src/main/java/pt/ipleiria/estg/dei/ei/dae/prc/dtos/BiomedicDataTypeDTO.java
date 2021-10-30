package pt.ipleiria.estg.dei.ei.dae.prc.dtos;

import java.io.Serializable;

public class BiomedicDataTypeDTO implements Serializable {
    private long code;
    private String name;
    private String unitMeasure;
    private float minValue;
    private float maxValue;

    public BiomedicDataTypeDTO(long code, String name, String unitMeasure, float minValue, float maxValue) {
        this.code = code;
        this.name = name;
        this.unitMeasure = unitMeasure;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public BiomedicDataTypeDTO() {
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
}
