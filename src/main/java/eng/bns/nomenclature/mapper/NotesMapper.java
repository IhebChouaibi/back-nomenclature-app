package eng.bns.nomenclature.mapper;

import eng.bns.nomenclature.dto.NotesDto;
import eng.bns.nomenclature.entities.Notes;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface NotesMapper {
    @InheritInverseConfiguration
    @Mapping(target = "taric", ignore = true)
    Notes toEntity(NotesDto notesDto);
    @Mappings(
            @Mapping(source = "taric.idNomenclature", target = "idNomenclature")
    )
    NotesDto toDto(Notes notes);
}
