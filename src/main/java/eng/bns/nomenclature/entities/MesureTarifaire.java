package eng.bns.nomenclature.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="mesure_tarifaire")
@Entity
public class MesureTarifaire  extends MetaDonnees {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMesure;
    private String  codeMesure;

    private LocalDate dateDebut ;
    private LocalDate dateFin ;
    private String numeroQuota;

    @ManyToOne
    @JoinColumn(name = "id_pays")
    private Pays pays;
    @ManyToOne
    @JoinColumn(name="id_mouvement")
    private MouvementCommercial mouvementCommercial ;


    @ManyToOne
    @JoinColumn(name = "reglementation_id")
    private Reglementation reglementation;

    @ManyToMany(mappedBy = "mesures")
     private List<TARIC> tarics = new ArrayList<>();

    @OneToMany(mappedBy = "mesure", cascade = CascadeType.ALL)
    private List<ValidationMesure> validations = new ArrayList<>();

}
