package cz.mendelu.best_scorers_stats.domain.country;

import cz.mendelu.best_scorers_stats.domain.league.League;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Entity
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String country_name;

    @OneToMany(mappedBy = "country_id", cascade = CascadeType.ALL, orphanRemoval = true)
    List<League> leagues =  new ArrayList<>();


}
