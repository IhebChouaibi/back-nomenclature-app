package eng.bns.nomenclature.web;

import eng.bns.nomenclature.dto.*;
import eng.bns.nomenclature.entities.Notes;
import eng.bns.nomenclature.service.NotesService;
import eng.bns.nomenclature.service.SuffixService;
import eng.bns.nomenclature.service.TaricService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")

@RestController
@RequiredArgsConstructor
public class TaricContoller {

    private final TaricService taricService;
    private final SuffixService suffixService;
    private  final NotesService notesService;


    @PostMapping("taric/addTaric")
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<TARICDto> addTaric(@RequestBody TaricWithDetailsRequest taricRequest){
      TARICDto createdTaric =  taricService.createTaric(taricRequest);
        return new  ResponseEntity<>(createdTaric, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("taric/addnote")
    public ResponseEntity<NotesDto>  addNotesToTaric(@RequestParam long idNomenclature , @RequestBody NotesDto notesDto){
       NotesDto newNote=  notesService.addNotesToTaric(idNomenclature,notesDto);
        return   ResponseEntity.ok(newNote);

    }


    @GetMapping("taric/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<TARICDto>> searchTaricByCode(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "20") int size,
                                                            @RequestParam String  codeTaric){
        Page<TARICDto> taricDtos = taricService.searchTaricByCode(codeTaric , PageRequest.of(page, size));
        return ResponseEntity.ok(taricDtos);

    }
    @GetMapping("taric/suffix/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuffixDto> getSuffix(@PathVariable Long id){
        SuffixDto suffixDto = suffixService.getSuffix(id);
        return ResponseEntity.ok(suffixDto);
    }


    @PostMapping("taric/addSuffix/{idNomenclature}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuffixDto> addSuffix(@PathVariable Long idNomenclature ,@RequestBody SuffixDto suffixDto){
        suffixService.addsuffix(idNomenclature,suffixDto);
        return  ResponseEntity.ok().build();
    }

}



