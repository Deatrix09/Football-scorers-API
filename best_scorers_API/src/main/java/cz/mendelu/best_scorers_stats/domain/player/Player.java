package cz.mendelu.best_scorers_stats.domain.player;

import cz.mendelu.best_scorers_stats.domain.club.Club;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String full_name;

    @NotNull
    private Long matches_played;

    @NotNull
    private Long count_of_substitutions;

    @NotNull
    private Long minutes_played;

    @NotNull
    private Long goals_scored;

    @NotNull
    private Double expected_goals;

    private Double expected_goals_per_match_avg;

    @NotNull
    private Long shots;

    @NotNull
    private Long shots_on_target;

    private Double shots_per_match_avg;

    private Double shots_on_target_per_match_avg;

    @NotNull
    private Long year;


    /**
         * The club_id field is a reference to the Club entity.
         * It is annotated with @ManyToOne, indicating that each Player is associated with one Club.
         * This forms a many-to-one relationship between Player and Club.
         * The @JoinColumn annotation is used to specify the foreign key column for the joined entity.
         * In this case, the name of the foreign key column is "club_id".
     */
    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club_id;


}
