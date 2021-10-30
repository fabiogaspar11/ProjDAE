package pt.ipleiria.estg.dei.ei.dae.prc.dtos;

import pt.ipleiria.estg.dei.ei.dae.prc.entities.BiomedicDataMeasure;
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
    private long healthNumber;
    private float weight;
    private float height;
    private List<Prescription> prescriptionList;
    private List<BiomedicDataMeasure> biomedicDataList;
    private List<HealthcareProfessional> healthcareProfessionals;


    public PatientDTO() {
        this.healthcareProfessionals = new LinkedList<>();
        this.prescriptionList = new LinkedList<>();
        this.biomedicDataList = new LinkedList<>();
    }
    //Constructor without password
    public PatientDTO(String name, String email, String birthDate, String contact, long healthNumber, float weight, float height) {
        this();
        this.username =  "P"+ healthNumber;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.contact = contact;
        this.healthNumber = healthNumber;
        this.weight = weight;
        this.height = height;
    }

    //Constructor with password
    public PatientDTO(String name, String email, String password, String birthDate, String contact, long healthNumber, float weight, float height) {
        this(name, email, birthDate, contact, healthNumber, weight, height);
        this.password = password;
    }

    //Constructor with password
    public PatientDTO(String name, String email, String password, String birthDate, String contact, long healthNumber, float weight, float height, List<Prescription> prescriptionList,  List<BiomedicDataMeasure> biomedicDataList, List<HealthcareProfessional> healthcareProfessionals) {
        this(name, email, password, birthDate, contact, healthNumber, weight, height);
        this.biomedicDataList = biomedicDataList;
        this.prescriptionList = prescriptionList;
        this.healthcareProfessionals = healthcareProfessionals;
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

    public long getHealthNumber() {
        return healthNumber;
    }

    public void setHealthNumber(long healthNumber) {
        this.healthNumber = healthNumber;
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
