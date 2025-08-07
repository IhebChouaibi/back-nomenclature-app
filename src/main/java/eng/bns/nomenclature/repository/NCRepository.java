package eng.bns.nomenclature.repository;

import eng.bns.nomenclature.entities.NC;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  NCRepository extends JpaRepository<NC,Long> {

    NC findByCodeNCombinee(String codeNC);
    boolean existsByCodeNCombinee(String codeNC);
}
