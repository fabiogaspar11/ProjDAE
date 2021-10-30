package pt.ipleiria.estg.dei.ei.dae.prc.ejbs;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityNotFoundException;

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
        patientBean.create("fabiogaspar11","Fábio Gaspar","2191264@my.ipleiria.pt","12345","04/12/2001","916364061",123456789,60,(float)1.68);
        biomedicDataTypeBean.create(1,"Febre","ºC",30,45);
        administratorBean.create("carlAdmin", "Carl Carl", "ola@aaa.aaa", "carladmin123", "17/04/1960", "919191911");
    }
}

