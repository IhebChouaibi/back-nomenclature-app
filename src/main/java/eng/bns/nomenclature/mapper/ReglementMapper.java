package eng.bns.nomenclature.mapper;

import eng.bns.nomenclature.dto.ReglementationDto;
import eng.bns.nomenclature.entities.Reglementation;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ReglementMapper {
    @Mappings({
            @Mapping(source = "typeReglement.id", target = "idTypeReglement"),
            @Mapping(source="pays.idPays" ,target = "idPays"),
            @Mapping(source = "etat.id", target = "idEtat")
    })
    ReglementationDto toDto(Reglementation reglementation);
    @InheritInverseConfiguration
    @Mapping(target = "typeReglement", ignore = true)
    @Mapping(target = "pays", ignore = true)
    @Mapping(target = "etat", ignore = true)

    Reglementation toEntity(ReglementationDto reglementationDto);
}
