package eng.bns.nomenclature.service;

import eng.bns.nomenclature.dto.DescriptionDto;
import eng.bns.nomenclature.entities.Description;
import eng.bns.nomenclature.mapper.DescriptionMapper;
import eng.bns.nomenclature.repository.DescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DescriptionServiceImpl implements DescriptionService{
    private final DescriptionMapper descriptionMapper;
    private final DescriptionRepository descriptionRepository;

    @Override
    public DescriptionDto updateDescription(Long idNomenclature, DescriptionDto descriptionDto) {
        Description oldDescription = descriptionRepository.findByTaric_IdNomenclatureOrderByIdDesc(idNomenclature);
        if(oldDescription != null){

            oldDescription.setStatus("0");
            descriptionRepository.save(oldDescription);
        }

      Description  newDescription = descriptionRepository.save(descriptionMapper.toEntity(descriptionDto));
        newDescription.setStatus("1");
        return descriptionMapper.toDto(newDescription);

    }

    @Override
    public DescriptionDto createDescription( Long idNomenclature,DescriptionDto descriptionDto) {
       descriptionDto.setIdNomenclature(idNomenclature);
       descriptionDto.setStatus("1");

      descriptionRepository.save(descriptionMapper.toEntity(descriptionDto));
      return descriptionDto;
    }

    @Override
    public DescriptionDto deleteDescription(Long idNomenclature) {
        Description description = descriptionRepository.findByTaric_IdNomenclatureOrderByIdDesc(idNomenclature);
        if(description != null){
            description.setStatus("0");
            descriptionRepository.save(description);
        }
        return descriptionMapper.toDto(description);
    }


}
