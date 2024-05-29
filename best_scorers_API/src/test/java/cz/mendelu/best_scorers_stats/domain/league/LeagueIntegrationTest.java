package cz.mendelu.best_scorers_stats.domain.league;

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

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/test-data/cleanup.sql")
@Sql("/test-data/test-data.sql")
public class LeagueIntegrationTest {

    private final static String BASE_URI = "http://localhost";

    @LocalServerPort
    private int port;

    @BeforeEach
    public void configureRestAssured() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
    }

    @Test
    public void testGetAllLeagues() {
        given()
        .when()
                .get("/leagues")
        .then()
                .statusCode(200)
                .body("items.size()", is(4))
                .body("items.id", containsInAnyOrder(1, 2, 3, 4))
                .body("items.league_name", containsInAnyOrder("La Liga", "Serie A","Bundesliga", "Ligue 1"))
                .body("items.clubs", containsInAnyOrder(List.of("Real Betis", "FC Barcelona", "Real Madrid"), List.of(), List.of("Hertha BSC","TSG 1899 Hoffenheim","Borussia Monchengladbach"), List.of("Paris Saint-Germain FC")));

    }

    @Test
    public void testDeleteLeagueById() {
        given()
        .when()
                .delete("/leagues/1")
        .then()
                .statusCode(204);
        when().get("/leagues/1").then().statusCode(404); //TODO CHECK IF IT IS DELETED


    }

    @Test
    public void testDeleteLeagueById_Not_Found() {
        given()
        .when()
                .delete("/leagues/100")
        .then()
                .statusCode(404);
    }

    @Test
    public void testCreateNewLeague() {

        var leagueRequest = new LeagueRequest("La Liga 2",1L);

        int id = given()
                .contentType(ContentType.JSON)
                .body(leagueRequest)
        .when()
                .post("/leagues")
        .then()
                .statusCode(201)
        .extract()
                .path("content.id");
        when()
                .get("/leagues/{id}", id)
        .then()
                .statusCode(200)
                .body("content.league_name", is("La Liga 2"))
                .body("content.clubs", containsInAnyOrder(List.of()));

    }

    @Test
    public void testCreateNewLeague_Invalid_Country() {

        var leagueRequest = new LeagueRequest("La Liga 2",100L);

        given()
                .contentType(ContentType.JSON)
                .body(leagueRequest)
        .when()
                .post("/leagues")
        .then()
                .statusCode(404);

    }

    @Test
    public void testCreateNewLeague_Invalid_League_Name() {

        var leagueRequest = new LeagueRequest("",1L);

        given()
                .contentType(ContentType.JSON)
                .body(leagueRequest)
        .when()
                .post("/leagues")
        .then()
                .statusCode(400);

    }

    @Test
    public void testGetLeagueById() {
        when()
                .get("/leagues/1")
        .then()
                .statusCode(200)
                .body("content.id", is(1))
                .body("content.league_name", is("La Liga"))
                .body("content.clubs", containsInAnyOrder("Real Betis", "FC Barcelona", "Real Madrid"));
    }

    @Test
    public void testGetLeagueById_Not_Found() {
        when()
                .get("/leagues/100")
        .then()
                .statusCode(404);
    }

}
