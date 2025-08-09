package eng.bns.nomenclature.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private Date dateDebutValid;
    private Date dateFinValid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_taric")
    private TARIC taric;

}
