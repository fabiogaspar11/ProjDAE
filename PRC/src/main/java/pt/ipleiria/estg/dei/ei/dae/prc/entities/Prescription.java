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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long code;
    @NotNull
    private String title;
    @NotNull
    private String observations;
    @NotNull
    private String isPharmacological;
    @NotNull
    private String treatmentInfo;
    @NotNull
    private String emissionDate;
    @NotNull
    private String expireDate;
    //TODO - @NotNull - patientBean removePrescription prescription.setPatient(null)
    @ManyToOne
    @JoinColumn(name = "patient_username")
    private Patient patient;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "healthcareProfessional_username")
    private HealthcareProfessional healthcareProfessional;


    public Prescription() {
    }

    public Prescription(String title, String observations,String isPharmacological,String treatmentInfo, String emissionDate, String expireDate, Patient patient, HealthcareProfessional healthcareProfessional) {
        this.title = title;
        this.observations = observations;
        this.isPharmacological = isPharmacological;
        this.treatmentInfo = treatmentInfo;
        this.emissionDate = emissionDate;
        this.expireDate = expireDate;
        this.patient = patient;
        this.healthcareProfessional = healthcareProfessional;
    }


    public HealthcareProfessional getHealthcareProfessional() {
        return healthcareProfessional;
    }

    public void setHealthcareProfessional(HealthcareProfessional healthcareProfessional) {
        this.healthcareProfessional = healthcareProfessional;
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

    public String getIsPharmacological() {
        return isPharmacological;
    }

    public void setIsPharmacological(String isPharmacological) {
        this.isPharmacological = isPharmacological;
    }

    public String getTreatmentInfo() {
        return treatmentInfo;
    }

    public void setTreatmentInfo(String treatmentInfo) {
        this.treatmentInfo = treatmentInfo;
    }

    @Override
    public String toString()
    {
        return "\t\t   ***Prescription number:" + this.code  + "***\n\n" +
                "\tEmission Date: " + this.emissionDate + " | Expire Date: " + this.expireDate  + "\n\n" +
                "Prescribing: " + this.healthcareProfessional.getName() +
                "\nPhone Number: " + this.healthcareProfessional.getContact() + "\n\n" +
                "Patient: " + this.patient.getName()  + "\n\n" +
               "\t\t   Title: "+ this.title + "\n" +
               "\nPrescription contains medication: " + this.isPharmacological + "\n" +
                "\nTreatment details: " + "\n" + this.treatmentInfo + "\n\n" +
                "Observations: " + "\n" + this.observations;
    }
}
