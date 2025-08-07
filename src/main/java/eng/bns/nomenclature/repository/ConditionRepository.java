package eng.bns.nomenclature.repository;

import eng.bns.nomenclature.entities.Condition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConditionRepository extends JpaRepository<Condition,String> {

}
