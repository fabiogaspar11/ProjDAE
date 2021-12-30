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
        patientBean.create("Daniel Carreira","2191215@my.ipleiria.pt","12345","11/08/2001","917766763",987654345);
        patientBean.create("Rafael Pereira","2191266@my.ipleiria.pt","12345","05/05/1999","987786541",981104345);
        patientBean.create("Bruna Leitão","2191268@my.ipleiria.pt","12345","23/06/1999","900086541",981066345);
        patientBean.create("Rodrigo Domingues","2198732@my.ipleiria.pt","12345","09/06/1960","901236541",981009345);


        healthcareProfessionalBean.create(987635151, "Maria Gomes", "mariagomes@mail.com", "12345", "22/09/1966", "924315414", "medic");
        healthcareProfessionalBean.create(987624242, "Ricardo Horta", "ricardoHorta@mail.com", "12345", "21/06/1987", "909000712", "medic");
        healthcareProfessionalBean.create(987613333, "Alicia Malhoa", "aliciaMalhoa@mail.com", "12345", "11/03/1998", "909212312", "medic");
        healthcareProfessionalBean.create(987672424, "Anibal André", "anibalandre@mail.com", "12345", "12/03/1983", "901345621", "medic");
        healthcareProfessionalBean.create(987601515, "Vasco Franco", "vascofranco@mail.com", "12345", "15/12/1982", "900000763", "medic");
        healthcareProfessionalBean.create(900837261, "Marco Santos", "vascofranco@mail.com", "12345", "01/09/1990", "900112763", "medic");
        String usernameH1 = healthcareProfessionalBean.create(111111321, "Clara Sousa", "clara@mail.com", "1234", "22/10/2000", "924359214", "medic");

        administratorBean.create("Ricardo Reis","ricardo.reis@clinic.com", "12345", "17/04/1960", "919191911", 978675432);
        administratorBean.create("Alicia Mendes","alicia.mendes@clinic.com", "12345", "02/10/1966", "918347563", 156789014);
        administratorBean.create("José Sousa","josesousa@clinic.com", "12345", "01/01/1977", "91214785", 156789999);



        biomedicDataTypeBean.create("Temperatura Corporal","ºC",30,45);
        biomedicDataTypeBean.create( "Obesidade", "Kilogramas", 40, 100);
        biomedicDataTypeBean.create( "Pressão arterial", "mmHg", 85, 89);
        biomedicDataTypeBean.create( "Frequência Cardíaca", "Batimentos", 73, 76);
        biomedicDataTypeBean.create( "Frequência Respiratória", "mpm", 20, 25);




            //prescriptionBean.create(1, "Prescrição - Medicamentos", "Ipobrunfeno - 3g/dia, Griponal - 5g/dia", "01/11/2021", "01/12/2021", "P123456789", );



        prescriptionBean.create("Insuficiência cardíaca","Edema","Yes","Fazer exercicios que estimulem o coração","21/12/2021","21/12/2022","123456789","H111111321");
        long code = diseaseBean.create("Hipertensão");
        diseaseBean.addDiseaseToPatient(code, usernameP1);

        healthcareProfessionalBean.addPatientFromHealthcareprofessional(usernameP1,usernameH1);

        }catch (Exception e){
            logger.log(Level.SEVERE, e.getMessage());
        }
    }



}

