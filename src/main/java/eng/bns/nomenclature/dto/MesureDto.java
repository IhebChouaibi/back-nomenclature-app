package eng.bns.nomenclature.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MesureDto {
    private Long idMesure;
    private String  codeMesure;
    private Long idStatut;
    private LocalDate dateDebut ;
    private LocalDate dateFin ;
    private String numeroQuota;
    private Long idMvtCommercial ;
    private Long  idReglement;


    private List<ValidationMesureDto> validations;


}
