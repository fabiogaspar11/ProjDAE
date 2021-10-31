package pt.ipleiria.estg.dei.ei.dae.prc.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllPatients",
                query = "SELECT p FROM Patient p ORDER BY p.name"
        )
})

public class Patient extends User implements Serializable {

    private float weight;
    private float height;
    @OneToMany
    private List<Prescription> prescriptionList;
    @OneToMany //falta fazer as relações
    private List<BiomedicDataMeasure> biomedicDataList;

    @ManyToMany(mappedBy = "patients")
    private List<HealthcareProfessional> healthcareProfessionals;

    @ManyToMany(mappedBy = "patients")
    private List<Disease> diseases;

    public Patient() {
        this.prescriptionList = new LinkedList<>();
        this.biomedicDataList = new LinkedList<>();
        this.healthcareProfessionals = new LinkedList<>();
        this.diseases = new LinkedList<>();
    }

    public Patient(String username, String name, String email, String password, String birthDate, String contact, long healthNumber, float weight, float height) {
        super(username, name, email, password, birthDate, contact, healthNumber);
        this.weight = weight;
        this.height = height;
        this.healthcareProfessionals = new LinkedList<>();
        this.prescriptionList = new LinkedList<>();
        this.biomedicDataList = new LinkedList<>();
        this.diseases = new LinkedList<>();
    }

    public List<Disease> getDiseases() {
        return diseases;
    }

    public void setDiseases(List<Disease> diseases) {
        this.diseases = diseases;
    }

    public List<HealthcareProfessional> getHealthcareProfessionals() {
        return healthcareProfessionals;
    }

    public void setHealthcareProfessionals(List<HealthcareProfessional> healthcareProfessionals) {
        this.healthcareProfessionals = healthcareProfessionals;
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

    public void addPrescription(Prescription prescription){
        if(prescription!=null){
            prescriptionList.add(prescription);
        }
    }

    public void removePrescription(Prescription prescription){
        if(prescription!=null){
            prescriptionList.remove(prescription);
        }
    }

    public void addHealthcareProfessional(HealthcareProfessional healthcareProfessional){
        if(healthcareProfessional == null || healthcareProfessionals.contains(healthcareProfessional)){
            return;
        }
        healthcareProfessionals.add(healthcareProfessional);
    }

    public void removeHealthcareProfessional(HealthcareProfessional healthcareProfessional){
        if(healthcareProfessional == null || !healthcareProfessionals.contains(healthcareProfessional)){
            return;
        }
        healthcareProfessionals.remove(healthcareProfessional);
    }

    public void addDisease(Disease disease){
        if(disease == null || diseases.contains(disease)){
            return;
        }
        diseases.add(disease);
    }

    public void removeDisease(Disease disease){
        if(disease == null || !diseases.contains(disease)){
            return;
        }
        diseases.remove(disease);
    }
}
