package eng.bns.nomenclature.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="nomenclatures")
@Entity
public class Nomenclature {
    @Id
    private String idNomenclature;
    private String descrition ;
    private Date dateDebutValid ;
    private Date dateFinValid ;


    @ManyToOne
    @JoinColumn(name ="id_Nc")
    private NomenclatureCombinee nomenclatureCombinee;

    public String getNomenclature(){
        return nomenclatureCombinee.getNC() + ""+idNomenclature;
    }


}
