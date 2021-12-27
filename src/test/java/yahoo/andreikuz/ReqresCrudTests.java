package yahoo.andreikuz;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.core.Is.is;


public class ReqresCrudTests {

    @BeforeAll
    static void startUrl() {
        RestAssured.baseURI = "https://reqres.in/";

    }

    @Test
    void createTest() {
        String requestBody = "{" +
                "\"name\": \"Andrei Kuznetsov\"," +
                "\"job\": \"QA Engineer\"}";
        given()
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("/api/users")
                .then().log().all()
                .statusCode(201)
                .body("name", is("Andrei Kuznetsov"),
                        "job", is("QA Engineer"));
    }

    @Test
    void updatePutTest() {
        String jsonBody = "{" +
                "\"name\": \"Andrei G Kuznetsov\"," +
                "\"job\": \"QA Automotive Engineer\"}";
        given()
                .contentType(JSON)
                .body(jsonBody)
                .when()
                .put("/api/users/2")
                .then().log().all()
                .statusCode(200)
                .body("name", is("Andrei G Kuznetsov"),
                        "job", is("QA Automotive Engineer"));
    }

    @Test
    void deleteTest() {
        delete("/api/users/2")
                .then()
                .statusCode(204);
    }

    @Test
    void registerTest() {
        String requestBody = "{" +
                "\"email\": \"eve.holt@reqres.in\"," +
                "\"password\": \"pistol\"}";
        given()
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("/api/register")
                .then().log().all()
                .statusCode(200)
                .body("id", is(4),
                        "token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void getRegisteredUserTest() {
        get("/api/users/4")
                .then().log().all()
                .statusCode(200)
                .body("data.email", is("eve.holt@reqres.in"),
                        "data.id", is(4),
                                             "data.first_name", is("Eve"),
                                             "data.last_name", is("Holt"));
    }

    @Test
    void loginTest() {
        String requestBody = "{" +
                "\"email\": \"eve.holt@reqres.in\"," +
                "\"password\": \"pistoletik\"}"; // бага - в качестве пароля принимается любое сочетание символов
        given()
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("/api/login")
                .then().log().all()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }
}