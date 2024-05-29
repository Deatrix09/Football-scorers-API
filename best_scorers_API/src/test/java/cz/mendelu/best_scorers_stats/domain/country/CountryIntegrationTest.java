package cz.mendelu.best_scorers_stats.domain.country;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/test-data/cleanup.sql")
@Sql("/test-data/test-data.sql")
public class CountryIntegrationTest {



    private final static String BASE_URI = "http://localhost";

    @LocalServerPort
    private int port;

    @BeforeEach
    public void configureRestAssured() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
    }


    @Test
    public void testGetAllCountries() {
        given()
        .when()
                .get("/countries")
        .then()
                .statusCode(200)
                .body("items.size()", is(4))
                .body("items.id", containsInAnyOrder(1, 2, 3, 6))
                .body("items.country_name", containsInAnyOrder("Spain", "Italy","Germany", "France"))
                .body("items.league_ids", containsInAnyOrder(List.of(1), List.of(2), List.of(3), List.of(4)));
    }

    @Test
    public void testGetCountryById() {
        given()
        .when()
                .get("/countries/1")
        .then()
                .statusCode(200)
                .body("content.id", is(1))
                .body("content.country_name", is("Spain"))
                .body("content.league_ids", containsInAnyOrder(1));
    }

    @Test
    public void testGetCountryById_NotFound() {
        given()
        .when()
                .get("/countries/100")
        .then()
                .statusCode(404);
    }

    @Test
    public void testDeleteCountryById() {
        given()
        .when()
                .delete("/countries/1")
        .then()
                .statusCode(204);
        given()
        .when()
                .get("/countries/1")
        .then()
                .statusCode(404);
    }

    @Test
    public void testDeleteCountryById_NotFound() {
        given()
        .when()
                .delete("/countries/100")
        .then()
                .statusCode(404);
    }

    @Test
    public void testCreateCountry() {

        var countryRequest = new CountryRequest("Czech Republic");

        int id = given()
                .contentType("application/json")
                .body(countryRequest)
            .when()
                .post("/countries")
            .then()
                .statusCode(201)
                .extract()
                .path("content.id");
        given()

            .when()
                .get("/countries/{id}", id)
            .then()
                .statusCode(200)
                .body("content.country_name", is("Czech Republic"))
                .body("content.league_ids", containsInAnyOrder(List.of()));

    }

    @Test
    public void testCreateCountry_Invalid_Country_Name() {

        var countryRequest = new CountryRequest("");

        given()
                .contentType("application/json")
                .body(countryRequest)
            .when()
                .post("/countries")
            .then()
                .statusCode(400);

    }
}
