package eng.bns.nomenclature.service;

import eng.bns.nomenclature.dto.TARICDto;
import eng.bns.nomenclature.dto.TaricRequest;
import eng.bns.nomenclature.dto.TaricWithDetailsRequest;
import eng.bns.nomenclature.entities.TARIC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaricService {
    TARICDto createTaric (TaricWithDetailsRequest taricRequest);
    TARICDto updateTaric (TaricRequest taricRequest);
    Page<TARICDto> searchTaricByCode(String keyword , Pageable pageable);
    TARIC getTaricById(Long idTaric);

    List<TARIC>  getTaricsById(List<Long> idTarics);


}
