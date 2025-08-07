package eng.bns.nomenclature.service;

import eng.bns.nomenclature.dto.*;
import eng.bns.nomenclature.entities.*;
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
    private final NCMapper           ncMapper;

private final TARICMapper       taricMapper;
private  SuffixMapper suffixMapper;
private SuffixRepository  suffixRepository;
    @Override
    public ChapitreDto addChapitre(ChapitreDto chapitreDto) {
        Chapitre chapitre = chapitreRepository.save(chapitreMapper.mapChapitreDtoToChapitre(chapitreDto));
        return chapitreMapper.mapChapitreToChapitreDto(chapitre);

    }

    @Override
    public void deleteChapitre(Long idChapitre) {
        chapitreRepository.deleteById(idChapitre);

    }

    @Override
    public ChapitreDto updateChapitreLibelle(Long idChapitre, String ChapitreLibelle) {
        Chapitre existChapitre = chapitreRepository.findById(idChapitre).orElseThrow(()->new RuntimeException("Chapitre not found"));
        existChapitre.setLibelleChapitre(ChapitreLibelle);
        chapitreRepository.save(existChapitre);
        return chapitreMapper.mapChapitreToChapitreDto(existChapitre);

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
                                            .map(taricMapper::mapTaricToTaricDto)
                                            .collect(Collectors.toList());
                                    NCDto ncDto = ncMapper.mapNCToNCDto(nc);
                                    ncDto.setNomenclatures(taricDtos);
                                    ncDtos.add(ncDto);

                                }
                                SousPositionDto spDto = sousPositionMapper.mapSousPositionToSousPositionDto(sousPosition);
                                spDto.setNomenclatureCombinees(ncDtos);
                                sousPositionDtos.add(spDto);

                            }

                            PositionDto posDto = positionMapper.mapPositionToPositionDto(position);
                            posDto.setSousPositions(sousPositionDtos);
                            positionDtos.add(posDto);
                        }

                        ChapitreDto chapDto = chapitreMapper.mapChapitreToChapitreDto(chapitre);
                        chapDto.setPositions(positionDtos);
                        chapitreDtos.add(chapDto);
                    }

                    SectionDto sectionDto = sectionMapper.mapSectionToSectionDto(section);
                    sectionDto.setChapitres(chapitreDtos);
                    return sectionDto;
                });
    }

    @Override
    public SectionDto searchSections(String libelleSection) {
        Section section = sectionRepository.findByLibelleSection(libelleSection);
        return sectionMapper.mapSectionToSectionDto(section);


    }

    @Override
    public PositionDto addPosition(PositionDto positionDto) {
        Position position = positionRepository.save(positionMapper.mapPositionDtoToPosition(positionDto));
        return positionMapper.mapPositionToPositionDto(position);
    }

    @Override
    public SousPositionDto addSousPosition(SousPositionDto sousPositionDto) {
        SousPosition sousPosition = sousPositionRespositry.save(sousPositionMapper.mapSousPositionDtoToSousPosition(sousPositionDto));
        return  sousPositionMapper.mapSousPositionToSousPositionDto(sousPosition);
    }

    @Override
    public SectionDto addSection(SectionDto sectionDto) {
        Section section = sectionRepository.save(sectionMapper.mapSectionDtoToSection(sectionDto));
        return sectionMapper.mapSectionToSectionDto(section);
    }

    @Override
    public SectionDto updateSectionLibelle(Long id ,String sectionLibelle) {
        Section existSection = sectionRepository.findById(id).orElseThrow(()->new RuntimeException("Section not found"));
        existSection.setLibelleSection(sectionLibelle);
        sectionRepository.save(existSection);
        return sectionMapper.mapSectionToSectionDto(existSection);
    }

    @Override
    public void deleteSection(Long idSectionDto) {
        sectionRepository.deleteById(idSectionDto);


    }

    @Override
    public PositionDto updatePositionLibelle(Long id ,String positionLibelle) {
        Position existPosition = positionRepository.findById(id).orElseThrow(()->new RuntimeException("Position not found"));

        existPosition.setLibellePosition(positionLibelle);
        positionRepository.save(existPosition);
        return positionMapper.mapPositionToPositionDto(existPosition);


    }

    @Override
    public void deletePosition(Long idposition) {
        positionRepository.deleteById(idposition);


    }

    @Override
    public SousPositionDto updateSousPositionLibelle(Long id ,String sousPositionLibelle) {
        SousPosition existSouposition = sousPositionRespositry.findById(id).orElseThrow(()->new RuntimeException("SousPosition not found"));
        existSouposition.setLibelleSousPosition(sousPositionLibelle);
        sousPositionRespositry.save(existSouposition);

        return sousPositionMapper.mapSousPositionToSousPositionDto(existSouposition);
    }

    @Override
    public void deleteSousPosition(Long idSousPositionDto) {
        sousPositionRespositry.deleteById(idSousPositionDto);
    }
}
