package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;

public class Footer extends AbstractComponent
{
    private SelenideElement footer = $("#footer");
    
    private SelenideElement aboutSection = $("#footer-section-about");
    
    private SelenideElement customerCareSection = $("#footer-section-support");
    
    private SelenideElement informationSection = $("#footer-section-information");
    
    private SelenideElement contactInformation = $("#footer-section-contact");

    @Override
    @Step("ensure availability footer")
    public void isComponentAvailable()
    {
        footer.should(exist);
    }
    
    @Step("validate about us section")
    private void validateAboutSection() 
    {
        aboutSection.find(".footer-section-headline").shouldHave(exactText(Neodymium.localizedText("footer.1.headline"))).shouldBe(visible);
        aboutSection.find(".footer-text-about").shouldHave(exactText(Neodymium.localizedText("footer.1.description"))).shouldBe(visible);
        aboutSection.find(".icon-github").shouldBe(visible);
    }
    
    @Step("validate customer care section")
    private void validateCustomerCareSection() 
    {
        customerCareSection.find(".footer-section-headline").shouldHave(exactText(Neodymium.localizedText("footer.2.headline"))).shouldBe(visible);
        customerCareSection.find(".footer-support").shouldHave(exactText(Neodymium.localizedText("footer.2.description"))).shouldBe(visible);
    }
    
    @Step("validate information section")
    private void validateInformationSection() 
    {
        informationSection.find(".footer-section-headline").shouldHave(exactText(Neodymium.localizedText("footer.3.headline"))).shouldBe(visible);
        informationSection.find(".footer-info").shouldHave(exactText(Neodymium.localizedText("footer.3.description"))).shouldBe(visible);       
    }
    
    @Step("validate contact information section")
    private void validateContactInformationSection() 
    {
        contactInformation.find(".footer-section-headline").shouldHave(exactText(Neodymium.localizedText("footer.4.headline"))).shouldBe(visible);
        contactInformation.find(".footer-contact-company").shouldHave(exactText(Neodymium.localizedText("footer.4.company"))).shouldBe(visible);
        contactInformation.find(".footer-contact-adress").shouldHave(text(Neodymium.localizedText("footer.4.street"))).shouldBe(visible);
        contactInformation.find(".footer-contact-adress").shouldHave(text(Neodymium.localizedText("footer.4.zip"))).shouldBe(visible);
        contactInformation.find(".footer-contact-adress").shouldHave(text(Neodymium.localizedText("footer.4.country"))).shouldBe(visible);
        contactInformation.find(".footer-contact-phone").shouldHave(exactText(Neodymium.localizedText("footer.4.phone"))).shouldBe(visible);
        contactInformation.find(".footer-contact-e-mail").shouldHave(exactText(Neodymium.localizedText("footer.4.email"))).shouldBe(visible);
        contactInformation.find(".footer-contact-website").shouldHave(exactText(Neodymium.localizedText("footer.4.website"))).shouldBe(visible);       
    }
    
    @Step("validate footer")
    public void validateStructure()
    {
        // validate about section
        validateAboutSection();
        
        // validate customer care section
        validateCustomerCareSection();
        
        // validate information section
        validateInformationSection();
        
        // validate contact information section
        validateContactInformationSection();
        
        // validate copyright text
        $("#footer-copyright").shouldHave(text(Neodymium.localizedText("footer.copyright")));
        
        // validate photo license
        $("#footer-copyright").shouldHave(text(Neodymium.localizedText("footer.photoLicense")));
        
        // validate version
        $("#footer-version-information").shouldHave(exactText(Neodymium.localizedText("footer.version")));        
    }
}
