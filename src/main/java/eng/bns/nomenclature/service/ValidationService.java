package eng.bns.nomenclature.service;

import eng.bns.nomenclature.dto.MesureDto;
import eng.bns.nomenclature.dto.ValidationMesureDto;
import eng.bns.nomenclature.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ValidationService {
  List<ValidationMesureDto> getValidationsByMesure(Long idMesure);

}
