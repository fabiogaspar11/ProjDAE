package pt.ipleiria.estg.dei.ei.dae.prc.ws;

import pt.ipleiria.estg.dei.ei.dae.prc.dtos.AdministratorDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.dtos.HealthcareProfessionalDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.dtos.PatientDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.ejbs.AdministratorBean;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Administrator;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Patient;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.*;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;


@Path("administrators") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class AdministratorService {
    @EJB
    private AdministratorBean administratorBean;
    @Context
    private SecurityContext securityContext;


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

    private AdministratorDTO toDTONoPassword(Administrator administrator) {
        return new AdministratorDTO(
                administrator.getName(),
                administrator.getEmail(),
                administrator.getBirthDate(),
                administrator.getContact(),
                administrator.getHealthNumber()
        );
    }

    // converts an entire list of entities into a list of DTOs
    private List<AdministratorDTO> toDTOs(List<Administrator> administrators) {
        return administrators.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/students/”
    @RolesAllowed({"Administrator"})
    public Response getAllAdministratorsWS() {
        return Response.status(Response.Status.OK)
                .entity(toDTOs(administratorBean.getAllAdministrators()))
                .build();
    }

    @GET
    @Path("{username}")
    @RolesAllowed({"Administrator"})
    public Response getAdministratorDetails(@PathParam("username") String username) throws MyEntityNotFoundException {
        Administrator administrator = administratorBean.findAdministrator(username);
        Principal principal = securityContext.getUserPrincipal();
        if(principal.getName().equals(username)) {
            return Response.ok(toDTO(administrator)).build();
        }
        return Response.ok(toDTONoPassword(administrator)).build();
    }


    @POST // means: to call this endpoint, we need to use the HTTP POST method
    @Path("/")
    @RolesAllowed({"Administrator"})
    public Response createNewAdministrator(AdministratorDTO administratorDTO) throws MyEntityExistsException, MyEntityNotFoundException {
        String username = administratorBean.create(administratorDTO.getName(), administratorDTO.getEmail(), administratorDTO.getPassword(), administratorDTO.getBirthDate(), administratorDTO.getContact(), administratorDTO.getHealthNumber());
        Administrator administrator = administratorBean.findAdministrator(username);
        return Response.status(Response.Status.CREATED)
                .entity(toDTO(administrator))
                .build();
    }

    @PUT
    @Path("/{username}")
    @RolesAllowed({"Administrator"})
    public Response updateAdministrator(@PathParam("username") String username, AdministratorDTO administratorDTO) throws MyEntityNotFoundException {
        Principal principal = securityContext.getUserPrincipal();
        Administrator administrator  = administratorBean.findAdministrator(username);
        administratorBean.update(administrator,administratorDTO);
        return Response.status(Response.Status.OK)
                .entity(toDTO(administrator))
                .build();
    }
    @PUT
    @Path("/{username}/password")
    @RolesAllowed({"Administrator"})
    public Response updatePasswordHealthcareProfessional(@PathParam("username") String username, AdministratorDTO administratorDTO) throws MyEntityNotFoundException, MyIllegalArgumentException {

        Principal principal = securityContext.getUserPrincipal();
        if(!principal.getName().equals(username)){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        Administrator administrator = administratorBean.findAdministrator(username);
        administratorBean.updatePassword(administrator, administratorDTO);
        return Response.status(Response.Status.OK)
                .entity(toDTO(administrator))
                .build();
    }

    @DELETE
    @Path("/{username}")
    @RolesAllowed({"Administrator"})
    public Response deleteAdministrator(@PathParam("username") String username) throws MyEntityNotFoundException {
        Principal principal = securityContext.getUserPrincipal();
        if(principal.getName().equals(username)) { //Administrator cannot delete itself
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        Administrator administrator = administratorBean.findAdministrator(username);
        administratorBean.delete(username);
        return Response.status(Response.Status.OK)
                .entity(toDTO(administrator))
                .build();
    }

}
