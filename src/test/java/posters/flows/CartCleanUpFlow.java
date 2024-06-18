package posters.flows;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.HomePage;

public class CartCleanUpFlow
{
    @Step("clean up cart flow")
    public static void flow()
    {
        HomePage homePage = new HomePage();

        if (!homePage.header.miniCart.isAvailable()) 
        {
            // if test failed on CheckoutPage, there is no mini cart (would cause the cleanup to fail)
            // that is why we go to the Homepage first
            homePage = homePage.openHomePage();
        }
        
        if (homePage.header.miniCart.getTotalCount() == 0) 
        {
            return;
        }
        else 
        {
            // go to cart page
            var cartPage = homePage.header.miniCart.openCartPage();
            
            // remove all products from cart
            for (int i = cartPage.getAmountDifferentProducts(); i >= 1; i--) 
            {
                cartPage.removeProduct(i);

                if (i == 1) 
                {
                    cartPage.waitForEmptyCartPage();
                }
            }
            
            // validate empty cart page
            cartPage.validateStructure();
            
            // go to homepage
            homePage = cartPage.openHomePage();            
        }
    }
}
