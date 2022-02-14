import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

public class GetOrderListTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Получение списка всех заказов") // имя теста
    public void checkGetOrderList() {
        Order order = new Order();
        Response response = order.sendGetOrders();
        order.printResponseBodyToConsole(response);
        response.then().assertThat().body("orders", notNullValue());
    }
}
