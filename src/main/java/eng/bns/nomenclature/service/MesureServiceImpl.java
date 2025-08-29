package eng.bns.nomenclature.service;

import eng.bns.nomenclature.dto.MesureDto;
import eng.bns.nomenclature.dto.ValidationMesureDto;
import eng.bns.nomenclature.entities.*;
import eng.bns.nomenclature.exception.CodeNotFoundException;
import eng.bns.nomenclature.exception.TaricNotFoundException;
import eng.bns.nomenclature.mapper.MesureMapper;
import eng.bns.nomenclature.mapper.ValidationMapper;
import eng.bns.nomenclature.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
@Service

public  class MesureServiceImpl implements MesureService {
    private final MesureMapper mesureMapper;
    private final MesureTarifaireRepository mesureTarifaireRepository;
    private final TaricRepository taricRepository;
    private final StatutRepository statutRepository ;
    private final ValidationMapper validationMapper;
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationRepository notificationRespository;
    private final UserRepository userRepository;

    @Override
    @Transactional

    public MesureDto addMesure(List<Long> idTarics, MesureDto mesureDto) {
        List<TARIC> tarics =         getTaricsById(idTarics);

        MesureTarifaire mesure = mesureMapper.toEntity(mesureDto);
        Statut enAttente = existStatut("EN_ATTENTE");

        mesure.setTarics(tarics);

        if (mesure.getValidations() == null) {
            mesure.setValidations(new ArrayList<>());
        }

        mesure.getValidations().add(createValidation(mesure,  "Demande d'ajout de la mesure, en attente de validation", enAttente));



        MesureTarifaire savedMesure = mesureTarifaireRepository.save(mesure);
        String message = "Nouvelle  mesure à valider: " + savedMesure.getCodeMesure();
        notify(message);

        return mesureMapper.toDto(savedMesure);


    }


    @Override
    public MesureDto updateMesure(Long idMesure,MesureDto mesureDto ,List<Long> idTarics) {
        MesureTarifaire foundMesure =existMesure(idMesure);
        mesureMapper.updateMesureFromDto(mesureDto,foundMesure);
        if(!idTarics.isEmpty()){
            List<TARIC> tarics =         getTaricsById(idTarics);
            if (tarics.size() != idTarics.size()){
                throw new RuntimeException("Ceratains TARIC n'existent pas");

            }
            foundMesure.setTarics(tarics);

        }

        Statut enAttente = existStatut("EN_ATTENTE");


        foundMesure.getValidations().add(createValidation(foundMesure,  "Demande de mise à jour de la mesure, en attente de revalidation", enAttente));

        MesureTarifaire savedMesure = mesureTarifaireRepository.save(foundMesure);
        String message = "Nouvelle mise à jour  à valider: " + savedMesure.getCodeMesure();

        notify(message);

        return mesureMapper.toDto(savedMesure);
    }

    @Override
    public MesureDto deleteMesure(Long idMesure) {
        MesureTarifaire existMesure = existMesure(idMesure);
        Statut enAttenteSuppression = existStatut("EN_ATTENTE_SUPPRESSION");




        existMesure.getValidations().add(createValidation(existMesure,"Demande de  suppression de la mesure, en attente de validation",enAttenteSuppression));
        MesureTarifaire savedMesure = mesureTarifaireRepository.save(existMesure);
        String message = "Nouvelle mise à jour  à valider: " + savedMesure.getCodeMesure();

        notify(message);
        return mesureMapper.toDto(savedMesure);

    }

    @Override
    public Page<MesureDto> getAllMesures( Pageable pageable) {

        Page<MesureTarifaire> mesureTarifaires = mesureTarifaireRepository.findAll( pageable);
        return mesureTarifaires.map(mesureMapper::toDto);

    }

