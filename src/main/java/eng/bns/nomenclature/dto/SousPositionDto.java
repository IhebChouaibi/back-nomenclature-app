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
public class SousPositionDto {
    private Long idSousPosition;
    private String codeSousPosition;
    private String libelleSousPosition;
    private Long idPosition;

    private List<NCDto> nomenclatureCombinees;

}
