package ru.netology.carddelivery;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryFormTest {
    private int daysDiff = 4;

    private String GetOrderDate(int daysDiff) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return dateFormat.format(LocalDateTime.now().plusDays(daysDiff));
    }

    @Test
    public void shouldCreditCardDeliveryOrder() {

        open("http://localhost:9999");

        SelenideElement element = $("form");
        element.$("[data-test-id=city] input").setValue("Новосибирск");
        element.$("[data-test-id=date] input").doubleClick();
        element.$("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        element.$("[data-test-id=date] input").setValue(GetOrderDate(daysDiff));
        element.$("[data-test-id=name] input").setValue("Иванов Петр Петрович");
        element.$("[data-test-id=phone] input").setValue("+79099099090");
        element.$("[data-test-id=agreement]").click();
        element.$$("button").find(exactText("Забронировать")).click();

        $(withText("Успешно!")).waitUntil(visible, 15000);
        $(byText("Встреча успешно забронирована на")).waitUntil(visible, 15000);
        $(byText(GetOrderDate(daysDiff))).waitUntil(visible, 15000);
    }
}
