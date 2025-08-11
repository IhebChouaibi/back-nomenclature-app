package eng.bns.nomenclature.mapper;

import eng.bns.nomenclature.dto.ChapitreDto;
import eng.bns.nomenclature.entities.Chapitre;
import eng.bns.nomenclature.entities.Section;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ChapitreMapper {
    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "section", expression = "java(mapSection(dto.getIdSection()))"),

            @Mapping(target = "positions", ignore = true)
    })
    Chapitre toEntity(ChapitreDto dto);
    @Mappings({
            @Mapping(source = "section.idSection", target = "idSection")
    })
    ChapitreDto toDto(Chapitre chapitre);
    default Section mapSection(Long idSection) {
        if (idSection == null) {
            return null;
        }
        Section section = new Section();
        section.setIdSection(idSection);
        return section;
    }
}
