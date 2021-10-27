package pt.ipleiria.estg.dei.ei.dae.prc.entities;

import pt.ipleiria.estg.dei.ei.dae.prc.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllPatients",
                query = "SELECT p FROM Patient p ORDER BY p.name" // JPQL
        )
})
public class Patient extends User implements Serializable {

    @NotNull
    private long healthUserNumber;
    private float weight;
    private float height;
    @OneToMany
    private List<Prescription> prescriptionList;
    @OneToMany //falta fazer as relações
    private List<BiomedicDataMeasure> biomedicDataList;


    public Patient(String username, String name, String email, String password, String birthDate, String contact, long healthUserNumber, float weight, float height) {
        super(username, name, email, password, birthDate, contact);
        this.healthUserNumber = healthUserNumber;
        this.weight = weight;
        this.height = height;
        prescriptionList = new LinkedList<>();
        biomedicDataList = new LinkedList<>();
    }

    public Patient() {
        prescriptionList = new LinkedList<>();
        biomedicDataList = new LinkedList<>();
    }

    public long getHealthUserNumber() {
        return healthUserNumber;
    }

    public void setHealthUserNumber(long healthUserNumber) {
        this.healthUserNumber = healthUserNumber;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public List<Prescription> getPrescriptionList() {
        return prescriptionList;
    }

    public void setPrescriptionList(List<Prescription> prescriptionList) {
        this.prescriptionList = prescriptionList;
    }

    public List<BiomedicDataMeasure> getBiomedicDataList() {
        return biomedicDataList;
    }

    public void setBiomedicDataList(List<BiomedicDataMeasure> biomedicDataList) {
        this.biomedicDataList = biomedicDataList;
    }
}
