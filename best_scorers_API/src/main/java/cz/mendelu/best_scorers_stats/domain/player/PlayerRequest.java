package cz.mendelu.best_scorers_stats.domain.player;

import cz.mendelu.best_scorers_stats.domain.club.Club;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class PlayerRequest {

    @NotEmpty
        @Schema(description = "Full name of player", example = "John Doe")
    private String fullName;

    @NotNull
        @Schema(description = "Number of matches played", example = "10")
    private Long matchesPlayed;

    @NotNull
        @Schema(description = "Number of substitutions", example = "5")
    private Long countOfSubstitutions;

    @NotNull
        @Schema(description = "Number of minutes played", example = "900")
    private Long minutesPlayed;

    @NotNull
        @Schema(description = "Number of goals scored", example = "5")
    private Long goalsScored;

    @NotNull
        @Schema(description = "Expected goals", example = "6.5")
    private Double expectedGoals;

        @Schema(description = "Expected goals per match average", example = "0.65")
    private Double expectedGoalsPerMatchAvg;

    @NotNull
        @Schema(description = "Number of shots", example = "20")
    private Long shots;

    @NotNull
        @Schema(description = "Number of shots on target", example = "10")
    private Long shotsOnTarget;

        @Schema(description = "Shots per match average", example = "2.0")
    private Double shotsPerMatchAvg;

        @Schema(description = "Shots on target per match average", example = "1.0")
    private Double shotsOnTargetPerMatchAvg;

    @NotNull
        @Schema(description = "Year", example = "2021")
    private Long year;

    @NotNull
        @Schema(description = "Club ID", example = "1")
    private Long clubId;

    public void toPlayer(Player player, Club club) {
        player.setFull_name(fullName);
        player.setMatches_played(matchesPlayed);
        player.setCount_of_substitutions(countOfSubstitutions);
        player.setMinutes_played(minutesPlayed);
        player.setGoals_scored(goalsScored);
        player.setExpected_goals(expectedGoals);
        player.setExpected_goals_per_match_avg(expectedGoalsPerMatchAvg);
        player.setShots(shots);
        player.setShots_on_target(shotsOnTarget);
        player.setShots_per_match_avg(shotsPerMatchAvg);
        player.setShots_on_target_per_match_avg(shotsOnTargetPerMatchAvg);
        player.setYear(year);
        player.setClub_id(club);
    }
}
