package pt.ipleiria.estg.dei.ei.dae.prc.ws;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import pt.ipleiria.estg.dei.ei.dae.prc.dtos.AuthDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.ejbs.JwtBean;
import pt.ipleiria.estg.dei.ei.dae.prc.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.User;
import pt.ipleiria.estg.dei.ei.dae.prc.jwt.Jwt;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.logging.Logger;

@Path("/auth")
public class LoginService {
    private static final Logger log =
            Logger.getLogger(LoginService.class.getName());

    @EJB
    JwtBean jwtBean;
    @EJB
    UserBean userBean;
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(AuthDTO authDTO) {
        String message="";
        try {
            User user = userBean.authenticate(authDTO.getUsername(), authDTO.getPassword());
            if (user != null) {
                if (user.getUsername() != null) {
                    log.info("Generating JWT for user " + user.getUsername());
                }
                String token = jwtBean.createJwt(user.getUsername(), new
                        String[]{user.getClass().getSimpleName()});
                return Response.ok(new Jwt(token)).build();
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            message = e.getMessage();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(message).build();
    }
    @GET
    @Path("/user")
    public Response demonstrateClaims(@HeaderParam("Authorization") String auth) {
        if (auth != null && auth.startsWith("Bearer ")) {
            try {
                JWT j = JWTParser.parse(auth.substring(7));
                return Response.ok(j.getJWTClaimsSet().getClaims()).build();
            } catch (ParseException e) {
                log.warning(e.toString());
                return Response.status(400).build();
            }
        }
        return Response.status(204).build();
    }

}
