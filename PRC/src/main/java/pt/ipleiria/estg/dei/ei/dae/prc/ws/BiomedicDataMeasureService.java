package pt.ipleiria.estg.dei.ei.dae.prc.ws;

import pt.ipleiria.estg.dei.ei.dae.prc.dtos.BiomedicDataMeasureDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.ejbs.BiomedicDataMeasureBean;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.BiomedicDataMeasure;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("biomedicDataMeasures")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class BiomedicDataMeasureService {
    @EJB
    private BiomedicDataMeasureBean biomedicDataMeasureBean;

    private List<BiomedicDataMeasureDTO> toDTOs(List<BiomedicDataMeasure> biomedicDataMeasures) {
        return biomedicDataMeasures.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private BiomedicDataMeasureDTO toDTO(BiomedicDataMeasure biomedicDataMeasure){
       return new BiomedicDataMeasureDTO(
               biomedicDataMeasure.getCode(),
               biomedicDataMeasure.getValue(),
               biomedicDataMeasure.getDate(),
               biomedicDataMeasure.getHour(),
               biomedicDataMeasure.getPatient().getUsername(),
               biomedicDataMeasure.getBiomedicDataType().getCode()
       );
    }

    @GET
    @Path("/")
    public List<BiomedicDataMeasureDTO> getAllbiomedicDataMeasuresWS() {
        return toDTOs(biomedicDataMeasureBean.getAllBiomedicDataMeasures());
    }

    @GET
    @Path("{code}")
    public Response getBiomedicDataMeasureDetails(@PathParam("code") long code) throws MyEntityNotFoundException {
        BiomedicDataMeasure biomedicDataMeasure = biomedicDataMeasureBean.findBiomedicDataMeasure(code);
        return Response.ok(toDTO(biomedicDataMeasure)).build();
    }

    @POST
    @Path("/")
    public Response createBiomedicDataMeasure(BiomedicDataMeasureDTO biomedicDataMeasureDTO) throws MyEntityExistsException, MyEntityNotFoundException {
        biomedicDataMeasureBean.create(
                biomedicDataMeasureDTO.getCode(),
                biomedicDataMeasureDTO.getValue(),
                biomedicDataMeasureDTO.getDate(),
                biomedicDataMeasureDTO.getHour(),
                biomedicDataMeasureDTO.getUsernamePatient(),
                biomedicDataMeasureDTO.getBiomedicDataTypeCode());
        BiomedicDataMeasure biomedicDataMeasure = biomedicDataMeasureBean.findBiomedicDataMeasure(biomedicDataMeasureDTO.getCode());
        return Response.status(Response.Status.CREATED)
                .entity(toDTO(biomedicDataMeasure))
                .build();
    }

    @PUT
    @Path("/{code}")
    public Response updateBiomedicDataMeasure(@PathParam("code") long code, BiomedicDataMeasureDTO biomedicDataMeasureDTO) throws MyEntityNotFoundException {
        BiomedicDataMeasure biomedicDataMeasure = biomedicDataMeasureBean.findBiomedicDataMeasure(code);
        biomedicDataMeasureBean.update(biomedicDataMeasure,biomedicDataMeasureDTO);
        return Response.status(Response.Status.OK)
                .entity(toDTO(biomedicDataMeasure))
                .build();
    }

    @DELETE
    @Path("/{code}")
    public Response deleteBiomedicDataMeasure(@PathParam("code") long code) throws MyEntityNotFoundException {
        BiomedicDataMeasure biomedicDataMeasure = biomedicDataMeasureBean.findBiomedicDataMeasure(code);
        biomedicDataMeasureBean.remove(biomedicDataMeasure);
        return Response.status(Response.Status.OK)
                .entity(toDTO(biomedicDataMeasure))
                .build();
    }

}
