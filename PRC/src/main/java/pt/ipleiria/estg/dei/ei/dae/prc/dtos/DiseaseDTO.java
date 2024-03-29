package pt.ipleiria.estg.dei.ei.dae.prc.dtos;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class DiseaseDTO implements Serializable {
    private long code;
    private String name;
    private List<PatientDTO> patientDTOS;


    public DiseaseDTO() {
       this.patientDTOS = new LinkedList<>();
    }

    public DiseaseDTO(String name) {
        this();
        this.name = name;
    }
    public DiseaseDTO(long code, String name) {
        this(name);
        this.code = code;
    }

    public DiseaseDTO(String name, List<PatientDTO> patientDTOS) {
        this(name);
        this.patientDTOS = patientDTOS;
    }

    public DiseaseDTO(long code, String name, List<PatientDTO> patientDTOS) {
        this(name, patientDTOS);
        this.code = code;
    }


    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<PatientDTO> getPatientDTOS() {
        return patientDTOS;
    }

    public void setPatientDTOS(List<PatientDTO> patientDTOS) {
        this.patientDTOS = patientDTOS;
    }
}
