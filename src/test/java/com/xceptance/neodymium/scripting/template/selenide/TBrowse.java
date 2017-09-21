/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide;

import org.junit.Test;

import com.xceptance.multibrowser.Browser;
import com.xceptance.neodymium.scripting.template.selenide.flow.FOpenHomepage;
import com.xceptance.neodymium.scripting.template.selenide.page.PCategory;
import com.xceptance.neodymium.scripting.template.selenide.page.PHome;
import com.xceptance.neodymium.scripting.template.selenide.page.PProduct;

/**
 * @author pfotenhauer
 */
@Browser(
{
  "Chrome_1024x768"
})
public class TBrowse extends BasicTest
{
    @Test
    public void test()
    {
        // Page types to use
        PHome homePage;
        PCategory categoryPage;
        PProduct productPage;

        // Goto homepage
        homePage = new FOpenHomepage().flow();
        homePage.validate();

        // Goto category
        final String categoryName = homePage.topNav().getSubCategoryNameByIndex(1, 1);
        categoryPage = homePage.topNav().clickSubCategoryByIndex(1, 1);
        // TODO Discuss validations style lot of one liners or specific ones (one per line see TAddToCart)
        categoryPage.validate(categoryName);

        // Goto product page
        final String productName = categoryPage.getProducNametByIndex(1, 1);
        productPage = categoryPage.clickProductByIndex(1, 1);
        productPage.validate(productName);

        // Goto category
        final String categoryName2 = productPage.topNav().getSubCategoryNameByIndex(2, 2);
        categoryPage = productPage.topNav().clickSubCategoryByIndex(2, 2);
        categoryPage.validate(categoryName2);

        // Goto product page
        final String productName2 = categoryPage.getProducNametByIndex(2, 2);
        productPage = categoryPage.clickProductByIndex(2, 2);
        productPage.validate(productName2);

        // Goto category
        final String categoryName3 = productPage.topNav().getSubCategoryNameByIndex(2, 3);
        categoryPage = productPage.topNav().clickSubCategoryByIndex(2, 3);
        categoryPage.validate(categoryName3);

        // Goto product page
        final String productName3 = categoryPage.getProducNametByIndex(2, 3);
        productPage = categoryPage.clickProductByIndex(2, 3);
        productPage.validate(productName3);

    }
}
