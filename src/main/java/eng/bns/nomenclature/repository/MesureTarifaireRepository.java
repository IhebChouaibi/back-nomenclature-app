package eng.bns.nomenclature.repository;

import eng.bns.nomenclature.entities.MesureTarifaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MesureTarifaireRepository extends JpaRepository<MesureTarifaire,String> {
}
