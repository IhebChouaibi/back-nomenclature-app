package eng.bns.nomenclature.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="chapitres")
@Entity
public class Chapitre {
    @Id
    private String idChapitre;
    private String libelleChapitre;

    @ManyToOne
    @JoinColumn(name="id_Section")
    private Section  sections;

    @OneToMany(mappedBy = "chapitre", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List <Condition> conditions;



@OneToMany(mappedBy = "chapitre", fetch = FetchType.LAZY)
private List<Position> positions;


}
