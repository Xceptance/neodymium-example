package posters.flows;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.HomePage;

public class CartCleanUpFlow
{
    @Step("cart clean up flow")
    public static void flow()
    {
        HomePage homePage = new HomePage();

        // go to cart page
        var cartPage = homePage.miniCart.openCartPage();
        
        // remove the first product as long as one is available
        while (cartPage.hasProductsInCart())
        {
            cartPage.removeProduct(1);
        }
    }
}
