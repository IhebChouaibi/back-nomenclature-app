package eng.bns.nomenclature.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="nomenclature combinee")
@Entity
public class NC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNCombinee;
    @Column(unique = true )

    private  String codeNCombinee;
    @Column(length = 1000)

    private  String libelleNC;
    @ManyToOne(optional = false)
    @JoinColumn(name="id_Sous_Position")
    private SousPosition sousPosition;
@OneToMany(mappedBy = "nomenclatureCombinee", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
@OrderBy("idNomenclature ASC")
private List<TARIC> nomenclatures;

}
