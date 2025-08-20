package eng.bns.nomenclature.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "validation_mesure",
        indexes = {
                @Index(name = "idx_mesure_date", columnList = "mesure_id, dateValidation"),
                @Index(name = "idx_validateur_statut", columnList = "user_id, statut")
        }
)

public class ValidationMesure extends MetaDonnees  {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idValidation ;

    @Column(nullable = false)
    private LocalDate dateValidation ;
    @ManyToOne
    @JoinColumn(name="id_Status")
    private Statut statut ;


    @ManyToOne(optional = false)
    @JoinColumn(name="id_mesure")

    private MesureTarifaire mesure ;

    @ManyToOne(optional = false)
    @JoinColumn(name="id_validateur")
    private User Validateur ;





}
