package eng.bns.nomenclature.repository;

import eng.bns.nomenclature.entities.Description;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DescriptionRepository extends JpaRepository<Description, Long> {

Description findByTaric_IdNomenclatureOrderByIdDesc(Long idTaric);

Description findByDescription(String description);
}
