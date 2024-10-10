package ru.kirbri.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import ru.kirbri.tests.data.Language;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class SelenideOrgTest extends TestBase{
    @BeforeEach
    void beforeEach() {
        open("https://selenide.org/");
    }

//    public static void main(String[] args) {
//        System.out.println(Language.RU.getDecription());
//        System.out.println(Language.RU);
//        System.out.println(Language.EN.getDecription());
//        System.out.println(Language.EN);
//    }

    @Tag("WEB")
    @EnumSource(Language.class)
    @ParameterizedTest
    void selenideSiteShouldDisplayCorrectTest(Language language) {
        $$("#languages a").find(text(language.name())).click();
        $(".wrapper-color-content h3").shouldHave(text(language.getDecription()));
    }

    static Stream<Arguments> selenideSiteShouldDisplayCorrectButtonsTest() {
        return Stream.of(
                Arguments.of(
                        Language.EN,
                        List.of("Quick start", "Docs", "FAQ", "Blog", "Javadoc", "Users", "Quotes")),
                Arguments.of(
                        Language.RU,
                        List.of("С чего начать?", "Док", "ЧАВО", "Блог", "Javadoc", "Пользователи", "Отзывы"))

        );
    }

    @Tag("WEB")
    @MethodSource
    @ParameterizedTest
    void selenideSiteShouldDisplayCorrectButtonsTest(Language language, List<String> expectedButtons) {
        $$("#languages a").find(text(language.name())).click();
        $$(".main-menu-pages a").filter(visible).shouldHave(texts(expectedButtons));
        $(".wrapper-color-content h3").shouldHave(text(language.getDecription()));
    }
}