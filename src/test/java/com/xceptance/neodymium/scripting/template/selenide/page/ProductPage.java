/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.page;

import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

import com.xceptance.neodymium.scripting.template.selenide.utility.VariableStoreProvider;

/**
 * @author pfotenhauer
 */
public class ProductPage extends BasicPage
{

    @Override
    protected boolean isAwaitedPage()
    {
        return $("#addToCartForm").exists();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.scripting.selenide.page.BasicPage#validate()
     */
    @Override
    public void validate()
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
        $("#btnAddToCart").shouldBe(text("Add to cart"));

        validateProductName(VariableStoreProvider.get("productName").toString());
    }

    public void validateProductName(String name)
    {
        // Verify product name
        // compares the displayed product name to the given parameter.
        $("#titleProductName").shouldBe(text(name));
    }

    public String getProductName()
    {
        // Get the product name
        return $("#titleProductName").text();
    }

    public void addToCart(String sizePositon, String style)
    {
        // Style
        // Checks the chosen style.
        $(".radio #finish-" + style).selectRadio(style);
        // Stores the style parameter to enable usage outside this module.
        VariableStoreProvider.put("productStyle", style);
        // Size
        // Stores the size to enable usage outside this module.
        // storeText("css= #selectSize > option:nth-child(" + size + ")", "productSize");
        VariableStoreProvider.put("productSize", $("#selectSize > option:nth-child(" + sizePositon + ")").text());
        // Selects the chosen size.
        // select("css= #selectSize", resolve("label=${productSize}"));
        $("#selectSize").selectOption(VariableStoreProvider.get("productSize").toString());
        // Click the Add to cart button
        // click("css= #btnAddToCart");
        $("#btnAddToCart").click();
        // Store the price to be used for verification.
        VariableStoreProvider.put("productPrice", $("#prodPrice").text());
        VariableStoreProvider.put("totalCount", $("#btnCartOverviewForm .headerCartProductCount").text());
    }
}
