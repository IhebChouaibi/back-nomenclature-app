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
    private String id;
  private String  pays;
  @Enumerated(EnumType.STRING)

  private MouvementCommercial mouvementCommercial;
  private Date dateDebut ;
  private Date dateFin ;

}
