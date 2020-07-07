package ru.netology.carddelivery;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

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
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Новосибирск");
        form.$("[data-test-id=date] input").doubleClick();
        form.$("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id=date] input").setValue(GetOrderDate(daysDiff));
        form.$("[data-test-id=name] input").setValue("Иванов Петр Петрович");
        form.$("[data-test-id=phone] input").setValue("+79099099090");
        form.$("[data-test-id=agreement]").click();
//        form.$("button[type=button]").click();
        form.$(".grid-col .form-field.form-field_size_m").click();
//        form.$(".button__content .spin").should(disappear);
        $(withText("Успешно!")).shouldBe(visible);
        $(byText("Встреча успешно запланирована на "
                + GetOrderDate(daysDiff))).shouldBe(visible);
//        form.$("[data-test-id=notification] .notification__title").should(appears);
//        form.$("[data-test-id=notification] .notification__title").shouldHave(text("Успешно!"));

        $(".notification .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на "
                         + GetOrderDate(daysDiff)));
    }
}
