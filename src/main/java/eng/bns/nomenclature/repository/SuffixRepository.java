package eng.bns.nomenclature.repository;

import eng.bns.nomenclature.entities.Suffix;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuffixRepository extends JpaRepository<Suffix,Long> {
    Suffix findByIdSuffix(Long idSuffix);
    Suffix findByCodeSuffix(String codeSuffix);
}
