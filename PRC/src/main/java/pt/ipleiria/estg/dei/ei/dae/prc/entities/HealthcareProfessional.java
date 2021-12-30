package pt.ipleiria.estg.dei.ei.dae.prc.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllHealthcareProfessionals",
                query = "SELECT h FROM HealthcareProfessional h ORDER BY h.name" // JPQL
        )
})

public class HealthcareProfessional extends User implements Serializable {
    @NotNull
    private String type;

    @ManyToMany
    @JoinTable(name = "HEALTHCAREPROFESSIONALS_PATIENTS",
            joinColumns = @JoinColumn(name = "USERNAME_HEALTHCAREPROFESSIONAL", referencedColumnName = "USERNAME"),
            inverseJoinColumns = @JoinColumn(name = "USERNAME_PATIENT", referencedColumnName = "USERNAME"))
    private List<Patient> patients;

    @OneToMany(mappedBy = "healthcareProfessional", cascade = CascadeType.REMOVE)
    private List<Prescription> prescriptions;

    public HealthcareProfessional() {
        this.patients= new LinkedList<>();
        this.prescriptions = new LinkedList<>();
    }

    public HealthcareProfessional(String username, long healthNumber, String name, String email, String password, String birthDate, String contact, String type) {
        super(username, name, email, password, birthDate, contact, healthNumber);
        this.type = type;
        this.patients = new LinkedList<>();
        this.prescriptions = new LinkedList<>();
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public void addPatient(Patient patient){
        if(patient == null || patients.contains(patient)){
            return;
        }
        patients.add(patient);
    }

    public void removePatient(Patient patient){
        if(patient == null || !patients.contains(patient)){
            return;
        }
        patients.remove(patient);
    }

    public void addPrescription(Prescription prescription){
        if(prescription == null || prescriptions.contains(prescription)){
            return;
        }
        prescriptions.add(prescription);
    }

    public void removePrescription(Prescription prescription){
        if(prescription == null || !prescriptions.contains(prescription)){
            return;
        }
        prescriptions.remove(prescription);
    }
}

