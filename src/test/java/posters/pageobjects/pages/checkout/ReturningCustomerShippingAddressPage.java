package posters.pageobjects.pages.checkout;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.codeborne.selenide.CheckResult;
import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.Driver;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebElementCondition;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.tests.testdata.dataobjects.Address;

public class ReturningCustomerShippingAddressPage extends AbstractCheckoutPage
{
    private SelenideElement title = $("#title-del-addr");
    
    private SelenideElement addShippingAddressButton = $(".form-group .btn");
    
    private SelenideElement useShippingAddressButton = $("#btn-use-address-continue");

    @Override
    @Step("ensure this is a shipping address page")
    public ReturningCustomerShippingAddressPage isExpectedPage()
    {
        super.isExpectedPage();
        $("#del-addr-0").should(exist);
        return this;
    }

    /// ========== validate content returning customer shipping address page ========== ///
    
    @Step("validate process wrap")
    public void validateProcessWrap() 
    {
        for (int i = 1; i <= 6; i++) 
        {
            $(".progress-step-" + i + " .progress-bubble").shouldHave(exactText(Neodymium.localizedText("checkoutHeader." + i + ".number"))).shouldBe(visible);
            $(".progress-step-" + i + " .progress-bubble-caption").shouldHave(exactText(Neodymium.localizedText("checkoutHeader." + i + ".name"))).shouldBe(visible);         
        }
    }
    
    @Override
    @Step("validate returning customer shipping address page structure")
    public void validateStructure()
    {
        super.validateStructure();
        
        // validate process wrap
        validateProcessWrap();
        
        // validate title
        title.shouldHave(exactText(Neodymium.localizedText("returningCustomerShippingAddressPage.title"))).shouldBe(visible);
        
        // validate first address
        $("#del-addr-0").shouldBe(visible);
        
        // validate add new shipping address button
        addShippingAddressButton.shouldHave(exactText(Neodymium.localizedText("button.addNewShippingAddress"))).shouldBe(visible);
        
        // validate continue button
        useShippingAddressButton.shouldHave(exactText(Neodymium.localizedText("button.continue"))).shouldBe(visible);
    }
    
    @Step("validate shipping address '{shippingAddress}' in address container")
    public void validateAddressContainer(Address shippingAddress) 
    {
        // selector for product
        final SelenideElement addressContainer = $$(".thumbnail").shouldHave(sizeGreaterThan(0))
                                                                 .findBy(new WebElementCondition("has full name " + shippingAddress.getFullName() + ", company " + shippingAddress.getCompany()
                                                                                                 + ", street " + shippingAddress.getStreet() + ", city" + shippingAddress.getCity()
                                                                                                 + ", state " + shippingAddress.getState() + ", zip " + shippingAddress.getZip()
                                                                                                 + " and country " + shippingAddress.getCountry())
                                                                 {
                                                                     @Override
                                                                     public CheckResult check(Driver driver, WebElement element)
                                                                     {
                                                                         boolean matchesName = element.findElement(By.cssSelector(".name")).getText()
                                                                                                      .equals(shippingAddress.getFullName());
                                                                         boolean matchesCompany = element.findElement(By.cssSelector(".company")).getText()
                                                                                                         .equals(shippingAddress.getCompany());
                                                                         boolean matchesStreet = element.findElement(By.cssSelector(".address-line")).getText()
                                                                                                        .equals(shippingAddress.getStreet());
                                                                         boolean matchesCity = element.findElement(By.cssSelector(".city")).getText()
                                                                                                      .equals(shippingAddress.getCity());
                                                                         boolean matchesState = element.findElement(By.cssSelector(".state")).getText()
                                                                                                       .equals(shippingAddress.getState());
                                                                         boolean matchesZip = element.findElement(By.cssSelector(".zip")).getText()
                                                                                                     .equals(shippingAddress.getZip());
                                                                         boolean matchesCountry = element.findElement(By.cssSelector(".country")).getText()
                                                                                                         .equals(shippingAddress.getCountry());
                                                                         return new CheckResult(matchesName && matchesCompany && matchesStreet && matchesCity && matchesState && matchesZip && matchesCountry, "");
                                                                     }
                                                                 }).shouldBe(visible, Duration.ofMillis(9000));
        
        // validate address data
        addressContainer.find(".name").shouldHave(exactText(shippingAddress.getFullName())).shouldBe(visible);
        addressContainer.find(".company").shouldHave(exactText(shippingAddress.getCompany())).shouldBe(visible);
        addressContainer.find(".address-line").shouldHave(exactText(shippingAddress.getStreet())).shouldBe(visible);
        addressContainer.find(".city").shouldHave(exactText(shippingAddress.getCity())).shouldBe(visible);
        addressContainer.find(".state").shouldHave(exactText(shippingAddress.getState())).shouldBe(visible);
        addressContainer.find(".zip").shouldHave(exactText(shippingAddress.getZip())).shouldBe(visible);
        addressContainer.find(".country").shouldHave(exactText(shippingAddress.getCountry())).shouldBe(visible);  
    }

    /// ========== select shipping address ========== ///

    @Step("select the shipping address '{shippingAddress}'")
    public ReturningCustomerBillingAddressPage selectShippingAddress(Address shippingAddress) 
    {
        // selector for product
        final SelenideElement addressContainer = $$(".thumbnail").shouldHave(sizeGreaterThan(0))
                                                                 .findBy(new WebElementCondition("has full name " + shippingAddress.getFullName() + ", company " + shippingAddress.getCompany()
                                                                                                 + ", street " + shippingAddress.getStreet() + ", city" + shippingAddress.getCity()
                                                                                                 + ", state " + shippingAddress.getState() + ", zip " + shippingAddress.getZip()
                                                                                                 + " and country " + shippingAddress.getCountry())
                                                                 {
                                                                     @Override
                                                                     public CheckResult check(Driver driver, WebElement element)
                                                                     {
                                                                         boolean matchesName = element.findElement(By.cssSelector(".name")).getText()
                                                                                                      .equals(shippingAddress.getFullName());
                                                                         boolean matchesCompany = element.findElement(By.cssSelector(".company")).getText()
                                                                                                         .equals(shippingAddress.getCompany());
                                                                         boolean matchesStreet = element.findElement(By.cssSelector(".address-line")).getText()
                                                                                                        .equals(shippingAddress.getStreet());
                                                                         boolean matchesCity = element.findElement(By.cssSelector(".city")).getText()
                                                                                                      .equals(shippingAddress.getCity());
                                                                         boolean matchesState = element.findElement(By.cssSelector(".state")).getText()
                                                                                                       .equals(shippingAddress.getState());
                                                                         boolean matchesZip = element.findElement(By.cssSelector(".zip")).getText()
                                                                                                     .equals(shippingAddress.getZip());
                                                                         boolean matchesCountry = element.findElement(By.cssSelector(".country")).getText()
                                                                                                         .equals(shippingAddress.getCountry());
                                                                         return new CheckResult(matchesName && matchesCompany && matchesStreet && matchesCity && matchesState && matchesZip && matchesCountry, "");
                                                                     }
                                                                 }).shouldBe(visible, Duration.ofMillis(9000));
        
        addressContainer.find("input").click(ClickOptions.usingJavaScript());
        useShippingAddressButton.click(ClickOptions.usingJavaScript());
        
        return new ReturningCustomerBillingAddressPage().isExpectedPage();
    }
}
