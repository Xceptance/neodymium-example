/**
 * 
 */
package posters.flows;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import posters.pageObjects.pages.browsing.HomePage;
import posters.pageObjects.pages.checkout.CartPage;

/**
 * @author pfotenhauer
 */
public class CartCleanUpFlow
{
    /**
     * 
     */
    public static void flow()
    {
        // click on the shop logo to ensure a home page afterwards
        $("#globalNavigation #brand").click();

        // open the minicart
        HomePage homePage = page(HomePage.class);
        homePage.miniCart().openMiniCart();

        // goto cart page
        CartPage cartPage = homePage.miniCart().openCartPage();
        while (cartPage.hasProductsInCart())
        {
            // remove the first product as long as one is available
            cartPage.removeProduct(1);
        }
    }
}
