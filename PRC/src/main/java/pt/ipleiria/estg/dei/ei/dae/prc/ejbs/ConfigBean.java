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
        healthcareProfessionalBean.create( 987654321, "Maria Gomes", "maria_gomes@gmail.com", "1234", "22/09/2021", "924359214", "medic");
        patientBean.create( "Fábio Gaspar", "2191264@my.ipleiria.pt", "12345", "04/12/2001", "916364061", 111111111, 60, (float) 1.68);
        patientBean.create("Carla Mendes","2191111@my.ipleiria.pt","12345","05/06/2001","916345345",111111112,60,(float)1.68);
        patientBean.create("Carlos Costa","2198322@my.ipleiria.pt","12345","14/03/2001","916364432",111111113,60,(float)1.68);
        administratorBean.create("Carl Carl", "Carl@gmail.com", "carladmin123", "17/04/1960", "919191911", 111111114);
        administratorBean.create("Rodrigo Faria","rodrigo.faria@gmail.com","smdnfkdj","21/02/1989","916364432",111111115);

        //biomedicDataTypeBean.create(1,"Febre","ºC",30,45);
        //biomedicDataTypeBean.create(1, "Febre", "ºC", 30, 45);

        diseaseBean.create(1, "Hipertensão", "130 x 80 mg");
        diseaseBean.enrollDiseaseInPatient(1, "P111111111");

    }



}

