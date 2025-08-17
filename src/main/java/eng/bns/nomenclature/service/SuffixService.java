package eng.bns.nomenclature.service;

import eng.bns.nomenclature.dto.SuffixDto;

public interface SuffixService {
    SuffixDto getSuffix(Long idSuffix  );
    SuffixDto addsuffix( Long idNomenclature,SuffixDto suffixDto);
    SuffixDto updateSuffix(SuffixDto suffixDto);
}
