package pt.ipleiria.estg.dei.ei.dae.prc.ejbs;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@Startup
@Singleton
public class ConfigBean {
    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");

    @EJB
    PatientBean patientBean;

    @EJB
    BiomedicDataTypeBean biomedicDataTypeBean;

    @EJB
    HealthcareProfessionalBean healthcareProfessionalBean;

    @EJB
    AdministratorBean administratorBean;

    @EJB
    DiseaseBean diseaseBean;

    @EJB
    PrescriptionBean prescriptionBean;

    @PostConstruct
    public void populateDB() throws MyEntityExistsException, MyEntityNotFoundException {
        try{
        String usernameP1 = patientBean.create("Fábio Gaspar", "2191264@my.ipleiria.pt", "12345", "04/12/2001", "916364061", 123456789);
        patientBean.create("Carla Mendes","2191111@my.ipleiria.pt","12345","05/06/1999","916345345",978675432);
        patientBean.create("Carlos Costa","2191131@my.ipleiria.pt","12345","11/04/1984","912343274",987654321);

        healthcareProfessionalBean.create(987654321, "Maria Gomes", "maria_gomes@gmail.com", "1234", "22/09/2021", "924359214", "medic");
        String usernameH1 = healthcareProfessionalBean.create(111111321, "Clara Sousa", "clara@gmail.com", "1234", "22/10/2000", "924359214", "medic");

        administratorBean.create("Ricardo Reis","ricardo.reis@clinic.com", "password", "17/04/1960", "919191911", 142394274);
        administratorBean.create("Alicia Mendes","alicia.mendes@clinic.com", "qwerty", "02/10/1966", "918347563", 123475471);

        //biomedicDataTypeBean.create(1,"Febre","ºC",30,45);
        //biomedicDataTypeBean.create(2, "Obesidade", "Kilogramas", 40, 100);

        prescriptionBean.create(1, "Prescrição - Medicamentos", "Ipobrunfeno - 3g/dia, Griponal - 5g/dia", "01/11/2021", "01/12/2021", usernameP1, usernameH1);
        int code = diseaseBean.create("Hipertensão");
        diseaseBean.addDiseaseToPatient(code, usernameP1);

        //healthcareProfessionalBean.addPrescriptionFromHealthcareprofessional(code, usernameH1);
        if (healthcareProfessionalBean.addPatientFromHealthcareprofessional(usernameP1,usernameH1)){
            System.out.println("WEFEWSU9HFE7Y89UFEHU8WFGHY8EWGY8UEWDGY87FEWFYG78EWYG8FEWY678GF6Y7GET8WRYG678FEW");
        };

        }catch (Exception e){
            logger.log(Level.SEVERE, e.getMessage());
        }
    }



}

