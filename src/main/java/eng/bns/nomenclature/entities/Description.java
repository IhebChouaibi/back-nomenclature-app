package eng.bns.nomenclature.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Description {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String status;
    @Column(length = 1000, nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "taric_id", nullable = false)
    private TARIC taric;
    @PrePersist
    @PreUpdate
    private void initializeDates() {
        if (dateDebut== null) {
            dateDebut = LocalDate.now();
        }

        if (dateFin == null) {
            dateFin = dateDebut.plusYears(1);
        }
    }
}
