package posters.flows;

import static com.codeborne.selenide.Selenide.$;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.HomePage;

/**
 * @author pfotenhauer
 */
public class CartCleanUpFlow
{
    @Step("cart clean up flow")
    public static void flow()
    {
        // click on the shop logo to ensure a home page afterwards
        $(".colorlib-nav #brand").click();

        // open the minicart
        var homePage = new HomePage();
        homePage.miniCart.openMiniCart();

        // go to cart page
        var cartPage = homePage.miniCart.openCartPage();
        while (cartPage.hasProductsInCart())
        {
            // remove the first product as long as one is available
            cartPage.removeProduct(1);
        }
    }
}
