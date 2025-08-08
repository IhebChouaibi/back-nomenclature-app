package eng.bns.nomenclature.mapper;

import eng.bns.nomenclature.dto.SousPositionDto;
import eng.bns.nomenclature.entities.SousPosition;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface SousPositionMapper {

    @Mappings({
            @Mapping(source = "position.idPosition", target = "idPosition")
    })
    SousPositionDto toDto(SousPosition sousPosition);

    @InheritInverseConfiguration
    @Mapping(target = "position", ignore = true) // géré manuellement dans le service
    SousPosition toEntity(SousPositionDto dto);
}