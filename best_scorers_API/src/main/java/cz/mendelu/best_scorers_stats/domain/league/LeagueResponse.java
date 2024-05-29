package cz.mendelu.best_scorers_stats.domain.league;

import cz.mendelu.best_scorers_stats.domain.club.Club;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class LeagueResponse {

    @NotNull
    private Long id;

    @NotEmpty
    private String league_name;

    private List<String> clubs;


    public LeagueResponse(League league) {
        this.id = league.getId();
        this.league_name = league.getLeague_name();
        this.clubs = league.getClubs().stream().map(Club::getClub_code).toList();
    }

}
