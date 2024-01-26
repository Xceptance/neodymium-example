package posters.pageobjects.pages.user;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;

public class OrderHistoryPage extends AbstractBrowsingPage
{
    private SelenideElement title = $("#titleOrderHistory");
    
    private ElementsCollection tableHead = $$(".product-name span");

    @Override
    @Step("ensure this is an order history page")
    public OrderHistoryPage isExpectedPage()
    {
        super.isExpectedPage();
        title.should(exist);
        return this;
    }
    
    /// ========== validate content order history page ========== ///
    
    @Override
    @Step("validate personal data page structure")
    public void validateStructure()
    {
        super.validateStructure();
        
        // validate title
        title.shouldHave(exactText(Neodymium.localizedText("orderHistoryPage.title"))).shouldBe(visible);
        
        // validate table Head
        tableHead.findBy(exactText(Neodymium.localizedText("orderHistoryPage.purchasedPosters"))).shouldBe(visible);
        tableHead.findBy(exactText(Neodymium.localizedText("orderHistoryPage.orderDetails"))).shouldBe(visible);
        tableHead.findBy(exactText(Neodymium.localizedText("product.quantity"))).shouldBe(visible);
    }
}