package eng.bns.nomenclature.service;

import eng.bns.nomenclature.dto.MesureDto;
import eng.bns.nomenclature.dto.ValidationMesureDto;
import eng.bns.nomenclature.entities.MesureTarifaire;
import eng.bns.nomenclature.entities.Statut;
import eng.bns.nomenclature.entities.User;
import eng.bns.nomenclature.entities.ValidationMesure;
import eng.bns.nomenclature.mapper.MesureMapper;
import eng.bns.nomenclature.mapper.ValidationMapper;
import eng.bns.nomenclature.repository.MesureTarifaireRepository;
import eng.bns.nomenclature.repository.StatutRepository;
import eng.bns.nomenclature.repository.ValidationMesureRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ValidationServiceImpl implements ValidationService {
    private final MesureTarifaireRepository mesureTarifaireRepository;
    private final ValidationMesureRepository validationMesureRepository;
    private final StatutRepository statutRepository;
    private final MesureMapper mesureMapper;
    private final ValidationMapper  validationMapper;
    @Transactional
    public ValidationMesureDto  updateMesureStatus(Long idMesure, User responsable, String codeStatut,String commentaire) {
        MesureTarifaire mesure = mesureTarifaireRepository.findById(idMesure)
                .orElseThrow(() -> new RuntimeException("Mesure introuvable"));

        Statut statut = statutRepository.findByLibelle(codeStatut)
                .orElseThrow(() -> new RuntimeException("Statut " + codeStatut + " manquant"));

        ValidationMesure validation = new ValidationMesure();
        validation.setMesure(mesure);
        validation.setDateValidation(LocalDate.now());
        validation.setStatut(statut);
        validation.setValidateur(responsable);

        mesure.getValidations().add(validation);

        mesureTarifaireRepository.save(mesure);

        return validationMapper.toDto(validation);
    }

    @Override
    public List<ValidationMesureDto> getValidationsByMesure(Long idMesure) {
        List<ValidationMesure> validationsMesure = validationMesureRepository.findByMesure_IdMesure(idMesure);
        if(validationsMesure.isEmpty()) {
            throw new RuntimeException("Mesure introuvable");
        }
        return List.of();
    }
}
