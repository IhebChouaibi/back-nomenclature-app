package eng.bns.nomenclature.repository;

import eng.bns.nomenclature.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
}
