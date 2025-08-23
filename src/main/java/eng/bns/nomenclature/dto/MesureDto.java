package eng.bns.nomenclature.dto;

import eng.bns.nomenclature.entities.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MesureDto {
    private Long idMesure;
    private String  codeMesure;

    private LocalDate dateDebut ;
    private LocalDate dateFin ;
    private String numeroQuota;
    private Long idMvtCommercial ;
    private Long  idReglement;


    private List<ValidationMesureDto> validations;


}
