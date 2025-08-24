package eng.bns.nomenclature.service;

import eng.bns.nomenclature.dto.StatutDto;
import eng.bns.nomenclature.entities.Statut;

public interface StatutService {
    StatutDto getStatutById(Long id);
}
