package posters.flows;

import static com.codeborne.selenide.Selenide.clearBrowserCookies;
import static com.codeborne.selenide.Selenide.open;

import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.browsing.ProductDetailPage;
import posters.pageobjects.pages.user.LoginPage;
import posters.pageobjects.pages.user.RegisterPage;

public class OpenPageFlow
{
    @Step("open home page")
    public static HomePage openHomePage()
    {
        // clear cookies to ensure a new session
        clearBrowserCookies();

        // open home page
        open(Neodymium.configuration().url());
        HomePage homePage = new HomePage();
        homePage.isExpectedPage();
        return homePage;
    };

    @Step("open product page with cookies")
    public static ProductDetailPage openProductPageWithCookies(String relativeUrl)
    {
        // open product page
        open(Neodymium.configuration().url() + relativeUrl);
        ProductDetailPage pdp = new ProductDetailPage();
        pdp.isExpectedPage();
        return pdp;
    };

    @Step("open product page")
    public static ProductDetailPage openProductPage(String relativeUrl)
    {
        clearBrowserCookies();
        return openProductPageWithCookies(relativeUrl);
    };

    @Step("open registration page")
    public static RegisterPage openRegistrationPage()
    {
        // clear cookies to ensure a new session
        clearBrowserCookies();

        open(Neodymium.configuration().url() + "registration");
        RegisterPage registerPage = new RegisterPage();
        registerPage.isExpectedPage();
        return registerPage;
    }

    @Step("open login page")
    public static LoginPage openLoginPage()
    {
        // clear cookies to ensure a new session
        clearBrowserCookies();

        open(Neodymium.configuration().url() + "login");
        LoginPage loginPage = new LoginPage();
        loginPage.isExpectedPage();
        return loginPage;
    }
}
