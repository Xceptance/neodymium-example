package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import java.time.Duration;

import com.codeborne.selenide.ClickOptions;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;

public class SaleBanner extends AbstractComponent
{
    @Override
    public void isComponentAvailable()
    {
        $("#carousel-sale").should(exist);
    }

    private void validateSaleBanner(String text)
    {
        $$(".carousel-content-text").findBy(exactText(text)).shouldBe(visible, Duration.ofMillis(9000));
    }

    @Step("validate sale banner")
    public void validateStructure()
    {
        validateSaleBanner(Neodymium.localizedText("header.sale.first"));
        $("#carousel-sale .carousel-control-next").click(ClickOptions.usingJavaScript());
        validateSaleBanner(Neodymium.localizedText("header.sale.second"));
    }
}