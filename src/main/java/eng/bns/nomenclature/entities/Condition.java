package eng.bns.nomenclature.entities;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.sql.Date;

public class Condition {
    private Long id ;
    private ConditionType type;
    private Date dateDebut;
    private Date dateFin;


    @ManyToOne
    @JoinColumn(name="id_chapitre")
    private Chapitre chapitre;
    @ManyToOne
    @JoinColumn(name="id_nomenclature")
    private Nomenclature nomenclature;


}
