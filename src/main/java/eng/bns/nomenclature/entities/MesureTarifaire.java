package eng.bns.nomenclature.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="mesure tarifaire")
@Entity
public class MesureTarifaire {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String  libelle;
  @ManyToOne
  @JoinColumn(name = "id_pays")
  private Pays pays;
  @ManyToOne
  @JoinColumn(name="id_mouvement")
  private MouvementCommercial mouvementCommercial ;
  private Date dateDebut ;
  private Date dateFin ;


}
