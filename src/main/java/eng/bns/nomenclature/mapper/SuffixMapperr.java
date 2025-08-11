package eng.bns.nomenclature.mapper;

import eng.bns.nomenclature.dto.SuffixDto;
import eng.bns.nomenclature.entities.Suffix;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SuffixMapperr {
    private final ModelMapper modelMapper=new ModelMapper();
    public Suffix mapSuffixDtoToSuffix(eng.bns.nomenclature.dto.SuffixDto suffixDto){
        return modelMapper.map(suffixDto,Suffix.class);
    }
    public SuffixDto mapSuffixToSuffixDto(Suffix suffix){
        SuffixDto dto = new SuffixDto();
        dto.setIdSuffix(suffix.getIdSuffix());
        dto.setCodeSuffix(suffix.getCodeSuffix());
        dto.setDeclarable(suffix.isDeclarable());
        dto.setNational(suffix.isNational());
return dto;

    }
}
