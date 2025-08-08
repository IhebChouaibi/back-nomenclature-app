package eng.bns.nomenclature.service;

import eng.bns.nomenclature.dto.*;
import eng.bns.nomenclature.entities.*;
import eng.bns.nomenclature.exception.CodeNotFoundException;
import eng.bns.nomenclature.mapper.*;
import eng.bns.nomenclature.repository.*;
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

@Service
@RequiredArgsConstructor

public class HomeServiceImpl implements HomeService{
    private  final SectionRepository sectionRepository;
    private final PositionRepository    positionRepository;
    private final SectionMapper sectionMapper;
    private final ChapitreMapper    chapitreMapper;
    private final PositionMapper positionMapper;
    private final SousPositionMapper sousPositionMapper;
    private final SousPositionRespositry sousPositionRespositry;
    private final ChapitreRepository chapitreRepository;
    private final NcMapper           ncMapper;

    private final TARICMapper       taricMapper;
        private  SuffixMapper suffixMapper;
    private SuffixRepository  suffixRepository;
    private final TaricRepository taricRepository;



    @Override
    public ChapitreDto addChapitre(ChapitreDto chapitreDto) {
        Chapitre chapitre = chapitreRepository.save(chapitreMapper.toEntity(chapitreDto));
        return chapitreMapper.toDto(chapitre);

    }

    @Override
    public void deleteChapitre(Long idChapitre) {
        chapitreRepository.deleteById(idChapitre);

    }

    @Override
    public ChapitreDto updateChapitre(Long idChapitre, ChapitreDto chapitreDto) {
        Section section = sectionRepository.findById(chapitreDto.getIdSection()).orElseThrow(()->new CodeNotFoundException("Section inexistante"));
        Chapitre existingChapitre = chapitreRepository.findById(idChapitre).orElseThrow(()->new CodeNotFoundException("Chapitre inexistant"));
         existingChapitre.setCodeChapitre(chapitreDto.getCodeChapitre());
         existingChapitre.setLibelleChapitre(chapitreDto.getLibelleChapitre());
         existingChapitre.setSection(section);
         Chapitre updatedChapitre = chapitreRepository.save(existingChapitre);
         return chapitreMapper.toDto(updatedChapitre);



    }


    @Override
    public Page<SectionDto> getAllSectionsPaginated(Pageable pageable) {
        return sectionRepository.findAllOrderedById(pageable)
                .map(section -> {
                    Hibernate.initialize(section.getChapitres());

                    List<ChapitreDto> chapitreDtos = new ArrayList<>();
                    for (Chapitre chapitre : Optional.ofNullable(section.getChapitres()).orElse(Collections.emptyList())) {
                        Hibernate.initialize(chapitre.getPositions());

                        List<PositionDto> positionDtos = new ArrayList<>();
                        for (Position position : Optional.ofNullable(chapitre.getPositions()).orElse(Collections.emptyList())) {
                            Hibernate.initialize(position.getSousPosition());

                            List<SousPositionDto> sousPositionDtos = new ArrayList<>();
                            for (SousPosition sousPosition : Optional.ofNullable(position.getSousPosition()).orElse(Collections.emptyList())) {
                                Hibernate.initialize(sousPosition.getNomenclatureCombinees());

                                List<NCDto> ncDtos =   new ArrayList<>();
                                for (NC nc :Optional.ofNullable(sousPosition.getNomenclatureCombinees()).orElse(Collections.emptyList())) {
                                    Hibernate.initialize(nc.getNomenclatures());
                                    List<TARICDto> taricDtos=

                                            Optional.ofNullable(nc.getNomenclatures())
                                            .orElse(Collections.emptyList())
                                            .stream()
                                            .map(taricMapper::toDto)
                                            .collect(Collectors.toList());
                                    NCDto ncDto = ncMapper.toDto(nc);
                                    ncDto.setNomenclatures(taricDtos);
                                    ncDtos.add(ncDto);

                                }
                                SousPositionDto spDto = sousPositionMapper.toDto(sousPosition);
                                spDto.setNomenclatureCombinees(ncDtos);
                                sousPositionDtos.add(spDto);

                            }

                            PositionDto posDto = positionMapper.toDto(position);
                            posDto.setSousPositions(sousPositionDtos);
                            positionDtos.add(posDto);
                        }

                        ChapitreDto chapDto = chapitreMapper.toDto(chapitre);
                        chapDto.setPositions(positionDtos);
                        chapitreDtos.add(chapDto);
                    }

                    SectionDto sectionDto = sectionMapper.toDto(section);
                    sectionDto.setChapitres(chapitreDtos);
                    return sectionDto;
                });
    }

