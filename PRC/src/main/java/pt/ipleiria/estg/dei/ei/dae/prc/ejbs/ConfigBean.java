package pt.ipleiria.estg.dei.ei.dae.prc.ejbs;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import pt.ipleiria.estg.dei.ei.dae.prc.Data;

@Startup
@Singleton
public class ConfigBean {

    @EJB
    PatientBean patientBean;

    @EJB
    BiomedicDataTypeBean biomedicDataTypeBean;

    @EJB
    HealthcareProfessionalBean healthcareProfessionalBean;
    @PostConstruct
    public void populateDB() {
        healthcareProfessionalBean.create("medicaMaria", 987654321, "Maria Gomes", "maria_gomes@gmail.com", "1234", "22/09/2021", "924359214");
        patientBean.create("fabiogaspar11","Fábio Gaspar","2191264@my.ipleiria.pt","12345","04/12/2001","916364061",123456789,60,(float)1.68, "medicaMaria");
        biomedicDataTypeBean.create(1,"Febre","ºC",30,45);
    }
}

