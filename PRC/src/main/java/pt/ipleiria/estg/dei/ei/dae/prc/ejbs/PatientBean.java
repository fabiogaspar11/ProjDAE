package pt.ipleiria.estg.dei.ei.dae.prc.ejbs;

import pt.ipleiria.estg.dei.ei.dae.prc.entities.Disease;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Patient;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class PatientBean {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(String username, String name, String email, String password, String birthDate, String contact, long healthUserNumber, float weight, float height) throws MyEntityExistsException {
        Patient patient = entityManager.find(Patient.class, username);
        if(patient != null) throw new MyEntityExistsException("A patient with the username \'" + username + "\' already exists");
        patient = new Patient(username,name,email,password,birthDate,contact,healthUserNumber,weight,height);
        entityManager.persist(patient);
    }

    public List<Patient> getAllPatients() {
        return (List<Patient>) entityManager.createNamedQuery("getAllPatients").getResultList();
    }

    public Patient findPatient(String username) throws MyEntityNotFoundException {
        Patient patient = entityManager.find(Patient.class, username);
        if(patient == null) throw new MyEntityNotFoundException("There is no Patient with the username: \'"+username+"\'");
        return patient;
    }

    public void enrollPatientInDisease(String username, int code) throws MyEntityNotFoundException {
        Patient patient = findPatient(username);
        if (patient != null){
            Disease disease = entityManager.find(Disease.class, code);
            if (disease != null) {
                if (!disease.getPatients().contains(patient)){
                    patient.addDisease(disease);
                    disease.addPatient(patient);
                }
            }
            else{
                throw new MyEntityNotFoundException("Patient with username: " + username + " not found.");
            }
        }
    }

    public void unrollPatientInDisease(String username, int code) throws MyEntityNotFoundException {
        Patient patient = findPatient(username);
        if (patient != null){
            Disease disease = entityManager.find(Disease.class, code);
            if (disease != null) {
                if (!disease.getPatients().contains(patient)){
                    patient.removeDisease(disease);
                    disease.removePatient(patient);
                }
            }
            else{
                throw new MyEntityNotFoundException("Patient with username: " + username + " not found.");
            }
        }
    }

}
