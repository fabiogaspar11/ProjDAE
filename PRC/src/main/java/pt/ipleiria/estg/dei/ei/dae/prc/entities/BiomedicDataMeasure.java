package pt.ipleiria.estg.dei.ei.dae.prc.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Table(name = "biomedicdatameasures")
@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllBiomedicDataMeasures",
                query = "SELECT b FROM BiomedicDataMeasure b ORDER BY b.code"
        )
})
public class BiomedicDataMeasure implements Serializable {
    @Id
    private long code;
    @NotNull
    private double value;
    @NotNull
    private String date;
    @NotNull
    private String hour;
    @ManyToOne
    @NotNull
    @JoinColumn(name = "patient_username")
    private Patient patient;
    @ManyToOne
    @NotNull
    @JoinColumn(name = "biomedicdatatype_code")
    private BiomedicDataType biomedicDataType;

    public BiomedicDataMeasure() {
    }

    public BiomedicDataMeasure(long code, double value, String date, String hour, Patient patient, BiomedicDataType biomedicDataType) {
        this.code = code;
        this.value = value;
        this.date = date;
        this.hour = hour;
        this.patient = patient;
        this.biomedicDataType = biomedicDataType;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public BiomedicDataType getBiomedicDataType() {
        return biomedicDataType;
    }

    public void setBiomedicDataType(BiomedicDataType biomedicDataType) {
        this.biomedicDataType = biomedicDataType;
    }
}
