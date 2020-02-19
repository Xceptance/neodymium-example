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

    private SelenideElement orderOverviewLink = $("#linkOrderOverview");

    private SelenideElement myAddressLink = $("#linkAddressOverview");

    private SelenideElement paymentLink = $("#linkPaymentOverview");

    @Override
    @Step("ensure this is an account overview page")
    public AccountOverviewPage isExpectedPage()
    {
        headline.should(exist);
        return this;
    }

    @Override
    @Step("validate account overview page structure")
    public AccountOverviewPage validateStructure()
    {
        super.validateStructure();

        // Headline
        // Make sure the headline is there and start with a capital letter
        headline.should(matchText("[A-Z].{3,}"));
        // Order Overview Link
        // Make sure the link to the order history is there and the text starts with a capital letter
        orderOverviewLink.should(matchText("[A-Z].{3,}"));
        // My Addresses Link
        // Make sure the link to the Addresses page is there and the text starts with a capital letter
        myAddressLink.should(matchText("[A-Z].{3,}"));
        // Payment Settings Link
        // Make sure the link to the Credit Cards page is there and the text starts with a capital letter
        paymentLink.should(matchText("[A-Z].{3,}"));
        // Personal Data Link
        // Make sure the link to the Personal Data page is there and the text starts with a capital letter
        personalDataLink.should(matchText("[A-Z].{3,}"));
        return this;
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
        PersonalDataPage personalDataPage = new PersonalDataPage();
        personalDataPage.isExpectedPage();
        return personalDataPage;
    }

    @Step("open order history")
    public OrderHistoryPage openOrderHistory()
    {
        orderOverviewLink.scrollTo().click();
        OrderHistoryPage orderHistoryPage = new OrderHistoryPage();
        orderHistoryPage.isExpectedPage();
        return orderHistoryPage;
    }

    @Step("open my address page")
    public MyAddressesPage openMyAddressesPage()
    {
        myAddressLink.click();
        MyAddressesPage myAddressesPage = new MyAddressesPage();
        myAddressesPage.isExpectedPage();
        return myAddressesPage;
    }
}
