package eng.bns.nomenclature.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class SuffixDto {
    private Long id;
    private String codeSuffix ;
    private boolean declarable;

    private boolean national;
    private Long idNomenclature;
}
