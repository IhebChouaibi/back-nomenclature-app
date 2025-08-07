package eng.bns.nomenclature.mapper;

import eng.bns.nomenclature.dto.SousPositionDto;
import eng.bns.nomenclature.entities.SousPosition;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SousPositionMapper {
    private ModelMapper modelMapper=new ModelMapper();


    public SousPosition mapSousPositionDtoToSousPosition(SousPositionDto sousPositionDto){
        return modelMapper.map(sousPositionDto,SousPosition.class);
    }
    public SousPositionDto mapSousPositionToSousPositionDto(SousPosition sousPosition){
        SousPositionDto dto = new SousPositionDto();
        dto.setIdSousPosition(sousPosition.getIdSousPosition());
        dto.setCodeSousPosition(sousPosition.getCodeSousPosition());
        dto.setLibelleSousPosition(sousPosition.getLibelleSousPosition());

        if (sousPosition.getPosition() != null) {
            dto.setIdPosition(sousPosition.getPosition().getIdPosition());
        }

        return dto;
    }
}
