package eng.bns.nomenclature.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Type;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="regelements")
@Entity
public class Reglementation {
    @Id
    private String idReglementation ;
    private String ref ;
    @Enumerated(EnumType.STRING)
    private TypeReglement   type ;
    private String resume ;
    private int numeroPage ;
    private int numeroJournal ;
    private String pays;
private Date debutValidite ;
    private Date finValidite ;
    @Enumerated(EnumType.STRING)

    private ApprobationEtat approbationEtat ;
    @Enumerated(EnumType.STRING)

    private RemplacementEtat remplacementEtat ;






}
