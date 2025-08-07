package eng.bns.nomenclature.repository;

import eng.bns.nomenclature.entities.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position,Long> {
Position findBycodePosition(String codePosition);
boolean existsBycodePosition(String codePosition);
}
