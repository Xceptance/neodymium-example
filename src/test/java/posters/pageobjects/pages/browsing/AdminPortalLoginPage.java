package posters.pageobjects.pages.browsing;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.switchTo;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.components.LoginHeader;

public class AdminPortalLoginPage extends AbstractBrowsingPage 
{
    public LoginHeader loginHeader = new LoginHeader();
    
    private SelenideElement panelHeader = $("#ext-gen29");
    private SelenideElement loginForm = $("#ext-gen17");
    SelenideElement usernameLabel = $("#ext-gen43");
    SelenideElement usernameInput = $("#loginUsername");
    SelenideElement passwordLabel = $("#ext-gen45");
    SelenideElement passwordInput = $("#loginPwd");
   
    SelenideElement languageLabel = $("#ext-gen47");
    SelenideElement languageInput = $("#langComboLogin");
    SelenideElement dropdownTrigger = $("#ext-gen49");
    SelenideElement feedbackPanel = $("#ext-gen51");

    SelenideElement loginOutput = $("#loginOutput");

    @Override
    @Step("ensure this is the portal home page")
    public AdminPortalLoginPage isExpectedPage()
    {
        super.isExpectedPage();
        panelHeader.should(exist).shouldBe(visible);
        loginForm.should(exist).shouldBe(visible);
        return this;
    }



    @Step("everything admin portal")
    public AdminPortalPage sendLoginForm(String email, String password)
    {

        $("#ext-gen9 #ext-comp-1197").should(exist).shouldBe(visible);
        $("#ext-gen9 #ext-comp-1197 #ext-gen42").shouldBe(visible);

        // fill out the login form
        $("#loginUsername").val(email);
        $("#loginPwd").val(password);

        // click on the Sign In button.
        $("#ext-gen37").click();

        return new AdminPortalPage().isExpectedPage();
    }


    @Override
    @Step("validate structure home page")
    public void validateStructure()
    {
        usernameLabel.shouldBe(visible);
        usernameInput.shouldBe(visible);

        passwordLabel.shouldBe(visible);
        passwordInput.shouldBe(visible);

        languageLabel.shouldBe(visible);
        languageInput.shouldBe(visible);
        dropdownTrigger.shouldBe(visible);
        feedbackPanel.shouldBe(visible);
        loginOutput.shouldBe(visible);

        //$("#bjt_grid_body_adminUserGrid2 > div.x-grid3-row.complete.x-grid3-row-first").shouldBe(visible).should(exist);
       
    }
}
