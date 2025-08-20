package eng.bns.nomenclature.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
 @Setter
 @AllArgsConstructor
 @NoArgsConstructor
public class ReglementationDto {
    private Long idReglement;
    private String codeReglementation;
    private String ref;
    private String resume;
    private Boolean approuve;
    private LocalDate debutValidite;
    private LocalDate finValidite;

     private Long  idEtat;
     private Long idPays;
     private Long idTypeReglement;
     private List<MesureDto> mesures;


 }
