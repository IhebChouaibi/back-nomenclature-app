package eng.bns.nomenclature.mapper;

import eng.bns.nomenclature.dto.MvtDto;
import eng.bns.nomenclature.entities.MouvementCommercial;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MvtMapper {
    MvtDto toDto ( MouvementCommercial mouvementCommercial);
    MouvementCommercial toEntity (MvtDto mvtDto);

}
