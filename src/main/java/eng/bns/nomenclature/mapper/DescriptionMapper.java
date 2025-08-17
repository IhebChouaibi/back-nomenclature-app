package eng.bns.nomenclature.mapper;

import eng.bns.nomenclature.dto.DescriptionDto;
import eng.bns.nomenclature.entities.Description;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")

public interface DescriptionMapper {

    @Mappings({
            @Mapping(source = "taric.idNomenclature", target = "idNomenclature")
    })



    DescriptionDto toDto(Description description);


    @InheritInverseConfiguration
    @Mapping(target = "taric", ignore = true)
    Description toEntity(DescriptionDto descriptionDto);

}
