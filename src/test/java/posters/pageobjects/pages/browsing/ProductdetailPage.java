/**
 * 
 */
package posters.pageobjects.pages.browsing;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Selenide.$;

import org.junit.Assert;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;
import com.xceptance.neodymium.util.SelenideAddons;

import io.qameta.allure.Step;

/**
 * @author pfotenhauer
 */
public class ProductdetailPage extends AbstractBrowsingPage
{
    private SelenideElement addToCartButton = $("#btnAddToCart");

    private SelenideElement productName = $("#titleProductName");

    private SelenideElement productPrice = $("#prodPrice");

    private SelenideElement productSize = $("#selectSize");

    @Override
    @Step("ensure this is a product detail page")
    public ProductdetailPage isExpectedPage()
    {
        $("#addToCartForm").should(exist);
        return this;
    }

    @Override
    @Step("validate product detail page structure")
    public ProductdetailPage validateStructure()
    {
        super.validateStructure();

        // Title
        // Make sure we have the proper headline and it is not empty, at least 4 characters, starting with uppercase
        // letter,
        productName.shouldBe(matchText("[A-Z].{3,}"));
        // Price
        // Check that the price is correct in its form. A dollar symbol, followed by 1-3 digits, optionally more groups
        // of exactly 3 digits separated by a comma as separator, a period and 2 digits for the cents as the end of the
        // String.
        productPrice.shouldBe(matchText("\\$(\\d{1,3})(,\\d{3})*\\.\\d{2}$"));
        // Add to cart button
        // Make sure we got an add to cart button and the text matches.
        addToCartButton.shouldHave(exactText(Neodymium.localizedText("ProductdetailPage.addToCartButton.text")));
        return this;
    }

    @Step("validate product name on product detail page")
    public ProductdetailPage validateProductName(String name)
    {
        // Verify product name
        // compares the displayed product name to the given parameter.
        productName.shouldHave(exactText(name));
        return this;
    }

    @Step("select size")
    public ProductdetailPage setSize(String size)
    {
        productSize.selectOptionContainingText(size);
        return this;
    }

    @Step("select style")
    public ProductdetailPage setStyle(String style)
    {
        $(".radio #finish-" + style).selectRadio(style);
        return this;
    }

    @Step("add product to the cart")
    public ProductdetailPage addToCart()
    {
        addToCartButton.scrollTo().click();
        return this;
    }

    @Step("add product with size:\"{size}\" and style:\"{style}\" to cart")
    public ProductdetailPage addToCart(String size, String style)
    {
        // Style
        // Checks the chosen style.
        setStyle(style);
        // Size
        // Selects the chosen size.
        setSize(size);
        // Click the Add to cart button
        addToCart();
        return this;
    }

    /**
     * @param productName
     */
    @Step("validate the product detail page of \"{productName}\"")
    public ProductdetailPage validate(String productName)
    {
        validateStructure();
        validateProductName(productName);
        return this;
    };

    /**
     * @param productName
     * @param string
     */
    @Step("validate the product detail page of \"{productName}\" and assert visually")
    public ProductdetailPage validateAndVisualAssert(String productName)
    {
        validateStructureAndVisual();
        validateProductName(productName);
        return this;
    }

    @Step("validate selected size is {size}")
    public ProductdetailPage validateSelectedSize(String size)
    {
        $("#selectSize").getSelectedOption().shouldHave(exactText(size));
        return this;
    }

    @Step("validate selected style is {style}")
    public ProductdetailPage validateSelectedStyle(String style)
    {
        SelenideAddons.wrapAssertionError(() -> {
            Assert.assertEquals($("#selectStyle input[checked='checked']").getValue(), style);
        });
        return this;
    }

    @Step("validate unit price is equal to price")
    public ProductdetailPage validateUnitPrice(String price)
    {
        $("#prodPrice").shouldHave(exactText(price));
        return this;
    }
}
