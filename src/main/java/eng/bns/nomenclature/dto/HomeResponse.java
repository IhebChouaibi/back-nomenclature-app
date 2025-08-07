package eng.bns.nomenclature.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor @Builder
public class HomeResponse {
    private String message;
    private String username;
    private String email;
    private String role;
    private Long userId;
}