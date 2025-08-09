package eng.bns.nomenclature.mapper;

import eng.bns.nomenclature.dto.TARICDto;
import eng.bns.nomenclature.entities.TARIC;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TARICMapper {

    @Mappings({
            @Mapping(source = "suffix.idSuffix", target = "idSuffix"),
            @Mapping(source = "nomenclatureCombinee.idNCombinee", target = "idNCombinee"),

    })
    TARICDto toDto(TARIC taric);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "suffix", ignore = true),
            @Mapping(target = "nomenclatureCombinee", ignore = true)
    })
    TARIC toEntity(TARICDto dto);
}