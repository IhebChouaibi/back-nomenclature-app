package eng.bns.nomenclature.repository;

import eng.bns.nomenclature.entities.MesureTarifaire;
import eng.bns.nomenclature.entities.ValidationMesure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ValidationMesureRepository extends JpaRepository<ValidationMesure, Long> {
List<ValidationMesure> findByMesure_IdMesure(Long idMesure);
Page<ValidationMesure> findByStatut_Libelle(String libelle, Pageable pageable);
}