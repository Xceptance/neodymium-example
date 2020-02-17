package posters.pageobjects.pages.user;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.AllureAddons;

import io.qameta.allure.Step;
import posters.dataobjects.OrderData;
import posters.dataobjects.Product;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;

public class OrderHistoryPage extends AbstractBrowsingPage
{
    private SelenideElement headline = $("#titleOrderHistory");

    @Override
    @Step("ensure this is an order history page")
    public OrderHistoryPage isExpectedPage()
    {
        headline.should(exist);
        return this;
    }

    @Step("validate product is in the order")
    public OrderHistoryPage validateContainsProduct(Product product)
    {
        SelenideElement productContainer = $$(".productInfo").filter((matchText(product.getRowRegex()))).shouldHaveSize(1).first()
                                                             .parent();

        productContainer.find(".productName").shouldBe(exactText(product.getName()));
        productContainer.find(".productStyle").shouldBe(exactText(product.getStyle()));
        productContainer.find(".productSize").shouldBe(exactText(product.getSize()));
        productContainer.find(".orderCount").shouldBe(exactText(Integer.toString(product.getAmount()) + "x"));
        return this;
    }

    @Step("validate order history contains ordered products and order total matches")
    public OrderHistoryPage validateOrder(OrderData orderData)
    {
        AllureAddons.addToReport("order data", orderData);
        orderData.getProducts().getAll().forEach(product -> validateContainsProduct(product));
        $(".orderTotalCosts").shouldHave(exactText(orderData.getOrderTotal()));
        return this;
    }
}
