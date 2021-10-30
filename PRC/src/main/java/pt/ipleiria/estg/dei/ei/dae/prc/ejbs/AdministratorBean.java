package pt.ipleiria.estg.dei.ei.dae.prc.ejbs;

import pt.ipleiria.estg.dei.ei.dae.prc.entities.Administrator;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class AdministratorBean {
    @PersistenceContext
    EntityManager entityManager;

    public void create(String username, String name, String email, String password, String birthDate, String contact) throws MyEntityExistsException, MyEntityNotFoundException {
        Administrator administratorExists = entityManager.find(Administrator.class,username);
        if (administratorExists == null) {
            Administrator administrator = new Administrator(username, name, email, password, birthDate, contact);
            entityManager.persist(administrator);
        }
        else{
            throw new MyEntityExistsException("Administrator with username: " + username + "already exists");
        }
    }

    public void delete(String username) throws MyEntityNotFoundException {
        Administrator administrator = findAdministrator(username);
        if (administrator != null) {
            entityManager.remove(administrator);
        }
    }

    public void update(String username, String name, String email, String password, String birthDate, String contact) throws MyEntityNotFoundException {
        Administrator administrator = findAdministrator(username);
        if (administrator != null){
            administrator.setName(name);
            administrator.setEmail(email);
            administrator.setPassword(password);
            administrator.setBirthDate(birthDate);
            administrator.setContact(contact);
        }
    }

    public List<Administrator> getAllAdministrators() {
        return (List<Administrator>) entityManager.createNamedQuery("getAllAdministrators").getResultList();
    }

    public Administrator findAdministrator(String username) throws MyEntityNotFoundException {
        Administrator administrator = entityManager.find(Administrator.class, username);
        if (administrator != null){
            return administrator;
        }
        else{
            throw new MyEntityNotFoundException("Administrator with username: " + username + " not found.");
        }
    }


}
