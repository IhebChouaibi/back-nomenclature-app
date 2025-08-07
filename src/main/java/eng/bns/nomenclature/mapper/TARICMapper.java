package eng.bns.nomenclature.mapper;

import eng.bns.nomenclature.dto.TARICDto;
import eng.bns.nomenclature.entities.TARIC;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TARICMapper {
    private ModelMapper modelMapper = new ModelMapper();
    public TARIC mapTaricDtoToTaric(eng.bns.nomenclature.dto.TARICDto taricDto){
        return modelMapper.map(taricDto,TARIC.class);
    }
    public eng.bns.nomenclature.dto.TARICDto mapTaricToTaricDto(TARIC taric){
        TARICDto taricDto = new TARICDto();
        taricDto.setIdNomenclature(taric.getIdNomenclature());
        taricDto.setCodeNomenclature(taric.getCodeNomenclature());
        taricDto.setLibelleNomenclature(taric.getLibelleNomenclature());
        if(taric.getSuffix() != null){
            taricDto.setIdSuffix(taric.getSuffix().getIdSuffix());
        }
        if (taric.getNomenclatureCombinee() != null) {
            taricDto.setIdNCombinee(taric.getNomenclatureCombinee().getIdNCombinee());
        }

        return taricDto;

    }
}
