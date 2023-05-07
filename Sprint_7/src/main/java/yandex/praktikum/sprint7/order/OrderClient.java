package yandex.praktikum.sprint7.order;

import io.restassured.response.ValidatableResponse;
import yandex.praktikum.sprint7.GeneralData;
import static io.restassured.RestAssured.given;

public class OrderClient extends GeneralData {
    private static final String ORDER_PATH = "/api/v1/orders";
    private static final String ORDER_CANCEL = "/api/v1/orders/cancel";
    private static final String ORDER_LIST = "/api/v1/orders";

    public OrderClient() {

    }

    // создать
    public ValidatableResponse create(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();
    }

    public ValidatableResponse cancel(OrderTrack orderTrack) {
        return given()
                .spec(getBaseSpec())
                .when()
                .body(orderTrack)
                .put(ORDER_CANCEL)
                .then();
    }

    public ValidatableResponse orderList() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_LIST)
                .then();
    }
}
