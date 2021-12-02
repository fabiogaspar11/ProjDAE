package pt.ipleiria.estg.dei.ei.dae.prc.ws;

import pt.ipleiria.estg.dei.ei.dae.prc.dtos.BiomedicDataTypeDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.dtos.PatientDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.ejbs.BiomedicDataTypeBean;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Administrator;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.BiomedicDataType;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Patient;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;


@Path("biomedicDataType")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class BiomedicDataTypeService {
    @EJB
    BiomedicDataTypeBean bDataTypeBean;

    private BiomedicDataTypeDTO toDTO(BiomedicDataType bDataType) {
        return new BiomedicDataTypeDTO(
                bDataType.getCode(),
                bDataType.getName(),
                bDataType.getUnitMeasure(),
                bDataType.getMinValue(),
                bDataType.getMaxValue()
        );
    }

    private List<BiomedicDataTypeDTO> toDTOs(List<BiomedicDataType> bDataTypes) {
        return bDataTypes.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("{code}")
    public Response getBdataTypeDetails(@PathParam("code") long code) throws MyEntityNotFoundException {
        BiomedicDataType biomedicDataType = bDataTypeBean.findBiomedicDataType(code);
        return Response.ok(toDTO(biomedicDataType)).build();
    }


    @GET
    @Path("/")
    public List<BiomedicDataTypeDTO> getAllBiomedicDataTypesWS() {
        return toDTOs(bDataTypeBean.getAllBiomedicDataType());
    }

    @DELETE
    @Path("/{code}")
    public Response deleteAdministrator(@PathParam("code") long code) throws MyEntityNotFoundException {
        BiomedicDataType biomedicDataType = bDataTypeBean.findBiomedicDataType(code);
        bDataTypeBean.delete(code);
        return Response.status(Response.Status.OK)
                .entity(toDTO(biomedicDataType))
                .build();
    }



}
