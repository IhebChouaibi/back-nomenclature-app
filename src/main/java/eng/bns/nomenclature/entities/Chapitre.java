package eng.bns.nomenclature.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="chapitres")
@Entity
public class Chapitre {
    @Id   @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_chapitre")
    private Long idChapitre;
    @Column(unique = true )
    private String codeChapitre;
    private String libelleChapitre;

    @ManyToOne
    @JoinColumn(name="id_section")
    private Section  section;


    @OneToMany(mappedBy = "chapitre", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List <Condition> conditions;


@OneToMany(mappedBy = "chapitre", fetch = FetchType.LAZY)
private List<Position> positions;


}
