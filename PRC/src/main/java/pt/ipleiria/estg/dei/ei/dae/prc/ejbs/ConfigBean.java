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
    BiomedicDataMeasureBean biomedicDataMeasureBean;

    @EJB
    PrescriptionBean prescriptionBean;

    @PostConstruct
    public void populateDB() throws MyEntityExistsException, MyEntityNotFoundException {
        try{
        healthcareProfessionalBean.create(900000001, "Maria Gomes", "mariagomes@mail.com", "12345", "22/09/1966", "924315414");
        healthcareProfessionalBean.create(900000002, "Ricardo Horta", "ricardoHorta@mail.com", "12345", "21/06/1987", "909000712");
        healthcareProfessionalBean.create(900000003, "Alicia Malhoa", "aliciaMalhoa@mail.com", "12345", "11/03/1998", "909212312");
        healthcareProfessionalBean.create(900000004, "Anibal Andre", "anibalandre@mail.com", "12345", "12/03/1983", "901345621");
        healthcareProfessionalBean.create(900000005, "Vasco Franco", "vascofranco@mail.com", "12345", "15/12/1982", "900000763");
        healthcareProfessionalBean.create(900000006, "Marco Santos", "vascofranco@mail.com", "12345", "01/09/1990", "900112763");
        String usernameH1 = healthcareProfessionalBean.create(900000007, "Clara Sousa", "clara@mail.com", "12345", "22/10/2000", "924359214");

        String usernameP1 = patientBean.create("Fabio Gaspar", "fabio@my.ipleiria.pt", "04/12/2001", "916364061", 900000011,"Masculino");                     //20 Anos
        String usernameP2 = patientBean.create("Carla Mendes","carla@my.ipleiria.pt","05/06/2021","916345345",900000012,"Feminino");                          //6 Meses
        String usernameP3 = patientBean.create("Carlos Costa","carlos@my.ipleiria.pt","11/04/2015","912343274",900000013,"Masculino");                         //6 Anos
        String usernameP4 = patientBean.create("Daniel Carreira","daniel@my.ipleiria.pt","12/08/2008","917766763",900000014,"Masculino");                      //13 Anos
        String usernameP5 = patientBean.create("Rafael Pereira","rafael@my.ipleiria.pt","14/06/2021","987786541",900000015,"Masculino");                       //6 meses
        String usernameP6 = patientBean.create("Bruna Leitao","bruna@my.ipleiria.pt","23/06/2012","900086541",900000016,"Feminino");                         //9 Anos
        patientBean.create("Rodrigo Domingues","rodrigo@my.ipleiria.pt","13/06/1950","901236541",900000017,"Masculino");                                        //71 Anos


        administratorBean.create("Ricardo Reis","ricardo.reis@clinic.com", "12345", "17/04/1960", "919191911", 978675432);
        administratorBean.create("Alicia Mendes","alicia.mendes@clinic.com", "12345", "02/10/1966", "918347563", 156789014);
        administratorBean.create("Jose Sousa","josesousa@clinic.com", "12345", "01/01/1977", "912147285", 156789999);


        biomedicDataTypeBean.create( "Temperatura Corporal","ºC",35,37.5F,30,48,0,0);
        biomedicDataTypeBean.create( "Peso", "Kilogramas", 60, 100,0,300,-5,-5);
        biomedicDataTypeBean.create( "Pressão arterial sistólica", "mmHg", 100, 130,0,210,2,2);
        biomedicDataTypeBean.create( "Pressão arterial diastólica", "mmHg", 50, 80,0,100,2,2);
        biomedicDataTypeBean.create( "Frequência Cardíaca", "Batimentos", 70, 73,60,80,1,1);
        biomedicDataTypeBean.create( "Frequência Respiratória", "mpm", 16, 20,12,26,1,2);



        for(int i = 1; i < 8; i++){
            String code = String.valueOf(i);
            int value;
            for (int j = 1; j < 8; j++) {
                String codePatient = String.valueOf(j);
                healthcareProfessionalBean.addPatientToHealthcareprofessional("P90000001" + codePatient,"H90000000" + code);
                prescriptionBean.create("Pressão arterial - Controlo","","Yes","O paciente deve continuar com as caminhadas 6 dias por semana. Realizar testes arteriais com a ajuda de um farmaceutico","01/11/2020","28/12/2022","P90000001" + codePatient ,"H90000000" + code);
                prescriptionBean.create("Insuficiência cardíaca","Edema","Yes","Fazer exercicios que estimulem o coração","21/12/2021","21/12/2022","P90000001" + codePatient,"H90000000" + code);
                prescriptionBean.create("insuficiência cardíaca sistólica","","Yes","Lisinopril 20 a 30 mg uma vez ao dia e ramipril 5 mg duas vezes","17/08/2021","17/08/2022","P90000001" + codePatient,"H90000000" + code);
                prescriptionBean.create("Insuficiência cardíaca com fração de ejeção preservada","","Yes","Enalapril 10 a 20 mg","15/09/2021","12/06/2022","P90000001" + codePatient,"H90000000" + code);
                prescriptionBean.create("Acumulação de gordura abdominal","","Yes","Caminhadas 6 dias por semana. O paciente deve ainda tomar vitaminas B12 durante 10 dias","03/10/2021","10/12/2022","P90000001" + codePatient,"H90000000" + code);
                prescriptionBean.create("Pressão arterial - Controlo","","Yes","O paciente deve continuar com as caminhadas 6 dias por semana. O paciente deve ainda tomar vitaminas B12 e C até a proxima consulta","28/12/2021","28/12/2022","P90000001" + codePatient,"H90000000" + code);
            }
        }


        biomedicDataMeasureBean.create(37,"01/01/2022","11:25","P900000011",1, "H900000001");
        biomedicDataMeasureBean.create(130,"02/01/2022","11:27","P900000011",2, "P900000011");
        biomedicDataMeasureBean.create(20,"02/01/2022","16:00","P900000011",5, "P900000011");

        biomedicDataMeasureBean.create(115,"02/01/2022","16:16","P900000012",3, "H900000002");
        biomedicDataMeasureBean.create(90,"29/12/2021","15:51","P900000012",4, "H900000005");
        biomedicDataMeasureBean.create(42,"01/01/2022","05:06","P900000012",1, "H900000001");


        biomedicDataMeasureBean.create(72,"02/01/2022","16:16","P900000013",5, "H900000003");
        biomedicDataMeasureBean.create(17,"29/12/2021","15:51","P900000013",6, "H900000006");
        biomedicDataMeasureBean.create(39,"01/01/2022","05:06","P900000013",1, "H900000001");

        biomedicDataMeasureBean.create(36.5,"01/01/2022","05:23","P900000014",1, "H900000003");
        biomedicDataMeasureBean.create(37.2,"01/01/2022","21:23","P900000014",1, "H900000004");


        long code1 = diseaseBean.create("Hipertensão");
        long code2 = diseaseBean.create("Infarto agudo do miocárdio");
        long code3 = diseaseBean.create("Endocardite");
        long code4 = diseaseBean.create("Arritmias cardíacas");
        long code5 = diseaseBean.create("Angina");
        long code6 = diseaseBean.create("Miocardite");


        patientBean.addDiseaseToPatient(usernameP1,code1);
        patientBean.addDiseaseToPatient(usernameP2,code2);
        patientBean.addDiseaseToPatient(usernameP3,code3);
        patientBean.addDiseaseToPatient(usernameP3,code5);
        patientBean.addDiseaseToPatient(usernameP4,code4);
        patientBean.addDiseaseToPatient(usernameP5,code5);
        patientBean.addDiseaseToPatient(usernameP6,code6);
        patientBean.addDiseaseToPatient(usernameP6,code1);

        }catch (Exception e){
            logger.log(Level.SEVERE, e.getMessage());
        }
    }



}

