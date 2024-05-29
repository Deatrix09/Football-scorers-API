package cz.mendelu.best_scorers_stats.domain.player;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/test-data/cleanup.sql")
@Sql("/test-data/test-data.sql")
public class PlayerIntegrationTest {


    private final static String BASE_URI = "http://localhost";

    @LocalServerPort
    private int port;

    @BeforeEach
    public void configureRestAssured() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
    }


    @Test
    public void testGetAllPlayers() {
        given()
                .when()
                .get("/players")
                .then()
                .statusCode(200)
                .body("items.size()", is(5))
                .body("items.id", containsInAnyOrder(1, 2, 3, 4, 5))
                .body("items.full_name", containsInAnyOrder("Juanmi Callejon", "Antoine Griezmann", "Karim Benzema", "Andrej Kramaric", "Alassane Plea"))
                .body("items.matches_played", containsInAnyOrder(19, 36, 23, 28, 28))
                .body("items.count_of_substitutions", containsInAnyOrder(16, 0, 6, 2, 6))
                .body("items.minutes_played", containsInAnyOrder(1849, 3129, 1967, 2527, 2613))
                .body("items.goals_scored", containsInAnyOrder(11, 16, 11, 16, 12))
                .body("items.expected_goals", containsInAnyOrder(6.62F, 11.86F, 13.25F, 19.42F, 11.28F))
                .body("items.expected_goals_per_match_avg", containsInAnyOrder(0.34F, 0.36F, 0.64F, 0.73F, 0.41F))
                .body("items.shots", containsInAnyOrder(48, 88, 69, 110, 83))
                .body("items.shots_on_target", containsInAnyOrder(20, 41, 34, 43, 41))
                .body("items.shots_per_match_avg", containsInAnyOrder(2.47F, 2.67F, 3.33F, 4.14F, 3.02F))
                .body("items.shots_on_target_per_match_avg", containsInAnyOrder(1.03F, 1.24F, 1.64F, 1.62F, 1.49F))
                .body("items.year", containsInAnyOrder(2016, 2016, 2016, 2018, 2018))
                .body("items.club.id", containsInAnyOrder(1, 2, 3, 6, 7))
                .body("items.club.club_code", containsInAnyOrder("Real Betis", "FC Barcelona", "Real Madrid", "TSG 1899 Hoffenheim", "Borussia Monchengladbach"));

    }

    @Test
    public void testGetPlayerById() {
        given()

                .when()
                .get("/players/1")
                .then()
                .statusCode(200)
                .body("content.id", is(1))
                .body("content.full_name", is("Juanmi Callejon"))
                .body("content.matches_played", is(19))
                .body("content.count_of_substitutions", is(16))
                .body("content.minutes_played", is(1849))
                .body("content.goals_scored", is(11))
                .body("content.expected_goals", is(6.62F))
                .body("content.expected_goals_per_match_avg", is(0.34F))
                .body("content.shots", is(48))
                .body("content.shots_on_target", is(20))
                .body("content.shots_per_match_avg", is(2.47F))
                .body("content.shots_on_target_per_match_avg", is(1.03F))
                .body("content.year", is(2016))
                .body("content.club.id", is(1))
                .body("content.club.club_code", is("Real Betis"));
    }

    @Test
    public void testGetPlayerById_NotFound() {
        when().get("/players/100").then().statusCode(404);
    }


    @Test
    public void testGetPlayersByClub() {
        given()
                .when()
                .get("/players/club/1")
                .then()
                .statusCode(200)
                .body("items.size()", is(1))
                .body("items.id", containsInAnyOrder(1))
                .body("items.full_name", containsInAnyOrder("Juanmi Callejon"))
                .body("items.matches_played", containsInAnyOrder(19))
                .body("items.count_of_substitutions", containsInAnyOrder(16))
                .body("items.minutes_played", containsInAnyOrder(1849))
                .body("items.goals_scored", containsInAnyOrder(11))
                .body("items.expected_goals", containsInAnyOrder(6.62F))
                .body("items.expected_goals_per_match_avg", containsInAnyOrder(0.34F))
                .body("items.shots", containsInAnyOrder(48))
                .body("items.shots_on_target", containsInAnyOrder(20))
                .body("items.shots_per_match_avg", containsInAnyOrder(2.47F))
                .body("items.shots_on_target_per_match_avg", containsInAnyOrder(1.03F))
                .body("items.year", containsInAnyOrder(2016))
                .body("items.club.id", containsInAnyOrder(1))
                .body("items.club.club_code", containsInAnyOrder("Real Betis"));
    }

    @Test
    public void testGetPlayersByClub_NotFound() {
        when().get("/players/club/100").then().statusCode(404);
    }

    @Test
    public void testGetPlayersByMatchesPlayedMoreThan() {
        given()
                .when()
                .get("/players/matchesPlayed/28")
                .then()
                .statusCode(200)
                .body("items.size()", is(1))
                .body("items.id", containsInAnyOrder(2))
                .body("items.full_name", containsInAnyOrder("Antoine Griezmann"))
                .body("items.matches_played", containsInAnyOrder(36))
                .body("items.count_of_substitutions", containsInAnyOrder(0))
                .body("items.minutes_played", containsInAnyOrder(3129))
                .body("items.goals_scored", containsInAnyOrder(16))
                .body("items.expected_goals", containsInAnyOrder(11.86F))
                .body("items.expected_goals_per_match_avg", containsInAnyOrder(0.36F))
                .body("items.shots", containsInAnyOrder(88))
                .body("items.shots_on_target", containsInAnyOrder(41))
                .body("items.shots_per_match_avg", containsInAnyOrder(2.67F))
                .body("items.shots_on_target_per_match_avg", containsInAnyOrder(1.24F))

                .body("items.year", containsInAnyOrder(2016))
                .body("items.club.id", containsInAnyOrder(2))
                .body("items.club.club_code", containsInAnyOrder("FC Barcelona"));
    }

    @Test
    public void testGetTop3ScorersByYear() {
        given()
                .when()
                .get("/players/top3/2016")
                .then()
                .statusCode(200)
                .body("items.size()", is(3))
                .body("items.id", containsInAnyOrder(2, 1, 3))
                .body("items.full_name", containsInAnyOrder("Antoine Griezmann", "Juanmi Callejon", "Karim Benzema"))
                .body("items.matches_played", containsInAnyOrder(36, 19, 23))
                .body("items.count_of_substitutions", containsInAnyOrder(0, 16, 6))
                .body("items.minutes_played", containsInAnyOrder(3129, 1849, 1967))
                .body("items.goals_scored", containsInAnyOrder(16, 11, 11))
                .body("items.expected_goals", containsInAnyOrder(11.86F, 6.62F, 13.25F))
                .body("items.expected_goals_per_match_avg", containsInAnyOrder(0.36F, 0.34F, 0.64F))
                .body("items.shots", containsInAnyOrder(88, 48, 69))
                .body("items.shots_on_target", containsInAnyOrder(41, 20, 34))
                .body("items.shots_per_match_avg", containsInAnyOrder(2.67F, 2.47F, 3.33F))
                .body("items.shots_on_target_per_match_avg", containsInAnyOrder(1.24F, 1.03F, 1.64F))
                .body("items.year", containsInAnyOrder(2016, 2016, 2016))
                .body("items.club.id", containsInAnyOrder(2, 1, 3))
                .body("items.club.club_code", containsInAnyOrder("FC Barcelona", "Real Betis", "Real Madrid"));
    }


    @Test
    public void testUpdatePlayer() {
        PlayerRequest playerRequest = new PlayerRequest();
        playerRequest.setFullName("Juanmi Callejon");
        playerRequest.setMatchesPlayed(19L);
        playerRequest.setCountOfSubstitutions(16L);
        playerRequest.setMinutesPlayed(1849L);
        playerRequest.setGoalsScored(11L);
        playerRequest.setExpectedGoals(6.62);
        playerRequest.setExpectedGoalsPerMatchAvg(0.34);
        playerRequest.setShots(48L);
        playerRequest.setShotsOnTarget(20L);
        playerRequest.setShotsPerMatchAvg(2.47);
        playerRequest.setShotsOnTargetPerMatchAvg(1.03);
        playerRequest.setYear(2016L);
        playerRequest.setClubId(1L);

        given()
                .contentType(ContentType.JSON)
                .body(playerRequest)
                .when()
                .put("/players/1")
                .then()
                .statusCode(200);

        when()
                .get("/players/1")
                .then()
                .body("content.id", is(1))
                .body("content.full_name", is("Juanmi Callejon"))
                .body("content.matches_played", is(19))
                .body("content.count_of_substitutions", is(16))
                .body("content.minutes_played", is(1849))
                .body("content.goals_scored", is(11))
                .body("content.expected_goals", is(6.62F))
                .body("content.expected_goals_per_match_avg", is(0.34F))
                .body("content.shots", is(48))
                .body("content.shots_on_target", is(20))
                .body("content.shots_per_match_avg", is(2.47F))
                .body("content.shots_on_target_per_match_avg", is(1.03F))
                .body("content.year", is(2016))
                .body("content.club.id", is(1))
                .body("content.club.club_code", is("Real Betis"));
    }

    @Test
    public void testUpdatePlayer_NotFound() {
        PlayerRequest playerRequest = new PlayerRequest();
        playerRequest.setFullName("Juanmi Callejon");
        playerRequest.setMatchesPlayed(19L);
        playerRequest.setCountOfSubstitutions(16L);
        playerRequest.setMinutesPlayed(1849L);
        playerRequest.setGoalsScored(11L);
        playerRequest.setExpectedGoals(6.62);
        playerRequest.setExpectedGoalsPerMatchAvg(0.34);
        playerRequest.setShots(48L);
        playerRequest.setShotsOnTarget(20L);
        playerRequest.setShotsPerMatchAvg(2.47);
        playerRequest.setShotsOnTargetPerMatchAvg(1.03);
        playerRequest.setYear(2016L);
        playerRequest.setClubId(1L);

        given()
                .contentType(ContentType.JSON)
                .body(playerRequest)
                .when()
                .put("/players/100")
                .then()
                .statusCode(404);
    }

    @Test
    public void testUpdatePlayer_InvalidRequest() {
        // Create a PlayerRequest object with invalid data
        PlayerRequest playerRequest = new PlayerRequest();
        playerRequest.setFullName(""); // Invalid full name (empty string)
        playerRequest.setMatchesPlayed(-1L); // Invalid matches played (negative number)
        playerRequest.setCountOfSubstitutions(16L);
        playerRequest.setMinutesPlayed(1849L);
        playerRequest.setGoalsScored(11L);
        playerRequest.setExpectedGoals(6.62);
        playerRequest.setExpectedGoalsPerMatchAvg(0.34);
        playerRequest.setShots(48L);
        playerRequest.setShotsOnTarget(20L);
        playerRequest.setShotsPerMatchAvg(2.47);
        playerRequest.setShotsOnTargetPerMatchAvg(1.03);
        playerRequest.setYear(2016L);
        playerRequest.setClubId(1L);

        given()
                .contentType(ContentType.JSON)
                .body(playerRequest)
                .when()
                .put("/players/1")
                .then()
                .statusCode(400);
    }

    @Test
    public void testDeletePlayer() {
        when()
                .delete("/players/1")
                .then()
                .statusCode(204);

        when()
                .get("/players/1")
                .then()
                .statusCode(404);
    }

    @Test
    public void testDeletePlayer_NotFound() {
        when()
                .delete("/players/100")
                .then()
                .statusCode(404);
    }


    @Test
    public void testCreatePlayer() {
        PlayerRequest playerRequest = new PlayerRequest();
        playerRequest.setFullName("Test Player");
        playerRequest.setMatchesPlayed(10L);
        playerRequest.setCountOfSubstitutions(5L);
        playerRequest.setMinutesPlayed(900L);
        playerRequest.setGoalsScored(5L);
        playerRequest.setExpectedGoals(6.5);
        playerRequest.setExpectedGoalsPerMatchAvg(0.65);
        playerRequest.setShots(20L);
        playerRequest.setShotsOnTarget(10L);
        playerRequest.setShotsPerMatchAvg(2.0);
        playerRequest.setShotsOnTargetPerMatchAvg(1.0);
        playerRequest.setYear(2021L);
        playerRequest.setClubId(1L);

        int id = given()
                .contentType(ContentType.JSON)
                .body(playerRequest)
                .when()
                .post("/players")
                .then()
                .statusCode(201)
                .extract()
                .path("content.id");

        when()
                .get("/players/{id}", id)
                .then()
                .statusCode(200)
                .body("content.id", is(id))
                .body("content.full_name", is("Test Player"))
                .body("content.matches_played", is(10))
                .body("content.count_of_substitutions", is(5))
                .body("content.minutes_played", is(900))
                .body("content.goals_scored", is(5))
                .body("content.expected_goals", is(6.5F))
                .body("content.expected_goals_per_match_avg", is(0.65F))
                .body("content.shots", is(20))
                .body("content.shots_on_target", is(10))
                .body("content.shots_per_match_avg", is(2.0F))
                .body("content.shots_on_target_per_match_avg", is(1.0F))
                .body("content.year", is(2021))
                .body("content.club.id", is(1))
                .body("content.club.club_code", is("Real Betis"));

    }

    @Test
    public void testCreatePlayer_InvalidRequest() {
        // Create a PlayerRequest object with invalid data
        PlayerRequest playerRequest = new PlayerRequest();
        playerRequest.setFullName(""); // Invalid full name (empty string)
        playerRequest.setMatchesPlayed(-1L); // Invalid matches played (negative number)
        playerRequest.setCountOfSubstitutions(5L);
        playerRequest.setMinutesPlayed(900L);
        playerRequest.setGoalsScored(5L);
        playerRequest.setExpectedGoals(6.5);
        playerRequest.setExpectedGoalsPerMatchAvg(0.65);
        playerRequest.setShots(20L);
        playerRequest.setShotsOnTarget(10L);
        playerRequest.setShotsPerMatchAvg(2.0);
        playerRequest.setShotsOnTargetPerMatchAvg(1.0);
        playerRequest.setYear(2021L);
        playerRequest.setClubId(1L);

        given()
                .contentType(ContentType.JSON)
                .body(playerRequest)
                .when()
                .post("/players")
                .then()
                .statusCode(400);
    }

    @Test
    public void testCreatePlayer_InvalidClub() {
        // Create a PlayerRequest object with invalid club ID
        PlayerRequest playerRequest = new PlayerRequest();
        playerRequest.setFullName("Test Player");
        playerRequest.setMatchesPlayed(10L);
        playerRequest.setCountOfSubstitutions(5L);
        playerRequest.setMinutesPlayed(900L);
        playerRequest.setGoalsScored(5L);
        playerRequest.setExpectedGoals(6.5);
        playerRequest.setExpectedGoalsPerMatchAvg(0.65);
        playerRequest.setShots(20L);
        playerRequest.setShotsOnTarget(10L);
        playerRequest.setShotsPerMatchAvg(2.0);
        playerRequest.setShotsOnTargetPerMatchAvg(1.0);
        playerRequest.setYear(2021L);
        playerRequest.setClubId(100L); // Invalid club ID

        given()
                .contentType(ContentType.JSON)
                .body(playerRequest)
                .when()
                .post("/players")
                .then()
                .statusCode(404);
    }

}


