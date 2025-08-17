package eng.bns.nomenclature.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TaricWithDetailsRequest {

    private String codeNomenclature;

    private LocalDate dateDebutValid ;
    private LocalDate dateFinValid ;
    private SuffixDto suffixDto ;
    private List<DescriptionDto> descriptions ;
    private List<NotesDto> notes ;



}
