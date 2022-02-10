import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrderListTest {
    @Before
    public void setUp() {

        RestAssured.baseURI= "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Получение списка всех заказов") // имя теста
    public void checkGetOrderList() {
        Response response =given()
                .header("Content-type", "application/json")
                .when()
                .get("/api/v1/orders");
        response.then().assertThat().body("orders", notNullValue());
        System.out.println(response.body().asString());

    }
}
