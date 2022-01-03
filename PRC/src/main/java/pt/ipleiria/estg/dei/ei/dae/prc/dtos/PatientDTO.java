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
    private String passwordOld;
    private String birthDate;
    private String contact;
    private long healthNumber;
    private String gender;
    private List<Prescription> prescriptionList;
    private List<BiomedicDataMeasure> biomedicDataList;
    private List<HealthcareProfessional> healthcareProfessionals;
    private List<Disease> diseases;

    public PatientDTO() {
        this.healthcareProfessionals = new LinkedList<>();
        this.prescriptionList = new LinkedList<>();
        this.biomedicDataList = new LinkedList<>();
        this.diseases = new LinkedList<>();
    }

    public PatientDTO(String password, String passwordOld) {
        this.password = password;
        this.passwordOld = passwordOld;
    }
    public PatientDTO(String name, long healthNumber){
        this.name = name;
        this.healthNumber = healthNumber;
    }

    //Constructor without password
    public PatientDTO(String name, String email, String birthDate, String contact, long healthNumber,String gender) {
        this();
        this.username =  "P"+ healthNumber;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.contact = contact;
        this.healthNumber = healthNumber;
        this.gender = gender;
    }


    //Constructor with password
    public PatientDTO(String name, String email, String password, String birthDate, String contact, long healthNumber,String gender) {
        this(name, email, birthDate, contact, healthNumber,gender);
        this.password = password;
    }

    //Constructor with password
    public PatientDTO(String name, String email, String password, String birthDate, String contact, long healthNumber,String gender, List<Prescription> prescriptionList,  List<BiomedicDataMeasure> biomedicDataList, List<HealthcareProfessional> healthcareProfessionals) {
        this(name, email, password, birthDate, contact, healthNumber,gender);
        this.biomedicDataList = biomedicDataList;
        this.prescriptionList = prescriptionList;
        this.healthcareProfessionals = healthcareProfessionals;
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

    public String getPasswordOld() {
        return passwordOld;
    }

    public void setPasswordOld(String passwordOld) {
        this.passwordOld = passwordOld;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
