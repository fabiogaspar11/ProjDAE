package pt.ipleiria.estg.dei.ei.dae.prc.dtos;

import java.io.Serializable;

public class PrescriptionDTO implements Serializable {


    private long code;
    private String title;
    private String observations;
    private String emissionDate;
    private String expireDate;
    private String usernamePatient;
    private String usernameHealthcareProfessional;

    public PrescriptionDTO() {
    }

    public PrescriptionDTO(long code, String title, String observations, String emissionDate, String expireDate, String usernamePatient, String usernameHealthcareProfessional) {
        this.code = code;
        this.title = title;
        this.observations = observations;
        this.emissionDate = emissionDate;
        this.expireDate = expireDate;
        this.usernamePatient = usernamePatient;
        this.usernameHealthcareProfessional = usernameHealthcareProfessional;
    }

    public String getUsernameHealthcareProfessional() {
        return usernameHealthcareProfessional;
    }

    public void setUsernameHealthcareProfessional(String usernameHealthcareProfessional) {
        this.usernameHealthcareProfessional = usernameHealthcareProfessional;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getEmissionDate() {
        return emissionDate;
    }

    public void setEmissionDate(String emissionDate) {
        this.emissionDate = emissionDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getUsernamePatient() {
        return usernamePatient;
    }

    public void setUsernamePatient(String usernamePatient) {
        this.usernamePatient = usernamePatient;
    }
}
