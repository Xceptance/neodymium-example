/**
 * 
 */
package posters.flows;

import static com.codeborne.selenide.Selenide.$;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.checkout.CartPage;

/**
 * @author pfotenhauer
 */
public class CartCleanUpFlow
{
    @Step("cart clean up flow")
    public static void flow()
    {
        // click on the shop logo to ensure a home page afterwards
        $(".navbar #brand").click();

        // open the minicart
        HomePage homePage = new HomePage();
        homePage.miniCart.openMiniCart();

        // go to cart page
        CartPage cartPage = homePage.miniCart.openCartPage();
        while (cartPage.hasProductsInCart())
        {
            // remove the first product as long as one is available
            cartPage.removeProduct(1);
        }
    }
}
