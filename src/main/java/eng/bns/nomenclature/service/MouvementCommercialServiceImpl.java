package eng.bns.nomenclature.service;

import eng.bns.nomenclature.entities.MouvementCommercial;
import eng.bns.nomenclature.repository.MouvementCommercialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class MouvementCommercialServiceImpl implements MouvementCommercialService {
    private final MouvementCommercialRepository mouvementCommercialRepository;
    @Override
    public List<MouvementCommercial> getAllMouvementCommercial() {
        return mouvementCommercialRepository.findAll();

    }

    @Override
    public MouvementCommercial getMouvementCommercialById(Long id) {
        return mouvementCommercialRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Mouvement Commercial inexistant"));
    }

    @Override
    public MouvementCommercial addMouvementCommercial(MouvementCommercial mouvementCommercial) {
        return mouvementCommercialRepository.save(mouvementCommercial);


    }


}
