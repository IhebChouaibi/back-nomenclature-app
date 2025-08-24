package eng.bns.nomenclature.service;

import eng.bns.nomenclature.dto.StatutDto;
import eng.bns.nomenclature.entities.Statut;
import eng.bns.nomenclature.mapper.StatutMapper;
import eng.bns.nomenclature.repository.StatutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatutServiceImpl implements StatutService{
    private final StatutMapper statutMapper;
    private final StatutRepository statutRepository;
    @Override
    public StatutDto getStatutById(Long idStatut) {
        Statut existStatut = statutRepository.findById(idStatut).orElseThrow(()->
                new RuntimeException("Statut introuvable"));
        return statutMapper.toDto(existStatut);
    }
}
