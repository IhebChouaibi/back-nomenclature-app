package eng.bns.nomenclature.service;

import eng.bns.nomenclature.dto.NotesDto;
import eng.bns.nomenclature.dto.TARICDto;
import eng.bns.nomenclature.dto.TaricRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaricService {
    TARICDto createTaric (TaricRequest taricRequest);
    TARICDto updateTaric (TaricRequest taricRequest);
    NotesDto addNotesToTaric(Long idTaric, NotesDto notesDto);
    Page<TARICDto> searchTaricByCode(String keyword , Pageable pageable);

}
