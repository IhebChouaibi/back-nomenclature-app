package eng.bns.nomenclature.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
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
    private Date debutValidite;
    private Date finValidite;

     private Long idEtat;
     private Long idPays;
     private Long idTypeReglement;
     private Long idTaric;


 }
