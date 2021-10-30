package pt.ipleiria.estg.dei.ei.dae.prc.dtos;

import pt.ipleiria.estg.dei.ei.dae.prc.entities.Patient;

import java.util.LinkedList;
import java.util.List;

public class HealthcareProfessionalDTO {
    private String username;
    private String name;
    private String email;
    private String password;
    private String birthDate;
    private String contact;
    private long healthNumber;
    private String type;
    private List<Patient> patients;

    public HealthcareProfessionalDTO(){
        this.patients = new LinkedList<>();
    }

    public HealthcareProfessionalDTO(String name, String email, String password, String birthDate, String contact, long healthNumber, String type) {
        this.username = "H"+healthNumber;
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.contact = contact;
        this.healthNumber = healthNumber;
        this.type=type;
        this.patients = new LinkedList<>();
    }
    public HealthcareProfessionalDTO(String name, String email, String password, String birthDate, String contact, long healthNumber, String type, List<Patient> patients) {
        this(name, email, password, birthDate, contact, healthNumber, type);
        this.patients = patients;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public long getHealthNumber() {
        return healthNumber;
    }

    public void setHealthNumber(long healthNumber) {
        this.healthNumber = healthNumber;
    }
}
