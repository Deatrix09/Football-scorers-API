package cz.mendelu.best_scorers_stats.domain.league;

import cz.mendelu.best_scorers_stats.domain.club.Club;
import cz.mendelu.best_scorers_stats.domain.country.Country;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class League {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String league_name;


    /**
         * The country_id field is a reference to the Country entity.
         * It is annotated with @ManyToOne, indicating that each League is associated with one Country.
         * This forms a many-to-one relationship between League and Country.
         * The @JoinColumn annotation is used to specify the foreign key column for the joined entity.
         * In this case, the name of the foreign key column is "country_id".
     */
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country_id;



    @OneToMany(mappedBy = "league_id", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Club> clubs = new ArrayList<>();







}
