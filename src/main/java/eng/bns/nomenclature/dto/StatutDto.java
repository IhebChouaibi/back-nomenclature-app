package eng.bns.nomenclature.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
 @AllArgsConstructor
@Setter
@Builder
public class StatutDto {
    private Long idStatut;
    private String libelle;
}
