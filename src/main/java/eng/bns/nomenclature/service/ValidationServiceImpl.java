package eng.bns.nomenclature.service;

import eng.bns.nomenclature.dto.ValidationMesureDto;
import eng.bns.nomenclature.entities.*;
import eng.bns.nomenclature.exception.CodeNotFoundException;
import eng.bns.nomenclature.exception.TaricNotFoundException;
import eng.bns.nomenclature.mapper.MesureMapper;
import eng.bns.nomenclature.mapper.ValidationMapper;
import eng.bns.nomenclature.repository.MesureTarifaireRepository;
import eng.bns.nomenclature.repository.StatutRepository;
import eng.bns.nomenclature.repository.UserRepository;
import eng.bns.nomenclature.repository.ValidationMesureRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ValidationServiceImpl implements ValidationService {
    private final MesureTarifaireRepository mesureTarifaireRepository;
    private final ValidationMesureRepository validationMesureRepository;
    private final StatutRepository statutRepository;
    private final UserRepository userRepository;
    private final ValidationMapper  validationMapper;


    @Override
    public List<ValidationMesureDto> getValidationsByMesure(Long idMesure) {
        List<ValidationMesure> validationsMesure = validationMesureRepository.findByMesure_IdMesure(idMesure);
        if(validationsMesure.isEmpty()) {
            throw new RuntimeException("Mesure introuvable");
        }
        return List.of();
    }




}
