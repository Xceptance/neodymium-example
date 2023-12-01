package posters.pageobjects.pages.browsing;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.tests.testdata.dataobjects.Product;

public class ProductDetailPage extends AbstractBrowsingPage
{
    private SelenideElement addToCartButton = $("#btnAddToCart");

    private SelenideElement productName = $("#product-detail-form-name");

    private SelenideElement productPrice = $("#product-detail-form-price");

    private SelenideElement productSize = $("#product-detail-form-size-selection");

    @Override
    @Step("ensure this is a product detail page")
    public ProductDetailPage isExpectedPage()
    {
        super.isExpectedPage();
        $(".h2fwpr#main .container.mt-4").should(exist);
        return this;
    }

    /// ----- validate content product detail page ----- ///

    private void validateSizeDropdown(String size)
    {
        productSize.should(matchText(size)).should(exist);
    }

    @Step("validate size dropdown")
    public void validateSizeDropdown()
    {
        productSize.scrollTo().click();

        if ($$("#product-detail-form-size-selection option").size() > 1)
        {
            validateSizeDropdown(Neodymium.localizedText("ProductdetailPage.size.16x12"));
            validateSizeDropdown(Neodymium.localizedText("ProductdetailPage.size.32x24"));
            validateSizeDropdown(Neodymium.localizedText("ProductdetailPage.size.64x48"));
        }
        else
        {
            validateSizeDropdown(Neodymium.localizedText("ProductdetailPage.size.96x32"));
        }

        productSize.scrollTo().click();
    }

    @Step("validate style radio")
    public void validateStyleRadio()
    {
        $("#product-detail-form-style-selection > div:nth-child(1) > label").shouldHave(exactText(Neodymium.localizedText("ProductdetailPage.style.matte"))).shouldBe(visible);
        $("#product-detail-form-style-selection > div:nth-child(2) > label").shouldHave(exactText(Neodymium.localizedText("ProductdetailPage.style.gloss"))).shouldBe(visible);
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
        validateSizeDropdown();

        // validate style selection
        $("#product-detail-form-style-selection").shouldBe(visible);
        validateStyleRadio();

        // validate print information
        $("#product-detail-form-print-information").shouldBe(visible);

        // validate add to cart button
        addToCartButton.shouldHave(exactText(Neodymium.localizedText("ProductdetailPage.addToCartButton"))).shouldBe(visible);
        $("#btnAddToCart .icon-shopping-cart").shouldBe(visible);
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

    /// ----- select product size/style, add to cart ----- ///

    @Step("select size '{size}'")
    public void setSize(String size)
    {
        productSize.selectOptionContainingText(size);
    }

    @Step("select style '{style}'")
    public void setStyle(String style)
    {
        $(".radio #finish-" + style).selectRadio(style);
    }

    @Step("click add to cart button")
    public void clickAddToCartButton()
    {
        addToCartButton.scrollTo().click();
    }

    @Step("add product with size '{size}' and style '{style}' to cart")
    public void addToCart(String size, String style)
    {
        setSize(size);
        setStyle(style);
        clickAddToCartButton();
    }

    /// ----- get product details ----- ///

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
        return $("input[name='finish']:checked").val();
    }

    @Step("get selected product size from product detail page")
    public String getChosenSize()
    {
        return productSize.getSelectedText();
    }

    @Step("get product details from product detail page")
    public Product getProduct()
    {
        return new Product(getProductName(), getProductPrice(), getChosenStyle(), getChosenSize(), 1);
    }
    
    /// ----- product detail page navigation ----- ///
    
    @Step("open homepage from product detail page")
    public HomePage openHomePage()
    {
        $("#header-brand").scrollTo().click();
        return new HomePage().isExpectedPage();
    }
}
