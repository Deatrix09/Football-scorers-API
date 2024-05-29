package cz.mendelu.best_scorers_stats.domain.club;

import cz.mendelu.best_scorers_stats.domain.league.League;
import cz.mendelu.best_scorers_stats.domain.player.Player;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Club {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Can be null  **/
    private String club_code;

    /** Optionally can be added full club name but must be created manually **/
    // private String club_name;


    /**
         * The league field is a reference to the League entity.
         * It is annotated with @ManyToOne, indicating that each Club is associated with one League.
         * This forms a many-to-one relationship between Club and League.
         * The @JoinColumn annotation is used to specify the foreign key column for the joined entity.
         * In this case, the name of the foreign key column is "league_id".
     */
    @ManyToOne
    @JoinColumn(name = "league_id")
    private League league_id;



    @OneToMany(mappedBy = "club_id", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Player> players = new ArrayList<>();



}
