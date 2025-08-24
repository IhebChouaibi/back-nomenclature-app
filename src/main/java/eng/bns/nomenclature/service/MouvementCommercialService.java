package eng.bns.nomenclature.service;

import eng.bns.nomenclature.dto.MvtDto;
import eng.bns.nomenclature.entities.MouvementCommercial;

import java.util.List;

public interface MouvementCommercialService {
    List<MvtDto> getAllMouvementCommercial();
    MvtDto getMouvementCommercialById(Long id);


}
