import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class CreateCourierTest {
    String jsonCreate;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Проверка - курьера можно создать и успешный запрос возвращаяет ok") // имя теста
    public void checkCreateCourier() {
        CreateNewLoginPass createNewLoginPass = new CreateNewLoginPass();
        jsonCreate = createNewLoginPass.registerNewLoginPass();
        System.out.println(jsonCreate);
        Courier courier = new Courier();
        Response response = courier.sendPostCreateCourier(jsonCreate);
        courier.compareCourierStatusCodeAndBodyOK(response, 201, true);
        courier.printResponseBodyToConsole(response); // вывели тело ответа на экран

    }

    @Test
    @DisplayName("Проверка создания двух одинаковых курьеров")
    public void checkCreatingAnAlreadyRegisteredCourier() {
        ScooterRegisterCourier scooterRegisterCourier = new ScooterRegisterCourier();
        ArrayList<String> Arr = scooterRegisterCourier.registerNewCourierAndReturnLoginPassword();
        String login = Arr.get(0);
        String pass = Arr.get(1);
        jsonCreate = "{\"login\":\"" + login + "\","
                + "\"password\":\"" + pass + "\"}";
        System.out.println(jsonCreate);
        Courier courier = new Courier();

        Response responseAlreadyRegistered = courier.sendPostCreateCourier(jsonCreate);
        courier.compareCourierStatusCodeAndMessage(responseAlreadyRegistered, 409, "Этот логин уже используется. Попробуйте другой.");
        courier.printResponseBodyToConsole(responseAlreadyRegistered);
    }

    @Test
    @DisplayName("Проверка создания курьера с существующим логином")
    public void checkCreatingCourierWithSomeLogin() {
        ScooterRegisterCourier scooterRegisterCourier = new ScooterRegisterCourier();
        ArrayList<String> Arr = scooterRegisterCourier.registerNewCourierAndReturnLoginPassword();
        String login = Arr.get(0);
        String pass = Arr.get(1);
        jsonCreate = "{\"login\":\"" + login + "\","
                + "\"password\":\"" + pass + "\"}";
        String jsonWithSameLogin = "{\"login\":\"" + login + "\","
                + "\"password\":\"" + "123" + "\"}";
        Courier courier = new Courier();
        Response responseSomeLogin = courier.sendPostCreateCourier(jsonWithSameLogin);
        courier.compareCourierStatusCodeAndMessage(responseSomeLogin, 409, "Этот логин уже используется. Попробуйте другой.");
        courier.printResponseBodyToConsole(responseSomeLogin);
    }

    @Test
    @DisplayName("Проверка создания курьера без логина")
    public void checkCreatingCourierWithoutLogin() {
        ScooterRegisterCourier scooterRegisterCourier = new ScooterRegisterCourier();
        ArrayList<String> Arr = scooterRegisterCourier.registerNewCourierAndReturnLoginPassword();
        String login = Arr.get(0);
        String pass = Arr.get(1);
        jsonCreate = "{\"login\":\"" + login + "\","
                + "\"password\":\"" + pass + "\"}";
        String jsonWithoutLogin = "{\"password\":\"" + pass + "\"}";
        Courier courier = new Courier();
        Response response = courier.sendPostCreateCourier(jsonWithoutLogin);
        courier.compareCourierStatusCodeAndMessage(response, 400, "Недостаточно данных для создания учетной записи");
        courier.printResponseBodyToConsole(response);
    }

    @Test
    @DisplayName("Проверка создания курьера без пароля")
    public void checkCreatingCourierWithoutPass() {
        ScooterRegisterCourier scooterRegisterCourier = new ScooterRegisterCourier();
        ArrayList<String> Arr = scooterRegisterCourier.registerNewCourierAndReturnLoginPassword();
        String login = Arr.get(0);
        String pass = Arr.get(1);
        jsonCreate = "{\"login\":\"" + login + "\","
                + "\"password\":\"" + pass + "\"}";
        String jsonWithoutPass = "{\"login\":\"" + login + "\"}";
        Courier courier = new Courier();
        Response response = courier.sendPostCreateCourier(jsonWithoutPass);
        courier.compareCourierStatusCodeAndMessage(response, 400, "Недостаточно данных для создания учетной записи");
        courier.printResponseBodyToConsole(response);
    }

    @After
    public void deleteCreateCourier() {

        DeleteCourier deleteCourier = new DeleteCourier();
        String answer = deleteCourier.DeleteCourier(jsonCreate);
        System.out.println(answer);
    }

}
