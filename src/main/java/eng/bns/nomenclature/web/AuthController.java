package eng.bns.nomenclature.web;

import com.nimbusds.jose.JOSEException;
import eng.bns.nomenclature.dto.AuthRequest;
import eng.bns.nomenclature.dto.AuthResponse;
import eng.bns.nomenclature.entities.User;
import eng.bns.nomenclature.repository.UserRepository;
import eng.bns.nomenclature.security.JwtService;
import eng.bns.nomenclature.security.UserDetailsImpl;
import eng.bns.nomenclature.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")

@RestController
 @RequiredArgsConstructor
public class AuthController {
    private final LoginService loginService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository   userRepository;
    private final JwtService  jwtService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> signin(@RequestBody AuthRequest authRequest) throws JOSEException {
        try {
            System.out.println("=== DÉBUT AUTHENTIFICATION ===");
            System.out.println("Username reçu: " + authRequest.getUsername());
            System.out.println("Password reçu: " + authRequest.getPassword());
            User user = userRepository.findUsersByUsername(authRequest.getUsername())
                    .orElseThrow(() -> {
                        System.err.println("ERREUR: Utilisateur non trouvé dans la base: " + authRequest.getUsername());
                        return new UsernameNotFoundException("Utilisateur non trouvé");
                    });

            System.out.println("Utilisateur trouvé:");
            System.out.println("- Username DB: " + user.getUsername());
            System.out.println("- Password hash DB: " + user.getPassword());
            System.out.println("- Role: " + user.getRoles());

            // Tenter l'authentification
            System.out.println("Tentative d'authentification...");
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );


            System.out.println("Tentative de connexion pour: " + authRequest.getUsername());
            AuthResponse response = loginService.login(authRequest);
            System.out.println("Connexion réussie pour: " + authRequest.getUsername());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Erreur de connexion: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


}
