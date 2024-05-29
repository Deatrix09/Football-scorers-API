package cz.mendelu.best_scorers_stats.domain.country;

import cz.mendelu.best_scorers_stats.utils.exceptions.AlreadyExistsException;
import cz.mendelu.best_scorers_stats.utils.exceptions.NotFoundException;
import cz.mendelu.best_scorers_stats.utils.response.ArrayResponse;
import cz.mendelu.best_scorers_stats.utils.response.ObjectResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("countries")
@Validated
@Tag(
        name = "Countries",
        description = "Operations over countries"
)
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping(value = "", produces = "application/json")
    @Valid
    @Operation(
            summary = "Get all countries"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Success")
            }
    )
    public ArrayResponse<CountryResponse> getAllCountries() {
        return ArrayResponse.of(
                countryService.getAllCountries(),
                CountryResponse::new
        );
    }


    @GetMapping(value = "/{id}", produces = "application/json")
    @Valid
    @Operation(
            summary = "Get country by id"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "404", description = "Country not found")
            }
    )
    public ObjectResponse<CountryResponse> getCountryById( @PathVariable Long id) {
        Country country = countryService
                .getCountryById(id)
                .orElseThrow( () -> new NotFoundException("Country not found"));

        return ObjectResponse.of(
                country,
                CountryResponse::new
        );
    }

    @PostMapping(value = "", produces = "application/json")
    @Valid
    @Operation(
            summary = "Add country"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Country added"),
                    @ApiResponse(responseCode = "409", description = "Country already exists")
            }
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ObjectResponse<CountryResponse> addCountry(@Valid @RequestBody CountryRequest countryRequest) {
        Country country = new Country();
        countryRequest.toCountry(country);


        Country savedCountry = countryService.addCountry(country).orElseThrow(
                () -> new AlreadyExistsException("Country already exists"));

        return ObjectResponse.of(savedCountry, CountryResponse::new);
    }


    @DeleteMapping(value = "/{id}", produces = "application/json")
    @Valid
    @Operation(
            summary = "Delete country by id"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Country deleted"),
                    @ApiResponse(responseCode = "404", description = "Country not found")
            }
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ObjectResponse<CountryResponse> deleteCountry(@PathVariable Long id) {
        Country country = countryService
                .getCountryById(id)
                .orElseThrow( () -> new NotFoundException("Country not found"));

        countryService.deleteCountry(country.getId());

        return ObjectResponse.of(
                country,
                CountryResponse::new
        );
    }

}
