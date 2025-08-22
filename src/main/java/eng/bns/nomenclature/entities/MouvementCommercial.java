package eng.bns.nomenclature.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor

public class MouvementCommercial {
    @Id @GeneratedValue
    @Column(name = "id_mouvement")

private Long idMvtCommercial;
private String libelle;
}
