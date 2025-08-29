package eng.bns.nomenclature.service;

import eng.bns.nomenclature.dto.MesureDto;
import eng.bns.nomenclature.dto.ValidationMesureDto;
import eng.bns.nomenclature.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MesureService {
    MesureDto addMesure(List<Long> idTarics, MesureDto mesureDto);
    MesureDto updateMesure( Long idMesure,MesureDto mesureDto ,List<Long> idTarics);
    MesureDto deleteMesure(Long idMesure);
    Page<MesureDto> getAllMesures(Pageable pageable);
    Page<MesureDto> getMesuresByStatut(String statut, Pageable pageable);
    List<MesureDto> tariterMesure(List<Long> idMesures, Long responsableId, String codeStatut, String commentaire);
}
