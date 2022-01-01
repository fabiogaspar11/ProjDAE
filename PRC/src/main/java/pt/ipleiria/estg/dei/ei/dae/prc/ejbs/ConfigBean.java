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

        healthcareProfessionalBean.create(900000001, "Maria Gomes", "mariagomes@mail.com", "12345", "22/09/1966", "924315414", "medic");
        healthcareProfessionalBean.create(900000002, "Ricardo Horta", "ricardoHorta@mail.com", "12345", "21/06/1987", "909000712", "medic");
        healthcareProfessionalBean.create(900000003, "Alicia Malhoa", "aliciaMalhoa@mail.com", "12345", "11/03/1998", "909212312", "medic");
        healthcareProfessionalBean.create(900000004, "Anibal André", "anibalandre@mail.com", "12345", "12/03/1983", "901345621", "medic");
        healthcareProfessionalBean.create(900000005, "Vasco Franco", "vascofranco@mail.com", "12345", "15/12/1982", "900000763", "medic");
        healthcareProfessionalBean.create(900000006, "Marco Santos", "vascofranco@mail.com", "12345", "01/09/1990", "900112763", "medic");
        String usernameH1 = healthcareProfessionalBean.create(900000007, "Clara Sousa", "clara@mail.com", "12345", "22/10/2000", "924359214", "medic");

        String usernameP1 = patientBean.create("Fábio Gaspar", "2191264@my.ipleiria.pt", "04/12/2001", "916364061", 900000011);
        //patientBean.create("Carla Mendes","2191111@my.ipleiria.pt","05/06/1999","916345345",900000012);
        //patientBean.create("Carlos Costa","2191131@my.ipleiria.pt","11/04/1984","912343274",900000013);
        patientBean.create("Daniel Carreira","2191215@my.ipleiria.pt","12/08/2001","917766763",900000014);
        patientBean.create("Rafael Pereira","2191266@my.ipleiria.pt","14/06/1999","987786541",900000015);
        //patientBean.create("Bruna Leitão","2191268@my.ipleiria.pt","23/06/1999","900086541",900000016);
        patientBean.create("Rodrigo Domingues","2198732@my.ipleiria.pt","13/06/1960","901236541",900000017);


        administratorBean.create("Ricardo Reis","ricardo.reis@clinic.com", "12345", "17/04/1960", "919191911", 978675432);
        administratorBean.create("Alicia Mendes","alicia.mendes@clinic.com", "12345", "02/10/1966", "918347563", 156789014);
        administratorBean.create("José Sousa","josesousa@clinic.com", "12345", "01/01/1977", "91214785", 156789999);


        biomedicDataTypeBean.create("Temperatura Corporal","ºC",30,45);
        biomedicDataTypeBean.create( "Obesidade", "Kilogramas", 40, 100);
        biomedicDataTypeBean.create( "Pressão arterial", "mmHg", 85, 89);
        biomedicDataTypeBean.create( "Frequência Cardíaca", "Batimentos", 73, 76);
        biomedicDataTypeBean.create( "Frequência Respiratória", "mpm", 20, 25);


        for(int i = 1; i < 8; i++){
            String code = String.valueOf(i);
            for (int j = 1; j < 8; j++) {
                String codePatient = String.valueOf(j);
                healthcareProfessionalBean.addPatientToHealthcareprofessional("P90000001" + codePatient,"H90000000" + code);
                prescriptionBean.create("Pressão arterial - Controlo","","Yes","O paciente deve continuar com as caminhadas 6 dias por semana. Realizar testes arteriais com a ajuda de um farmaceutico","01/11/2020","28/12/2022","90000001" + codePatient ,"H90000000" + code);
                prescriptionBean.create("Insuficiência cardíaca","Edema","Yes","Fazer exercicios que estimulem o coração","21/12/2021","21/12/2022","90000001" + codePatient,"H90000000" + code);
                prescriptionBean.create("insuficiência cardíaca sistólica","","Yes","Lisinopril 20 a 30 mg uma vez ao dia e ramipril 5 mg duas vezes","17/08/2021","17/08/2022","90000001" + codePatient,"H90000000" + code);
                prescriptionBean.create("Insuficiência cardíaca com fração de ejeção preservada","","Yes","Enalapril 10 a 20 mg","15/09/2021","12/06/2022","90000001" + codePatient,"H90000000" + code);
                prescriptionBean.create("Acumulação de gordura abdominal","","Yes","Caminhadas 6 dias por semana. O paciente deve ainda tomar vitaminas B12 durante 10 dias","03/10/2021","10/12/2022","90000001" + codePatient,"H90000000" + code);
                prescriptionBean.create("Pressão arterial - Controlo","","Yes","O paciente deve continuar com as caminhadas 6 dias por semana. O paciente deve ainda tomar vitaminas B12 e C até a proxima consulta","28/12/2021","28/12/2022","90000001" + codePatient,"H90000000" + code);
            }
        }

        long code = diseaseBean.create("Hipertensão");
        diseaseBean.addDiseaseToPatient(code, usernameP1);



        }catch (Exception e){
            logger.log(Level.SEVERE, e.getMessage());
        }
    }



}

