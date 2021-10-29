package pt.ipleiria.estg.dei.ei.dae.prc.ws;

import pt.ipleiria.estg.dei.ei.dae.prc.dtos.HealthcareProfessionalDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.dtos.PatientDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.ejbs.HealthcareProfessionalBean;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.HealthcareProfessional;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Patient;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path('healthcareprofessionals')
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
//TODO
public class HealthcareprofessionalService {
    @EJB
    private HealthcareProfessionalBean healthcareProfessionalBean;

    private HealthcareProfessionalDTO toDTO(HealthcareProfessional healthcareProfessional){
        return new HealthcareProfessionalDTO(
                healthcareProfessional.getUsername(),
                healthcareProfessional.getName(),
                healthcareProfessional.getEmail(),
                healthcareProfessional.getPassword(),
                healthcareProfessional.getBirthDate(),
                healthcareProfessional.getContact(),
                healthcareProfessional.getHealthcareProfessionalNumber()
        );
    }

    private List<HealthcareProfessionalDTO> toDTOs(List<HealthcareProfessional> healthcareProfessionals) {
        return healthcareProfessionals.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<HealthcareProfessionalDTO> getAllHealthcareProfessionalsWS() {
        return toDTOs(healthcareProfessionalBean.getAllHealthcareProfessionals());
    }

}
