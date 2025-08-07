package eng.bns.nomenclature.repository;

import eng.bns.nomenclature.entities.Chapitre;
import eng.bns.nomenclature.entities.Section;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

//@RepositoryRestResource(collectionResourceRel = "sections", path = "sections")

public interface SectionRepository extends  JpaRepository<Section,Long> {

 @Query("SELECT s FROM Section s ORDER BY s.idSection ASC")
 Page<Section> findAllOrderedById(Pageable pageable);
 Section findByLibelleSection(String libelleSection);

}
