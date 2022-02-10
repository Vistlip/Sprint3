import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class DeleteCourier {

    public String DeleteCourier(String json) {
        String answer = "";

        Response response = given()
                    .header("Content-type", "application/json")
                    .body(json)
                    .when()
                    .post("api/v1/courier/login");

        CourirerId courirerId = response.body().as(CourirerId.class);
        String id = courirerId.getIdCourier();



        Response responseDel = given()
                    .header("Content-type", "application/json")
                    .delete("api/v1/courier/" + id);
        if (response.statusCode() == 200) {
            String an = "Учетная запись удалена";
            answer = an + "  :  " + responseDel.body().asString();
        }

        return answer;
    }


}
