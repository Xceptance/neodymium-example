package posters.flows;

import static com.codeborne.selenide.Selenide.open;

import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.UserLoginPage;

public class OpenUserLoginPageFlow
{
    @Step("open login page flow")
    public static UserLoginPage flow()
    {
        // initialize the session and go to home page
        // var homePage = OpenHomePageFlow.flow();

        // open login page and check for expected page
        open(Neodymium.configuration().url());
        return new UserLoginPage().isExpectedPage();
    }
}
