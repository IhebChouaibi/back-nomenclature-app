package eng.bns.nomenclature.repository;

import eng.bns.nomenclature.entities.SousPosition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SousPositionRespositry extends JpaRepository<SousPosition, Long> {
SousPosition findBycodeSousPosition(String codeSousPosition);
boolean existsBycodeSousPosition(String codeSousPosition);
}
