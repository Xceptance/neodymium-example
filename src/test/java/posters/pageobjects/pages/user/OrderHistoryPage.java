package posters.pageobjects.pages.user;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.ClickOptions;

import static com.codeborne.selenide.Selenide.$$x;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;
import posters.pageobjects.pages.browsing.HomePage;
import posters.tests.testdata.dataobjects.Product;

public class OrderHistoryPage extends AbstractBrowsingPage
{
    private SelenideElement title = $("#title-order-history");
    
    private ElementsCollection tableHead = $$x("//div[@id='order-overview']/table/thead/tr/th"); 

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
    
    @Step("validate order")
    public void validateOrder(int numberOfOrder, int numberOfProductInOrder, Product product) 
    {
        SelenideElement productOfOrder;
        
        if (numberOfProductInOrder == 1) 
        {
            productOfOrder = $x("//div[@id='order-overview']//tr[2 and th[contains(text(), '" + numberOfOrder + "')]]");            
        }
        else 
        {
            numberOfProductInOrder--;
            productOfOrder = $x("//div[@id='order-overview']//tr[th[contains(text(), '" + numberOfOrder + "')]]/following-sibling::tr[" + numberOfProductInOrder + "]");            
        }
        
        // validate first product of order
        productOfOrder.find(".img-thumbnail").shouldBe(visible);
        productOfOrder.find(".product-name").shouldHave(exactText(product.getName()));
        productOfOrder.find(".product-style").should(matchText(product.getStyle()));
        productOfOrder.find(".product-size").should(matchText(product.getSize()));
        productOfOrder.find("td:nth-child(4)").shouldHave(exactText(Integer.toString(product.getAmount()) + "x"));
    }
    
    /// ========== order history page navigation ========== ///
    
    @Step("open homepage from order confirmation page")
    public HomePage openHomePage()
    {
        $("#header-brand").click(ClickOptions.usingJavaScript());
        return new HomePage().isExpectedPage();
    }
}