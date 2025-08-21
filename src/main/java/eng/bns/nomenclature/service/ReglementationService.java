package eng.bns.nomenclature.service;

import eng.bns.nomenclature.dto.ReglementationDto;
import org.apache.xmlbeans.impl.xb.xmlconfig.Extensionconfig;

import java.util.List;

public interface ReglementationService {
    List<ReglementationDto>  getAllReglementation();

}
