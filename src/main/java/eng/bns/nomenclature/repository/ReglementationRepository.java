package eng.bns.nomenclature.repository;

import eng.bns.nomenclature.entities.Reglementation;
import eng.bns.nomenclature.entities.TARIC;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReglementationRepository extends JpaRepository<Reglementation, Long> {
    List<Reglementation> findByTaric(TARIC taric);

    List<Reglementation> findByTaric_CodeNomenclature( String codeTaric);
}
