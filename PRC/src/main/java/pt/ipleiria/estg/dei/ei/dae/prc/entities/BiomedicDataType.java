package pt.ipleiria.estg.dei.ei.dae.prc.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;



@NamedQueries({
        @NamedQuery(
                name = "getAllBiomedicDataType",
                query = "SELECT b FROM BiomedicDataType b ORDER BY b.code" // JPQL
        )
})
@Table(
        name = "biomedicdatatypes",
        uniqueConstraints = @UniqueConstraint(columnNames = {"NAME"})
)
@Entity
public class BiomedicDataType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long code;
    @NotNull
    private String name;
    @NotNull
    private String unitMeasure;
    @NotNull
    private float normalMinValue;
    @NotNull
    private float normalMaxValue;
    @NotNull
    private float genderValuedifferentiation;
    @NotNull
    private float ageValuedifferentiation;
    @NotNull
    private float minValue;
    @NotNull
    private float maxValue;

    public BiomedicDataType() {
    }

    public BiomedicDataType(String name, String unitMeasure,float normalMinValue, float normalMaxValue, float minValue, float maxValue,float genderValuedifferentiation,float ageValuedifferentiation) {
        this.name = name;
        this.unitMeasure = unitMeasure;
        this.normalMaxValue =normalMaxValue;
        this.normalMinValue=normalMinValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.genderValuedifferentiation = genderValuedifferentiation;
        this.ageValuedifferentiation = ageValuedifferentiation;
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

    public BiomedicDataType(long code, String name, String unitMeasure, float normalMinValue, float normalMaxValue, float minValue, float maxValue, float genderValuedifferentiation,float ageValuedifferentiation) {
        this(name,unitMeasure,normalMinValue,normalMaxValue,minValue, maxValue, genderValuedifferentiation, ageValuedifferentiation);
        this.code = code;
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
