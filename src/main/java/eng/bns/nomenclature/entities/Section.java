package eng.bns.nomenclature.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="sections")
public class Section {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSection ;
  private  String codeSection;
  private String libelleSection;
  @OneToMany (mappedBy = "section" , fetch = FetchType.EAGER ,cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("idChapitre ASC")
  private List<Chapitre> chapitres;

}
