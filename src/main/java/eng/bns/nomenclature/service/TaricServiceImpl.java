package eng.bns.nomenclature.service;

import eng.bns.nomenclature.dto.*;
import eng.bns.nomenclature.entities.*;
import eng.bns.nomenclature.exception.CodeNotFoundException;
import eng.bns.nomenclature.exception.TaricNotFoundException;
import eng.bns.nomenclature.mapper.MesureMapper;
import eng.bns.nomenclature.mapper.NotesMapper;
import eng.bns.nomenclature.mapper.TARICMapper;
import eng.bns.nomenclature.mapper.ValidationMapper;
import eng.bns.nomenclature.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
     private final MesureMapper mesureMapper;
     private final ValidationMapper validationMapper;
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
    public TARICDto getTaricById(Long idTaric) {
        TARIC taric = taricRepository.findById(idTaric)
                .orElseThrow(() -> new RuntimeException("TARIC introuvable"));

        Hibernate.initialize(taric.getMesures());
        List<MesureDto> mesureDtos = new ArrayList<>();

        for (MesureTarifaire mesure : Optional.ofNullable(taric.getMesures()).orElse(Collections.emptyList())) {
            Hibernate.initialize(mesure.getValidations());

            List<ValidationMesureDto> validationDtos = Optional.ofNullable(mesure.getValidations())
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(validationMapper::toDto)
                    .collect(Collectors.toList());

            MesureDto mesureDto = mesureMapper.toDto(mesure);
            mesureDto.setValidations(validationDtos);
            mesureDtos.add(mesureDto);
        }

        TARICDto taricDto = taricMapper.toDto(taric);
        taricDto.setMesures(mesureDtos);

        return taricDto;
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
