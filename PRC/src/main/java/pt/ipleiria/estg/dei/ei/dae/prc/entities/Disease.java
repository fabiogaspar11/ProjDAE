package pt.ipleiria.estg.dei.ei.dae.prc.entities;

import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int code;
    @NotNull
    private String name;


    @ManyToMany
    @JoinTable(name = "DISEASES_PATIENTS",
            joinColumns = @JoinColumn(name = "CODE_DISEASE", referencedColumnName = "CODE"),
            inverseJoinColumns = @JoinColumn(name = "USERNAME_PATIENT", referencedColumnName = "USERNAME"))
    private List<Patient> patients;

    public Disease() {
        patients = new ArrayList<>();
    }


    public Disease(String name) {
        this();
        this.name = name;
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
