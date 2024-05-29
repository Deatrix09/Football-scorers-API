package cz.mendelu.best_scorers_stats.domain.country;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryRequest {

    @NotEmpty
    @Schema(description = "Country name", example = "Czech Republic")
    private String country_name;


    public void toCountry(Country country) {
        country.setCountry_name(country_name);
    }


}
