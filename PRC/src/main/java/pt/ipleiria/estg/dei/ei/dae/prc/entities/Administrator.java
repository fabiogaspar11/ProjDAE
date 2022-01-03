package pt.ipleiria.estg.dei.ei.dae.prc.entities;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllAdministrators",
                query = "SELECT s FROM Administrator s ORDER BY s.name" // JPQL
        )
})
public class Administrator extends User implements Serializable {
    @Version private int version;
    public Administrator(String username, String name, String email, String password, String birthDate, String contact, long healthNumber) {
        super(username, name, email, password, birthDate, contact,healthNumber);
    }

    public Administrator() {
        super();
    }

}
