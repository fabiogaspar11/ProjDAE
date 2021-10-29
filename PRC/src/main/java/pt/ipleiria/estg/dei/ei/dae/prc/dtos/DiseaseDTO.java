package pt.ipleiria.estg.dei.ei.dae.prc.dtos;

import pt.ipleiria.estg.dei.ei.dae.prc.entities.Patient;

import java.util.ArrayList;
import java.util.List;

public class DiseaseDTO {
    private int code;
    private String name;
    private String type;
    private List<PatientDTO> patientDTOS;

    public DiseaseDTO(int code, String name, String type) {
        this.code = code;
        this.name = name;
        this.type = type;
    }

    public DiseaseDTO(int code, String name, String type, List<PatientDTO> patientDTOS) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.patientDTOS = patientDTOS;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<PatientDTO> getPatientDTOS() {
        return patientDTOS;
    }

    public void setPatientDTOS(List<PatientDTO> patientDTOS) {
        this.patientDTOS = patientDTOS;
    }
}
