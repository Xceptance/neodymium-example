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
        var cartPage = homePage.header.miniCart.openCartPage();
        
        // remove the first product as long as one is available
        while (cartPage.header.miniCart.getTotalCount() != 0)
        {
            cartPage.removeProduct(1);
        }
    }
}
