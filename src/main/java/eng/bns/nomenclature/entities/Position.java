package eng.bns.nomenclature.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="position")
@Entity
public class Position {
    @Id   @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idPosition ;
    @Column(unique = true )

    private String codePosition;
    @Column(length = 1000)

    private String libellePosition;
    @ManyToOne(optional = false)

    @JoinColumn(name="id_Chapitre")
    private Chapitre chapitre;
    @OneToMany(mappedBy = "position", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("idSousPosition ASC")
    private List<SousPosition> sousPosition;


}
