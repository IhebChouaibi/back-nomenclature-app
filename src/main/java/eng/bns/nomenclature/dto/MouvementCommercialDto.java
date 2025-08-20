package eng.bns.nomenclature.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class MouvementCommercialDto {

    private Long idMvtCommercial;
    private String libelle;
}
