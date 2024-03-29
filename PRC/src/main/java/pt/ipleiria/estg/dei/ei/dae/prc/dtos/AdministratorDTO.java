package pt.ipleiria.estg.dei.ei.dae.prc.dtos;

import java.io.Serializable;

public class AdministratorDTO implements Serializable {
    private String username;
    private String name;
    private String email;
    private String passwordOld;
    private String password;
    private String birthDate;
    private String contact;
    private long healthNumber;

    public AdministratorDTO() {
    }
    public AdministratorDTO(String name, String email, String birthDate, String contact, long healthNumber) {
        this.username = "A"+healthNumber;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.contact = contact;
        this.healthNumber = healthNumber;
    }

    public AdministratorDTO(String name, String email, String password, String birthDate, String contact, long healthNumber) {
        this(name, email, birthDate, contact, healthNumber);
        this.password = password;
    }

    public AdministratorDTO(String passwordOld, String password) {
        this.passwordOld = passwordOld;
        this.password = password;
    }

    public String getPasswordOld() {
        return passwordOld;
    }

    public void setPasswordOld(String oldPassword) {
        this.passwordOld = oldPassword;
    }

    public long getHealthNumber() {
        return healthNumber;
    }

    public void setHealthNumber(long healthNumber) {
        this.healthNumber = healthNumber;
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
}
