package pt.ipleiria.estg.dei.ei.dae.prc.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Table(name = "prescriptions")
@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllPrescriptions",
                query = "SELECT p FROM Prescription p ORDER BY p.code"
        )
})
public class Prescription implements Serializable {
    @Id
    private long code;
    @NotNull
    private String title;
    @NotNull
    private String observations;
    @NotNull
    private String emissionDate;
    @NotNull
    private String expireDate;
    @NotNull
    @ManyToOne
    private Patient patient;

    public Prescription() {
    }

    public Prescription(long code, String title, String observations, String emissionDate, String expireDate, Patient patient) {
        this.code = code;
        this.title = title;
        this.observations = observations;
        this.emissionDate = emissionDate;
        this.expireDate = expireDate;
        this.patient = patient;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getEmissionDate() {
        return emissionDate;
    }

    public void setEmissionDate(String emissionDate) {
        this.emissionDate = emissionDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
