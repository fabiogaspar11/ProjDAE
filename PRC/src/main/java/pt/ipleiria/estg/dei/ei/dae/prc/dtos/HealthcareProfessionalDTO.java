package pt.ipleiria.estg.dei.ei.dae.prc.dtos;

public class HealthcareProfessionalDTO {
    private String username;
    private String name;
    private String email;
    private String password;
    private String birthDate;
    private String contact;
    private long healthcareProfessionalNumber;

    public HealthcareProfessionalDTO(){
    }

    public HealthcareProfessionalDTO(String username, String name, String email, String password, String birthDate, String contact, long healthcareProfessionalNumber) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.contact = contact;
        this.healthcareProfessionalNumber = healthcareProfessionalNumber;
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

    public long getHealthcareProfessionalNumber() {
        return healthcareProfessionalNumber;
    }

    public void setHealthcareProfessionalNumber(long healthcareProfessionalNumber) {
        this.healthcareProfessionalNumber = healthcareProfessionalNumber;
    }
}
