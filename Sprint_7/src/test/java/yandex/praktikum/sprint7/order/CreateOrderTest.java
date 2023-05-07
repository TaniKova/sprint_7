package yandex.praktikum.sprint7.order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private OrderClient orderClient;
    private Integer track;
    private Order order;
    private String[] color;
    private int expectedStatusCode;

    public CreateOrderTest(String[] color, int expectedStatusCode) {
        this.color = color;
        this.expectedStatusCode = expectedStatusCode;
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        order = OrderGenerator.getRandomOrder(color);
    }

    @After
    public void cleanUp() {
        OrderTrack orderTrack = new OrderTrack(track);
        orderClient.cancel(orderTrack);
    }

    @Parameterized.Parameters(name = "Тестовые данные: Color = {0}, statusCode = {1}")
    public static Object[][] orderData() {
        return new Object[][] {
                {new String[]{"GREY"}, 201},
                {new String[]{"BLACK", "GREY"}, 201},
                {new String[]{"BLACK"}, 201},
                {null, 201}
        };
    }

    @DisplayName("Create order with all fields")
    @Test
    public void createOrderTest() {

        // вызвать метод для создания ордера
        ValidatableResponse createResponse = orderClient.create(order);

        // проверить статус код, проверить боди
        int actualStatusCode = createResponse.extract().statusCode();
        assertEquals(expectedStatusCode, actualStatusCode);

        track = createResponse.extract().path("track");
        assertNotNull(track);
    }
}
