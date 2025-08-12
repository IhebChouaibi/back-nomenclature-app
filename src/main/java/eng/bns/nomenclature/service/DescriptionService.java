package eng.bns.nomenclature.service;

import eng.bns.nomenclature.dto.DescriptionDto;

public interface DescriptionService {
    DescriptionDto updateDescription(Long idNomenclature,DescriptionDto descriptionDto);
    DescriptionDto createDescription(Long idNomenclature,DescriptionDto descriptionDto);
    DescriptionDto deleteDescription(Long idNomenclature);
}
