package ru.netology.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.UserData;
import ru.netology.data.UserGeneratorData;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class UserRegistrationTest {

    @BeforeEach
    void SetUp() {
        open("http://localhost:9999");

    }

    @Test
    void mustLogInToYourPersonalAccount() {
        UserData user = UserGeneratorData.validActive();

        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $("button[data-test-id=action-login]").click();
        $("h2.heading.heading_size_l.heading_theme_alfa-on-white")
                .shouldHave(text("Личный кабинет"))
                .shouldHave(visible);

    }
    @Test
    void theUserMustBeBlocked() {
        UserData user = UserGeneratorData.validBlocked();

        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $("button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]")
                .shouldHave(text("Пользователь заблокирован"))
                .shouldHave(visible);

    }

    @Test
    void warningMessageShouldAppearIfAnIncorrectPasswordIsEntered() {
        UserData user = UserGeneratorData.invalidPassword();

        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $("button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]")
                .shouldHave(text("Ошибка!"))
                .shouldHave(text("Неверно указан логин или пароль"))
                .shouldHave(visible);

    }

    @Test
    void warningMessageShouldAppearIfAnIncorrectUsernameIsEntered() {
        UserData user = UserGeneratorData.invalidLogin();

        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $("button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]")
                .shouldHave(text("Ошибка!"))
                .shouldHave(text("Неверно указан логин или пароль"))
                .shouldHave(visible);

    }

    @Test
    void warningMessageShouldAppearIfThePasswordFieldIsNotFilledIn() {
        UserData user = UserGeneratorData.validActive();

        $("[data-test-id=login] input").setValue(user.getLogin());
        $("button[data-test-id=action-login]").click();
        $("[data-test-id=password]")
                .shouldHave(text("Поле обязательно для заполнения"))
                .shouldHave(visible);
    }
    @Test
    void warningMessageShouldAppearIfTheLoginFieldIsNotFilledIn() {
        UserData user = UserGeneratorData.validActive();

        $("[data-test-id=password] input").setValue(user.getPassword());
        $("button[data-test-id=action-login]").click();
        $("[data-test-id=login]")
                .shouldHave(text("Поле обязательно для заполнения"))
                .shouldHave(visible);

    }
}