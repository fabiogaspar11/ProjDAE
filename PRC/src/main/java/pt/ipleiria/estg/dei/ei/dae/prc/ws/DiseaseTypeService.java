package pt.ipleiria.estg.dei.ei.dae.prc.ws;

import pt.ipleiria.estg.dei.ei.dae.prc.dtos.DiseaseDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.dtos.DiseaseTypeDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.ejbs.DiseaseBean;
import pt.ipleiria.estg.dei.ei.dae.prc.ejbs.DiseaseTypeBean;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Administrator;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Disease;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.DiseaseType;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.prc.exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("diseaseTypes")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})

public class DiseaseTypeService {

    @EJB
    DiseaseTypeBean diseaseTypeBean;


    private DiseaseTypeDTO toDTO(DiseaseType diseaseType) {
        return new DiseaseTypeDTO(
                diseaseType.getCode(),
                diseaseType.getName()
        );
    }
    private List<DiseaseTypeDTO> toDTOs(List<DiseaseType> diseaseTypes) {
        return diseaseTypes.stream().map(this::toDTO).collect(Collectors.toList());
    }
    @GET
    @Path("/")
    public List<DiseaseTypeDTO> getAllDiseaseTypesWS() {
        return toDTOs(diseaseTypeBean.getAllDiseaseTypes());
    }


    @POST
    @Path("/")
    public Response createNewDisease (DiseaseTypeDTO diseaseTypeDTO) throws MyEntityExistsException, MyEntityNotFoundException {
        int diseaseCode = diseaseTypeBean.create(diseaseTypeDTO.getName());
        DiseaseType newDiseaseType = diseaseTypeBean.findDiseaseType(diseaseCode);
        return Response.status(Response.Status.CREATED)
                .entity(toDTO(newDiseaseType))
                .build();
    }

    @GET
    @Path("/{code}")
    public Response getDiseaseType(@PathParam("code") int code) throws MyEntityNotFoundException {
        DiseaseType diseaseType = diseaseTypeBean.findDiseaseType(code);
        return Response.status(Response.Status.OK)
                .entity(toDTO(diseaseType))
                .build();
    }

    @PUT
    @Path("/{code}")
    public Response updateDiseaseType(@PathParam("code") int code, DiseaseTypeDTO diseaseTypeDTO) throws MyEntityExistsException, MyEntityNotFoundException {
        diseaseTypeBean.update(code,diseaseTypeDTO);
        DiseaseType diseaseType = diseaseTypeBean.findDiseaseType(code);
        return Response.status(Response.Status.CREATED)
                .entity(toDTO(diseaseType))
                .build();
    }

    @DELETE
    @Path("/{code}")
    public Response deleteDiseaseType(@PathParam("code") int code) throws MyEntityNotFoundException {
        DiseaseType diseaseType = diseaseTypeBean.findDiseaseType(code);
        diseaseTypeBean.delete(code);
        return Response.status(Response.Status.OK)
                .entity(toDTO(diseaseType))
                .build();
    }
}
