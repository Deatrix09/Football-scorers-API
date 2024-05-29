package cz.mendelu.best_scorers_stats.domain.country;

import cz.mendelu.best_scorers_stats.domain.league.League;
import cz.mendelu.best_scorers_stats.domain.league.LeagueService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CountryResponse {


    @NotNull
    Long id;

    @NotEmpty
    String country_name;

    @NotNull
    List<Long> league_ids;


    public CountryResponse(Country country) {
        this.id = country.getId();
        this.country_name = country.getCountry_name();
        this.league_ids = country.getLeagues().stream().map(League::getId).toList();
    }
}
