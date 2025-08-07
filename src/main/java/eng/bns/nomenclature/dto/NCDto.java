package eng.bns.nomenclature.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NCDto {

    private Long idNCombinee;
    private  String codeNCombinee;
    private  String libelleNC;
    private Long idSousPosition;
    private List<TARICDto> nomenclatures;
}
