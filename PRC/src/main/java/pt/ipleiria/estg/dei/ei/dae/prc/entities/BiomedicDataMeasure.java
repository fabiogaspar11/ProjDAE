package pt.ipleiria.estg.dei.ei.dae.prc.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class BiomedicDataMeasure implements Serializable {
    @Id
    private long code;
}
