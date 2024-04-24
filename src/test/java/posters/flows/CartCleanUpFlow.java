package posters.flows;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.HomePage;

public class CartCleanUpFlow
{
    @Step("clean up cart flow")
    public static void flow()
    {
        HomePage init = new HomePage();

        // go to homepage (needed cause checkout header don't has miniCart -> clean up would fail)
        var homePage = init.openHomePage();
        
        if (homePage.header.miniCart.getTotalCount() == 0) 
        {
            return;
        }
        else 
        {
            // go to cart page
            var cartPage = homePage.header.miniCart.openCartPage();
            
            // remove the first product as long as one is available
            while (cartPage.header.miniCart.getTotalCount() != 0)
            {
                // make sure cart page is empty
                cartPage.removeProduct(1);                
                cartPage.validateEmptyCartPage();
            }
            
            // go to homepage
            homePage = cartPage.openHomePage();            
        }
    }
}
