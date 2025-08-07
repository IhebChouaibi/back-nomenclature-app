package eng.bns.nomenclature.service;

import eng.bns.nomenclature.dto.ReglementationDto;
import eng.bns.nomenclature.dto.TARICDto;

import java.util.List;

public interface DetailsNomenclatureService {
     TARICDto getTaric(String codeNomenclature);
    List<ReglementationDto> getRegles(String codeNomenclature);


}
