package eng.bns.nomenclature.mapper;

import eng.bns.nomenclature.dto.DescriptionDto;
import eng.bns.nomenclature.entities.Description;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface DescriptionMapper {
    Description toEntity(DescriptionDto descriptionDto);

    DescriptionDto toDto(Description description);



}
