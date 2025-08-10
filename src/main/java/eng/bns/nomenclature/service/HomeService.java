package eng.bns.nomenclature.service;

import eng.bns.nomenclature.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface HomeService {


ChapitreDto addChapitre(ChapitreDto chapitreDto);
void deleteChapitre(Long idChapitre);
ChapitreDto updateChapitre(Long idChapitre, ChapitreDto chapitreDto);
    Page<SectionDto> getAllSectionsPaginated(Pageable pageable);
    PositionDto addPosition(PositionDto positionDto);
    SousPositionDto addSousPosition(SousPositionDto sousPositionDto);
    SectionDto addSection(SectionDto sectionDto);
    SectionDto updateSectionLibelle(Long id ,String sectionLibelle);
    void deleteSection(Long idSectionDto);
    PositionDto updatePosition(Long id ,PositionDto positionDto);
    void deletePosition( Long idPositionDto);
    SousPositionDto updateSousPosition(Long id ,SousPositionDto sousPositionDto);
    void deleteSousPosition(Long idSousPositionDto);

}
