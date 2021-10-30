package pt.ipleiria.estg.dei.ei.dae.prc.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllDiseases",
                query = "SELECT p FROM Disease p ORDER BY p.code"
        )
})
@Table(name = "diseases")
public class Disease {
    @Id
    private int code;
    @NotNull
    private String name;
    @NotNull
    private String type;

    @ManyToMany
    @JoinTable(name = "DISEASES_PATIENTS",
            joinColumns = @JoinColumn(name = "CODE_DISEASE", referencedColumnName = "CODE"),
            inverseJoinColumns = @JoinColumn(name = "USERNAME_PATIENT", referencedColumnName = "USERNAME"))
    private List<Patient> patients;

    public Disease(int code, String name, String type) {
        this.code = code;
        this.name = name;
        this.type = type;
        patients = new ArrayList<>();
    }

    public Disease() {
        code = 0;
        name = "";
        type = "";
        patients = new ArrayList<>();
    }


    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
