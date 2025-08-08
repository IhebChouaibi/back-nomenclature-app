package eng.bns.nomenclature.repository;

import eng.bns.nomenclature.entities.TARIC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaricRepository  extends JpaRepository<TARIC,Long> {
    TARIC findByCodeNomenclature(String codeTaric);
    Page<TARIC> findByCodeNomenclatureStartingWith(String code,Pageable pageable);
}
