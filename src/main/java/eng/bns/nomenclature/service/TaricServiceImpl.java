package eng.bns.nomenclature.service;

import eng.bns.nomenclature.dto.NotesDto;
import eng.bns.nomenclature.dto.TARICDto;
import eng.bns.nomenclature.dto.TaricRequest;
import eng.bns.nomenclature.dto.TaricWithDetailsRequest;
import eng.bns.nomenclature.entities.*;
import eng.bns.nomenclature.exception.CodeNotFoundException;
import eng.bns.nomenclature.exception.TaricNotFoundException;
import eng.bns.nomenclature.mapper.NotesMapper;
import eng.bns.nomenclature.mapper.TARICMapper;
import eng.bns.nomenclature.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TaricServiceImpl implements TaricService {
    private final ChapitreRepository chapitreRepository;
    private final PositionRepository positionRepository;
    private final SousPositionRespositry sousPositionRespositry;
    private final NCRepository ncRepository;
    private  final TaricRepository  taricRepository;
    private final TARICMapper taricMapper;
     private final NotesService notesService;
     private final  DescriptionService descriptionService;
     private final SuffixService suffixService;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional

    public TARICDto createTaric(TaricWithDetailsRequest taricRequest) {
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

        NC nc = ncRepository.findByCodeNCombinee(codeNc);

        TARIC taric = new TARIC();

        taric.setCodeNomenclature(code);
        taric.setDateDebutValid(taricRequest.getDateDebutValid());
        taric.setDateFinValid(taricRequest.getDateFinValid());
        taric.setNomenclatureCombinee(nc);

        Description description = new Description();
        description.setDescription(taricRequest.getDescriptions().get(0).getDescription());
        description.setStatus("1");
        description.setTaric(taric);
        taric.getDescriptions().add(description);
        TARIC savedTaric = taricRepository.save(taric);

        nc.getNomenclatures().add(savedTaric);


        if (taricRequest.getSuffixDto() != null){
            suffixService.addsuffix(savedTaric.getIdNomenclature(),taricRequest.getSuffixDto());
        }
        if (taricRequest.getDescriptions() != null){
            taricRequest.getDescriptions().forEach(descriptionDto ->
                    descriptionService.createDescription(savedTaric.getIdNomenclature(),descriptionDto) )  ;


        }
        if (taricRequest.getNotes() != null){
            taricRequest.getNotes().forEach(notesDto ->
                    notesService.addNotesToTaric(savedTaric.getIdNomenclature(),notesDto));
        }

        return taricMapper.toDto(savedTaric);

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
    public TARIC getTaricById(Long idTaric) {
        return null;
    }



    @Override
    public    List<TARIC>  getTaricsById(List<Long> idTarics) {
        List<TARIC> tarics = taricRepository.findAllById(idTarics);

        if (tarics.isEmpty()) {
            throw new TaricNotFoundException("Aucun TARIC trouvé avec les id: " + idTarics  );
        }
        if(tarics.size() != idTarics.size()){
            List<Long> taricIds = tarics.stream().map(TARIC::getIdNomenclature).toList();
            List<Long> notFoundIds = idTarics.stream().filter(id -> !taricIds.contains(id)).toList();
            throw new TaricNotFoundException("les Taric suivants sont introuvable:" + notFoundIds);

        }

        return tarics;


    }


}
