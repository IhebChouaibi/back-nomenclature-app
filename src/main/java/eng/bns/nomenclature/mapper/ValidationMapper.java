package eng.bns.nomenclature.mapper;

import eng.bns.nomenclature.dto.ValidationMesureDto;
import eng.bns.nomenclature.entities.ValidationMesure;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")

public interface ValidationMapper {

    @Mappings(
            {
                    @Mapping(source = "mesure.idMesure" , target = "idMesure"),
                    @Mapping(source = "validateur.id" , target="idValidateur"),
                    @Mapping(source="statut.idStatut" ,target = "idStatus")
            }


    )

    ValidationMesureDto toDto(ValidationMesure validationMesure);
    @InheritInverseConfiguration
    @Mapping(target = "mesure", ignore = true)
    @Mapping(target = "validateur", ignore = true)
    @Mapping(target = "statut", ignore = true)
    ValidationMesure toEntity(ValidationMesureDto validationMesure);
}
