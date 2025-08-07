package eng.bns.nomenclature.service;

import eng.bns.nomenclature.dto.ChapitreDto;
import eng.bns.nomenclature.dto.PositionDto;
import eng.bns.nomenclature.dto.SectionDto;
import eng.bns.nomenclature.dto.SousPositionDto;
import eng.bns.nomenclature.entities.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HomeService {


    //List<ChapitreDto> getAllChapitres(SectionDto sectionDto);
ChapitreDto addChapitre(ChapitreDto chapitreDto);
void deleteChapitre(Long idChapitre);
ChapitreDto updateChapitreLibelle(Long idChapitre,String ChapitreLibelle);
    Page<SectionDto> getAllSectionsPaginated(Pageable pageable);
    SectionDto searchSections(String keyword);
    PositionDto addPosition(PositionDto positionDto);
    SousPositionDto addSousPosition(SousPositionDto sousPositionDto);
    SectionDto addSection(SectionDto sectionDto);
    SectionDto updateSectionLibelle(Long id ,String sectionLielle);
    void deleteSection(Long idSectionDto);
    PositionDto updatePositionLibelle(Long id ,String positionLibelle);
    void deletePosition( Long idPositionDto);
    SousPositionDto updateSousPositionLibelle(Long id ,String sousPositionLibelle);
    void deleteSousPosition(Long idSousPositionDto);
}
