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
public class TARIC extends MetaDonnees {
    @Id   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNomenclature;
    @Column(unique = true )

    private String codeNomenclature;


    private LocalDate dateDebutValid ;
    private LocalDate dateFinValid ;
    @ManyToOne
    @JoinColumn(name = "id_suffix")
    private Suffix suffix;


    @ManyToOne(optional = false)

    @JoinColumn(name ="id_Nc")
    private NC nomenclatureCombinee;

    @OneToMany(mappedBy = "taric", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Description> descriptions = new ArrayList<>();

    @OneToMany(mappedBy = "taric", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notes> notes = new ArrayList<>();


    @ManyToMany()
    @JoinTable(
            name = "mesure_tARIC",
            joinColumns = @JoinColumn(name = "idNomenclature"),
            inverseJoinColumns = @JoinColumn(name = "idMesure")
    )
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


