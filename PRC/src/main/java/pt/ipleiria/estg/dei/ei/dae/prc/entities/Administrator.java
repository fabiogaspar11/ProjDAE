package pt.ipleiria.estg.dei.ei.dae.prc.entities;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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

    @NotNull
    private long citizenNumber;

    public Administrator(String username, String name, String email, String password, String birthDate, String contact, long citizenNumber) {
        super(username, name, email, password, birthDate, contact);
        this.citizenNumber= citizenNumber;
    }

    public Administrator() {
        super();
    }

    public long getCitizenNumber() {
        return citizenNumber;
    }

    public void setCitizenNumber(long citizenNumber) {
        this.citizenNumber = citizenNumber;
    }
}
