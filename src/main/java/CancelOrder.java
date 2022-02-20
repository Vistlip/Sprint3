import io.restassured.RestAssured;
import io.restassured.response.Response;

public class CancelOrder {

        public String CancelOrder(OrderTrack orderTrack) {
            String answer = "";

            String track = orderTrack.getTrack();
            Response responsePut = RestAssured.given()
                    .header("Content-type", "application/json")
                    .put("api/v1/orders/cancel" + "?track=" + track);
            if (responsePut.statusCode() == 200) {
                answer = "Заказ отменен";
            }
            return answer;
        }

    }

