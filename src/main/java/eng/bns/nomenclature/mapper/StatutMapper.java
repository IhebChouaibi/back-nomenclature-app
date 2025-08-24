package eng.bns.nomenclature.mapper;

import eng.bns.nomenclature.dto.StatutDto;
import eng.bns.nomenclature.entities.Statut;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatutMapper {
    StatutDto toDto(Statut statut);
    Statut toEntity(StatutDto statutDto);
}
