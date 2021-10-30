package pt.ipleiria.estg.dei.ei.dae.prc.ejbs;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.*;

@Startup
@Singleton
public class ConfigBean {

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

    @PostConstruct
    public void populateDB() throws MyEntityExistsException, MyEntityNotFoundException {
       String usernameP1 = patientBean.create("Fábio Gaspar", "2191264@my.ipleiria.pt", "12345", "04/12/2001", "916364061", 123456789, 60, (float) 1.68);
        patientBean.create("Carla Mendes","2191111@my.ipleiria.pt","12345","05/06/1999","916345345",978675432,(float)55.5,(float)1.60);
        patientBean.create("Carlos Costa","2191131@my.ipleiria.pt","12345","11/04/1984","912343274",987654321,(float)70.2,(float)1.80);

        healthcareProfessionalBean.create(987654321, "Maria Gomes", "maria_gomes@gmail.com", "1234", "22/09/2021", "924359214", "medic");
        healthcareProfessionalBean.create(111111321, "Clara Sousa", "clara@gmail.com", "1234", "22/10/2000", "924359214", "medic");

        administratorBean.create("Ricardo Reis","ricardo.reis@clinic.com", "password", "17/04/1960", "919191911", 142394274);
        administratorBean.create("Alicia Mendes","alicia.mendes@clinic.com", "qwerty", "02/10/1966", "918347563", 123475471);

        biomedicDataTypeBean.create(1,"Febre","ºC",30,45);
        biomedicDataTypeBean.create(1, "Obesidade", "ºC", 40, 100);

        diseaseBean.create(1, "Hipertensão", "Estágio 1");
        diseaseBean.enrollDiseaseInPatient(1, usernameP1);
    }



}

