package pt.ipleiria.estg.dei.ei.dae.prc.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "patient", cascade = CascadeType.REMOVE)
    private List<Prescription> prescriptions;
    @OneToMany(mappedBy = "patient", cascade = CascadeType.REMOVE)
    private List<BiomedicDataMeasure> biomedicDataMeasures;
    @ManyToMany(mappedBy = "patients")
    private List<HealthcareProfessional> healthcareProfessionals;
    @ManyToMany(mappedBy = "patients")
    private List<Disease> diseases;
    @NotNull
    private String gender;

    public Patient() {
        this.prescriptions = new LinkedList<>();
        this.biomedicDataMeasures = new LinkedList<>();
        this.healthcareProfessionals = new LinkedList<>();
        this.diseases = new LinkedList<>();
    }

    public Patient(String username, String name, String email, String password, String birthDate, String contact, long healthNumber,String gender) {
        super(username, name, email, password, birthDate, contact, healthNumber);
        this.gender = gender;
        this.healthcareProfessionals = new LinkedList<>();
        this.prescriptions = new LinkedList<>();
        this.biomedicDataMeasures = new LinkedList<>();
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

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptionList) {
        this.prescriptions = prescriptionList;
    }

    public List<BiomedicDataMeasure> getBiomedicDataMeasures() {
        return biomedicDataMeasures;
    }
    public void setBiomedicDataMeasures(List<BiomedicDataMeasure> biomedicDataList) {
        this.biomedicDataMeasures = biomedicDataList;
    }

    public void addPrescription(Prescription prescription){
        if(prescription!=null){
            prescriptions.add(prescription);
        }
    }

    public void removePrescription(Prescription prescription){
        if(prescription!=null){
            prescriptions.remove(prescription);
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

    public void addBiomedicDataMeasure(BiomedicDataMeasure biomedicDataMeasure){
        if(biomedicDataMeasure == null || biomedicDataMeasures.contains(biomedicDataMeasure)){
            return;
        }
        biomedicDataMeasures.add(biomedicDataMeasure);
    }

    public void removeBiomedicDataMeasure(BiomedicDataMeasure biomedicDataMeasure){
        if(biomedicDataMeasure == null || !biomedicDataMeasures.contains(biomedicDataMeasure)){
            return;
        }
        biomedicDataMeasures.remove(biomedicDataMeasure);
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
