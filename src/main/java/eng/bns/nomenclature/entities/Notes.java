package eng.bns.nomenclature.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
@Entity
@Table(name="notes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNote;

    @Column(columnDefinition = "TEXT")
    private String contenu;

    private LocalDate dateDebutValid;
    private LocalDate dateFinValid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_taric")
    private TARIC taric;

}
