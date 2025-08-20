package eng.bns.nomenclature.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="sous-position")
@Entity
public class SousPosition {
    @Id    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idSousPosition;
    @Column(unique = true )

    private String codeSousPosition;
    @Column(length = 1000)

    private String libelleSousPosition;
    @ManyToOne(optional = false)

    @JoinColumn(name = "id_position")
    private Position position;
    @OneToMany(mappedBy = "sousPosition", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("idNCombinee ASC")
    private List<NC> nomenclatureCombinees;
}