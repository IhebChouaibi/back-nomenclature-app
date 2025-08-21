package eng.bns.nomenclature.web;

import eng.bns.nomenclature.dto.MesureDto;
import eng.bns.nomenclature.service.MesureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/mesure")
@RequiredArgsConstructor
public class MesureController {
    private final MesureService mesureService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
     public ResponseEntity<MesureDto> addMesure( @RequestParam("idTarics") List<Long> idTarics,
                                               @RequestBody MesureDto mesureDto
    ){
        MesureDto createdMesure = mesureService.addMesure(idTarics,mesureDto);
         return new ResponseEntity<>(createdMesure , HttpStatus.CREATED);
     }
}
