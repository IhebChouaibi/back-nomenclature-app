package eng.bns.nomenclature.service;

import eng.bns.nomenclature.dto.MvtDto;
import eng.bns.nomenclature.entities.MouvementCommercial;
import eng.bns.nomenclature.mapper.MvtMapper;
import eng.bns.nomenclature.repository.MouvementCommercialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class MouvementCommercialServiceImpl implements MouvementCommercialService {
    private final MouvementCommercialRepository mouvementCommercialRepository;
    private final MvtMapper mvtMapper;
    @Override
    public List<MvtDto> getAllMouvementCommercial() {
        return mouvementCommercialRepository.findAll().stream().map(mvtMapper::toDto).toList();

    }

    @Override
    public MvtDto getMouvementCommercialById(Long id) {
        MouvementCommercial mvt = mouvementCommercialRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Mouvement Commercial inexistant"));
        return mvtMapper.toDto(mvt);
    }




}
