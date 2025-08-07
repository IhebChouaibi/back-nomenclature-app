package eng.bns.nomenclature.dto;

import lombok.Data;

import java.sql.Date;


@Data
public class TaricRequest {
    private String codeNomenclature;
    private String libelleNomenclature;
    private Date dateDebutValid;
    private Date dateFinValid;
    private Long suffixId;
}
