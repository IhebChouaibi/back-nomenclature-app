package eng.bns.nomenclature.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="notes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notes  extends MetaDonnees{
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
