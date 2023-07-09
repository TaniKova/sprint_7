package yandex.praktikum.sprint7.courier;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {

    public static Courier getRandomCourier() {
        final String login = RandomStringUtils.randomAlphabetic(6);
        final String password = RandomStringUtils.randomAlphabetic(6);
        final String firstName = RandomStringUtils.randomAlphabetic(6);
        return new Courier(login, password, firstName);
    }

    public static Courier getDefaultCourier() {
        final String login = "courier1";
        final String password = "Qwe12345";
        final String firstName = "Ivan";
        return new Courier(login, password, firstName);
    }

    public static Courier getRandomWithoutPassword() {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new Courier(login, null, firstName);
    }

    public static Courier getRandomWithoutLogin() {
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new Courier(null, password, firstName);
    }

    public static Courier getExistingLogin() {
        final String login = "user2";
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new Courier(login, password, firstName);
    }
}
