package com.xceptance.neodymium.scripting.template.selenide.flow;

import static com.codeborne.selenide.Selenide.page;

import com.xceptance.neodymium.scripting.template.selenide.page.browsing.HomePage;
import com.xceptance.neodymium.scripting.template.selenide.page.user.LoginPage;

public class OpenLoginPageFlow extends AbstractFlow<LoginPage>
{

    @Override
    public LoginPage flow()
    {
        HomePage homePage = new OpenHomePageFlow().flow();
        homePage.userMenu().openLogin();
        return page(LoginPage.class);
    };
}
