package yandex.praktikum.sprint7.courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreateCourierTest {

    private CourierClient courierClient;
    private Courier courierWithoutPassword;
    private Courier courierWithoutLogin;
    private Courier courierDefault;
    private Courier courier;
    private Courier courierWithExistLogin;
    private Integer courierId;

    @Before
    public void setUp() {
        courier = CourierGenerator.getRandomCourier();
        courierClient = new CourierClient();
        courierWithoutPassword = CourierGenerator.getRandomWithoutPassword();
        courierWithoutLogin = CourierGenerator.getRandomWithoutLogin();
        courierDefault = CourierGenerator.getDefaultCourier();
        courierWithExistLogin = CourierGenerator.getExistingLogin();
    }

    @After
    public void cleanUp() {
        if (courierId != null) {
            courierClient.delete(courierId);
        }
    }

    @Test
    @DisplayName("Courier can not be created without password")
    public void courierCanNotBeCreatedWithoutPasswordTest() {

        // вызвать метод создание курьера
        ValidatableResponse createResponse = courierClient.create(courierWithoutPassword);

        // проверить, что статус код 400 и боди
        int statusCode = createResponse.extract().statusCode();
        assertEquals(400, statusCode);

        String actualMessage = createResponse.extract().path("message");
        assertEquals("Недостаточно данных для создания учетной записи", actualMessage);
    }

    @Test
    @DisplayName("Courier can not be created without login")
    public void courierCanNotBeCreatedWithoutLoginTest() {

        // вызвать метод создание курьера
        ValidatableResponse createResponse = courierClient.create(courierWithoutLogin);

        // проверить, что статус код 400 и тело
        int statusCode = createResponse.extract().statusCode();
        assertEquals(400, statusCode);

        String actualMessage = createResponse.extract().path("message");
        assertEquals("Недостаточно данных для создания учетной записи", actualMessage);
    }

    @Test
    @DisplayName("Courier can not be created with existing courier")
    public void courierCanNotBeCreatedWithExistingCourierTest() {

        // создать курьера
        courierClient.create(courierDefault);
        // вызвать метод создания курьера
        ValidatableResponse createResponse = courierClient.create(courierDefault);

        // авторизуемся созданным аккаунтом и получаем id для дальнейшего удаления в @After
        courierId = courierClient.login(CourierCredentials.from(courierDefault)).extract().path("id");

        // проверить, что статус 409 и боди
        int statusCode = createResponse.extract().statusCode();
        assertEquals(409, statusCode);

        String actualMessage = createResponse.extract().path("message");
        assertEquals("Этот логин уже используется. Попробуйте другой.", actualMessage);
    }

    @Test
    @DisplayName("Courier can not be created with existing login")
    public void courierCanNotBeCreatedWithExistingLoginTest() {

        // создать курьера
        courierClient.create(courierWithExistLogin);
        // вызвать метод создания курьера
        ValidatableResponse createResponse = courierClient.create(courierWithExistLogin);

        // авторизуемся созданным аккаунтом и получаем id для дальнейшего удаления в @After
        courierId = courierClient.login(CourierCredentials.from(courierWithExistLogin)).extract().path("id");

        // проверить, что статус 409 и боди
        // в ТЗ ошибка: Этот логин уже используется, но на самом деле выводит: Этот логин уже используется. Попробуйте другой.
        int statusCode = createResponse.extract().statusCode();
        assertEquals(409, statusCode);

        String actualMessage = createResponse.extract().path("message");
        assertEquals("Этот логин уже используется. Попробуйте другой.", actualMessage);
    }

    // можно создать курьера, заполнив все обязательные поля
    @DisplayName("Courier can be created with all required fields")
    @Test
    public void courierCanBeCreatedTest() {

        // вызвать метод создания курьера
        ValidatableResponse createResponse = courierClient.create(courier);

        // проверить статус код, проверить боди
        int statusCode = createResponse.extract().statusCode();
        assertEquals(201, statusCode);

        boolean isCourierCreated = createResponse.extract().path("ok");
        assertTrue(isCourierCreated);

        // авторизуемся созданным аккаунтом и получаем id для дальнейшего удаления в @After
        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");
    }
}
