package eng.bns.nomenclature.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Condition {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY )
    private Long id ;
    private Date dateDebut;
    private Date dateFin;


    @ManyToOne
    @JoinColumn(name="id_chapitre")
    private Chapitre chapitre;
    @ManyToOne
    @JoinColumn(name="id_nomenclature")
    private TARIC nomenclature;


}
