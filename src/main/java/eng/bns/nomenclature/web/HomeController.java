package eng.bns.nomenclature.web;

import eng.bns.nomenclature.dto.*;
import eng.bns.nomenclature.entities.Section;
import eng.bns.nomenclature.entities.User;
import eng.bns.nomenclature.mapper.SectionMapper;
import eng.bns.nomenclature.repository.SectionRepository;
import eng.bns.nomenclature.repository.UserRepository;
import eng.bns.nomenclature.service.HomeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")

@RestController
@PreAuthorize("hasRole('ADMIN')")

public class HomeController {
    private final UserRepository userRepository;
    private final HomeService homeService;

private final SectionRepository sectionRepository;

    public HomeController(UserRepository userRepository, HomeService homeService, SectionMapper sectionMapper, SectionRepository sectionRepository) {
        this.userRepository = userRepository;
        this.homeService = homeService;

        this.sectionRepository = sectionRepository;
    }

    @GetMapping("/home")

    public ResponseEntity <HomeResponse>home (){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findUsersByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        HomeResponse response = new HomeResponse(
                "Bienvenue sur l'espace administrateur",
                user.getUsername(),
                user.getEmail(),
                user.getRoles().toString(),
                user.getId()
        );

        return ResponseEntity.ok(response);
    }
    @GetMapping("/test-sections")
    public ResponseEntity<?> testSections() {
        List<Section> sections = sectionRepository.findAll();

        return ResponseEntity.ok(sections);
    }

    @GetMapping("/section")

    public ResponseEntity<Page<SectionDto>> section( @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "7") int size){
        Page<SectionDto> response =homeService.getAllSectionsPaginated(PageRequest.of(page, size));

    return  ResponseEntity.ok(response);

    }



    @PostMapping("addPosition")
    public ResponseEntity<PositionDto> addPosition (
            @RequestBody PositionDto positionDto){
        PositionDto createdPosition = homeService.addPosition(positionDto);
        return new ResponseEntity<>(createdPosition, HttpStatus.CREATED);

    }

    @PostMapping("addSousPosition")
    public ResponseEntity<SousPositionDto> addSousPosition (
            @RequestBody SousPositionDto sousPositionDto){
        SousPositionDto sousPosition = homeService.addSousPosition(sousPositionDto);
        return new ResponseEntity<>(sousPositionDto, HttpStatus.CREATED);

    }

    @PostMapping("addSection")
    public ResponseEntity<SectionDto> addSection (
            @RequestBody SectionDto sectionDto) {
        SectionDto section = homeService.addSection(sectionDto);
        return new ResponseEntity<>(section, HttpStatus.CREATED);
    }

    @PostMapping("addChapitre")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ChapitreDto> addChapitre (
            @RequestBody ChapitreDto chapitreDto) {
        ChapitreDto chapitre = homeService.addChapitre(chapitreDto);
        return new ResponseEntity<>(chapitre, HttpStatus.CREATED);
    }

    @PatchMapping("updateSection/{idSection}")
    public ResponseEntity<SectionDto> updateSection(@PathVariable Long idSection, @RequestBody String  sectionLibelle) {
        SectionDto newSection= homeService.updateSectionLibelle(idSection,sectionLibelle);

return ResponseEntity.ok(newSection);

    }


    @PatchMapping("updatePosition/{idPosition}")
    public ResponseEntity<PositionDto> updatePosition( @PathVariable Long idPosition, @RequestBody PositionDto  positionDto) {
        PositionDto newPosition= homeService.updatePosition(idPosition,positionDto);

        return ResponseEntity.ok(newPosition);

    }

    @PatchMapping("updateSousPosition/{idSousPosition}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SousPositionDto> updateSousPosition( @PathVariable Long idSousPosition, @RequestBody SousPositionDto  sousPositionDto) {
        SousPositionDto newSousPosition= homeService.updateSousPosition(idSousPosition,sousPositionDto);

        return ResponseEntity.ok(newSousPosition);

    }

    @PatchMapping("updateChapitre/{idChapitre}")
    public ResponseEntity<ChapitreDto> updateChapitre( @PathVariable Long idChapitre, @RequestBody ChapitreDto  chapitreDto) {
        ChapitreDto newSousPosition= homeService.updateChapitre(idChapitre,chapitreDto);

        return ResponseEntity.ok(newSousPosition);

    }


    @DeleteMapping("deleteSection")
    public ResponseEntity<SectionDto> deleteSection(Long idSection) {
        homeService.deleteSection(idSection);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("deletePosition")
    public ResponseEntity<PositionDto> deletePosition(Long idPosition) {
        homeService.deletePosition(idPosition);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("deleteSousPosition")
    public ResponseEntity<SousPositionDto> deleteSousPosition(Long idSousPosition) {
        homeService.deleteSousPosition(idSousPosition);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("deleteChapitre")
    public ResponseEntity<ChapitreDto> deleteChapitre(Long idChapitre) {
        homeService.deleteChapitre(idChapitre);
        return ResponseEntity.ok().build();
    }


}
