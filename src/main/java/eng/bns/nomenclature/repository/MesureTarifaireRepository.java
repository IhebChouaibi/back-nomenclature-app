package eng.bns.nomenclature.repository;

import eng.bns.nomenclature.entities.MesureTarifaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface MesureTarifaireRepository extends JpaRepository<MesureTarifaire,Long> {
    @Query("""
        SELECT m FROM MesureTarifaire m
        JOIN m.validations v
        WHERE v.statut.libelle = :libelle
        ORDER BY v.dateValidation DESC
    """)
    Page<MesureTarifaire> findByStatut(@Param("libelle") String libelle, Pageable pageable);
}
