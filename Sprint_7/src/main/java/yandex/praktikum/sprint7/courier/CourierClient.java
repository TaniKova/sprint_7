package yandex.praktikum.sprint7.courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import yandex.praktikum.sprint7.GeneralData;
import static io.restassured.RestAssured.given;

public class CourierClient extends GeneralData {
     private static final String COURIER_PATH = "/api/v1/courier/";
     private static final String COURIER_LOGIN = "/api/v1/courier/login/";
     private static final String COURIER_DELETE = "/api/v1/courier/{courierId}";

     public CourierClient() { }

    @Step("Create courier")
    public ValidatableResponse create(Courier courier) {
         return given()
                 .spec(getBaseSpec())
                 .body(courier)
                 .when()
                 .post(COURIER_PATH)
                 .then();
     }

    @Step("Login courier")
     public ValidatableResponse login(CourierCredentials courierCredentials) {
         return given()
                 .spec(getBaseSpec())
                 .when()
                 .body(courierCredentials)
                 .post(COURIER_LOGIN)
                 .then();
     }

     @Step("delete courier")
     public ValidatableResponse delete(int courierId) {
         return given()
                 .spec(getBaseSpec())
                 .pathParam("courierId", courierId)
                 .when()
                 .body(courierId)
                 .delete(COURIER_DELETE)
                 .then();
     }
}
