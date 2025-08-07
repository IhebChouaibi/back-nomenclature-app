package eng.bns.nomenclature.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChapitreDto {
    private Long idChapitre;
    private String codeChapitre;
    private String libelleChapitre;
// Add this
    private Long idSection;
    private List<PositionDto> positions;
}
