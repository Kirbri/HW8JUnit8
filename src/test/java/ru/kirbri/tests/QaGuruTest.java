package ru.kirbri.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class QaGuruTest extends TestBase{
    @BeforeEach
    void beforeEach() {
        open("https://qa.guru/");
    }

        static Stream<Arguments> qaGuruSiteShouldCorrectCourseTest() {
        return Stream.of(
                Arguments.of("java", "Java"),
                Arguments.of("python", "Python"),
                Arguments.of("java_advanced", "Java Advanced 2.0"),
                Arguments.of("python_advanced", "Python Advanced"),
                Arguments.of("chatgpt", "ChatGPT")
        );
    }

    @Tags({
        @Tag("WEB"),
        @Tag("Главная страница")
            })
    @MethodSource
    @ParameterizedTest
    void qaGuruSiteShouldCorrectCourseTest(String expectedUrl, String expectedText) {
        String checkText;
        if (expectedUrl.equals("java") || expectedUrl.equals("python")) {
            checkText = "Курс по автоматизации тестирования на " + expectedText;
        } else if (expectedUrl.equals("java_advanced") || expectedUrl.equals("python_advanced")) {
            checkText = "Продвинутый курс по автоматизации\n" +
                    "тестирования " + expectedText;
        } else {
            checkText = "Курс-интенсив\n" +
                    "Автоматизация тестов с " + expectedText;
        }
        $$(".t-menu__link-item.t-menusub__target-link").get(1).hover();
        $("[href=\"https://qa.guru/" + expectedUrl + "\"]").click();
        $(".t995__inner-wrapper").shouldHave(text(checkText));
    }
}