package pt.ipleiria.estg.dei.ei.dae.prc.dtos;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class DiseaseDTO implements Serializable {
    private int code;
    private String name;
    private int diseaseTypeCode;
    private List<PatientDTO> patientDTOS;


   public DiseaseDTO() {
        System.out.println("############# shit 1");
        this.patientDTOS = new LinkedList<>();
    }

    public DiseaseDTO(String name, int diseaseTypeCode) {
      //  this();
        System.out.println("############# shit 2");
        this.patientDTOS = new LinkedList<>();
        this.name = name;
        this.diseaseTypeCode = diseaseTypeCode;

    }
    public DiseaseDTO(int code, String name, int diseaseTypeCode) {
        this(name, diseaseTypeCode);
        this.code = code;
    }

    public DiseaseDTO(String name, int diseaseTypeCode, List<PatientDTO> patientDTOS) {
        this(name,diseaseTypeCode);
        this.patientDTOS = patientDTOS;
    }

    public DiseaseDTO(int code, String name, int diseaseTypeCode, List<PatientDTO> patientDTOS) {
        this(name, diseaseTypeCode, patientDTOS);
        this.code = code;
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

    public int getType() {
        return diseaseTypeCode;
    }

    public void setType(int type) {
        this.diseaseTypeCode = type;
    }

    public List<PatientDTO> getPatientDTOS() {
        return patientDTOS;
    }

    public void setPatientDTOS(List<PatientDTO> patientDTOS) {
        this.patientDTOS = patientDTOS;
    }
}
