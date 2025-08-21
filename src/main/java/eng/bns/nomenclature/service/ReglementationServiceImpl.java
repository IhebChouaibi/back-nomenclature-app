package eng.bns.nomenclature.service;

import eng.bns.nomenclature.dto.ReglementationDto;
import eng.bns.nomenclature.entities.Reglementation;
import eng.bns.nomenclature.exception.CodeNotFoundException;
import eng.bns.nomenclature.mapper.ReglementMapper;
import eng.bns.nomenclature.repository.ReglementationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReglementationServiceImpl implements ReglementationService{
    private final ReglementationRepository reglementationRepository;
    private final ReglementMapper reglementMapper;

    @Override
    public List<ReglementationDto> getAllReglementation() {
        List<Reglementation> reglementations = reglementationRepository.findAll();
        if (reglementations.isEmpty()) {
            throw new CodeNotFoundException("Aucune reglementation trouvée ");
        }

        return  reglementations.stream().map(reglementMapper::toDto).toList();
    }
}
