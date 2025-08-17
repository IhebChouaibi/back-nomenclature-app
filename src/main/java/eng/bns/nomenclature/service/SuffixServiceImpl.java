package eng.bns.nomenclature.service;

import eng.bns.nomenclature.dto.SuffixDto;
import eng.bns.nomenclature.entities.Suffix;
import eng.bns.nomenclature.entities.TARIC;
import eng.bns.nomenclature.exception.CodeNotFoundException;
import eng.bns.nomenclature.mapper.SuffixMapper;
import eng.bns.nomenclature.repository.SuffixRepository;
import eng.bns.nomenclature.repository.TaricRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SuffixServiceImpl  implements SuffixService{
    private final SuffixRepository suffixRepository;
    private final SuffixMapper suffixMapper;
    private final TaricRepository taricRepository;
    @Override
    public SuffixDto getSuffix(Long idSuffix) {
        Suffix suffix = suffixRepository.findByIdSuffix(idSuffix);

        if (suffix == null) {
           throw new CodeNotFoundException("suffix not found");
        }
        return suffixMapper.toDto(suffix);


    }

    @Override
    @Transactional

    public SuffixDto addsuffix(Long idNomenclature, SuffixDto suffixDto) {
        TARIC existTaric = taricRepository.findById(idNomenclature)
                .orElseThrow(() -> new CodeNotFoundException("TARIC not found with id: " + idNomenclature));
        if (existTaric.getSuffix() != null) {
            throw new IllegalStateException("TARIC already has a suffix");
        }

        Suffix suffix = null;
        if (suffixDto.getCodeSuffix() !=null){
             suffix = suffixRepository.findByCodeSuffix(suffixDto.getCodeSuffix());

        }
        if (suffix != null ){
            suffix.getTarics().add(existTaric);
            existTaric.setSuffix(suffix);
        }else{
             suffix = suffixMapper.toEntity(suffixDto);

            suffix.getTarics().add(existTaric);
            existTaric.setSuffix(suffix);
             suffix = suffixRepository.save(suffix);


        }

        taricRepository.save(existTaric);

        return suffixMapper.toDto(suffix);


    }

    @Override
    public SuffixDto updateSuffix(SuffixDto suffixDto) {

        return null;
    }
}
