package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;

public class SaleBanner extends AbstractComponent
{
    public void isComponentAvailable()
    {
        $(".sale").should(exist);
    }

    @Step("validate strings sale banner")
    private static void validateSaleBanner(String text) 
    {
        $$(".owl-stage").findBy(exactText(Neodymium.localizedText(text))).waitUntil(visible, 6500);
    }
    
    @Step("validate sale banner")
    public static void validateStructure()
    {
        validateSaleBanner("header.sale.second");
        validateSaleBanner("header.sale.first");
    }
}