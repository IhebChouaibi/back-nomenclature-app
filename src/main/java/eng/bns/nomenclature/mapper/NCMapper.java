package eng.bns.nomenclature.mapper;

import eng.bns.nomenclature.dto.NCDto;
import eng.bns.nomenclature.entities.NC;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class NCMapper {
    private ModelMapper modelMapper=new ModelMapper();
    public NCDto mapNCToNCDto(NC nc){
       NCDto dto =new NCDto();
       dto.setIdNCombinee(nc.getIdNCombinee());
       dto.setCodeNCombinee(nc.getCodeNCombinee());
       dto.setLibelleNC(nc.getLibelleNC());
        if (nc.getSousPosition() != null) {
            dto.setIdSousPosition(nc.getSousPosition().getIdSousPosition());
        }
        return dto;
    }
    public NC mapNCDtoToNC(NCDto ncDto){
        return modelMapper.map(ncDto,NC.class);
    }
}
