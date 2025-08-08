package eng.bns.nomenclature.mapper;

import eng.bns.nomenclature.dto.PositionDto;
import eng.bns.nomenclature.entities.Position;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PositionMapper {
    PositionDto toDto(Position position);
    Position toEntity(PositionDto positionDto);
}