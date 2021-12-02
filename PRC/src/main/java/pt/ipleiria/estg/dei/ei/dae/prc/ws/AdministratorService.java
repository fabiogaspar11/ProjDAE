package pt.ipleiria.estg.dei.ei.dae.prc.ws;

import pt.ipleiria.estg.dei.ei.dae.prc.dtos.AdministratorDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.ejbs.AdministratorBean;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Administrator;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Patient;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.*;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("administrators") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class AdministratorService {
    @EJB
    private AdministratorBean administratorBean;

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/students/”
    public List<AdministratorDTO> getAllAdministratorsWS() {
        return toDTOs(administratorBean.getAllAdministrators());
    }

    // Converts an entity Student to a DTO Student class
    private AdministratorDTO toDTO(Administrator administrator) {
        return new AdministratorDTO(
                administrator.getName(),
                administrator.getEmail(),
                administrator.getPassword(),
                administrator.getBirthDate(),
                administrator.getContact(),
                administrator.getHealthNumber()
        );
    }
    // converts an entire list of entities into a list of DTOs
    private List<AdministratorDTO> toDTOs(List<Administrator> administrators) {
        return administrators.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("{username}")
    public Response getAdministratorDetails(@PathParam("username") String username) throws MyEntityNotFoundException {
        Administrator administrator = administratorBean.findAdministrator(username);
        return Response.ok(toDTO(administrator)).build();
    }

    @POST // means: to call this endpoint, we need to use the HTTP POST method
    @Path("/")
    public Response createNewAdministrator(AdministratorDTO administratorDTO) throws MyEntityExistsException, MyEntityNotFoundException {
        String username = administratorBean.create(administratorDTO.getName(), administratorDTO.getEmail(), administratorDTO.getPassword(), administratorDTO.getBirthDate(), administratorDTO.getContact(), administratorDTO.getHealthNumber());
        Administrator administrator = administratorBean.findAdministrator(username);
        return Response.status(Response.Status.CREATED)
                .entity(toDTO(administrator))
                .build();
    }

    @PUT
    @Path("/{username}")
    public Response updateAdministrator(@PathParam("username") String username, AdministratorDTO administratorDTO) throws MyEntityNotFoundException {
        Administrator administrator  = administratorBean.findAdministrator(username);
        administratorBean.update(administrator,administratorDTO);
        return Response.status(Response.Status.OK)
                .entity(toDTO(administrator))
                .build();
    }

    @DELETE
    @Path("/{username}")
    public Response deleteAdministrator(@PathParam("username") String username) throws MyEntityNotFoundException {
        Administrator administrator = administratorBean.findAdministrator(username);
        administratorBean.delete(username);
        return Response.status(Response.Status.OK)
                .entity(toDTO(administrator))
                .build();
    }

}
