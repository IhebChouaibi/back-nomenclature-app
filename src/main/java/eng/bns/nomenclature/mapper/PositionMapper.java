package eng.bns.nomenclature.mapper;

import eng.bns.nomenclature.dto.PositionDto;
import eng.bns.nomenclature.entities.Position;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PositionMapper {
    private ModelMapper modelMapper=new ModelMapper();

    public Position mapPositionDtoToPosition(eng.bns.nomenclature.dto.PositionDto positionDto){
        return modelMapper.map(positionDto,Position.class);
    }
    public PositionDto mapPositionToPositionDto(Position position){
        return modelMapper.map(position,PositionDto.class);
    }
}
