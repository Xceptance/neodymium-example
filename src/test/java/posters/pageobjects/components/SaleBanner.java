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
    
    /// ----- validate sale banner ----- ///

    private void validateSaleBanner(String text) 
    {
        $$(".owl-stage").findBy(exactText(Neodymium.localizedText(text))).waitUntil(visible, 9000);
    }
    
    @Step("validate sale banner")
    public void validateStructure()
    {
        validateSaleBanner("header.sale.second");
        validateSaleBanner("header.sale.first");
    }
}