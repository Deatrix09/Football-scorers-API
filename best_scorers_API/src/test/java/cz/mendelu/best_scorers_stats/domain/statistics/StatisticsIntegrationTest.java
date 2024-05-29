package cz.mendelu.best_scorers_stats.domain.statistics;

import io.restassured.RestAssured;
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
import static org.hamcrest.Matchers.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/test-data/cleanup.sql")
@Sql("/test-data/test-data.sql")
public class StatisticsIntegrationTest {


    private final static String BASE_URI = "http://localhost";

    @LocalServerPort
    private int port;

    @BeforeEach
    public void configureRestAssured() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
    }


    // Player Efficiency =
    // (goals_scored + expected_goals) / (matches_played + count_of_substitutions)
    // × (shots_on_target / shots)
    // × (minutes_played / 90)

    @Test
    public void testGetTop3PlayersByEfficiency() {
        given()
                .when()
                .get("/statistics/top3/2016")
                .then()
                .statusCode(200)
                .body("content.size()", is(3))
                .body("content[0].fullName", is("Antoine Griezmann"))
                .body("content[0].efficiency", is(12.535534F))
                .body("content[0].year", is(2016))
                .body("content[1].fullName", is("Karim Benzema"))
                .body("content[1].efficiency", is(9.00545F))
                .body("content[1].year", is(2016))
                .body("content[2].fullName", is("Juanmi Callejon"))
                .body("content[2].efficiency", is(4.3094416F))
                .body("content[2].year", is(2016));
    }

    @Test
    public void testGetTop3PlayersByEfficiency_InvalidYear() {
        when()
                .get("/statistics/top3/2020")
                .then()
                .statusCode(404);
    }

    @Test
    public void testGetStatistics() {
        given()
                .when()
                .get("/statistics")
                .then()
                .statusCode(200)
                .body("content.consistency_index_for_all_players", is(0.06323802F))
                .body("content.average_impact_score_for_all_players", is(16.369402F))
                .body("content.average_player_efficiency_for_all_players", is(9.72583F))
                .body("content.count_deleted_players", is(0));
    }


















}
