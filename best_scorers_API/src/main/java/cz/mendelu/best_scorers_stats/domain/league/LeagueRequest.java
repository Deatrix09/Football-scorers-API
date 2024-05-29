package cz.mendelu.best_scorers_stats.domain.league;

import cz.mendelu.best_scorers_stats.domain.country.Country;
import cz.mendelu.best_scorers_stats.domain.country.CountryService;
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
public class LeagueRequest {

    @NotEmpty
        @Schema(description = "League name", example = "Gambrinus liga")
    private String league_name;

    @NotNull
        @Schema(description = "Country ID", example = "1")
    private Long country_id;



    public void toLeague(League league, CountryService countryService) {
        league.setLeague_name(league_name);

        //check if country_id is not null or if has valid value
        if (country_id != null && country_id > 0) {
            Country country = countryService.getCountryById(country_id).orElseThrow(() -> new NotFoundException("Country not found"));

            if (country != null) {
                league.setCountry_id(country);
            }
        }
    }

}
