package api.core;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class Client {
    public static Response post(String endpoint, Object requestBody) {
        return given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }
}
