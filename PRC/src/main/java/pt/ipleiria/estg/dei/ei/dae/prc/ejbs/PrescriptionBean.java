package pt.ipleiria.estg.dei.ei.dae.prc.ejbs;

import pt.ipleiria.estg.dei.ei.dae.prc.dtos.PatientDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.dtos.PrescriptionDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.HealthcareProfessional;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Patient;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Prescription;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class PrescriptionBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(long code, String title, String observations, String emissionDate, String expireDate, String usernamePatient, String usernameHealthcareProfessional) throws MyEntityExistsException, MyEntityNotFoundException {
        Prescription prescription = entityManager.find(Prescription.class,code);
        if(prescription != null) {
            throw new MyEntityExistsException("A Prescription with the code \'" + code + "\' already exists");
        }
        Patient patient = entityManager.find(Patient.class,usernamePatient);
        if(patient == null) {
            throw new MyEntityNotFoundException("There is no Patient with the username \'" + usernamePatient + "\'");
        }
        HealthcareProfessional healthcareProfessional = entityManager.find(HealthcareProfessional.class, usernameHealthcareProfessional);
        if(healthcareProfessional == null) {
            throw new MyEntityNotFoundException("There is no Healthcare Professional with the username \'" + usernameHealthcareProfessional + "\'");
        }
        prescription = new Prescription(code, title, observations, emissionDate, expireDate, patient, healthcareProfessional);
        patient.addPrescription(prescription);
        healthcareProfessional.addPrescription(prescription);
        entityManager.persist(prescription);
        entityManager.merge(patient);
    }

    public List<Prescription> getAllPrescriptions() {
        return (List<Prescription>) entityManager.createNamedQuery("getAllPrescriptions").getResultList();
    }

    public Prescription findPrescription(long code) throws MyEntityNotFoundException {
        Prescription prescription = entityManager.find(Prescription.class, code);
        if(prescription == null) throw new MyEntityNotFoundException("There is no Prescription with the code: \'"+code+"\'");
        return prescription;
    }

    public void remove(Prescription prescription){
        prescription.getPatient().removePrescription(prescription);
        entityManager.merge(prescription.getPatient());
        entityManager.remove(entityManager.merge(prescription));
    }

    public void update(Prescription prescription, PrescriptionDTO prescriptionDTO) throws MyEntityNotFoundException {
        if(prescriptionDTO.getTitle() != null && !prescription.getTitle().equals(prescriptionDTO.getTitle())){
            prescription.setTitle(prescriptionDTO.getTitle());
        }
        if(prescriptionDTO.getObservations() != null && !prescription.getObservations().equals(prescriptionDTO.getObservations())){
            prescription.setObservations(prescriptionDTO.getObservations());
        }
        if(prescriptionDTO.getEmissionDate() != null && !prescription.getEmissionDate().equals(prescriptionDTO.getEmissionDate())){
            prescription.setEmissionDate(prescriptionDTO.getEmissionDate());
        }

        if(prescriptionDTO.getExpireDate() != null && !prescription.getExpireDate().equals(prescriptionDTO.getExpireDate())){
            prescription.setExpireDate(prescriptionDTO.getExpireDate());
        }

        if(prescriptionDTO.getUsernamePatient() != null && !prescription.getPatient().getUsername().equals(prescriptionDTO.getUsernamePatient())){
            Patient patientNew = entityManager.find(Patient.class,prescriptionDTO.getUsernamePatient());
            if(patientNew == null){
                throw new MyEntityNotFoundException("There is no Patient with the username \'"+prescriptionDTO.getUsernamePatient()+"\'");
            }
            Patient patientOld = prescription.getPatient();
            patientOld.removePrescription(prescription);
            prescription.setPatient(patientNew);
            patientNew.addPrescription(prescription);
            entityManager.merge(patientNew);
            entityManager.merge(patientOld);
        }
        //TODO Duvida 2 - deixar dar update ao profissional de saude msm que o paciente nao tenha o profissional de saude na lista?
        if(prescriptionDTO.getUsernameHealthcareProfessional() != null && !prescription.getHealthcareProfessional().getUsername().equals(prescriptionDTO.getUsernameHealthcareProfessional())){
            HealthcareProfessional healthcareProfessional = entityManager.find(HealthcareProfessional.class,prescriptionDTO.getUsernameHealthcareProfessional());
            prescription.setHealthcareProfessional(healthcareProfessional);
        }

        entityManager.merge(prescription);
    }
}
