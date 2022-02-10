import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName; // импорт DisplayName
import io.qameta.allure.Step; // импорт Step
import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest {
    String jsonWithoutLogin = "{\"password\":\"" + "123" + "\"}";
    String jsonWithoutPass = "{\"login\":\"" + "123" + "\"}";
    @Before
    public void setUp() {

        RestAssured.baseURI= "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Проверка - курьера можно создать и успешный запрос возвращаяет ok") // имя теста
    public void checkCreateCourier() {
        createNewLoginPass createNewLoginPass =new createNewLoginPass();
        String json = createNewLoginPass.registerNewLoginPass();
        System.out.println(json);
        
        Response response = sendPostCreateCourier(json);
        compareCourierStatusCodeAndBodyOK(response, 201, true);
        printResponseBodyToConsole(response); // вывели тело ответа на экран

        DeleteCourier deleteCourier = new DeleteCourier();
        String answer = deleteCourier.DeleteCourier(json);
        System.out.println(answer);
    }


    @Test
    @DisplayName("Проверка создания двух одинаковых курьеров")
    public void checkCreatingAnAlreadyRegisteredCourier(){
        scooterRegisterCourier scooterRegisterCourier =new scooterRegisterCourier();
        ArrayList<String> Arr = scooterRegisterCourier.registerNewCourierAndReturnLoginPassword();
        String login = Arr.get(0);
        String pass = Arr.get(1);
        String jsonCreate = "{\"login\":\"" + login + "\","
                + "\"password\":\"" + pass + "\"}";
        System.out.println(jsonCreate);

        Response responseAlreadyRegistered = sendPostCreateCourier(jsonCreate);
        compareCourierStatusCodeAndMessage(responseAlreadyRegistered, 409, "Этот логин уже используется. Попробуйте другой.");
        printResponseBodyToConsole(responseAlreadyRegistered);

        DeleteCourier deleteCourier = new DeleteCourier();
        String answer = deleteCourier.DeleteCourier(jsonCreate);
        System.out.println(answer);
    }

    @Test
    @DisplayName("Проверка создания курьера с существующим логином")
    public void checkCreatingCourierWithSomeLogin(){
        scooterRegisterCourier scooterRegisterCourier =new scooterRegisterCourier();
        ArrayList<String> Arr = scooterRegisterCourier.registerNewCourierAndReturnLoginPassword();
        String login = Arr.get(0);
        String pass = Arr.get(1);
        String jsonCreate = "{\"login\":\"" + login + "\","
                + "\"password\":\"" + pass + "\"}";
        String jsonWithSameLogin = "{\"login\":\"" + login + "\","
                + "\"password\":\"" + "123" + "\"}";
        Response responseSomeLogin = sendPostCreateCourier(jsonWithSameLogin);
        compareCourierStatusCodeAndMessage(responseSomeLogin, 409, "Этот логин уже используется. Попробуйте другой.");
        printResponseBodyToConsole(responseSomeLogin);

        DeleteCourier deleteCourier = new DeleteCourier();
        String answer = deleteCourier.DeleteCourier(jsonCreate);
        System.out.println(answer);

    }

    @Test
    @DisplayName("Проверка создания курьера без логина")
    public  void checkCreatingCourierWithoutLogin() {

        Response response = sendPostCreateCourier(jsonWithoutLogin);
        compareCourierStatusCodeAndMessage(response, 400, "Недостаточно данных для создания учетной записи");
        printResponseBodyToConsole(response);
    }


    @Test
    @DisplayName("Проверка создания курьера без пароля")
    public  void checkCreatingCourierWithoutPass() {
        Response response = sendPostCreateCourier(jsonWithoutPass);
        compareCourierStatusCodeAndMessage(response, 400, "Недостаточно данных для создания учетной записи");
        printResponseBodyToConsole(response);
    }


    @Step("Send POST request to /api/v1/courier")
    public Response sendPostCreateCourier(String body){
        Response response =given()
                .header("Content-type", "application/json")
                .body(body)
                .when()
                .post("api/v1/courier");
        return response;
    }


    @Step("Compare statusCode and bodyOK")
    public void compareCourierStatusCodeAndBodyOK(Response response, int statusCode, boolean answerGood){
        response.then().assertThat().body("ok", equalTo(answerGood)).statusCode(statusCode);
    }

    @Step("Compare statusCode and body")
    public void compareCourierStatusCodeAndMessage(Response response, int statusCode, String body){
        response.then().assertThat().body("message", equalTo(body)).statusCode(statusCode);
    }


    // метод для шага "Вывести тело ответа в консоль":
    @Step("Print response body to console")
    public void printResponseBodyToConsole(Response response){
        System.out.println(response.body().asString());
    }






}
