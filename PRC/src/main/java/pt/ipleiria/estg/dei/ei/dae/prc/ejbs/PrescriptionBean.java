package pt.ipleiria.estg.dei.ei.dae.prc.ejbs;

import pt.ipleiria.estg.dei.ei.dae.prc.dtos.PatientDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.dtos.PrescriptionDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.HealthcareProfessional;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Patient;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Prescription;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class PrescriptionBean {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    EmailBean emailBean;
    public long create(String title, String observations,String isPharmacological,String treatmentInfo, String emissionDate, String expireDate, String usernamePatient, String usernameHealthcareProfessional) throws MyEntityExistsException, MyEntityNotFoundException, MessagingException {
        Patient patient = entityManager.find(Patient.class,usernamePatient);
        if(patient == null) {
            throw new MyEntityNotFoundException("There is no Patient with the username \'" + usernamePatient + "\'");
        }
        HealthcareProfessional healthcareProfessional = entityManager.find(HealthcareProfessional.class, usernameHealthcareProfessional);
        if(healthcareProfessional == null) {
            throw new MyEntityNotFoundException("There is no Healthcare Professional with the username \'" + usernameHealthcareProfessional + "\'");
        }
        boolean hasPatient = false;
        for (Patient patient1: healthcareProfessional.getPatients()) {
            if(patient1.getUsername().equals(patient.getUsername())) {
                hasPatient = true;
                break;
            }
        }
        if(!hasPatient){
            throw new MyEntityNotFoundException("Healthcare Professional can only create prescriptions for his patients");
        }

        if(observations==null){
            observations = "";
        }
        Prescription prescription = new Prescription(title, observations,isPharmacological,treatmentInfo, emissionDate, expireDate, patient, healthcareProfessional);
        patient.addPrescription(prescription);
        healthcareProfessional.addPrescription(prescription);
        healthcareProfessional.addPatient(patient);
        entityManager.persist(prescription);
        entityManager.flush();

        emailBean.send(patient.getEmail(), "Prescription n:" + prescription.getCode(), prescription.toString());

        return prescription.getCode();
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
        prescription.getHealthcareProfessional().removePrescription(prescription);
        entityManager.merge(prescription.getPatient());
        entityManager.merge(prescription.getHealthcareProfessional());
        entityManager.remove(entityManager.merge(prescription));
    }

    public void update(Prescription prescription, PrescriptionDTO prescriptionDTO) throws MyEntityNotFoundException {
        entityManager.lock(entityManager.merge(prescription), LockModeType.PESSIMISTIC_WRITE);
        if(prescriptionDTO.getTitle() != null && !prescription.getTitle().equals(prescriptionDTO.getTitle())){
            prescription.setTitle(prescriptionDTO.getTitle());
        }
        if(prescriptionDTO.getObservations() != null && !prescription.getObservations().equals(prescriptionDTO.getObservations())){
            prescription.setObservations(prescriptionDTO.getObservations());
        }
        if(prescriptionDTO.getIsPharmacological() != null && !prescription.getIsPharmacological().equals(prescriptionDTO.getIsPharmacological())){
            prescription.setIsPharmacological(prescriptionDTO.getIsPharmacological());
        }
        if(prescriptionDTO.getTreatmentInfo() != null && !prescription.getTreatmentInfo().equals(prescriptionDTO.getTreatmentInfo())){
            prescription.setTreatmentInfo(prescriptionDTO.getTreatmentInfo());
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
