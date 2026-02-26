package page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private final SelenideElement codeField = $("[data-test-id=code] input");

    public VerificationPage() {
        codeField.shouldBe(visible);
    }

    public void verify(String verificationCode) {
        codeField.setValue(verificationCode);
        $("[data-test-id=action-verify]").click();
    }

    public DashboardPage validVerify(String verificationCode) {
        verify(verificationCode);
        return new DashboardPage();
    }

    public void verifyErrorNotification(String expectedText) {
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(exactText(expectedText))
                .shouldBe(visible);
    }
}
