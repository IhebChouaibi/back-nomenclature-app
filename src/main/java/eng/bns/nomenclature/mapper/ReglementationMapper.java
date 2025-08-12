package eng.bns.nomenclature.mapper;

import eng.bns.nomenclature.dto.ReglementationDto;
import eng.bns.nomenclature.entities.Reglementation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ReglementationMapper {
    private ModelMapper modelMapper=new ModelMapper();

   public  Reglementation mapReglementationDtoToReglementation(ReglementationDto reglementationDto){
        return modelMapper.map(reglementationDto,Reglementation.class);
    }
   public ReglementationDto mapReglementationToReglementationDto(Reglementation reglementation){

        ReglementationDto   dto = new ReglementationDto();
        dto.setIdReglement(reglementation.getIdReglement());
        dto.setCodeReglementation(reglementation.getCodeReglementation());
        dto.setApprouve(reglementation.getApprouve());
        dto.setRef(reglementation.getRef());
        dto.setDebutValidite(reglementation.getDebutValidite());
        dto.setFinValidite(reglementation.getFinValidite());
        if (reglementation.getEtat() != null) {
            dto.setIdEtat(reglementation.getEtat().getId());
        }
        if (reglementation.getPays() != null) {
            dto.setIdPays(reglementation.getPays().getIdPays());
        }
        if (reglementation.getTypeReglement() != null) {
            dto.setIdTypeReglement(reglementation.getTypeReglement().getId());
        }

        return  dto;
    }
}
