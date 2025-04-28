package posters.pageobjects.components;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;
import com.xceptance.neodymium.util.SelenideAddons;
import io.qameta.allure.Step;

import java.time.Duration;

import org.junit.Assert;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class SaleBanner extends AbstractComponent
{

    private SelenideElement saleCarousel = $("#carousel-sale");

    @Override
    @Step("check availability of sale banner")
    public void assertComponentAvailable()
    {
        Assert.assertTrue(SelenideAddons.optionalWaitUntilCondition(saleCarousel, exist));
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
