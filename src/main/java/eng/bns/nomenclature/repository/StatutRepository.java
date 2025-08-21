package eng.bns.nomenclature.repository;

import eng.bns.nomenclature.entities.Statut;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatutRepository extends JpaRepository<Statut, Long> {
    Optional<Statut> findByLibelle(String libelle);
}
