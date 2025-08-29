package eng.bns.nomenclature.dto;

import lombok.Data;

@Data
public class ValidationRequest {
    private String codeStatut;
    private String commentaire;
}
