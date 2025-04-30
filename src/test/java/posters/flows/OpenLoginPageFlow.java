package posters.flows;

import static com.codeborne.selenide.Selenide.clearBrowserCookies;
import static com.codeborne.selenide.Selenide.open;

import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.UserLoginPage;
import posters.pageobjects.pages.user.LoginPage;

public class OpenLoginPageFlow
{
    @Step("open login page flow")
    public static LoginPage flow()
    {
        // clear cookies to ensure a new session
        clearBrowserCookies();

        // open login page and check for expected page.
        open(Neodymium.configuration().url());
        // open("https://attui-dev.mooo.com/index.php");

        return new LoginPage().isExpectedPage();
    }
}
