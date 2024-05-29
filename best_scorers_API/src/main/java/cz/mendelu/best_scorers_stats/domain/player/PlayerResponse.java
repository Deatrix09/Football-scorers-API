package cz.mendelu.best_scorers_stats.domain.player;

import cz.mendelu.best_scorers_stats.domain.club.Club;
import cz.mendelu.best_scorers_stats.domain.club.ClubResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PlayerResponse {


    @NotNull
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

    private ClubResponse club;

    public PlayerResponse(Player player) {
        this.id = player.getId();
        this.full_name = player.getFull_name();
        this.matches_played = player.getMatches_played();
        this.count_of_substitutions = player.getCount_of_substitutions();
        this.minutes_played = player.getMinutes_played();
        this.goals_scored = player.getGoals_scored();
        this.expected_goals = player.getExpected_goals();
        this.expected_goals_per_match_avg = player.getExpected_goals_per_match_avg();
        this.shots = player.getShots();
        this.shots_on_target = player.getShots_on_target();
        this.shots_per_match_avg = player.getShots_per_match_avg();
        this.shots_on_target_per_match_avg = player.getShots_on_target_per_match_avg();
        this.year = player.getYear();
        this.club = new ClubResponse(player.getClub_id());
    }

}

