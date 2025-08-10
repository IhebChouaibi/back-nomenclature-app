package eng.bns.nomenclature.web;

import eng.bns.nomenclature.dto.NotesDto;
import eng.bns.nomenclature.dto.TARICDto;
import eng.bns.nomenclature.service.TaricService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")

@RestController
@RequiredArgsConstructor
public class TaricContoller {

    private final TaricService taricService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("addnote")
    public ResponseEntity<NotesDto>  addNotesToTaric(@RequestParam long id , @RequestBody NotesDto notesDto){
       NotesDto newNote=  taricService.addNotesToTaric(id,notesDto);
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
}
