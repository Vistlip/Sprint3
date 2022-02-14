import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Courier {
    @Step("Send POST request to /api/v1/courier")
    public Response sendPostCreateCourier(String body) {
        Response response = given()
                .header("Content-type", "application/json")
                .body(body)
                .when()
                .post("api/v1/courier");
        return response;
    }

    // метод для шага "Вывести тело ответа в консоль":
    @Step("Print response body to console")
    public void printResponseBodyToConsole(Response response) {
        System.out.println(response.body().asString());
    }

    @Step("Send POST request to /api/v1/courier")
    public Response sendPostLoginCourier(String json) {
        Response response = given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("api/v1/courier/login");
        return response;
    }
}
