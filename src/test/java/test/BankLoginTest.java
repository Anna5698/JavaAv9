package test;

import data.DataHelper;
import data.SQLHelper;
import org.junit.jupiter.api.*;
import page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static data.SQLHelper.cleanAuthCodes;
import static data.SQLHelper.cleanDatabase;

public class BankLoginTest {
    LoginPage loginPage;
    DataHelper.UserInfo userInfo = DataHelper.getAuthInfoWithTestData();

    @AfterAll
    static void clearAllData() {
        cleanDatabase();
    }

    @AfterEach
    void clearAllCodes() {
        cleanAuthCodes();
    }

    @BeforeEach
    void setUp() {
        loginPage = open("http://localhost:9999", LoginPage.class);
    }

    @Test
    @DisplayName("Should successfully login to dashboard with existing login and password from sut test data")
    void shouldLoginSuccessfully() {
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(userInfo);
        verificationPage.validVerify(SQLHelper.getVerificationCode());
    }

    @Test
    @DisplayName("Should get error notification if no such user in database")
    void shouldGetErrorIfUserDoesNotExist() {
        var loginPage = new LoginPage();
        var randomUser = DataHelper.generateRandomUser();
        loginPage.login(randomUser);
        loginPage.verifyErrorNotification("Ошибка! Неверно указан логин или пароль");
    }

    @Test
    @DisplayName("Should get error notification if user exists in database but verification code is random")
    void shouldGetErrorWithRandomVerificationCode() {
        var verificationPage = loginPage.validLogin(userInfo);
        var randomVerificationCode = DataHelper.generateRandomVerificationCode();
        verificationPage.verify(randomVerificationCode.getCode());
        verificationPage.verifyErrorNotification("Ошибка! Неверно указан код! Попробуйте ещё раз.");
    }
}