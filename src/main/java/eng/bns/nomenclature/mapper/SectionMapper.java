package eng.bns.nomenclature.mapper;

import eng.bns.nomenclature.dto.ChapitreDto;
import eng.bns.nomenclature.dto.SectionDto;
import eng.bns.nomenclature.entities.Section;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SectionMapper {
    private final ModelMapper modelMapper=new ModelMapper();
    public SectionDto mapSectionToSectionDto(Section section) {
        SectionDto dto = modelMapper.map(section, SectionDto.class);

        // Map chapitres with their DTOs (which now only have IDs)
        if (section.getChapitres() != null) {
            List<ChapitreDto> chapitreDtos = section.getChapitres().stream()
                    .map(chapitre -> {
                        ChapitreDto chapitreDto = new ChapitreDto();
                        chapitreDto.setIdChapitre(chapitre.getIdChapitre());
                        chapitreDto.setCodeChapitre(chapitre.getCodeChapitre());
                        chapitreDto.setLibelleChapitre(chapitre.getLibelleChapitre());
                        chapitreDto.setIdSection(section.getIdSection());
                        return chapitreDto;
                    })
                    .collect(Collectors.toList());
            dto.setChapitres(chapitreDtos);
        }

        return dto;
    }
    public Section mapSectionDtoToSection(SectionDto sectionDto){
        return modelMapper.map(sectionDto , Section.class);
    }
}
