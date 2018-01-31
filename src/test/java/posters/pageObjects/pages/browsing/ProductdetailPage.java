/**
 * 
 */
package posters.pageObjects.pages.browsing;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Context;

import posters.dataObjects.Product;

/**
 * @author pfotenhauer
 */
public class ProductdetailPage extends AbstractBrowsingPage
{
    private SelenideElement addToCartButton = $("#btnAddToCart");

    private SelenideElement productName = $("#titleProductName");

    private SelenideElement productPrice = $("#prodPrice");

    private SelenideElement productSize = $("#selectSize");

    public void isExpectedPage()
    {
        $("#addToCartForm").should(exist);
    }

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
        addToCartButton.shouldHave(exactText(Context.localizedText("ProductdetailPage.addToCartButton.text")));
    }

    public void validateProductName(String name)
    {
        // Verify product name
        // compares the displayed product name to the given parameter.
        productName.shouldHave(exactText(name));
    }

    public void addToCart(String size, String style)
    {
        // Style
        // Checks the chosen style.
        $(".radio #finish-" + style).selectRadio(style);
        // Size
        // Selects the chosen size.
        productSize.selectOptionContainingText(size);
        // Click the Add to cart button
        // click("css= #btnAddToCart");
        addToCartButton.scrollTo().click();
    }

    public String getProductName()
    {
        // Get the product name to enable usage outside this module.
        return productName.text();
    }

    public String getChosenStyle()
    {
        // Get the style to enable usage outside this module.
        return $(".radio input[name='finish']:checked").val();
    }

    public String getChosenSize()
    {
        // Get the size to enable usage outside this module.
        return productSize.getSelectedText();
    }

    public String getProductPrice()
    {
        // Get the product price to enable usage outside this module.
        return productPrice.text();
    }

    public Product getProduct()
    {
        return new Product(getProductName(), getProductPrice(), getProductPrice(), getChosenStyle(), getChosenSize(), 1);
    }

    /**
     * @param productName
     */
    public void validate(String productName)
    {
        validateStructure();
        validateProductName(productName);
    };
}
