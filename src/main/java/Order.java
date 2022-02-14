import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class Order {
    @Step("Send POST request to /api/v1/orders")
    public Response sendPostCreateOrders(String json) {
        Response response = given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("/api/v1/orders");
        return response;
    }

    @Step("Send Get request to /api/v1/orders")
    public Response sendGetOrders() {
        Response response = given()
                .header("Content-type", "application/json")
                .when()
                .get("/api/v1/orders");
        return response;
    }

    @Step("Print response body to console")
    public void printResponseBodyToConsole(Response response) {
        System.out.println(response.body().asString());
    }

}