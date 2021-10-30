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

    @PostConstruct
    public void populateDB() throws MyEntityExistsException, MyEntityNotFoundException {
        healthcareProfessionalBean.create("medicaMaria", 987654321, "Maria Gomes", "maria_gomes@gmail.com", "1234", "22/09/2021", "924359214", "medic");
        patientBean.create("fabiogaspar11", "Fábio Gaspar", "2191264@my.ipleiria.pt", "12345", "04/12/2001", "916364061", 123456789, 60, (float) 1.68);

        administratorBean.create("carlAdmin", "Carl Carl", "ola@aaa.aaa", "carladmin123", "17/04/1960", "919191911");
        administratorBean.create("carla", "Carl Carl", "ola@aaa.aaa", "carladmin123", "17/04/1960", "919191911");
        administratorBean.create("rodrigo.faria","Rodrigo Faria","rodrigo.faria@my.ipleiria.pt","123","04/05/1970","911234322");

        //healthcareProfessionalBean.create("medicaMaria", 987654321, "Maria Gomes", "maria_gomes@gmail.com", "1234", "22/09/2021", "924359214");
        //patientBean.create("fabiogaspar11","Fábio Gaspar","2191264@my.ipleiria.pt","12345","04/12/2001","916364061",123456789,60,(float)1.68, "medicaMaria");
        //patientBean.create("carlamendes12","Carla Mendes","2191111@my.ipleiria.pt","12345","05/06/2001","916345345",123456789,60,(float)1.68, "medicaMaria");
        //patientBean.create("carloscosta13","Carlos Costa","2198322@my.ipleiria.pt","12345","14/03/2001","916364432",123456789,60,(float)1.68, "medicaMaria");


        //biomedicDataTypeBean.create(1,"Febre","ºC",30,45);
        //biomedicDataTypeBean.create(1, "Febre", "ºC", 30, 45);
    }



}

