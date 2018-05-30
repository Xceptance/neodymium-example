package posters.pageobjects.pages.user;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;

/**
 * @author pfotenhauer
 */
public class AccountOverviewPage extends AbstractBrowsingPage
{
    private SelenideElement headline = $("#titleAccountOverview");

    private SelenideElement personalDataLink = $("#linkPersonalData");

    @Override
    @Step("ensure this is an account overview page")
    public void isExpectedPage()
    {
        headline.should(exist);
    }

    @Override
    @Step("validate account overview page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // Headline
        // Make sure the headline is there and start with a capital letter
        headline.should(matchText("[A-Z].{3,}"));
        // Order Overview Link
        // Make sure the link to the order history is there and the text starts with a capital letter
        $("#linkOrderOverview").should(matchText("[A-Z].{3,}"));
        // My Addresses Link
        // Make sure the link to the Addresses page is there and the text starts with a capital letter
        $("#linkAddressOverview").should(matchText("[A-Z].{3,}"));
        // Payment Settings Link
        // Make sure the link to the Credit Cards page is there and the text starts with a capital letter
        $("#linkPaymentOverview").should(matchText("[A-Z].{3,}"));
        // Personal Data Link
        // Make sure the link to the Personal Data page is there and the text starts with a capital letter
        personalDataLink.should(matchText("[A-Z].{3,}"));
    }

    /**
     * @return
     */
    @Step("open personal data page")
    public PersonalDataPage openPersonalData()
    {
        // Open the personal data page
        // Click on the link to Personal Data
        personalDataLink.scrollTo().click();
        return new PersonalDataPage();
    }

    public OrderHistoryPage openOrderHistory()
    {
        $("#linkOrderOverview").scrollTo().click();
        return new OrderHistoryPage();
    }
}
