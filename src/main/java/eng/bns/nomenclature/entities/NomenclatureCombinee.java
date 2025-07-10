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
public class NomenclatureCombinee {
    @Id
    private  String idNC;
    private  String libelleNC;
    @ManyToOne
    @JoinColumn(name="id_Sous_Position")
    private SousPosition sousPosition;
@OneToMany(mappedBy = "nomenclatureCombinee", fetch = FetchType.LAZY)
private List<Nomenclature> nomenclatures;
    public String getNC (){
        return sousPosition.getSHSix()+ idNC;
    }

}
