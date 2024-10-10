package ru.kirbri.tests;

import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThanOrEqual;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class YandexSearchTest extends TestBase {


    @BeforeEach
    void beforeEach() {
        open("https://ya.ru/");
    }

    @Test
    @DisplayName("Поисковый запрос на ya.ru : selenide должен отдавать не нулевой список результатов")
    @Tag("BLOCKER")
    void successfulNotParametrizedSearchSelenideTest() {
        $("#text").setValue("selenide");
        $(".search3__button").click();
        $(".Distribution-Actions .Distribution-Close").click();
        $$(".serp-item").shouldBe(sizeGreaterThan(0));
    }

    @ValueSource(strings = {
            "qa guru", "rest", "soap", "api"
    })
    @ParameterizedTest(name = "Поисковый запрос на ya.ru : {0} должен отдавать не нулевой список результатов")
    @Tag("BLOCKER")
    void successfulSearchResultShouldNotBeEmptyTest(String searchQuery) {
        $("#text").setValue(searchQuery);
        $(".search3__button").click();
        $(".Distribution-Actions .Distribution-Close").click();
        $$(".serp-item").shouldBe(sizeGreaterThan(5));
    }

    @CsvSource(value = {
            "selenium,selenium.dev",
            "wiki,ru.wikipedia.org",
            "selenide,ru.selenide.org",
    })
    @ParameterizedTest(name = "Поисковый запрос на ya.ru : {0} на странице должна присутствовать ссылка {1}")
    @Tag("BLOCKER")
    void searchResultShouldContainExpectedUrlTest(String searchQuery, String expectedLink) {
        $("#text").setValue(searchQuery);
        $(".search3__button").click();
        $(".Distribution-Actions .Distribution-Close").click();
        $("#search-result").shouldHave(text(expectedLink));
    }

    @CsvFileSource(resources = "/test_data/searchResultShouldContainExpectedUrlWithTestDataTest.csv")
    @ParameterizedTest(name = "Поисковый запрос на ya.ru : {0} на странице должна присутствовать ссылка {1}")
    @Tag("BLOCKER")
    void searchResultShouldContainExpectedUrlWithTestDataTest(String searchQuery, String expectedLink) {
        $("#text").setValue(searchQuery);
        $(".search3__button").click();
        $(".Distribution-Actions .Distribution-Close").click();
        $("#search-result").shouldHave(text(expectedLink));
    }

    @Disabled("Task-34222")
    @ValueSource(strings = {
            "Quality Assurance", "Quality Control", "Software Quality"
    })
    @ParameterizedTest(name = "Поисковый запрос на ya.ru : {0} должен отдавать не нулевой список результатов")
    @Tag("SMOKE")
    void successfulSearchResultShouldBeMore20ResultTest(String searchQuery) {
        $("#text").setValue(searchQuery);
        $(".search3__button").click();
        $$(".serp-item").shouldBe(sizeGreaterThanOrEqual(20));
    }

    @ValueSource(strings = {
            "java", "git", "github", "ci cd"
    })
    @ParameterizedTest(name = "Поисковый запрос на ya.ru : {0} должен отдавать не пустой список картинок")
    @Tag("BLOCKER")
    void successfulSearchPhotoTest(String searchQuery) {
        $("#text").setValue(searchQuery);
        $(".search3__button").click();
        $(".Distribution-Actions .Distribution-Close").click();
        $(".HeaderDesktop-Navigation .HeaderNav-Tab", 2).click();

        String newTabHandle = WebDriverRunner.getWebDriver().getWindowHandles().stream()
                .filter(handle -> !handle.equals(WebDriverRunner.getWebDriver().getWindowHandle()))
                .findFirst()
                .orElseThrow();

        switchTo().window(newTabHandle);
        $(".SerpPage").shouldBe(visible);
        $$(".JustifierRowLayout-Item").shouldHave(sizeGreaterThan(0));
    }
}