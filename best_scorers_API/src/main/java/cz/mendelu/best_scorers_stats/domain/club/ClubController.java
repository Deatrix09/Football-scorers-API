package cz.mendelu.best_scorers_stats.domain.club;

import cz.mendelu.best_scorers_stats.domain.country.Country;
import cz.mendelu.best_scorers_stats.domain.country.CountryService;
import cz.mendelu.best_scorers_stats.domain.league.League;
import cz.mendelu.best_scorers_stats.domain.league.LeagueService;
import cz.mendelu.best_scorers_stats.utils.exceptions.BadRequestException;
import cz.mendelu.best_scorers_stats.utils.exceptions.NotFoundException;
import cz.mendelu.best_scorers_stats.utils.response.ArrayResponse;
import cz.mendelu.best_scorers_stats.utils.response.ObjectResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clubs")
@Validated
@Tag(
        name = "Clubs",
        description = "Operations over clubs"
)
public class ClubController {

    private final ClubService clubService;
    private final LeagueService leagueService;

    private final CountryService countryService;


    public ClubController(ClubService clubService, LeagueService leagueService, CountryService countryService) {
        this.clubService = clubService;
        this.leagueService = leagueService;
        this.countryService = countryService;
    }


    @GetMapping(value = "", produces = "application/json")
    @Valid
    @Operation(
            summary = "Get all clubs"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Success")
            }
    )
    public ArrayResponse<ClubResponse> getAllClubs() {
        return ArrayResponse.of(
                clubService.getAllClubs(),
                ClubResponse::new
        );
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @Valid
    @Operation(
            summary = "Delete club by id"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Club not found")
            }
    )
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ObjectResponse<ClubResponse> deleteClubById(
            @PathVariable Long id
    ) {
        Club club = clubService.findClubById(id).orElseThrow(() -> new NotFoundException("Club not found"));
        clubService.deleteClub(club.getId());
        return ObjectResponse.of(
                club,
                ClubResponse::new
        );
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Valid
    @Operation(
            summary = "Get club by id"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "404", description = "Club not found")
            }
    )
    public ObjectResponse<ClubResponse> getClubById(
            @PathVariable Long id
    ) {
        Club club = clubService.findClubById(id).orElseThrow(() -> new NotFoundException("Club not found"));
        return ObjectResponse.of(
                club,
                ClubResponse::new
        );
    }


    @PostMapping(value = "", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @Valid
    @Operation(
            summary = "Create club"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Created successfully"),
                    @ApiResponse(responseCode = "404", description = "League not found")
            }
    )
    public ObjectResponse<ClubResponse> createClub(
            @RequestBody @Valid ClubRequest clubRequest
    ) throws BadRequestException {

        Club club = new Club();

        clubRequest.toClub(club, leagueService);
        club = clubService.createClub(club);
        return ObjectResponse.of(
                club,
                ClubResponse::new
        );
    }










}
