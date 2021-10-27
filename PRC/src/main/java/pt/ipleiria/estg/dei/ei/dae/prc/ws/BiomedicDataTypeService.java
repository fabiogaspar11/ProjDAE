package pt.ipleiria.estg.dei.ei.dae.prc.ws;

import pt.ipleiria.estg.dei.ei.dae.prc.dtos.BiomedicDataTypeDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.dtos.PatientDTO;
import pt.ipleiria.estg.dei.ei.dae.prc.ejbs.BiomedicDataTypeBean;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.BiomedicDataType;
import pt.ipleiria.estg.dei.ei.dae.prc.entities.Patient;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;


@Path("patients")
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
    @Path("/")
    public List<BiomedicDataTypeDTO> getAllBiomedicDataTypesWS() {
        return toDTOs(bDataTypeBean.getAllBiomedicDataType());
    }



}
