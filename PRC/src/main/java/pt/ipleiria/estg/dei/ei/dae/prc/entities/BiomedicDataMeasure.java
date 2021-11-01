package pt.ipleiria.estg.dei.ei.dae.prc.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

//TODO
@Table(name = "biomedicdatameasures")
@Entity
public class BiomedicDataMeasure implements Serializable {
    @Id
    private long code;
}
