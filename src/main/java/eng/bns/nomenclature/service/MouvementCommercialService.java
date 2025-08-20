package eng.bns.nomenclature.service;

import eng.bns.nomenclature.entities.MouvementCommercial;

import java.util.List;

public interface MouvementCommercialService {
    List<MouvementCommercial> getAllMouvementCommercial();
    MouvementCommercial getMouvementCommercialById(Long id);
    MouvementCommercial addMouvementCommercial(MouvementCommercial mouvementCommercial);


}
