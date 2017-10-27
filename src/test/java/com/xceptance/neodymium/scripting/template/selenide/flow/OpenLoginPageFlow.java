package com.xceptance.neodymium.scripting.template.selenide.flow;

import static com.codeborne.selenide.Selenide.page;

import com.xceptance.neodymium.scripting.template.selenide.page.browsing.PHome;
import com.xceptance.neodymium.scripting.template.selenide.page.user.PLogin;

public class OpenLoginPageFlow extends AbstractFlow<PLogin>
{

    @Override
    public PLogin flow()
    {
        PHome homePage = new OpenHomePageFlow().flow();
        homePage.userMenu().openLogin();
        return page(PLogin.class);
    };
}
