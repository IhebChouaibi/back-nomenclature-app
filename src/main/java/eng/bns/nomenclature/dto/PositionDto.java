package eng.bns.nomenclature.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PositionDto {


    private Long idPosition ;
    private String codePosition;
    private String libellePosition;
    private Long idChapitre;

    private List<SousPositionDto> sousPositions;


}
