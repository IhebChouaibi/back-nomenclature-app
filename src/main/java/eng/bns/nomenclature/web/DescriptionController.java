package eng.bns.nomenclature.web;

import eng.bns.nomenclature.dto.DescriptionDto;
import eng.bns.nomenclature.service.DescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DescriptionController {
    private final DescriptionService descriptionService;

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/description/update")
    public ResponseEntity<DescriptionDto> updateDescription(@RequestParam Long idNomenclature, DescriptionDto descriptionDto){
        descriptionService.updateDescription(idNomenclature,descriptionDto);
        return ResponseEntity.ok().build();

    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/description/delete")
    public ResponseEntity<DescriptionDto> deleteDescription(@RequestParam Long idNomenclature){
        DescriptionDto descriptionDto = descriptionService.deleteDescription(idNomenclature);
        return ResponseEntity.ok(descriptionDto);
    }

}