    @Override
    public Page<TARICDto> searchTaricByCode(String keyword,Pageable pageable) {
        if (keyword == null || keyword.length() >10 || keyword.length() < 4) {
            throw new IllegalArgumentException("Le code doit contenir au moins 2 caractères");
        }
        Page<TARIC> taric = taricRepository.findByCodeNomenclatureStartingWith(keyword,pageable);
        if (taric.isEmpty()) {
            throw new CodeNotFoundException("Aucun code TARIC trouvé commençant par: " + keyword);
        }

        return taric.map(taricMapper::toDto);
    }


    @Override
    public PositionDto addPosition(PositionDto positionDto) {
        Position position = positionRepository.save(positionMapper.toEntity(positionDto));
        return positionMapper.toDto(position);
    }

    @Override
    public SousPositionDto addSousPosition(SousPositionDto sousPositionDto) {
        SousPosition sousPosition = sousPositionRespositry.save(sousPositionMapper.toEntity(sousPositionDto));
        return  sousPositionMapper.toDto(sousPosition);
    }

    @Override
    public SectionDto addSection(SectionDto sectionDto) {
        Section section = sectionRepository.save(sectionMapper.toEntity(sectionDto));
        return sectionMapper.toDto(section);
    }

    @Override
    public SectionDto updateSectionLibelle(Long id ,String sectionLibelle) {
        Section existSection = sectionRepository.findById(id).orElseThrow(()->new RuntimeException("Section not found"));
        existSection.setLibelleSection(sectionLibelle);
        sectionRepository.save(existSection);
        return sectionMapper.toDto(existSection);
    }


    @Override
    public void deleteSection(Long idSectionDto) {
        sectionRepository.deleteById(idSectionDto);


    }

    @Override
    public PositionDto updatePosition(Long id, PositionDto positionDto) {
      Chapitre chapitre = chapitreRepository.findById(positionDto.getIdChapitre()).orElseThrow(()->new CodeNotFoundException("Chapitre inexistant"));
      Position existingPosition = positionRepository.findById(id).orElseThrow(()->new CodeNotFoundException("Position inexistante"));
      existingPosition.setCodePosition(positionDto.getCodePosition());
      existingPosition.setLibellePosition(positionDto.getLibellePosition());
      existingPosition.setChapitre(chapitre);
      Position updatedPosition = positionRepository.save(existingPosition);
      return positionMapper.toDto(updatedPosition);
    }


    @Override
    public void deletePosition(Long idposition) {
        positionRepository.deleteById(idposition);


    }

    @Override
    public SousPositionDto updateSousPosition(Long id, SousPositionDto sousPositionDto) {
      Position position = positionRepository.findById(sousPositionDto.getIdPosition()).orElseThrow(()->new CodeNotFoundException("Position inexistante"));
      SousPosition existingSousPosition = sousPositionRespositry.findById(id).orElseThrow(()->new CodeNotFoundException("Sous-position inexistante"));
      existingSousPosition.setCodeSousPosition(sousPositionDto.getCodeSousPosition());
      existingSousPosition.setLibelleSousPosition(sousPositionDto.getLibelleSousPosition());
      existingSousPosition.setPosition(position);
      SousPosition updatedSousPosition = sousPositionRespositry.save(existingSousPosition);
      return sousPositionMapper.toDto(updatedSousPosition);
    }


    @Override
    public void deleteSousPosition(Long idSousPositionDto) {
        sousPositionRespositry.deleteById(idSousPositionDto);
    }
}
