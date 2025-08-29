package eng.bns.nomenclature.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdNotif;
    private String message;
    private boolean seen;
    private LocalDateTime date;
    private LocalDateTime creatAt = LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name="id_user")
    private User user;
}
