package eng.bns.nomenclature.repository;

import eng.bns.nomenclature.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
     Optional<User> findUsersByUsername(String username);
     List<User> findAllByRoles_Name(String name);
}
