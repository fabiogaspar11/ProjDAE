package pt.ipleiria.estg.dei.ei.dae.prc.dtos;

import pt.ipleiria.estg.dei.ei.dae.prc.entities.Patient;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Prescription;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class HealthcareProfessionalDTO implements Serializable {
    private String username;
    private String name;
    private String email;
    private String password;
    private String passwordOld;
    private String birthDate;
    private String contact;
    private long healthNumber;
    private List<Patient> patients;
    private List<Prescription> prescriptions;

    public HealthcareProfessionalDTO(){
        this.patients = new LinkedList<>();
        this.prescriptions = new LinkedList<>();
    }

    public HealthcareProfessionalDTO(String name, String email, String password, String birthDate, String contact, long healthNumber) {
        this();
        this.username = "H"+healthNumber;
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.contact = contact;
        this.healthNumber = healthNumber;
    }

    public HealthcareProfessionalDTO(String name, String email, String birthDate, String contact, long healthNumber) {
        this.username = "H"+healthNumber;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.contact = contact;
        this.healthNumber = healthNumber;
    }

    public HealthcareProfessionalDTO(String name, String email, String password, String birthDate, String contact, long healthNumber, List<Patient> patients, List<Prescription> prescriptions) {
        this(name, email, password, birthDate, contact, healthNumber);
        this.patients = patients;
        this.prescriptions = prescriptions;
    }
    public HealthcareProfessionalDTO(String name, String email, String contact) {
        this.name = name;
        this.email = email;
        this.contact = contact;
    }


    public HealthcareProfessionalDTO(String passwordOld, String password) {
        this.passwordOld = passwordOld;
        this.password = password;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public String getPasswordOld() {
        return passwordOld;
    }

    public void setPasswordOld(String passwordOld) {
        this.passwordOld = passwordOld;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
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
