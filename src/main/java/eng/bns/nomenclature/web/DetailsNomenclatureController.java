package eng.bns.nomenclature.web;

import eng.bns.nomenclature.dto.TARICDto;
import eng.bns.nomenclature.service.DetailsNomenclatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DetailsNomenclatureController {
    private final DetailsNomenclatureService detailsNomenclatureService;
    @PreAuthorize("hasRole('ADMIN')")

    @GetMapping("/info")
    public ResponseEntity<TARICDto> getInfo(String code){
        TARICDto dto = detailsNomenclatureService.getTaric(code) ;
        return ResponseEntity.ok(dto);
    }



}
