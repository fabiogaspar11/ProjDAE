package pt.ipleiria.estg.dei.ei.dae.prc.dtos;

public class BiomedicDataMeasureDTO {
    private long code;
    private double value;
    private String date;
    private String hour;
    private String usernamePatient;
    private long biomedicDataTypeCode;
    private String usernameRegister;

    public BiomedicDataMeasureDTO() {
    }

    public BiomedicDataMeasureDTO(long code, double value, String date, String hour,String usernamePatient, long biomedicDataTypeCode, String usernameRegister) {
        this.code = code;
        this.value = value;
        this.date = date;
        this.hour = hour;
        this.usernamePatient = usernamePatient;
        this.biomedicDataTypeCode = biomedicDataTypeCode;
        this.usernameRegister = usernameRegister;
    }

    public String getUsernameRegister() {
        return usernameRegister;
    }

    public void setUsernameRegister(String usernameRegister) {
        this.usernameRegister = usernameRegister;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsernamePatient() {
        return usernamePatient;
    }

    public void setUsernamePatient(String usernamePatient) {
        this.usernamePatient = usernamePatient;
    }

    public long getBiomedicDataTypeCode() {
        return biomedicDataTypeCode;
    }

    public void setBiomedicDataTypeCode(long biomedicDataTypeCode) {
        this.biomedicDataTypeCode = biomedicDataTypeCode;
    }
}
