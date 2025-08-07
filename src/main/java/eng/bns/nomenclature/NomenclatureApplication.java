package eng.bns.nomenclature;

import eng.bns.nomenclature.entities.Role;
import eng.bns.nomenclature.entities.Section;
import eng.bns.nomenclature.entities.User;
import eng.bns.nomenclature.repository.SectionRepository;
import eng.bns.nomenclature.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class NomenclatureApplication {
    private final SectionRepository sectionRepository;

    public NomenclatureApplication(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    public static void main(String[] args) {

        SpringApplication.run(NomenclatureApplication.class, args);

    }

  @Bean
    CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Page<Section> sections = sectionRepository.findAll(PageRequest.of(0, 10));
            System.out.println("Sections trouvées: " + sections.getTotalElements());

            System.out.println("=== TEST DU HASH EXISTANT ===");

            User user = userRepository.findUsersByUsername("adem")
                    .orElseThrow(() -> new RuntimeException("User not found"));

            System.out.println("Utilisateur: " + user);
            System.out.println("Hash existant: " + user.getPassword());

            // Test avec le mot de passe
            String rawPassword = "54110116";
            boolean matches = passwordEncoder.matches(rawPassword, user.getPassword());
            System.out.println("Le mot de passe '" + rawPassword + "' correspond au hash: " + matches);

            if (!matches) {
                System.out.println("❌ HASH INVALIDE - Recréation nécessaire");
                user.setPassword(passwordEncoder.encode(rawPassword));
                userRepository.save(user);
                System.out.println("✅ Nouveau hash créé: " + user.getPassword());
            } else {
                System.out.println("✅ Hash valide");
            }
        };
    }


}
