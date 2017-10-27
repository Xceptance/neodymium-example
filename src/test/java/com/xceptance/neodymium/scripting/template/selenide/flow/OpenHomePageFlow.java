package com.xceptance.neodymium.scripting.template.selenide.flow;

import static com.codeborne.selenide.Selenide.clearBrowserCookies;
import static com.codeborne.selenide.Selenide.open;

import com.xceptance.neodymium.scripting.template.selenide.page.browsing.HomePage;

public class OpenHomePageFlow extends AbstractFlow<HomePage>
{

    @Override
    public HomePage flow()
    {
        clearBrowserCookies();
        return open("https://localhost:8443/posters/", HomePage.class);
    };
}
