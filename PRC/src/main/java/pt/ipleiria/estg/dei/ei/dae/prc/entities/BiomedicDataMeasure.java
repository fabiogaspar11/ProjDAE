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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long code;
    @NotNull
    private double value;
    @NotNull
    private String date;
    @NotNull
    private String classification;
    @NotNull
    private float normalMinValue;
    @NotNull
    private float normalMaxValue;
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
    @ManyToOne
    @NotNull
    @JoinColumn(name = "username_register")
    private User userRegister;

    public BiomedicDataMeasure() {
    }

    public BiomedicDataMeasure(double value, String date, String hour, Patient patient, BiomedicDataType biomedicDataType, User userRegister,String classification,float normalMinValue, float normalMaxValue) {
        this.value = value;
        this.date = date;
        this.hour = hour;
        this.patient = patient;
        this.biomedicDataType = biomedicDataType;
        this.userRegister = userRegister;
        this.classification = classification;
        this.normalMinValue = normalMinValue;
        this.normalMaxValue = normalMaxValue;
    }

    public BiomedicDataMeasure(long code, double value, String date, String hour, Patient patient, BiomedicDataType biomedicDataType,User userRegister,String classification,float normalMinValue, float normalMaxValue) {
        this(value,date,hour, patient,biomedicDataType,userRegister,classification,normalMinValue,normalMaxValue);
        this.code = code;
    }

    public User getUserRegister() {
        return userRegister;
    }

    public void setUserRegister(User userRegister) {
        this.userRegister = userRegister;
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

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
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
}
