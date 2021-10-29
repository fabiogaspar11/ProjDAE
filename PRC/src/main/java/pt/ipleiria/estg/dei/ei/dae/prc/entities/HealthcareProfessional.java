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
    private long healthcareProfessionalNumber;
    @NotNull
    private String type;

    @ManyToMany
    @JoinTable(name = "HEALTHCAREPROFESSIONALS_PACIENTS",
            joinColumns = @JoinColumn(name = "USERNAME_HEALTHCAREPROFESSIONAL", referencedColumnName = "USERNAME"),
            inverseJoinColumns = @JoinColumn(name = "USERNAME_PACIENT", referencedColumnName =
                    "USERNAME"))
    private List<Patient> patients;

    public HealthcareProfessional() {
        this.patients= new LinkedList<>();
    }

    public HealthcareProfessional(String username, long healthcareProfessionalNumber, String name, String email, String password, String birthDate, String contact, String type) {
        super(username, name, email, password, birthDate, contact);
        this.healthcareProfessionalNumber = healthcareProfessionalNumber;
        this.patients = new LinkedList<>();
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getHealthcareProfessionalNumber() {
        return healthcareProfessionalNumber;
    }

    public void setHealthcareProfessionalNumber(long healthcareProfessionalNumber) {
        this.healthcareProfessionalNumber = healthcareProfessionalNumber;
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
}

