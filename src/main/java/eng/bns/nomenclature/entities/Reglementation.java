package eng.bns.nomenclature.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Type;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="regelements")
@Entity
public class Reglementation extends MetaDonnees {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReglement ;


    private String codeReglementation ;
    private String ref ;
    private String resume ;
    private int numeroPage ;
    private int numeroJournal ;

    @Column(name = "approuve")
    private Boolean approuve;
    private LocalDate debutValidite ;
    private LocalDate finValidite ;
    @ManyToOne
    @JoinColumn(name = "id_etat")
    private Etat etat;
    @ManyToOne
    @JoinColumn(name = "id_pays")
    private Pays pays;

    @ManyToOne
    @JoinColumn(name="id_type_reglement")
    private TypeReglement typeReglement ;

    @OneToMany(mappedBy = "reglementation")
    private List<MesureTarifaire> mesures = new ArrayList<>();



}
