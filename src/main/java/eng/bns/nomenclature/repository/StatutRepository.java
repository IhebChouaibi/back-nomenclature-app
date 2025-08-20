package eng.bns.nomenclature.repository;

import eng.bns.nomenclature.entities.Statut;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatutRepository extends JpaRepository<Statut, Long> {
}
