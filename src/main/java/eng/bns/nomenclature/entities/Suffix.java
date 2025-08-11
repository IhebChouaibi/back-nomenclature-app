package eng.bns.nomenclature.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Suffix {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSuffix;

    @Column(unique = true, nullable = false, length = 2)
    private String codeSuffix;

    @Column(nullable = false)
    private boolean declarable;

    @Column(nullable = false)
    private boolean national;
    @OneToMany(mappedBy = "suffix")
    private List<TARIC> tarics = new ArrayList<>();

}
