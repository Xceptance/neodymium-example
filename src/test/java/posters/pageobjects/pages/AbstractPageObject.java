package posters.pageobjects.pages;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import org.apache.commons.lang3.StringUtils;

import com.xceptance.neodymium.util.Neodymium;
import com.xceptance.neodymium.visual.ai.AI;

import io.qameta.allure.Step;
import posters.pageobjects.components.Title;

public abstract class AbstractPageObject
{
    public Title title = new Title();

    public AbstractPageObject isExpectedPage()
    {
        return this;
    }

    abstract public void validateStructure();

    public void validateVisual(String testCaseName)
    {
        String className = this.getClass().getSimpleName();

        if (StringUtils.isAllEmpty(testCaseName))
        {
            new AI().execute(Neodymium.getDriver(), className, "validate" + className);
        }
        else
        {
            new AI().execute(Neodymium.getDriver(), testCaseName, "validate" + className);
        }
    }

    public void validateStructureAndVisual()
    {
        validateStructureAndVisual(null);
    }

    public void validateStructureAndVisual(String testCaseName)
    {
        validateStructure();
        validateVisual(testCaseName);
    }

    public void scrollToTop()
    {
        $("body").scrollTo();
    }
    
    /// ----- ShippingAddressPage, BillingAddressPage, PaymentPage, PlaceOrderPage, OrderConfirmationPage ----- ///
    
    @Step("validate process wrap")
    public void validateProcessWrap() 
    {
        // validate process numbers
        $("#crt span").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.1.number"))).shouldBe(visible);
        $("#ship span").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.2.number"))).shouldBe(visible);
        $("#bill span").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.3.number"))).shouldBe(visible);
        $("#payment span").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.4.number"))).shouldBe(visible);
        $("#chkout span").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.5.number"))).shouldBe(visible);
        $("#orderCmplt span").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.6.number"))).shouldBe(visible);
        
        // validate process names
        $("#crt h3").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.1.name"))).shouldBe(visible);
        $("#ship h3").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.2.name"))).shouldBe(visible);
        $("#bill h3").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.3.name"))).shouldBe(visible);
        $("#payment h3").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.4.name"))).shouldBe(visible);
        $("#chkout h3").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.5.name"))).shouldBe(visible);
        $("#orderCmplt h3").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.6.name"))).shouldBe(visible);
    }
}
