package posters.pageobjects.pages.browsing;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.tests.testdata.dataobjects.Product;

public class ProductDetailPage extends AbstractBrowsingPage
{
    private SelenideElement addToCartButton = $("#btn-add-to-cart");

    private SelenideElement productName = $("#product-detail-form-name");

    private SelenideElement productPrice = $("#product-detail-form-price");

    private SelenideElement productSize = $("#product-detail-form-size-selection");

    private ElementsCollection productStyle = $$(".form-check-label");

    @Override
    @Step("ensure this is a product detail page")
    public ProductDetailPage isExpectedPage()
    {
        super.isExpectedPage();
        productName.should(exist);
        return this;
    }

    /// ========== validate content product detail page ========== ///

    @Step("validate size dropdown")
    public void validateSizeDropdown()
    {
        productSize.click(ClickOptions.usingJavaScript());

        if ($$("#product-detail-form-size-selection option").size() > 1)
        {
            productSize.should(matchText(Neodymium.localizedText("product.size.16x12"))).should(exist);
            productSize.should(matchText(Neodymium.localizedText("product.size.32x24"))).should(exist);
            productSize.should(matchText(Neodymium.localizedText("product.size.64x48"))).should(exist);
        }
        else
        {
            productSize.should(matchText(Neodymium.localizedText("product.size.96x32"))).should(exist);
        }

        productSize.click(ClickOptions.usingJavaScript());
    }

    @Step("validate style radio")
    public void validateStyleRadio()
    {
        productStyle.findBy(exactText(Neodymium.localizedText("product.style.matte"))).shouldBe(visible);
        productStyle.findBy(exactText(Neodymium.localizedText("product.style.gloss"))).shouldBe(visible);
    }

    @Override
    @Step("validate product detail page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // validate image
        $(".img-fluid").shouldBe(visible);

        // validate title
        productName.should(matchText("[A-Z].{3,}")).shouldBe(visible);

        // validate product price
        productPrice.should(matchText("\\$\\d+\\.\\d{2}")).shouldBe(visible);

        // validate product description
        $("#product-detail-form-description-overview").shouldBe(visible);
        $("#product-detail-form-description").shouldBe(visible);

        // validate size selection
        $$(".form-label").findBy(attribute("for", "product-detail-form-size-selection"))
                         .shouldHave(exactText(Neodymium.localizedText("productDetailPage.selectSize"))).shouldBe(visible);
        validateSizeDropdown();

        // validate style selection
        $$(".form-label").findBy(attribute("for", "product-detail-form-style-selection"))
                         .shouldHave(exactText(Neodymium.localizedText("productDetailPage.selectStyle"))).shouldBe(visible);
        validateStyleRadio();

        // validate print information
        $("#product-detail-form-print-information").shouldHave(exactText(Neodymium.localizedText("productDetailPage.printInformation"))).shouldBe(visible);

        // validate add to cart button
        addToCartButton.shouldHave(exactText(Neodymium.localizedText("button.addToCart"))).shouldBe(visible);
        $("#btn-add-to-cart .icon-shopping-cart").shouldBe(visible);
    }

    @Step("validate product name '{prodName}' on product detail page")
    public void validateProductName(String prodName)
    {
        productName.shouldHave(exactText(prodName));
    }

    @Step("validate product detail page")
    public void validate(String productName)
    {
        validateStructure();
        validateProductName(productName);
    }

    /// ========== select product size/style, add to cart ========== ///

    @Step("select size '{size}'")
    public void setSize(String size)
    {
        productSize.selectOptionContainingText(size);
    }

    @Step("select style '{style}'")
    public void setStyle(String style)
    {
        $("#finish-" + style).click(ClickOptions.usingJavaScript());
    }

    @Step("click add to cart button")
    public void clickAddToCartButton()
    {
        addToCartButton.click(ClickOptions.usingJavaScript());
        header.miniCart.closeMiniCart();
    }

    @Step("add product with size '{size}' and style '{style}' to cart")
    public void addToCart(String size, String style)
    {
        // save product price and size before updating
        String productPriceBeforeUpdate = productPrice.text();
        String currentlySelectedSizeOption = productSize.getSelectedOptionText();
        
        // set style and size
        setSize(size);
        setStyle(style);

        // wait for product price to be updated
        if (!size.equals(currentlySelectedSizeOption)) 
        {
            productPrice.shouldNotHave(exactText(productPriceBeforeUpdate));
        }
        
        clickAddToCartButton();
    }

    /// ========== get product details ========== ///

    @Step("get product name from product detail page")
    public String getProductName()
    {
        return productName.text();
    }

    @Step("get product price from product detail page")
    public String getProductPrice()
    {
        return productPrice.text();
    }

    @Step("get selected product style from product detail page")
    public String getChosenStyle()
    {
        return $(".form-check-input:checked").val();
    }

    @Step("get selected product size from product detail page")
    public String getChosenSize()
    {
        return productSize.getSelectedOptionText();
    }

    @Step("get product details from product detail page")
    public Product getProduct()
    {
        return new Product(getProductName(), getProductPrice(), getChosenStyle(), getChosenSize(), 1);
    }

    /// ========== product detail page navigation ========== ///

    @Step("open homepage from product detail page")
    public HomePage openHomePage()
    {
        $("#header-brand").click(ClickOptions.usingJavaScript());
        return new HomePage().isExpectedPage();
    }
}
