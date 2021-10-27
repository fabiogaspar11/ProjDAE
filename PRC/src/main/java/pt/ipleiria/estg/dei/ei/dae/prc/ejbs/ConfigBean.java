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

    @PostConstruct
    public void populateDB() {
        patientBean.create("fabiogaspar11","Fábio Gaspar","2191264@my.ipleiria.pt","12345","04/12/2001","916364061",123456789,60,(float)1.68);
    }
}

