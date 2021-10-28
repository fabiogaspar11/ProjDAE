package pt.ipleiria.estg.dei.ei.dae.prc.entities;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllAdministrators",
                query = "SELECT s FROM Administrator s ORDER BY s.name" // JPQL
        )
})
public class Administrator extends User implements Serializable {
    public Administrator(String username, String name, String email, String password, String birthDate, String contact) {
        super(username, name, email, password, birthDate, contact);
    }

    public Administrator() {
        super();
    }


}
