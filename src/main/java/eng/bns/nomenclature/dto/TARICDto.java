package eng.bns.nomenclature.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TARICDto {
    private Long idNomenclature;
    private String codeNomenclature;
    private List<DescriptionDto> descriptions;

    private LocalDate dateDebutValid;
    private LocalDate dateFinValid;
    private List<NotesDto> notes;
    private Long idNCombinee;
    private Long idSuffix;
    private List<MesureDto> mesures;
}