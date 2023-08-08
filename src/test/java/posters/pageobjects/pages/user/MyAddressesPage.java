package posters.pageobjects.pages.user;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;

public class MyAddressesPage extends AbstractBrowsingPage
{
//    private SelenideElement headline = $("#titlePersonalData");
//
//    private SelenideElement deleteButton = $("#btnDeleteAccount");

    @Override
    @Step("ensure this is a personal data page")
    public MyAddressesPage isExpectedPage()
    {
//        super.isExpectedPage();
//        headline.should(exist);
        return this;
    }

    /// ----- validate my addresses page ----- ///
    
    @Override
    @Step("validate personal data page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // TODO
    }
}