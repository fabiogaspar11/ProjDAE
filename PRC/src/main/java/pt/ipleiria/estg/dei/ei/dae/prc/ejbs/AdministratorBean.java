package pt.ipleiria.estg.dei.ei.dae.prc.ejbs;

import pt.ipleiria.estg.dei.ei.dae.prc.dtos.AdministratorDTO;
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

    public String create(String name, String email, String password, String birthDate, String contact, long healthNumber) throws MyEntityExistsException, MyEntityNotFoundException {
        String username = "A"+ healthNumber;
        Administrator administratorExists = entityManager.find(Administrator.class,username);
        if (administratorExists == null) {
            administratorExists = new Administrator(username, name, email, password, birthDate, contact, healthNumber);
            entityManager.persist(administratorExists);
        }
        else{
            throw new MyEntityExistsException("Administrator with username: " + username + "already exists");
        }
        return administratorExists.getUsername();
    }

    public void delete(String username) throws MyEntityNotFoundException {
        Administrator administrator = findAdministrator(username);
        entityManager.remove(administrator);
    }

    public void update(Administrator administrator, AdministratorDTO administratorDTO) throws MyEntityNotFoundException {
        if(administratorDTO.getName() != null && !administrator.getName().equals(administratorDTO.getName())){
            administrator.setName(administratorDTO.getName());
        }
        if(administratorDTO.getEmail() != null && !administrator.getEmail().equals(administratorDTO.getEmail())){
            administrator.setEmail(administratorDTO.getEmail());
        }
        if(administratorDTO.getPassword() != null && !administrator.getPassword().equals(administratorDTO.getPassword())){
            administrator.setPassword(administratorDTO.getPassword());
        }
        if(administratorDTO.getBirthDate() != null && !administrator.getBirthDate().equals(administratorDTO.getBirthDate())){
            administrator.setBirthDate(administratorDTO.getBirthDate());
        }
        if(administratorDTO.getContact() != null && !administrator.getContact().equals(administratorDTO.getContact())){
            administrator.setContact(administratorDTO.getContact());
        }
        entityManager.merge(administrator);
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
