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
    @Id
    private String idSousPosition;
    private String libelleSousPosition;
    @ManyToOne
    @JoinColumn(name = "id_Position")
    private Position position;
    @OneToMany(mappedBy = "sousPosition", fetch = FetchType.LAZY)
    private List<NomenclatureCombinee> nomenclatureCombinees;
    public String getSHSix(){
        return position.getSHFour()+""+idSousPosition;
    }
}