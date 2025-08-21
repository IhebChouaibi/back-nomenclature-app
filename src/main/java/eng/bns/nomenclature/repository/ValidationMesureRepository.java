package eng.bns.nomenclature.repository;

import eng.bns.nomenclature.entities.MesureTarifaire;
import eng.bns.nomenclature.entities.ValidationMesure;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface ValidationMesureRepository extends JpaRepository<ValidationMesure, Long> {
List<ValidationMesure> findByMesure_IdMesure(Long idMesure);
}