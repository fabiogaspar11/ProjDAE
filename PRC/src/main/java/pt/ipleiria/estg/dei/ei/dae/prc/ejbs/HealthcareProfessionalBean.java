package pt.ipleiria.estg.dei.ei.dae.prc.ejbs;

import pt.ipleiria.estg.dei.ei.dae.prc.entities.HealthcareProfessional;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Patient;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class HealthcareProfessionalBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(String username, long healthcareProfessionalNumber, String name, String email, String password, String birthDate, String contact){
        //TODO check if professional exists or not in the DB
        HealthcareProfessional healthcareProfessional = new HealthcareProfessional(username,healthcareProfessionalNumber, name, email, password, birthDate, contact);
        entityManager.persist(healthcareProfessional);
    }

    public List<HealthcareProfessional> getAllHealthcareProfessionals(){
        return (List<HealthcareProfessional>) entityManager.createNamedQuery("getAllHealthcareProfessionals").getResultList();
    }
    public HealthcareProfessional findHealthcareProfessional(String username){
        return entityManager.find(HealthcareProfessional.class, username);
    }
}
