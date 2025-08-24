package eng.bns.nomenclature.web;

import eng.bns.nomenclature.dto.StatutDto;
import eng.bns.nomenclature.service.StatutService;
import eng.bns.nomenclature.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/validation")
@RequiredArgsConstructor
public class ValidationController {
    private final ValidationService validationService;
    private final StatutService statutService ;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/statut")
    public ResponseEntity<StatutDto> getSatutById( @RequestParam Long idStatut){
        return ResponseEntity.ok(statutService.getStatutById(idStatut));
    }
}
