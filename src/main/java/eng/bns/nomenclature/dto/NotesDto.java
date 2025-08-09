package eng.bns.nomenclature.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component

public class NotesDto {
    private Long idNote;

    private String contenu ;

    private Date dateDebutValid;
    private Date dateFinValid;

    private long  idNomenclature;

}
