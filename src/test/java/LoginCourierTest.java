import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest {
    String jsonCreate;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Проверка авторизации курьера")
    public void checkCourierCanLogIn() {
        ScooterRegisterCourier scooterRegisterCourier = new ScooterRegisterCourier();
        ArrayList<String> Arr = scooterRegisterCourier.registerNewCourierAndReturnLoginPassword();
        String login = Arr.get(0);
        String pass = Arr.get(1);
        jsonCreate = "{\"login\":\"" + login + "\","
                + "\"password\":\"" + pass + "\"}";
        System.out.println(jsonCreate);
        Courier courier = new Courier();
        Response response = courier.sendPostLoginCourier(jsonCreate);
        courier.compareCourierStatusCode(response, 200);
        courier.printResponseBodyToConsole(response);
    }

    @Test
    @DisplayName("Проверка авторизации курьера c неправильным логином")
    public void checkCourierIncorrectLogin() {
        ScooterRegisterCourier scooterRegisterCourier = new ScooterRegisterCourier();
        ArrayList<String> Arr = scooterRegisterCourier.registerNewCourierAndReturnLoginPassword();
        String login = Arr.get(0);
        String pass = Arr.get(1);
        jsonCreate = "{\"login\":\"" + login + "\","
                + "\"password\":\"" + pass + "\"}";
        System.out.println(jsonCreate);
        String jsonCreateIncorrect = "{\"login\":\"" + login + "\","
                + "\"password\":\"" + "pass" + "\"}";
        System.out.println(jsonCreateIncorrect);
        Courier courier = new Courier();
        Response response = courier.sendPostLoginCourier(jsonCreateIncorrect);
        courier.compareCourierStatusCode(response, 404);
        courier.printResponseBodyToConsole(response);
    }


    @Test
    @DisplayName("Проверка авторизации курьера c неправильным паролем")
    public void checkCourierIncorrectPass() {
        ScooterRegisterCourier scooterRegisterCourier = new ScooterRegisterCourier();
        ArrayList<String> Arr = scooterRegisterCourier.registerNewCourierAndReturnLoginPassword();
        String login = Arr.get(0);
        String pass = Arr.get(1);
        jsonCreate = "{\"login\":\"" + login + "\","
                + "\"password\":\"" + pass + "\"}";
        System.out.println(jsonCreate);
        String jsonCreateIncorrect = "{\"login\":\"" + login + "\","
                + "\"password\":\"" + "pass" + "\"}";
        System.out.println(jsonCreateIncorrect);
        Courier courier = new Courier();
        Response response = courier.sendPostLoginCourier(jsonCreateIncorrect);
        courier.compareCourierStatusCode(response, 404);
        courier.printResponseBodyToConsole(response);
    }

    @Test
    @DisplayName("Проверка авторизации курьера без логина")
    public void checkCourierWithoutLogin() {
        ScooterRegisterCourier scooterRegisterCourier = new ScooterRegisterCourier();
        ArrayList<String> Arr = scooterRegisterCourier.registerNewCourierAndReturnLoginPassword();
        String login = Arr.get(0);
        String pass = Arr.get(1);
        jsonCreate = "{\"login\":\"" + login + "\","
                + "\"password\":\"" + pass + "\"}";
        System.out.println(jsonCreate);
        String jsonCreateWithoutLogin = "{\"password\":\"" + pass + "\"}";
        System.out.println(jsonCreateWithoutLogin);
        Courier courier = new Courier();
        Response response = courier.sendPostLoginCourier(jsonCreateWithoutLogin);
        courier.compareCourierStatusCode(response, 400);
        courier.printResponseBodyToConsole(response);
    }

    // Тест падает и в Postman, но по документации все ок. Так что посчитал намеренным багом
    @Test
    @DisplayName("Проверка авторизации курьера без пароля")
    public void checkCourierWithoutPass() {
        ScooterRegisterCourier scooterRegisterCourier = new ScooterRegisterCourier();
        ArrayList<String> Arr = scooterRegisterCourier.registerNewCourierAndReturnLoginPassword();
        String login = Arr.get(0);
        String pass = Arr.get(1);
        jsonCreate = "{\"login\":\"" + login + "\","
                + "\"password\":\"" + pass + "\"}";
        System.out.println(jsonCreate);
        String jsonCreateWithoutLogin = "{\"login\":\"" + login + "\"}";
        System.out.println(jsonCreateWithoutLogin);
        Courier courier = new Courier();
        Response response = courier.sendPostLoginCourier(jsonCreateWithoutLogin);
        courier.compareCourierStatusCode(response, 400);
        courier.printResponseBodyToConsole(response);
    }

    @Test
    @DisplayName("Проверка авторизации курьера под не существующим пользователем")
    public void checkCourierDoesNotExist() {
        ScooterRegisterCourier scooterRegisterCourier = new ScooterRegisterCourier();
        ArrayList<String> Arr = scooterRegisterCourier.registerNewCourierAndReturnLoginPassword();
        String login = Arr.get(0);
        String pass = Arr.get(1);
        jsonCreate = "{\"login\":\"" + login + "\","
                + "\"password\":\"" + pass + "\"}";
        System.out.println(jsonCreate);
        CreateNewLoginPass createNewLoginPass = new CreateNewLoginPass();
        String jsonCrateDoesNotExist = createNewLoginPass.registerNewLoginPass();
        Courier courier = new Courier();
        Response response = courier.sendPostLoginCourier(jsonCrateDoesNotExist);
        courier.compareCourierStatusCode(response, 404);
        courier.printResponseBodyToConsole(response);
    }

    @Test
    @DisplayName("Проверка авторизации курьера и получения id")
    public void checkCourierCanLogInAndGetId() {
        ScooterRegisterCourier scooterRegisterCourier = new ScooterRegisterCourier();
        ArrayList<String> Arr = scooterRegisterCourier.registerNewCourierAndReturnLoginPassword();
        String login = Arr.get(0);
        String pass = Arr.get(1);
        jsonCreate = "{\"login\":\"" + login + "\","
                + "\"password\":\"" + pass + "\"}";
        System.out.println(jsonCreate);
        Courier courier = new Courier();
        Response response = courier.sendPostLoginCourier(jsonCreate);
        courier.compareCourierStatusCode(response, 200);
        courier.printResponseBodyToConsole(response);
        response.then().assertThat().body("id", notNullValue());
    }

    @After
    public void deleteCreateCourier() {

        DeleteCourier deleteCourier = new DeleteCourier();
        String answer = deleteCourier.DeleteCourier(jsonCreate);
        System.out.println(answer);
    }
}
