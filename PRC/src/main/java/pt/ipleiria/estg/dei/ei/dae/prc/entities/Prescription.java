package pt.ipleiria.estg.dei.ei.dae.prc.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "prescriptions")
@Entity
public class Prescription implements Serializable {
    @Id
    private long code;

}
