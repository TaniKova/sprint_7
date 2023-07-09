package yandex.praktikum.sprint7.courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CourierLoginTests {

    private CourierClient courierClient;
    private Integer courierId;
    private Courier courier;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandomCourier();
    }

    @After
    public void cleanUp() {
        if (courierId != null) {
            courierClient.delete(courierId);
        }
    }

    @Test
    @DisplayName("Courier can not login with invalid login")
    public void courierCanNotLoginWithInvalidLoginTest() {

        // создали курьера
        courierClient.create(courier);

        // вызвать метод для логина
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials("invalidLogin", courier.getPassword()));

        // авторизуемся созданным аккаунтом и получаем id для дальнейшего удаления в @After
        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");

        // проверить статус код и ошибку
        int statusCode = loginResponse.extract().statusCode();
        assertEquals(404, statusCode);

        String actualMessage = loginResponse.extract().path("message");
        assertEquals("Учетная запись не найдена", actualMessage);
    }

    @Test
    @DisplayName("Courier can not login with invalid password")
    public void courierCanNotLoginWithInvalidPasswordTest() {

        // создали курьера
        courierClient.create(courier);

        // вызвать метод для логина
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), "invalidPass"));

        // авторизуемся созданным аккаунтом и получаем id для дальнейшего удаления в @After
        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");

        // проверить статус код и ошибку
        int statusCode = loginResponse.extract().statusCode();
        assertEquals(404, statusCode);

        String actualMessage = loginResponse.extract().path("message");
        assertEquals("Учетная запись не найдена", actualMessage);
    }

    @Test
    @DisplayName("Courier can not login without login")
    public void courierCanNotLoginWithoutLoginTest() {

        // создали курьера
        courierClient.create(courier);

        // вызвать метод для логина
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials("", courier.getPassword()));

        // авторизуемся созданным аккаунтом и получаем id для дальнейшего удаления в @After
        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");

        // проверить статус код и ошибку
        int statusCode = loginResponse.extract().statusCode();
        assertEquals(400, statusCode);

        String actualMessage = loginResponse.extract().path("message");
        assertEquals("Недостаточно данных для входа", actualMessage);
    }

    @Test
    @DisplayName("Courier can not login without password")
    public void courierCanNotLoginWithoutPasswordTest() {

        // создали курьера
        courierClient.create(courier);

        // вызвать метод для логина
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), ""));

        // авторизуемся созданным аккаунтом и получаем id для дальнейшего удаления в @After
        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");

        // проверить статус код и ошибку
        int statusCode = loginResponse.extract().statusCode();
        assertEquals(400, statusCode);

        String actualMessage = loginResponse.extract().path("message");
        assertEquals("Недостаточно данных для входа", actualMessage);
    }

    @Test
    @DisplayName("Courier can not login without created account")
    public void courierCanNotLoginWithoutCreatedAccountTest() {

        // вызвать метод для логина
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials("noCreatedUser", "noCreatedUser"));

        // проверить статус код и ошибку
        int statusCode = loginResponse.extract().statusCode();
        assertEquals(404, statusCode);

        String actualMessage = loginResponse.extract().path("message");
        assertEquals("Учетная запись не найдена", actualMessage);

    }


    @DisplayName("Courier can login")
    @Test
    public void courierCanLoginTest() {

        // создали курьера
        courierClient.create(courier);

        // вызвать метод для логина
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        // проверить статус код и боди
        int statusCode = loginResponse.extract().statusCode();
        assertEquals(200, statusCode);

        courierId = loginResponse.extract().path("id");
        assertNotNull(courierId);
    }
}
