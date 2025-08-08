package eng.bns.nomenclature.mapper;

import eng.bns.nomenclature.dto.SectionDto;
import eng.bns.nomenclature.entities.Section;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ChapitreMapper.class})
public interface SectionMapper {
    SectionDto toDto(Section section);
    Section toEntity(SectionDto dto);
}
