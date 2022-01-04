package pt.ipleiria.estg.dei.ei.dae.prc.ejbs;

import pt.ipleiria.estg.dei.ei.dae.prc.dtos.AdministratorDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.dtos.HealthcareProfessionalDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Administrator;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.HealthcareProfessional;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.User;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class AdministratorBean {
    @PersistenceContext
    EntityManager entityManager;

    public String create(String name, String email, String password, String birthDate, String contact, long healthNumber) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        if(Long.toString(healthNumber).length() != 9){
            throw new MyConstraintViolationException("Health Number can only have 9 digits");
        }
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
        entityManager.lock(entityManager.merge(administrator), LockModeType.OPTIMISTIC);
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
    public void updatePassword(Administrator administrator, AdministratorDTO administratorDTO) throws MyIllegalArgumentException {
        if (!administrator.getPassword().equals(hashPassword(administratorDTO.getPasswordOld()))){
            throw new MyIllegalArgumentException("Password field must be the same as current password");
        }
        if (hashPassword(administratorDTO.getPassword()).equals(hashPassword(administratorDTO.getPasswordOld()))){
            throw new MyIllegalArgumentException("Old Password and New password cannot be the same");
        }
        administrator.setPassword(administratorDTO.getPassword());
        entityManager.merge(administrator);
    }
    private static String hashPassword(String password) {
        char[] encoded = null;
        try {
            ByteBuffer passwdBuffer =
                    Charset.defaultCharset().encode(CharBuffer.wrap(password));
            byte[] passwdBytes = passwdBuffer.array();
            MessageDigest mdEnc = MessageDigest.getInstance("SHA-256");
            mdEnc.update(passwdBytes, 0, password.toCharArray().length);
            encoded = new BigInteger(1, mdEnc.digest()).toString(16).toCharArray();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new String(encoded);
    }
}
