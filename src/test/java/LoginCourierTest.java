
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest {
    @Before
    public void setUp() {

        RestAssured.baseURI= "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Проверка авторизации курьера")
    public  void checkCourierCanLogIn() {
        scooterRegisterCourier scooterRegisterCourier =new scooterRegisterCourier();
        ArrayList<String> Arr = scooterRegisterCourier.registerNewCourierAndReturnLoginPassword();
        String login = Arr.get(0);
        String pass = Arr.get(1);
        String jsonCreate = "{\"login\":\"" + login + "\","
                + "\"password\":\"" + pass + "\"}";
        System.out.println(jsonCreate);
        Response response = sendPostLoginCourier(jsonCreate);
        compareCourierStatusCode(response,200);
        printResponseBodyToConsole(response);

        DeleteCourier deleteCourier = new DeleteCourier();
        String answer = deleteCourier.DeleteCourier(jsonCreate);
        System.out.println(answer);
    }

    @Test
    @DisplayName("Проверка авторизации курьера c неправильным логином")
    public  void checkCourierIncorrectLogin() {
        scooterRegisterCourier scooterRegisterCourier =new scooterRegisterCourier();
        ArrayList<String> Arr = scooterRegisterCourier.registerNewCourierAndReturnLoginPassword();
        String login = Arr.get(0);
        String pass = Arr.get(1);
        String jsonCreate = "{\"login\":\"" + login + "\","
                + "\"password\":\"" + pass + "\"}";
        System.out.println(jsonCreate);
        String jsonCreateIncorrect = "{\"login\":\"" + login + "\","
                + "\"password\":\"" + "pass" + "\"}";
        System.out.println(jsonCreateIncorrect);
        Response response = sendPostLoginCourier(jsonCreateIncorrect);
        compareCourierStatusCode(response,404);
        printResponseBodyToConsole(response);

        DeleteCourier deleteCourier = new DeleteCourier();
        String answer = deleteCourier.DeleteCourier(jsonCreate);
        System.out.println(answer);
    }


    @Test
    @DisplayName("Проверка авторизации курьера c неправильным паролем")
    public  void checkCourierIncorrectPass() {
        scooterRegisterCourier scooterRegisterCourier =new scooterRegisterCourier();
        ArrayList<String> Arr = scooterRegisterCourier.registerNewCourierAndReturnLoginPassword();
        String login = Arr.get(0);
        String pass = Arr.get(1);
        String jsonCreate = "{\"login\":\"" + login + "\","
                + "\"password\":\"" + pass + "\"}";
        System.out.println(jsonCreate);
        String jsonCreateIncorrect = "{\"login\":\"" + login + "\","
                + "\"password\":\"" + "pass" + "\"}";
        System.out.println(jsonCreateIncorrect);
        Response response = sendPostLoginCourier(jsonCreateIncorrect);
        compareCourierStatusCode(response,404);
        printResponseBodyToConsole(response);

        DeleteCourier deleteCourier = new DeleteCourier();
        String answer = deleteCourier.DeleteCourier(jsonCreate);
        System.out.println(answer);
    }

    @Test
    @DisplayName("Проверка авторизации курьера без логина")
    public  void checkCourierWithoutLogin() {
        scooterRegisterCourier scooterRegisterCourier =new scooterRegisterCourier();
        ArrayList<String> Arr = scooterRegisterCourier.registerNewCourierAndReturnLoginPassword();
        String login = Arr.get(0);
        String pass = Arr.get(1);
        String jsonCreate = "{\"login\":\"" + login + "\","
                + "\"password\":\"" + pass + "\"}";
        System.out.println(jsonCreate);
        String jsonCreateWithoutLogin = "{\"password\":\"" + pass + "\"}";
        System.out.println(jsonCreateWithoutLogin);
        Response response = sendPostLoginCourier(jsonCreateWithoutLogin);
        compareCourierStatusCode(response,400);
        printResponseBodyToConsole(response);

        DeleteCourier deleteCourier = new DeleteCourier();
        String answer = deleteCourier.DeleteCourier(jsonCreate);
        System.out.println(answer);
    }
    // Тест падает и в Postman, но по документации все ок. Так что посчитал намеренным багом
    @Test
    @DisplayName("Проверка авторизации курьера без пароля")
    public  void checkCourierWithoutPass() {
        scooterRegisterCourier scooterRegisterCourier =new scooterRegisterCourier();
        ArrayList<String> Arr = scooterRegisterCourier.registerNewCourierAndReturnLoginPassword();
        String login = Arr.get(0);
        String pass = Arr.get(1);
        String jsonCreate = "{\"login\":\"" + login + "\","
                + "\"password\":\"" + pass + "\"}";
        System.out.println(jsonCreate);
        String jsonCreateWithoutLogin = "{\"login\":\"" + login + "\"}";
        System.out.println(jsonCreateWithoutLogin);
        Response response = sendPostLoginCourier(jsonCreateWithoutLogin);
        compareCourierStatusCode(response,400);
        printResponseBodyToConsole(response);

        DeleteCourier deleteCourier = new DeleteCourier();
        String answer = deleteCourier.DeleteCourier(jsonCreate);
        System.out.println(answer);
    }

    @Test
    @DisplayName("Проверка авторизации курьера под не существующим пользователем")
    public  void checkCourierDoesNotExist() {
        scooterRegisterCourier scooterRegisterCourier =new scooterRegisterCourier();
        ArrayList<String> Arr = scooterRegisterCourier.registerNewCourierAndReturnLoginPassword();
        String login = Arr.get(0);
        String pass = Arr.get(1);
        String jsonCreate = "{\"login\":\"" + login + "\","
                + "\"password\":\"" + pass + "\"}";
        System.out.println(jsonCreate);
        createNewLoginPass createNewLoginPass =new createNewLoginPass();
        String jsonCrateDoesNotExist = createNewLoginPass.registerNewLoginPass();
        Response response = sendPostLoginCourier(jsonCrateDoesNotExist);
        compareCourierStatusCode(response,404);
        printResponseBodyToConsole(response);

        DeleteCourier deleteCourier = new DeleteCourier();
        String answer = deleteCourier.DeleteCourier(jsonCreate);
        System.out.println(answer);
    }

    @Test
    @DisplayName("Проверка авторизации курьера и получения id")
    public  void checkCourierCanLogInAndGetId() {
        scooterRegisterCourier scooterRegisterCourier =new scooterRegisterCourier();
        ArrayList<String> Arr = scooterRegisterCourier.registerNewCourierAndReturnLoginPassword();
        String login = Arr.get(0);
        String pass = Arr.get(1);
        String jsonCreate = "{\"login\":\"" + login + "\","
                + "\"password\":\"" + pass + "\"}";
        System.out.println(jsonCreate);
        Response response = sendPostLoginCourier(jsonCreate);
        compareCourierStatusCode(response,200);
        printResponseBodyToConsole(response);
        response.then().assertThat().body("id", notNullValue());

        DeleteCourier deleteCourier = new DeleteCourier();
        String answer = deleteCourier.DeleteCourier(jsonCreate);
        System.out.println(answer);
    }




    @Step("Send POST request to /api/v1/courier")
    public Response sendPostLoginCourier(String json){
        Response response = given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("api/v1/courier/login");
        return response;
    }

    @Step("Compare statusCode")
    public void compareCourierStatusCode(Response response, int statusCode){
        response.then().assertThat().statusCode(statusCode);
    }
    @Step("Compare statusCode")
    public void compareCourierStatusCodeAndMessage(Response response, int statusCode, String body){
        response.then().assertThat().body("message", equalTo(body)).statusCode(statusCode);
    }


    // метод для шага "Вывести тело ответа в консоль":
    @Step("Print response body to console")
    public void printResponseBodyToConsole(Response response){
        System.out.println(response.body().asString());
    }
}
