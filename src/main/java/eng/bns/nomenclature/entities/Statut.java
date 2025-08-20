package eng.bns.nomenclature.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "statut")
public class Statut {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStatut;
    private String libelle;



}
