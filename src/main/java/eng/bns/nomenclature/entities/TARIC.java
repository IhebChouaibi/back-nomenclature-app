package eng.bns.nomenclature.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Taric")
@Entity
public class TARIC {
    @Id   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNomenclature;
    @Column(unique = true )

    private String codeNomenclature;
    @Column(length = 1000)

    //private String libelleNomenclature ;

    private LocalDate dateDebutValid ;
    private LocalDate dateFinValid ;
    @ManyToOne
    @JoinColumn(name = "id_suffix")
    private Suffix suffix;


    @ManyToOne
    @JoinColumn(name ="id_Nc")
    private NC nomenclatureCombinee;
   // @OneToMany(mappedBy = "taric", cascade = CascadeType.ALL, orphanRemoval = true)
   // private List<Reglementation> reglementations = new ArrayList<>();

    @OneToMany(mappedBy = "taric", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Description> descriptions = new ArrayList<>();

    @OneToMany(mappedBy = "taric", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notes> notes = new ArrayList<>();


    @OneToMany(mappedBy = "taric", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MesureTarifaire> mesures = new ArrayList<>();
    @PrePersist
    @PreUpdate
    private void initializeDates() {
        if (dateDebutValid == null) {
            dateDebutValid = LocalDate.now();
        }

        if (dateFinValid == null) {
            dateFinValid = dateDebutValid.plusYears(1);
        }
    }

}


