package cz.mendelu.best_scorers_stats.domain.club;

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
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/test-data/cleanup.sql")
@Sql("/test-data/test-data.sql")
public class ClubIntegrationTest {

    private final static String BASE_URI = "http://localhost";

    @LocalServerPort
    private int port;

    @BeforeEach
    public void configureRestAssured() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
    }


    @Test
    public void testGetAllClubs() {
        given()
        .when()
                .get("/clubs")
        .then()
                .statusCode(200)
                .body("items.size()", is(7))
                .body("items.id", containsInAnyOrder(1, 2, 3, 4, 5, 6, 7))
                .body("items.club_code", containsInAnyOrder("Real Betis", "FC Barcelona", "Real Madrid", "Paris Saint-Germain FC", "Hertha BSC", "TSG 1899 Hoffenheim", "Borussia Monchengladbach"));
    }

    @Test
    public void testDeleteClubById() {
        given()
        .when()
                .delete("/clubs/1")
        .then()
                .statusCode(204);
        when().get("/clubs/1").then().statusCode(404); //TODO CHECK IF IT IS DELETED PROBLEM WITH STATUS CODE BUT IT IS DELETED
    }

    @Test
    public void testDeleteClubById_NotFound() {
        when().delete("/clubs/100").then().statusCode(404);
    }

    @Test
    public void testGetClubById() {
        given()
        .when()
                .get("/clubs/1")
        .then()
                .statusCode(200)
                .body("content.id", is(1))
                .body("content.club_code", is("Real Betis"));
    }

    @Test
    public void testGetClubById_NotFound() {
        when().get("/clubs/100").then().statusCode(404);
    }


    @Test
    public void testCreateClub() {
        var ClubRequest = new ClubRequest();

        ClubRequest.setClub_code("ClubTest");
        ClubRequest.setLeague_id(1L);


        int id = given()
                .contentType("application/json")
                .body(ClubRequest)
            .when()
                .post("/clubs")
            .then()
                .statusCode(201)
                .extract()
                .path("content.id");

        when()
                .get("/clubs/{id}", id)
            .then()
                .statusCode(200)
                .body("content.id", is(id))
                .body("content.club_code", is("ClubTest"));

    }

    @Test
    public void testCreateNewClub_InvalidClubCode() {
        var ClubRequest = new ClubRequest();

        ClubRequest.setClub_code("");

        given()
                .contentType("application/json")
                .body(ClubRequest)
            .when()
                .post("/clubs")
            .then()
                .statusCode(400);
    }


    @Test
    public void testCreateNewClub_InvalidLeagueId() {
        var ClubRequest = new ClubRequest();

        ClubRequest.setClub_code("ClubTest");
        ClubRequest.setLeague_id(100L);

        given()
                .contentType("application/json")
                .body(ClubRequest)
            .when()
                .post("/clubs")
            .then()
                .statusCode(404);
    }




}
