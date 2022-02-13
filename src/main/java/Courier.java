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

    @Step("Compare statusCode and bodyOK")
    public void compareCourierStatusCodeAndBodyOK(Response response, int statusCode, boolean answerGood) {
        response.then().assertThat().body("ok", equalTo(answerGood)).statusCode(statusCode);
    }

    @Step("Compare statusCode and body")
    public void compareCourierStatusCodeAndMessage(Response response, int statusCode, String body) {
        response.then().assertThat().body("message", equalTo(body)).statusCode(statusCode);
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

    @Step("Compare statusCode")
    public void compareCourierStatusCode(Response response, int statusCode) {
        response.then().assertThat().statusCode(statusCode);
    }

}
