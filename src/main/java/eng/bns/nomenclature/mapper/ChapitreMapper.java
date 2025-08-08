package eng.bns.nomenclature.mapper;

import eng.bns.nomenclature.dto.ChapitreDto;
import eng.bns.nomenclature.entities.Chapitre;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChapitreMapper {
    Chapitre toEntity(ChapitreDto dto);
    ChapitreDto toDto(Chapitre chapitre);
}
