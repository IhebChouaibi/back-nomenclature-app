package eng.bns.nomenclature.repository;

import eng.bns.nomenclature.entities.Role;
import eng.bns.nomenclature.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long> {
 Role findByUsersId(Long userId);
}
