package posters.pageobjects.pages.user;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;
import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;
import posters.pageobjects.pages.browsing.HomePage;
import posters.tests.testdata.dataobjects.Order;
import posters.tests.testdata.dataobjects.Product;

import java.time.LocalDate;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class OrderHistoryPage extends AbstractBrowsingPage
{
    private SelenideElement title = $("#title-order-history");

    private SelenideElement goBackButton = $(".btn-sm");

    @Override
    @Step("ensure this is an order history page")
    public OrderHistoryPage assertExpectedPage()
    {
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
        $("#order-content-col").shouldHave(exactText(Neodymium.localizedText("orderHistoryPage.purchasedPosters"))).shouldBe(visible);
        $("#order-details-col").shouldHave(exactText(Neodymium.localizedText("orderHistoryPage.orderDetails"))).shouldBe(visible);
        $("#order-quantity-col").shouldHave(exactText(Neodymium.localizedText("product.quantity"))).shouldBe(visible);

        // validate button
        goBackButton.shouldHave(exactText(Neodymium.localizedText("button.back"))).shouldBe(visible);
    }

    @Step("validate order")
    public void validateOrder(Order order, String totalOrderPrice)
    {
        ElementsCollection productNames = $$(".product-name");

        for (Product product : order.getProducts())
        {
            SelenideElement productContainer = productNames.findBy(exactText(product.getName())).closest(".order-product");
            SelenideElement productIndex = productContainer.find(".prod-index");

            if (!productIndex.text().isEmpty())
            {
                productContainer.find("#order-detail-date").shouldHave(exactText(Neodymium.localizedText("orderHistoryPage.orderDate"))).shouldBe(visible);
                productContainer.find("#order-detail-date-value").shouldHave(exactText(LocalDate.now().toString())).shouldBe(visible);
                productContainer.find("#order-detail-total").shouldHave(exactText(Neodymium.localizedText("orderHistoryPage.orderTotal"))).shouldBe(visible);
                productContainer.find("#order-detail-total-value").shouldHave(exactText(totalOrderPrice)).shouldBe(visible);
            }
            else
            {
                productContainer.find("#order-detail-date").shouldNot(exist);
                productContainer.find("#order-detail-date-value").shouldNot(exist);
                productContainer.find("#order-detail-total").shouldNot(exist);
                productContainer.find("#order-detail-total-value").shouldNot(exist);
            }

            productContainer.find(".img-thumbnail").shouldBe(visible);
            productContainer.find(".product-name").shouldHave(exactText(product.getName()));
            productContainer.find(".product-style").should(matchText(product.getStyle()));
            productContainer.find(".product-size").should(matchText(product.getSize()));
            productContainer.find(".prod-quantity").shouldHave(exactText(Integer.toString(product.getAmount()) + "x"));
        }
    }

    /// ========== order history page navigation ========== ///

    @Step("open homepage")
    public HomePage openHomePage()
    {
        $("#header-brand").click(ClickOptions.usingJavaScript());
        return new HomePage().assertExpectedPage();
    }

    @Step("open account overview page")
    public AccountOverviewPage openAccountOverviewPage()
    {
        goBackButton.click(ClickOptions.usingJavaScript());
        return new AccountOverviewPage().assertExpectedPage();
    }
}
