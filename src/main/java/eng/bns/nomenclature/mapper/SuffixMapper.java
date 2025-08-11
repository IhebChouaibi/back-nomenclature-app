package eng.bns.nomenclature.mapper;

import eng.bns.nomenclature.dto.SuffixDto;
import eng.bns.nomenclature.entities.Suffix;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SuffixMapper {
    Suffix toEntity(SuffixDto suffixDto);
    SuffixDto toDto(Suffix suffix);
}
