package eng.bns.nomenclature.service;

import eng.bns.nomenclature.dto.NotesDto;
import eng.bns.nomenclature.dto.TARICDto;
import eng.bns.nomenclature.dto.TaricRequest;
import eng.bns.nomenclature.dto.TaricWithDetailsRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaricService {
    TARICDto createTaric (TaricWithDetailsRequest taricRequest);
    TARICDto updateTaric (TaricRequest taricRequest);
    Page<TARICDto> searchTaricByCode(String keyword , Pageable pageable);

}
