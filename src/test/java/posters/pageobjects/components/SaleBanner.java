package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;

public class SaleBanner extends AbstractComponent
{
    public void isComponentAvailable()
    {
        $("#carousel-sale").should(exist);
    }
    
    /// ----- validate sale banner ----- ///

    private static void validateSaleBanner(String text, int num) 
    {
        $("#carousel-sale>div.carousel-inner>div:nth-child("+num+")>a").shouldHave(exactText(text)).waitUntil(visible, 9000);;
    }
    
    @Step("validate sale banner")
    public static void validateStructure()
    {
        validateSaleBanner(Neodymium.localizedText("header.sale.first"), 1);
        $("#carousel-sale .carousel-control-next").click();
        validateSaleBanner(Neodymium.localizedText("header.sale.second"), 2);
    }
}