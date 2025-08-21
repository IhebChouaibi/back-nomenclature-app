package eng.bns.nomenclature.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@Builder
public class ValidationMesureDto {
    private Long idValidation;
    private Long idMesure;
    private Long idStatus;
    private String commentaire ;
    private LocalDate dateValidation;
    private Long idValidateur;

}
