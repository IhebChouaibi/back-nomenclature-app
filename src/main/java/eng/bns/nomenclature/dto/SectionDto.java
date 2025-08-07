package eng.bns.nomenclature.dto;

import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class SectionDto {
    private Long idSection;
    private String codeSection;
    private String libelleSection;

    private List<ChapitreDto> chapitres;

}
