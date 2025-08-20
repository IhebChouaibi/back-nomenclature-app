package eng.bns.nomenclature.mapper;

import eng.bns.nomenclature.dto.MesureDto;
import eng.bns.nomenclature.entities.MesureTarifaire;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MesureMapper {
    @Mappings({
            @Mapping(source = "mouvementCommercial.idMvtCommercial" , target = "idMvtCommercial"),
            @Mapping(source = "reglementation.idReglement",target = "idReglement")
    })
    MesureDto toDto(MesureTarifaire  mesureTarifaire);


    @InheritInverseConfiguration
    @Mapping(target = "mouvementCommercial", ignore = true)
    @Mapping(target = "reglementation", ignore = true)

    MesureTarifaire toEntity(MesureDto mesureDto);
}
