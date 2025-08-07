package eng.bns.nomenclature.repository;

import eng.bns.nomenclature.entities.Chapitre;
import eng.bns.nomenclature.entities.Section;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChapitreRepository extends  JpaRepository<Chapitre,Long> {
 Chapitre findBySectionIdSection(Long idSection);
 Chapitre findByCodeChapitre(String codeChapitre);
 boolean existsByCodeChapitre(String codeChapitre);
}
