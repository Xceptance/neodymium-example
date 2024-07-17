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

public class ReturningCustomerBillingAddressPage extends AbstractCheckoutPage
{
    private SelenideElement title = $("#title-bill-addr");
    
    private SelenideElement addBillingAddressButton = $(".form-group .btn");
    
    private SelenideElement useBillingAddressButton = $("#btn-use-bill-address");

    @Override
    @Step("ensure this is a billing address page")
    public ReturningCustomerBillingAddressPage isExpectedPage()
    {
        $("#bill-addr-0").should(exist);
        return this;
    }
    
    /// ========== validate content returning customer billing address page ========== ///

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
    @Step("validate returning customer billing address page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // validate process wrap
        validateProcessWrap();
        
        // validate title
        title.shouldHave(exactText(Neodymium.localizedText("returningCustomerBillingAddressPage.title"))).shouldBe(visible);
        
        // validate first address
        $("#bill-addr-0").shouldBe(visible);
        
        // validate add new billing address button
        addBillingAddressButton.shouldHave(exactText(Neodymium.localizedText("button.addNewBillingAddress"))).shouldBe(visible);
        
        // validate continue button
        useBillingAddressButton.shouldHave(exactText(Neodymium.localizedText("button.useThisBillingAddress"))).shouldBe(visible);
    }
    
    @Step("validate billing address '{billingAddress}' in address container")
    public void validateAddressContainer(Address billingAddress) 
    {
        // selector for product
        final SelenideElement addressContainer = $$(".thumbnail").shouldHave(sizeGreaterThan(0))
                                                                 .findBy(new WebElementCondition("has full name " + billingAddress.getFullName() + ", company " + billingAddress.getCompany()
                                                                                                 + ", street " + billingAddress.getStreet() + ", city" + billingAddress.getCity()
                                                                                                 + ", state " + billingAddress.getState() + ", zip " + billingAddress.getZip()
                                                                                                 + " and country " + billingAddress.getCountry())
                                                                 {
                                                                     @Override
                                                                     public CheckResult check(Driver driver, WebElement element)
                                                                     {
                                                                         boolean matchesName = element.findElement(By.cssSelector(".name")).getText()
                                                                                                      .equals(billingAddress.getFullName());
                                                                         boolean matchesCompany = element.findElement(By.cssSelector(".company")).getText()
                                                                                                         .equals(billingAddress.getCompany());
                                                                         boolean matchesStreet = element.findElement(By.cssSelector(".address-line")).getText()
                                                                                                        .equals(billingAddress.getStreet());
                                                                         boolean matchesCity = element.findElement(By.cssSelector(".city")).getText()
                                                                                                      .equals(billingAddress.getCity());
                                                                         boolean matchesState = element.findElement(By.cssSelector(".state")).getText()
                                                                                                       .equals(billingAddress.getState());
                                                                         boolean matchesZip = element.findElement(By.cssSelector(".zip")).getText()
                                                                                                     .equals(billingAddress.getZip());
                                                                         boolean matchesCountry = element.findElement(By.cssSelector(".country")).getText()
                                                                                                         .equals(billingAddress.getCountry());
                                                                         return new CheckResult(matchesName && matchesCompany && matchesStreet && matchesCity && matchesState && matchesZip && matchesCountry, "");
                                                                     }
                                                                 }).shouldBe(visible, Duration.ofMillis(9000));
        
        // validate address data
        addressContainer.find(".name").shouldHave(exactText(billingAddress.getFullName())).shouldBe(visible);
        addressContainer.find(".company").shouldHave(exactText(billingAddress.getCompany())).shouldBe(visible);
        addressContainer.find(".address-line").shouldHave(exactText(billingAddress.getStreet())).shouldBe(visible);
        addressContainer.find(".city").shouldHave(exactText(billingAddress.getCity())).shouldBe(visible);
        addressContainer.find(".state").shouldHave(exactText(billingAddress.getState())).shouldBe(visible);
        addressContainer.find(".zip").shouldHave(exactText(billingAddress.getZip())).shouldBe(visible);
        addressContainer.find(".country").shouldHave(exactText(billingAddress.getCountry())).shouldBe(visible);  
    }

    /// ========== select billing address ========== ///
    
    @Step("select the billing address '{billingAddress}'")
    public ReturningCustomerPaymentPage selectBillingAddress(Address billingAddress) 
    {
        // selector for product
        final SelenideElement addressContainer = $$(".thumbnail").shouldHave(sizeGreaterThan(0))
                                                                 .findBy(new WebElementCondition("has full name " + billingAddress.getFullName() + ", company " + billingAddress.getCompany()
                                                                                                 + ", street " + billingAddress.getStreet() + ", city" + billingAddress.getCity()
                                                                                                 + ", state " + billingAddress.getState() + ", zip " + billingAddress.getZip()
                                                                                                 + " and country " + billingAddress.getCountry())
                                                                 {
                                                                     @Override
                                                                     public CheckResult check(Driver driver, WebElement element)
                                                                     {
                                                                         boolean matchesName = element.findElement(By.cssSelector(".name")).getText()
                                                                                                      .equals(billingAddress.getFullName());
                                                                         boolean matchesCompany = element.findElement(By.cssSelector(".company")).getText()
                                                                                                         .equals(billingAddress.getCompany());
                                                                         boolean matchesStreet = element.findElement(By.cssSelector(".address-line")).getText()
                                                                                                        .equals(billingAddress.getStreet());
                                                                         boolean matchesCity = element.findElement(By.cssSelector(".city")).getText()
                                                                                                      .equals(billingAddress.getCity());
                                                                         boolean matchesState = element.findElement(By.cssSelector(".state")).getText()
                                                                                                       .equals(billingAddress.getState());
                                                                         boolean matchesZip = element.findElement(By.cssSelector(".zip")).getText()
                                                                                                     .equals(billingAddress.getZip());
                                                                         boolean matchesCountry = element.findElement(By.cssSelector(".country")).getText()
                                                                                                         .equals(billingAddress.getCountry());
                                                                         return new CheckResult(matchesName && matchesCompany && matchesStreet && matchesCity && matchesState && matchesZip && matchesCountry, "");
                                                                     }
                                                                 }).shouldBe(visible, Duration.ofMillis(9000));
        
        addressContainer.find("input").click(ClickOptions.usingJavaScript());
        useBillingAddressButton.click(ClickOptions.usingJavaScript());
        
        return new ReturningCustomerPaymentPage().isExpectedPage();
    }
}