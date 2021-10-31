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
    private long code;
    @NotNull
    private String name;
    @NotNull
    private String unitMeasure;
    @NotNull
    private float minValue;
    @NotNull
    private float maxValue;

    public BiomedicDataType(long code, String name, String unitMeasure, float minValue, float maxValue) {
        this.code = code;
        this.name = name;
        this.unitMeasure = unitMeasure;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public BiomedicDataType() {
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
