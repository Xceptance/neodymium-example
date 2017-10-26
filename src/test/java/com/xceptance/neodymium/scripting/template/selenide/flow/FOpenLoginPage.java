package com.xceptance.neodymium.scripting.template.selenide.flow;

import static com.codeborne.selenide.Selenide.page;

import com.xceptance.neodymium.scripting.template.selenide.page.PHome;
import com.xceptance.neodymium.scripting.template.selenide.page.user.PLogin;

public class FOpenLoginPage implements BasicFlow<PLogin>
{

    @Override
    public PLogin flow()
    {
        PHome homePage = new FOpenHomepage().flow();
        homePage.userMenu().openLogin();
        return page(PLogin.class);
    };
}
