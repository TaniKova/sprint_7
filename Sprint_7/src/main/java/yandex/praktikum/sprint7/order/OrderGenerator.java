package yandex.praktikum.sprint7.order;

import org.apache.commons.lang3.RandomStringUtils;
import java.time.LocalDateTime;

public class OrderGenerator {

    public static Order getDefaultOrder() {
        final String firstName = "Andrey";
        final String lastName = "Ivanov";
        final String address = "Moscow, Lenina street, 18";
        final String metroStation = "4";
        final String phone = "+7 999 158 12 17";

        final int rentTime = 5;
        final String deliveryDate = "2023-05-15";
        final String comment = "Hello there";
        final String[] color = {"BLACK"};
        return new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }

    public static Order getRandomOrder(String[] color) {
        final String firstName = RandomStringUtils.randomAlphabetic(7);
        final String lastName = RandomStringUtils.randomAlphabetic(7);
        final String address = RandomStringUtils.randomAlphanumeric(7);
        final String metroStation = RandomStringUtils.randomAlphabetic(7);
        final String phone = RandomStringUtils.randomNumeric(11);

        final int rentTime = (int) (Math.random() * 9 + 1);
        final String deliveryDate = LocalDateTime.now().toLocalDate().toString();
        final String comment = RandomStringUtils.randomAlphabetic(7);
        return new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }
}
