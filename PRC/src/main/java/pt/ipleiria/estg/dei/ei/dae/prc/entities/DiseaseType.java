package pt.ipleiria.estg.dei.ei.dae.prc.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllDiseaseTypes",
                query = "SELECT d FROM DiseaseType d ORDER BY d.code"
        )
})
@Table(name = "diseasetypes")
public class DiseaseType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int code;
    @NotNull
    private String name;
    //TODO RELATION
    @OneToMany(mappedBy = "diseaseType", cascade = CascadeType.REMOVE)
    private List<Disease> diseases;

    public DiseaseType() {
        this.diseases = new LinkedList<>();
    }

    public DiseaseType(String name) {
        this();
        this.name = name;
    }

    public DiseaseType(int code, String name) {
        this(name);
        this.code = code;
    }

    public void addDisease(Disease disease){
        if(disease == null || diseases.contains(disease)){
            return;
        }
        diseases.add(disease);
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

}
