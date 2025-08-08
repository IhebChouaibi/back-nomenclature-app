package eng.bns.nomenclature.mapper;

import eng.bns.nomenclature.dto.NCDto;
import eng.bns.nomenclature.entities.NC;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface NcMapper {

    @Mappings({
            @Mapping(source = "sousPosition.idSousPosition", target = "idSousPosition")
    })
    NCDto toDto(NC nc);

    @InheritInverseConfiguration
    @Mapping(target = "sousPosition", ignore = true)
    NC toEntity(NCDto dto);
}