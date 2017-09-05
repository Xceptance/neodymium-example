/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.page;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Selenide.$;

import com.xceptance.neodymium.scripting.template.selenide.objects.Product;

/**
 * @author pfotenhauer
 */
public class PProduct extends BasicPage
{

    @Override
    public boolean isExpectedPage()
    {
        return $("#addToCartForm").exists();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.scripting.selenide.page.BasicPage#validate()
     */
    @Override
    public void validateStructure()
    {
        // Title
        // Make sure we have the proper headline and it is not empty, at least 4 characters, starting with uppercase
        // letter,
        $("h2#titleProductName").shouldBe(matchText("[A-Z].{3,}"));
        // Price
        // Check that the price is correct in its form. A dollar symbol, followed by 1-3 digits, optionally more groups
        // of exactly 3 digits separated by a comma as separator, a period and 2 digits for the cents as the end of the
        // String.
        $("#prodPrice").shouldBe(matchText("\\$(\\d{1,3})(,\\d{3})*\\.\\d{2}$"));
        // Add to cart button
        // Make sure we got an add to cart button and the text matches.
        $("#btnAddToCart").shouldHave(exactText("Add to cart"));
    }

    public void validateProductName(String name)
    {
        // Verify product name
        // compares the displayed product name to the given parameter.
        $("#titleProductName").shouldHave(exactText(name));
    }

    public void addToCart(String size, String style)
    {
        // Style
        // Checks the chosen style.
        $(".radio #finish-" + style).selectRadio(style);
        // Size
        // Selects the chosen size.
        $("#selectSize").selectOptionContainingText(size);
        // Click the Add to cart button
        // click("css= #btnAddToCart");
        $("#btnAddToCart").click();
    }

    public String getProductName()
    {
        // Get the product name to enable usage outside this module.
        return $("#titleProductName").text();
    }

    public String getChosenStyle()
    {
        // Get the style to enable usage outside this module.
        return $(".radio input[name='finish']:checked").val();
    }

    public String getChosenSize()
    {
        // Get the size to enable usage outside this module.
        return $("select#selectSize").getSelectedText();
    }

    public String getProductPrice()
    {
        // Get the product price to enable usage outside this module.
        return $("#prodPrice").text();
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
