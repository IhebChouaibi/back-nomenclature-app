package eng.bns.nomenclature.repository;

import eng.bns.nomenclature.entities.MesureTarifaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface MesureTarifaireRepository extends JpaRepository<MesureTarifaire,Long> {

    Page<MesureTarifaire> findByStatut_Libelle( String libelle, Pageable pageable);
}
