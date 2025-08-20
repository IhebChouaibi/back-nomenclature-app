package eng.bns.nomenclature.dto;

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

    private Long idSection;
    private List<PositionDto> positions;
}
