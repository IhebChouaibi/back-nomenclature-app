package eng.bns.nomenclature.dto;

import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;


@Data
public class TaricRequest {
    private String codeNomenclature;
    private String libelleNomenclature;
    private LocalDate dateDebutValid;
    private LocalDate dateFinValid;
    private Long suffixId;
}
