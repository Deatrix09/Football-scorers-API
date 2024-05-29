package cz.mendelu.best_scorers_stats.domain.league;

import cz.mendelu.best_scorers_stats.domain.country.CountryService;
import cz.mendelu.best_scorers_stats.utils.exceptions.BadRequestException;
import cz.mendelu.best_scorers_stats.utils.exceptions.NotFoundException;
import cz.mendelu.best_scorers_stats.utils.response.ArrayResponse;
import cz.mendelu.best_scorers_stats.utils.response.ObjectResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("leagues")
@Validated
@Tag(
        name = "Leagues",
        description = "Operations over leagues"
)
public class LeagueController {

    private final LeagueService leagueService;

    private final CountryService countryService;


    public LeagueController(LeagueService leagueService, CountryService countryService) {
        this.leagueService = leagueService;
        this.countryService = countryService;
    }

    @GetMapping(value = "", produces = "application/json")
    @Valid
    @Operation(
            summary = "Get all leagues"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Success")
            }
    )
    public ArrayResponse<LeagueResponse> getAllLeagues() {
        return ArrayResponse.of(
                leagueService.getAllLeagues(),
                LeagueResponse::new
        );
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @Valid
    @Operation(
            summary = "Delete league by id"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "404", description = "League not found")
            }
    )
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ObjectResponse<LeagueResponse> deleteLeagueById(
            @PathVariable Long id
    ) {
        League league = leagueService.getLeagueById(id).orElseThrow(
                () -> new NotFoundException("League not found")
        );

        leagueService.deleteLeague(league.getId());

        return ObjectResponse.of(
                league,
                LeagueResponse::new
        );
    }


    @PostMapping(value = "", produces = "application/json")
    @Valid
    @Operation(
            summary = "Create league"
    )
    @ResponseStatus(value = HttpStatus.CREATED)
    public ObjectResponse<LeagueResponse> createLeague(
            @Valid @RequestBody LeagueRequest leagueRequest
    ) throws BadRequestException {
        League league = new League();

        leagueRequest.toLeague(league, countryService);

        league = leagueService.createLeague(league);

        return ObjectResponse.of(
                league,
                LeagueResponse::new
        );
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Valid
    @Operation(
            summary = "Get league by id"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "404", description = "League not found")
            }
    )
    public ObjectResponse<LeagueResponse> getLeagueById(
            @PathVariable Long id
    ) {
        League league = leagueService.getLeagueById(id).orElseThrow(
                () -> new NotFoundException("League not found")
        );

        return ObjectResponse.of(
                league,
                LeagueResponse::new
        );
    }






}
