package eng.bns.nomenclature.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DescriptionDto {

    private Long id;

    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String status;
    private Long idTaric;
    private String description;


    private Long idNomenclature;
}
