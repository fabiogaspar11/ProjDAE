package pt.ipleiria.estg.dei.ei.dae.prc.ejbs;

import pt.ipleiria.estg.dei.ei.dae.prc.Data;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Patient;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PatientBean {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(String username, String name, String email, String password, String birthDate, String contact, long healthUserNumber, float weight, float height){
        Patient patient = new Patient(username,name,email,password,birthDate,contact,healthUserNumber,weight,height);
        entityManager.persist(patient);
    }
}
