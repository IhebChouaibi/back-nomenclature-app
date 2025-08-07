package eng.bns.nomenclature.mapper;

import eng.bns.nomenclature.dto.ChapitreDto;
import eng.bns.nomenclature.entities.Chapitre;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ChapitreMapper {
    private final  ModelMapper modelMapper=new ModelMapper();

    public ChapitreDto mapChapitreToChapitreDto(Chapitre chapitre){
        ChapitreDto dto = modelMapper.map(chapitre, ChapitreDto.class);

        // Set section ID only
        if (chapitre.getSection() != null) {
            dto.setIdSection(chapitre.getSection().getIdSection());
        }

        return dto;
    }

    public Chapitre mapChapitreDtoToChapitre(ChapitreDto chapitreDto){
        return modelMapper.map(chapitreDto,Chapitre.class);
    }
}
