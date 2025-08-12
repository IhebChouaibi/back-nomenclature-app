package eng.bns.nomenclature.service;

import eng.bns.nomenclature.dto.NotesDto;
import eng.bns.nomenclature.dto.TARICDto;
import eng.bns.nomenclature.dto.TaricRequest;
import eng.bns.nomenclature.entities.*;
import eng.bns.nomenclature.exception.CodeNotFoundException;
import eng.bns.nomenclature.mapper.NotesMapper;
import eng.bns.nomenclature.mapper.TARICMapper;
import eng.bns.nomenclature.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class TaricServiceImpl implements TaricService {
    private final ChapitreRepository chapitreRepository;
    private final PositionRepository positionRepository;
    private final SousPositionRespositry sousPositionRespositry;
    private final NCRepository ncRepository;
    private  final TaricRepository  taricRepository;
    private final TARICMapper taricMapper;
    private final SuffixRepository suffixRepository;
     private final NotesMapper notesMapper;
     private final NotesRepository notesRepository;
    @Override
    public TARICDto createTaric(TaricRequest taricRequest) {
        String code = taricRequest.getCodeNomenclature();
        String  codeChapitre = code.substring(0,2);
        String codePosition = code.substring(0,4);
        String  codeSousPosition = code.substring(0,6);
        String codeNc = code.substring(0,8);
        if (!chapitreRepository.existsByCodeChapitre(codeChapitre)) {
            throw new CodeNotFoundException("Chapitre " + codeChapitre + " inexistant");
        }

        if (!positionRepository.existsBycodePosition(codePosition)) {
            throw new CodeNotFoundException("Position " + codePosition + " inexistante");
        }

        if (!sousPositionRespositry.existsBycodeSousPosition(codeSousPosition)) {
            throw new CodeNotFoundException("Sous-position " + codeSousPosition + " inexistante");
        }

        if (!ncRepository.existsByCodeNCombinee(codeNc)) {
            throw new CodeNotFoundException("Nomenclature combinée " + codeNc + " inexistante");
        }

        Suffix suffix = suffixRepository.findByIdSuffix(taricRequest.getSuffixId());
        NC nc = ncRepository.findByCodeNCombinee(codeNc);

        TARIC taric = new TARIC();
        Description description = new Description();
        description.setDescription(taricRequest.getLibelleNomenclature());
        description.setStatus("1");
        description.setTaric(taric);
        taric.setCodeNomenclature(code);
        taric.getDescriptions().add(description);
        taric.setDateDebutValid(taricRequest.getDateDebutValid());
        taric.setDateFinValid(taricRequest.getDateFinValid());
        taric.setSuffix(suffix);
        taric.setNomenclatureCombinee(nc);

        taricRepository.save(taric);
        return taricMapper.toDto(taric);

    }

    @Override
    public TARICDto updateTaric(TaricRequest taricRequest) {
return null;
    }
    @Override
    public Page<TARICDto> searchTaricByCode(String keyword, Pageable pageable) {
        if (keyword == null || keyword.length() >10 || keyword.length() < 2) {
            throw new IllegalArgumentException("Le code doit contenir au moins 2 caractères");
        }
        Page<TARIC> taric = taricRepository.findByCodeNomenclatureStartingWith(keyword,pageable);

        if (taric.isEmpty()) {
            throw new CodeNotFoundException("Aucun code TARIC trouvé commençant par: " + keyword);
        }

        return taric.map(taricMapper::toDto);
    }

    @Override
    public NotesDto addNotesToTaric(Long idTaric, NotesDto notesDto) {
        TARIC taric = taricRepository.findById(idTaric)
                .orElseThrow(() -> new CodeNotFoundException("Code TARIC inexistant"));

        Notes notes = notesMapper.toEntity(notesDto);
        notes.setTaric(taric);

        notesRepository.save(notes);

        return notesMapper.toDto(notes);


     }

}
