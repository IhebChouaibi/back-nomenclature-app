package eng.bns.nomenclature.web;

import eng.bns.nomenclature.dto.*;
import eng.bns.nomenclature.entities.MesureTarifaire;
import eng.bns.nomenclature.entities.MouvementCommercial;
import eng.bns.nomenclature.service.MesureService;
import eng.bns.nomenclature.service.MouvementCommercialService;
import eng.bns.nomenclature.service.ReglementationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/mesure")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class MesureController {
    private final MesureService mesureService;
    private final MouvementCommercialService mouvementCommercialService;
private final ReglementationService reglementationService;

    @PostMapping("/add")
     public ResponseEntity<MesureDto> addMesure( @RequestParam("idTarics") List<Long> idTarics,
                                               @RequestBody MesureDto mesureDto
    ){
        MesureDto createdMesure = mesureService.addMesure(idTarics,mesureDto);
         return new ResponseEntity<>(createdMesure , HttpStatus.CREATED);
     }
     @PatchMapping("/update")
     public ResponseEntity<MesureDto> updateMesure(@RequestParam Long idMesure, @RequestParam List<Long> idTarics, @RequestBody MesureDto mesureDto){
          MesureDto updatedMesure = mesureService.updateMesure(idMesure,mesureDto,idTarics);
          return ResponseEntity.ok(updatedMesure);
      }

      @GetMapping("/getAllMesure")
      public  ResponseEntity<Page<MesureDto>> getAllMesures(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size){
       Page<MesureDto> mesureDtos  = mesureService.getAllMesures(PageRequest.of(page,size));
        return ResponseEntity.ok(mesureDtos);

      }
    @PreAuthorize("hasRole('ADMIN') or hasRole('RESPONSABLE')")
      @GetMapping("/getMesureByStatut")
      public  ResponseEntity<Page<MesureDto>> getMesuresByStatut(@RequestParam String statut,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size){
       Page<MesureDto> mesureDtos = mesureService.getMesuresByStatut(statut,PageRequest.of(page,size));
            return ResponseEntity.ok(mesureDtos);

      }
      @GetMapping("/getAllMvt")
    public ResponseEntity<List<MvtDto>> getAllMvt(){
       List<MvtDto> MvtList = mouvementCommercialService.getAllMouvementCommercial();
       return ResponseEntity.ok(MvtList);

    }
    @GetMapping("/getAllReglementation")
    public ResponseEntity<List<ReglementationDto>> getAllReglementation(){
        List<ReglementationDto> reglementationList = reglementationService.getAllReglementation();
        return ResponseEntity.ok(reglementationList);
    }
    @PreAuthorize("hasRole('RESPONSABLE')")
    @PostMapping("/validation")
    public ResponseEntity< List<MesureDto>> updateMesureStatus(
            @RequestParam List<Long> idMesures,
            @RequestParam  Long responsableId,
            @RequestBody ValidationRequest validationRequest
    ){
        List<MesureDto> mesureDtos =  mesureService.tariterMesure(idMesures, responsableId,validationRequest.getCodeStatut(),
                validationRequest.getCommentaire());
        return ResponseEntity.ok(mesureDtos);

    }

}
