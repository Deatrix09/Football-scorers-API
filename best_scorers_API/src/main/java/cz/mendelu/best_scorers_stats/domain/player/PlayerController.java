package cz.mendelu.best_scorers_stats.domain.player;

import com.fasterxml.jackson.annotation.JsonView;
import cz.mendelu.best_scorers_stats.domain.club.Club;
import cz.mendelu.best_scorers_stats.domain.club.ClubService;
import cz.mendelu.best_scorers_stats.utils.exceptions.NotFoundException;
import cz.mendelu.best_scorers_stats.utils.response.ArrayResponse;
import cz.mendelu.best_scorers_stats.utils.response.ObjectResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("players")
@Tag(
        name = "Players",
        description = "Operations over players")
@Validated
public class PlayerController {

    private final PlayerService playerService;

    private final ClubService clubService;

    public PlayerController(PlayerService playerService, ClubService clubService) {
        this.playerService = playerService;
        this.clubService = clubService;
    }


    @GetMapping(value = "", produces = "application/json")
    @Valid
    @Operation(
            summary = "Get all players"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Success")
            }
    )
    //@JsonView(PlayersView.Summary.class)
    public ArrayResponse<PlayerResponse> getAllPlayers() {
        return ArrayResponse.of(
                playerService.findAllPlayers(),
                PlayerResponse::new
        );
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Valid
    @Operation(
            summary = "Get player by id"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "404", description = "Player not found")
            }
    )
    //@JsonView(PlayersView.Detailed.class)
    public ObjectResponse<PlayerResponse> getPlayerById(@PathVariable Long id) {
        Player player = playerService.findById(id).orElseThrow(
                () -> new NotFoundException("Player with id " + id + " was not found")
        );

        return ObjectResponse.of(
                player,
                PlayerResponse::new
        );
    }

    @GetMapping(value = "/club/{clubId}", produces = "application/json")
    @Valid
    @Operation(
            summary = "Get players by club"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "404", description = "Club not found")
            }
    )
    public ArrayResponse<PlayerResponse> getPlayersByClub(@PathVariable Long clubId) {

        Club club = clubService.findById(clubId).orElseThrow(() -> new NotFoundException("Club not found"));
        return ArrayResponse.of(
                playerService.getPlayersByClub(club),
                PlayerResponse::new
        );

    }

    @GetMapping(value = "/matchesPlayed/{matchesPlayed}", produces = "application/json")
    @Valid
    @Operation(
            summary = "Get players by matches played more than"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "404", description = "No players found")
            }
    )
    public ArrayResponse<PlayerResponse> getPlayersByMatchesPlayedMoreThan(@PathVariable Long matchesPlayed) {
        return ArrayResponse.of(
                playerService.findPlayersByMatchesPlayedMoreThan(matchesPlayed),
                PlayerResponse::new
        );
    }

    @GetMapping(value = "/top3/{year}", produces = "application/json")
    @Valid
    @Operation(
            summary = "Get top 3 scorers by year"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Success"),
            }
    )
    public ArrayResponse<PlayerResponse> getTop3ScorersByYear(@PathVariable Long year) {
        return ArrayResponse.of(
                playerService.getTop3ScorersByYear(year),
                PlayerResponse::new
        );
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    @Valid
    @Operation(
            summary = "Update player"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "404", description = "Player with id was not found")
            }
    )
    public ObjectResponse<PlayerResponse> updatePlayer(@PathVariable Long id, @RequestBody @Valid PlayerRequest playerRequest) {
        Player player = playerService.findById(id).orElseThrow(
                () -> new NotFoundException("Player with id " + id + " was not found")
        );

        Club club = clubService.findById(playerRequest.getClubId()).orElseThrow(() -> new NotFoundException("Club not found"));

        playerRequest.toPlayer(player, club);
        playerService.updatePlayer(player, id);

        return ObjectResponse.of(
                player,
                PlayerResponse::new
        );
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @Valid
    @Operation(
            summary = "Delete player"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Player with id was not found")
            }
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ObjectResponse<PlayerResponse> deletePlayer(@PathVariable Long id) {
        Player player = playerService.findById(id).orElseThrow(
                () -> new NotFoundException("Player with id " + id + " was not found")
        );

        playerService.deletePlayer(player.getId());

        return ObjectResponse.of(
                player,
                PlayerResponse::new
        );
    }

    @Operation(
            summary = "Create player"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Created successfully"),
                    @ApiResponse(responseCode = "404", description = "Club not found")
            }
    )
    @PostMapping(value = "", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @Valid
    public ObjectResponse<PlayerResponse> createPlayer(@RequestBody @Valid PlayerRequest playerRequest) {
        Club club = clubService.findById(playerRequest.getClubId()).orElseThrow(() -> new NotFoundException("Club not found"));

        Player player = new Player();
        playerRequest.toPlayer(player, club);

        playerService.createPlayer(player);

        return ObjectResponse.of(
                player,
                PlayerResponse::new
        );
    }






}
