import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final int metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String color;
    private final int statusCodeExpected;

    public CreateOrderTest(String firstName, String lastName, String address, int metroStation, String phone, int rentTime, String deliveryData, String comment, String color, int statusCodeExpected) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryData;
        this.comment = comment;
        this.color = color;
        this.statusCodeExpected = statusCodeExpected;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderData() {
        return new Object[][]{
                {"Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2022-02-06", "Saske, come back to Konoha", "BLACK", 201},
                {"Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2022-02-06", "Saske, come back to Konoha", "GREY", 201},
                {"Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2022-02-06", "Saske, come back to Konoha", "BLACK" + " " + "WHITE", 201},
                {"Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2022-02-06", "Saske, come back to Konoha", "", 201},
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Проверка создания заказов с разным цветам самоката и проверка того что трек номер назначается")
    public void CreateOrderCheck() {
        String jsonCreate = "{\"firstName\":\"" + firstName + "\","
                + "\"lastName\":\"" + lastName + "\","
                + "\"address\":\"" + address + "\","
                + "\"metroStation\":" + metroStation + ","
                + "\"phone\":\"" + phone + "\","
                + "\"rentTime\":" + rentTime + ","
                + "\"deliveryDate\":\"" + deliveryDate + "\","
                + "\"comment\":\"" + comment + "\","
                + "\"color\":" + "[" + "\"" + color + "\"" + "]}";
        Order order = new Order();
        Response response = order.sendPostCreateOrders(jsonCreate);
        order.compareOrderTrackNotNull(response);
        order.printResponseBodyToConsole(response);
    }

}
