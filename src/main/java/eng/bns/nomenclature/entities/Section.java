package eng.bns.nomenclature.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="sections")
public class Section {
    @Id
  private  String idSection;
  private String libelleSection;
  @OneToMany (mappedBy = "section" , fetch = FetchType.LAZY)
  private List<Chapitre> chapitres;

}
