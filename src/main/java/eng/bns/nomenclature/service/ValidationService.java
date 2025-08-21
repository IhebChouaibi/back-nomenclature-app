package eng.bns.nomenclature.service;

import eng.bns.nomenclature.dto.MesureDto;
import eng.bns.nomenclature.dto.ValidationMesureDto;
import eng.bns.nomenclature.entities.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ValidationService {
    ValidationMesureDto updateMesureStatus(Long idMesure, User responsable,String codeStatut, String commentaire);
  List<ValidationMesureDto> getValidationsByMesure(Long idMesure);


}
