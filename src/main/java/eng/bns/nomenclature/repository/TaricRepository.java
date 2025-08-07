package eng.bns.nomenclature.repository;

import eng.bns.nomenclature.entities.TARIC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface TaricRepository  extends JpaRepository<TARIC,Long> {
    TARIC findByCodeNomenclature(String codeTaric);

}
