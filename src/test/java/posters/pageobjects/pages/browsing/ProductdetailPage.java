/**
 * 
 */
package posters.pageobjects.pages.browsing;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.dataobjects.Product;

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
    public void isExpectedPage()
    {
        $("#addToCartForm").should(exist);
    }

    @Step("validate product detail page structure")
    public void validateStructure()
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
    }

    @Step("validate product name on product detail page")
    public void validateProductName(String name)
    {
        // Verify product name
        // compares the displayed product name to the given parameter.
        productName.shouldHave(exactText(name));
    }

    @Step("select size")
    public void setSize(String size)
    {
        productSize.selectOptionContainingText(size);
    }

    @Step("select style")
    public void setStyle(String style)
    {
        $(".radio #finish-" + style).selectRadio(style);

    }

    @Step("add product to the cart")
    public void addToCart()
    {
        addToCartButton.scrollTo().click();

    }

    @Step("add product with size:\"{size}\" and style:\"{style}\" to cart")
    public void addToCart(String size, String style)
    {
        // Style
        // Checks the chosen style.
        setStyle(style);
        // Size
        // Selects the chosen size.
        setSize(size);
        // Click the Add to cart button
        addToCart();
    }

    @Step("get product name from product detail page")
    public String getProductName()
    {
        // Get the product name to enable usage outside this module.
        return productName.text();
    }

    @Step("get selected product style from product detail page")
    public String getChosenStyle()
    {
        // Get the style to enable usage outside this module.
        return $(".radio input[name='finish']:checked").val();
    }

    @Step("get selected product size from product detail page")
    public String getChosenSize()
    {
        // Get the size to enable usage outside this module.
        return productSize.getSelectedText();
    }

    @Step("get product price from product detail page")
    public String getProductPrice()
    {
        // Get the product price to enable usage outside this module.
        return productPrice.text();
    }

    @Step("get product details from product detail page")
    public Product getProduct()
    {
        return new Product(getProductName(), getProductPrice(), getChosenStyle(), getChosenSize(), 1);
    }

    /**
     * @param productName
     */
    @Step("validate the product detail page of \"{productName}\"")
    public void validate(String productName)
    {
        validateStructure();
        validateProductName(productName);
    };

    /**
     * @param productName
     * @param string
     */
    @Step("validate the product detail page of \"{productName}\" and assert visually")
    public void validateAndVisualAssert(String productName)
    {
        validateStructureAndVisual();
        validateProductName(productName);
    }
}
