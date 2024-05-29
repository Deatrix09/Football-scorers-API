package cz.mendelu.best_scorers_stats.domain.club;

import cz.mendelu.best_scorers_stats.domain.country.Country;
import cz.mendelu.best_scorers_stats.domain.league.League;
import cz.mendelu.best_scorers_stats.domain.league.LeagueService;
import cz.mendelu.best_scorers_stats.utils.exceptions.NotFoundException;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClubRequest {

    @NotEmpty
        @Schema(description = "Club name", example = "Olympia Praha")
    private String club_code;

    @NotNull
        @Schema(description = "League ID", example = "1")
    private Long league_id;

    public void toClub(Club club, LeagueService leagueService) {
        club.setClub_code(club_code);

        //check if league_id is not null or if has valid value
        if (league_id != null && league_id > 0) {
            League league = leagueService.getLeagueById(league_id).orElseThrow(() -> new NotFoundException("League not found"));

            if (league != null) {
                club.setLeague_id(league);
            }
        }
    }

}
