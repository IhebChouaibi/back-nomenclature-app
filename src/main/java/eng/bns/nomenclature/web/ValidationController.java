package eng.bns.nomenclature.web;

import eng.bns.nomenclature.dto.StatutDto;
import eng.bns.nomenclature.dto.ValidationMesureDto;
import eng.bns.nomenclature.dto.ValidationRequest;
import eng.bns.nomenclature.entities.User;
import eng.bns.nomenclature.entities.ValidationMesure;
import eng.bns.nomenclature.service.StatutService;
import eng.bns.nomenclature.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/validation")
@RequiredArgsConstructor
public class ValidationController {
    private final StatutService statutService ;
    private final ValidationService validationService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/statut")
    public ResponseEntity<StatutDto> getSatutById( @RequestParam Long idStatut){
        return ResponseEntity.ok(statutService.getStatutById(idStatut));
    }


}