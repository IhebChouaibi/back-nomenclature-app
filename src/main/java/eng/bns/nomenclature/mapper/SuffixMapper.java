package eng.bns.nomenclature.mapper;

import eng.bns.nomenclature.dto.SuffixDto;
import eng.bns.nomenclature.entities.Suffix;
import org.modelmapper.ModelMapper;

public class SuffixMapper {
    private ModelMapper modelMapper=new ModelMapper();
    public Suffix mapSuffixDtoToSuffix(eng.bns.nomenclature.dto.SuffixDto suffixDto){
        return modelMapper.map(suffixDto,Suffix.class);
    }
    public SuffixDto mapSuffixToSuffixDto(Suffix suffix){
        SuffixDto dto = new SuffixDto();
        dto.setId(suffix.getIdSuffix());
        dto.setCodeSuffix(suffix.getCodeSuffix());
        dto.setDeclarable(suffix.isDeclarable());
        dto.setNational(suffix.isNational());
return dto;

    }
}
