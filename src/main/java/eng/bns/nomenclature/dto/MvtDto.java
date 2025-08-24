package eng.bns.nomenclature.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
 @Setter
@Builder
@AllArgsConstructor
public class MvtDto {
    private Long idMvtCommercial;
    private String libelle;
}
