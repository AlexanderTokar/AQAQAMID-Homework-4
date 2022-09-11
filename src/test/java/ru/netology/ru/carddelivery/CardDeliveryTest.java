package ru.netology.ru.carddelivery;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardDeliveryTest {

    String deliveryDay (int days) {
            return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeEach
    void WebOpen() {
        Configuration.holdBrowserOpen=true;
        open("http://localhost:9999");
    }


    @Test
    void shouldSubmitRequestHappyPath() {
        String deliveryDate = deliveryDay(3);
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Джон Голт");
        $("[data-test-id='phone'] input").setValue("+79109109191");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $x("//*[@data-test-id='notification']").shouldHave(Condition.text("Успешно! Встреча успешно забронирована на " + deliveryDate), Duration.ofSeconds(15));
    }

    @Test
    void shouldSubmitRequestHappyPathDoubleName() {
        String deliveryDate = deliveryDay(3);
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Джон Голт-Иванов");
        $("[data-test-id='phone'] input").setValue("+79109109191");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $x("//*[@data-test-id='notification']").shouldHave(Condition.text("Успешно! Встреча успешно забронирована на " + deliveryDate), Duration.ofSeconds(15));
    }

    @Test
    void shouldSubmitRequestHappyPathLongDate() {
        String deliveryDate = deliveryDay(33);
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Джон Голт-Иванов");
        $("[data-test-id='phone'] input").setValue("+79109109191");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $x("//*[@data-test-id='notification']").shouldHave(Condition.text("Успешно! Встреча успешно забронирована на " + deliveryDate), Duration.ofSeconds(15));
    }

    @Test
    void shouldNotSubmitRequestInvalidCity() {
        String deliveryDate = deliveryDay(3);
        $("[data-test-id='city'] input").setValue("Ухта");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Джон Голт-Иванов");
        $("[data-test-id='phone'] input").setValue("+79109109191");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        String text= $x("//*[@data-test-id='city']").text();
        assertEquals("Доставка в выбранный город недоступна", text);
    }

    @Test
    void shouldNotSubmitRequestNoCity() {
        String deliveryDate = deliveryDay(3);
        $("[data-test-id='city'] input").setValue("");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Джон Голт-Иванов");
        $("[data-test-id='phone'] input").setValue("+79109109191");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        String text= $x("//*[@data-test-id='city']").text();
        assertEquals("Поле обязательно для заполнения", text);
    }

    @Test
    void shouldNotSubmitRequestAbroadCity() {
        String deliveryDate = deliveryDay(3);
        $("[data-test-id='city'] input").setValue("Rome");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Джон Голт-Иванов");
        $("[data-test-id='phone'] input").setValue("+79109109191");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        String text= $x("//*[@data-test-id='city']").text();
        assertEquals("Доставка в выбранный город недоступна", text);
    }

    @Test
    void shouldNotSubmitRequestInvalidDate() {
        String deliveryDate = deliveryDay(2);
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Джон Голт-Иванов");
        $("[data-test-id='phone'] input").setValue("+79109109191");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        String text= $x("//*[@data-test-id='date']").text();
        assertEquals("Заказ на выбранную дату невозможен", text);
    }

    @Test
    void shouldNotSubmitRequestNoDate() {
        String deliveryDate = deliveryDay(2);
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='name'] input").setValue("Джон Голт-Иванов");
        $("[data-test-id='phone'] input").setValue("+79109109191");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        String text= $x("//*[@data-test-id='date']").text();
        assertEquals("Неверно введена дата", text);
    }

    @Test
    void shouldNotSubmitRequestInvalidName() {
        String deliveryDate = deliveryDay(3);
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Джон Голт1");
        $("[data-test-id='phone'] input").setValue("+79109109191");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNotSubmitRequestLatinName() {
        String deliveryDate = deliveryDay(3);
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("John Galt");
        $("[data-test-id='phone'] input").setValue("+79109109191");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNotSubmitRequestInvalidPhone() {
        String deliveryDate = deliveryDay(3);
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Джон Голт");
        $("[data-test-id='phone'] input").setValue("+7910910919");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id='phone']").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNotSubmitRequestNoPhone() {
        String deliveryDate = deliveryDay(3);
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Джон Голт");
        $("[data-test-id='phone'] input").setValue("");
        $("[data-test-id='agreement']").click();
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id='phone']").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotSubmitRequestCheckboxOff() {
        String deliveryDate = deliveryDay(3);
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Джон Голт");
        $("[data-test-id='phone'] input").setValue("+79109109191");
        $("[data-test-id='agreement']");
        $x("//*[contains(text(),'Забронировать')]").click();
        $("[data-test-id='agreement']").shouldNotBe(selected);
    }
}
