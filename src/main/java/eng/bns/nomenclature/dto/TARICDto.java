package eng.bns.nomenclature.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TARICDto {
    private Long idNomenclature;
    private String codeNomenclature;
    private String libelleNomenclature;
    private Date dateDebutValid;
    private Date dateFinValid;
    private Long idNCombinee;
    private Long idSuffix;
}