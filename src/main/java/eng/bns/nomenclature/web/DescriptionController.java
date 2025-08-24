package eng.bns.nomenclature.web;

import eng.bns.nomenclature.dto.DescriptionDto;
import eng.bns.nomenclature.service.DescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/description")
public class DescriptionController {
    private final DescriptionService descriptionService;

    @PatchMapping("/update")
    public ResponseEntity<DescriptionDto> updateDescription(@RequestParam Long idNomenclature, @RequestBody DescriptionDto descriptionDto){
        DescriptionDto newDescription = descriptionService.updateDescription(idNomenclature,descriptionDto);
        return ResponseEntity.ok(newDescription);

    }
    @PatchMapping("/delete")
    public ResponseEntity<DescriptionDto> deleteDescription(@RequestParam Long idNomenclature){
        DescriptionDto descriptionDto = descriptionService.deleteDescription(idNomenclature);
        return ResponseEntity.ok(descriptionDto);
    }

    @PatchMapping("/add")
    public ResponseEntity<DescriptionDto>   addDescription(@RequestParam Long idNomenclature,DescriptionDto descriptionDto){
        descriptionService.createDescription(idNomenclature,descriptionDto);
        return ResponseEntity.ok().build();

    }
}
