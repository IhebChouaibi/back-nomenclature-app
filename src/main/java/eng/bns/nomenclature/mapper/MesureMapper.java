package eng.bns.nomenclature.mapper;

import eng.bns.nomenclature.dto.MesureDto;
import eng.bns.nomenclature.entities.MesureTarifaire;
import org.mapstruct.*;

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

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateMesureFromDto(MesureDto dto, @MappingTarget MesureTarifaire entity);
}
