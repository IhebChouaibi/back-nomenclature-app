package eng.bns.nomenclature.service;

import eng.bns.nomenclature.dto.MesureDto;
import eng.bns.nomenclature.entities.*;
import eng.bns.nomenclature.mapper.MesureMapper;
import eng.bns.nomenclature.repository.MesureTarifaireRepository;
import eng.bns.nomenclature.repository.StatutRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service

public class MesureServiceImpl implements MesureService{
    private final MesureMapper mesureMapper;
    private final MesureTarifaireRepository mesureTarifaireRepository;
    private final TaricService taricService;
    private final StatutRepository statutRepository ;


    @Override
    @Transactional

    public MesureDto addMesure(List<Long> idTarics, MesureDto mesureDto) {
        List<TARIC> tarics = taricService.getTaricsById(idTarics);
        MesureTarifaire mesure = mesureMapper.toEntity(mesureDto);
        Statut enAttente = statutRepository.findByLibelle("EN_ATTENTE").orElseThrow(()->
                new RuntimeException("Statut en attente introuvable"));


        mesure.setTarics(tarics);
        ValidationMesure validation = new ValidationMesure();
        validation.setMesure(mesure);
        validation.setDateValidation(null);
        validation.setStatut(enAttente);
        validation.setValidateur(null);
        validation.setCommentaire("Ajout de la mesure, en attente de validation");
        if (mesure.getValidations() == null) {
            mesure.setValidations(new ArrayList<>());
        }

        mesure.getValidations().add(validation);



        MesureTarifaire savedMesure = mesureTarifaireRepository.save(mesure);
        return mesureMapper.toDto(savedMesure);


    }


    @Override
    public MesureDto updateMesure(Long idMesure,MesureDto mesureDto ,List<Long> idTarics) {
        MesureTarifaire foundMesure = mesureTarifaireRepository.findById(idMesure)
                .orElseThrow(() -> new RuntimeException("Mesure introuvable"));
        mesureMapper.updateMesureFromDto(mesureDto,foundMesure);
        if(!idTarics.isEmpty()){
            List<TARIC> tarics = taricService.getTaricsById(idTarics);
            if (tarics.size() != idTarics.size()){
                throw new RuntimeException("Ceratains TARIC n'existent pas");

            }
            foundMesure.setTarics(tarics);

        }

        Statut enAttente = statutRepository.findByLibelle("EN_ATTENTE").orElseThrow(()->
                new RuntimeException("Statut en attente introuvable"));


        ValidationMesure validation = new ValidationMesure();
        validation.setMesure(foundMesure);
        validation.setDateValidation(null);
        validation.setStatut(enAttente);
        validation.setCommentaire("Mise à jour de la mesure, en attente de revalidation");

        validation.setValidateur(null);

        foundMesure.getValidations().add(validation);

        MesureTarifaire savedMesure = mesureTarifaireRepository.save(foundMesure);
        return mesureMapper.toDto(savedMesure);
    }

    @Override
    public MesureDto deleteMesure(Long idMesure) {
        MesureTarifaire existMesure = mesureTarifaireRepository.findById(idMesure).orElseThrow(()
                ->new RuntimeException("Mesure introuvable"));
        Statut enAttente = statutRepository.findByLibelle("EN_ATTENTE").orElseThrow(()->
                new RuntimeException("Statut en attente introuvable"));


        ValidationMesure validation = new ValidationMesure();
        validation.setMesure(existMesure);
        validation.setDateValidation(null);
        validation.setStatut(enAttente);
        validation.setCommentaire(" suppression de la mesure, en attente de validation");

        validation.setValidateur(null);

        validation.setStatut(enAttente);
        existMesure.getValidations().add(validation);
        mesureTarifaireRepository.save(existMesure);
        return mesureMapper.toDto(existMesure);

    }

    @Override
    public Page<MesureDto> getAllMesures( Pageable pageable) {

        Page<MesureTarifaire> mesureTarifaires = mesureTarifaireRepository.findAll( pageable);
        return mesureTarifaires.map(mesureMapper::toDto);

    }

    @Override
    public Page<MesureDto> getMesuresByStatut(String statut, Pageable pageable) {
        Statut status =statutRepository.findByLibelle(statut).orElseThrow(()->
                new RuntimeException("Statut introuvable"));

        return mesureTarifaireRepository.findByStatut(statut, pageable)
                .map(mesureMapper::toDto);
    }


}
