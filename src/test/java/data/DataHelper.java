package data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.Locale;

public class DataHelper {
    private static final Faker FAKER = new Faker(new Locale("en"));

    private DataHelper() {
    }

    public static UserInfo getAuthInfoWithTestData() {
        return new UserInfo("vasya", "qwerty123");
    }

    public static String generateRandomLogin() {
        return FAKER.name().username();
    }

    public static String generateRandomPassword() {
        return FAKER.internet().password();
    }

    public static UserInfo generateRandomUser() {
        return new UserInfo(generateRandomLogin(), generateRandomPassword());
    }

    public static VerificationCode generateRandomVerificationCode() {
        return new VerificationCode(FAKER.numerify("#####"));
    }

    @Value
    public static class UserInfo {
        String login;
        String password;
    }

    @Data
    @NoArgsConstructor //для dbutils - он создает объект с помощью конструктора noArgs
    @AllArgsConstructor //для dbutils - и с помощью сеттеров добавляет значение
    public static class VerificationCode {
        String code;
    }
}

