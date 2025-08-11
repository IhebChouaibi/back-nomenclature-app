package eng.bns.nomenclature.service;

import eng.bns.nomenclature.dto.SuffixDto;
import eng.bns.nomenclature.entities.Suffix;
import eng.bns.nomenclature.exception.CodeNotFoundException;
import eng.bns.nomenclature.mapper.SuffixMapper;
import eng.bns.nomenclature.repository.SuffixRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SuffixServiceImpl  implements SuffixService{
    private final SuffixRepository suffixRepository;
    private final SuffixMapper suffixMapper;
    @Override
    public SuffixDto getSuffix(Long idSuffix) {
        Suffix suffix = suffixRepository.findByIdSuffix(idSuffix);

        if (suffix == null) {
           throw new CodeNotFoundException("suffix not found");
        }
        return suffixMapper.toDto(suffix);


    }
}
