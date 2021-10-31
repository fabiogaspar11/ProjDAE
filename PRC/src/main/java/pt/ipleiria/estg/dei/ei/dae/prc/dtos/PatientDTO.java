package pt.ipleiria.estg.dei.ei.dae.prc.dtos;

import pt.ipleiria.estg.dei.ei.dae.prc.entities.BiomedicDataMeasure;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Disease;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.HealthcareProfessional;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Prescription;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class PatientDTO implements Serializable {

    private String username;
    private String name;
    private String email;
    private String password;
    private String birthDate;
    private String contact;
    private long healthUserNumber;
    private float weight;
    private float height;
    private List<Prescription> prescriptionList;
    private List<BiomedicDataMeasure> biomedicDataList;
    private List<HealthcareProfessional> healthcareProfessionals;
    private List<Disease> diseases;

    public PatientDTO(String username, String name, String email, String password, String birthDate, String contact, long healthUserNumber, float weight, float height) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.contact = contact;
        this.healthUserNumber = healthUserNumber;
        this.weight = weight;
        this.height = height;
        this.healthcareProfessionals = new LinkedList<>();
        this.prescriptionList = new LinkedList<>();
        this.biomedicDataList = new LinkedList<>();
        this.diseases = new LinkedList<>();
    }

    public PatientDTO() {
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

    public PatientDTO(String username, String name, String email, String birthDate, String contact, long healthUserNumber, float weight, float height) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.contact = contact;
        this.healthUserNumber = healthUserNumber;
        this.weight = weight;
        this.height = height;
    }

    public List<HealthcareProfessional> getHealthcareProfessionals() {
        return healthcareProfessionals;
    }

    public void setHealthcareProfessionals(List<HealthcareProfessional> healthcareProfessionals) {
        this.healthcareProfessionals = healthcareProfessionals;
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
