package eng.bns.nomenclature.service;

import eng.bns.nomenclature.dto.MesureDto;
import eng.bns.nomenclature.entities.MouvementCommercial;

public interface MesureService {
    MesureDto addMesure(Long idNomenclature,MesureDto mesureDto);
    MesureDto updateMesure(MesureDto mesureDto);
    MesureDto deleteMesure(Long idMesure);
    MesureDto getMesure(Long idMesure);
    MesureDto addMouvementCommercial(MouvementCommercial mvt, MesureDto mesureDto);
}
