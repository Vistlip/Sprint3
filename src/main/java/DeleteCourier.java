import io.restassured.RestAssured;
import io.restassured.response.Response;

public class DeleteCourier {

    public String DeleteCourier(String json) {
        String answer = "";

        Response response = RestAssured.given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("api/v1/courier/login");

        CourirerId courirerId = response.body().as(CourirerId.class);
        String id = courirerId.getIdCourier();
        Response responseDel = RestAssured.given()
                .header("Content-type", "application/json")
                .delete("api/v1/courier/" + id);
        if (response.statusCode() == 200) {
            String an = "Учетная запись удалена";
            answer = an + "  :  " + responseDel.body().asString();
        }

        return answer;
    }

}
