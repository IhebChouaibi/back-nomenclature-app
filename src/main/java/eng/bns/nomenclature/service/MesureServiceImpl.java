package eng.bns.nomenclature.service;

import eng.bns.nomenclature.dto.MesureDto;
import eng.bns.nomenclature.entities.MesureTarifaire;
import eng.bns.nomenclature.entities.MouvementCommercial;
import eng.bns.nomenclature.mapper.MesureMapper;
import eng.bns.nomenclature.repository.MesureTarifaireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service

public class MesureServiceImpl implements MesureService{
    private final MesureMapper mesureMapper;
    private final MesureTarifaireRepository mesureTarifaireRepository;
    @Override
    public MesureDto addMesure(Long idNomenclature, MesureDto mesureDto) {
        return null;
    }

    @Override
    public MesureDto updateMesure(MesureDto mesureDto) {
        return null;
    }

    @Override
    public MesureDto deleteMesure(Long idMesure) {
        return null;
    }

    @Override
    public MesureDto getMesure(Long idMesure) {
        return null;
    }

    @Override
    public MesureDto addMouvementCommercial(MouvementCommercial mvt, MesureDto mesureDto) {
        return null;}
}
