package eng.bns.nomenclature.dto;

import eng.bns.nomenclature.entities.Role;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor @Builder
public class AuthResponse {
    private String token;
    private String username;
    private String role;
}
