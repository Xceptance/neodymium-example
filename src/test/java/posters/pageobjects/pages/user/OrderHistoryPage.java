package posters.pageobjects.pages.user;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;

public class OrderHistoryPage extends AbstractBrowsingPage
{
    private SelenideElement headline = $("#titleOrderHistory");

    @Override
    @Step("ensure this is an order history page")
    public OrderHistoryPage isExpectedPage()
    {
        super.isExpectedPage();
        headline.should(exist);
        return this;
    }
}
