package eng.bns.nomenclature.service;

import eng.bns.nomenclature.dto.TARICDto;
import eng.bns.nomenclature.entities.TARIC;
import eng.bns.nomenclature.mapper.ReglementMapper;
import eng.bns.nomenclature.mapper.TARICMapper;
import eng.bns.nomenclature.repository.ReglementationRepository;
import eng.bns.nomenclature.repository.TaricRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DetailsNomenclatureServiceImpl implements DetailsNomenclatureService {
    private final TARICMapper taricMapper;
    private final TaricRepository taricRepository ;
    private final ReglementationRepository reglementationRepository;
    private final ReglementMapper reglementationMapper;
    @Override
    public TARICDto getTaric(String codeNomenclature) {
        TARIC taric = taricRepository.findByCodeNomenclature(codeNomenclature);
        if (taric == null) {
            return null;
        }
        return taricMapper.toDto(taric);

    }


}