    @Override
    public Page<MesureDto> getMesuresByStatut(String statut, Pageable pageable) {

        Statut existStatus = existStatut(statut);
        return mesureTarifaireRepository.findByStatut_Libelle(existStatus.getLibelle(), pageable)
                .map(mesure->{
                    List<ValidationMesureDto> validationMesureDtos = Optional.ofNullable(mesure.getValidations())
                            .orElse(Collections.emptyList())
                            .stream()
                            .map(validationMapper::toDto)
                            .toList();
                    MesureDto mesureDto=mesureMapper.toDto(mesure);
                    mesureDto.setValidations(validationMesureDtos);

                    return mesureDto;
                });
    }

    @Override
    @Transactional

    public List<MesureDto> tariterMesure(List<Long> idMesures, Long responsableId, String codeStatut, String commentaire) {
        List<MesureTarifaire> mesures = existMesures(idMesures);
        Statut existStatus = existStatut(codeStatut);
        User responsable = userRepository.findById(responsableId).orElseThrow(()->
                new RuntimeException("Responsable introuvable"));
        List<User> admin =userRepository.findAllByRoles_Name("ADMIN");
        List<MesureDto> mesureDtos = new ArrayList<>();
        List<ValidationMesureDto> validationResults = new ArrayList<>();
        for (MesureTarifaire mesureTarifaire : mesures) {
            ValidationMesure lastValidation = mesureTarifaire.getValidations()
                    .stream()
                    .max(Comparator.comparing(ValidationMesure::getDateValidation))
                    .orElseThrow(() -> new RuntimeException("Aucune validation existante"));

            lastValidation.setMesure(mesureTarifaire);
            lastValidation.setDateValidation(LocalDate.now());
            lastValidation.setStatut(existStatus);
            lastValidation.setValidateur(responsable);
            lastValidation.setCommentaire(commentaire);

            mesureTarifaire.getValidations().add(lastValidation);
            mesureTarifaire.setStatut(existStatus);
            MesureTarifaire savedMesure = mesureTarifaireRepository.save(mesureTarifaire);


            mesureDtos.add(mesureMapper.toDto(savedMesure));
            String message = "new : " + savedMesure.getStatut().getLibelle();

            notify(message);
        }
        return  mesureDtos;
    }


    private MesureTarifaire existMesure(Long id){
        return mesureTarifaireRepository.findById(id).orElseThrow(()->
               new RuntimeException("Mesure introuvable"));
     }
     private Statut existStatut(String libelle){
         return statutRepository.findByLibelle(libelle).orElseThrow(()->
                 new RuntimeException("Statut introuvable"));
     }
    private ValidationMesure createValidation(MesureTarifaire mesure, String commentaire, Statut statut) {
        ValidationMesure validation = new ValidationMesure();
        validation.setMesure(mesure);
        validation.setValidateur(null);
        validation.setCommentaire(commentaire);
        validation.setDateValidation(null);
        validation.setStatut(statut);
        return validation;
    }
    private void notify( String msg) {
        List<User> responsables = userRepository.findAllByRoles_Name("RESPONSABLE");
        for (User responsable : responsables) {


            Notification notification = new Notification();
            notification.setUser(responsable);
            notification.setMessage(msg);
            notificationRespository.save(notification);

            messagingTemplate.convertAndSend(
                    "/topic/notifications/" + responsable.getId(),
                    msg
            );
        }
    }
    private    List<TARIC>  getTaricsById(List<Long> idTarics) {
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
    private   List<MesureTarifaire>  existMesures(List<Long> idMesures) {
        List<MesureTarifaire> mesures = mesureTarifaireRepository.findAllById(idMesures);
        if(mesures.isEmpty()){
            throw new CodeNotFoundException("Mesure introuvable");
        }

        if(mesures.size() != idMesures.size()){
            List<Long> mesuresId = mesures.stream().map(MesureTarifaire::getIdMesure).toList();
            List<Long> notFoundIds = idMesures.stream().filter(id -> !mesuresId.contains(id)).toList();
            throw new TaricNotFoundException("les Mesures suivants sont introuvable:" + notFoundIds);

        }


        return mesures;


    }
}
