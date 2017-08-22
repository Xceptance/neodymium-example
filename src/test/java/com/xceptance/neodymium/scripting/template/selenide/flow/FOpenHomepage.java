package com.xceptance.neodymium.scripting.template.selenide.flow;

import static com.codeborne.selenide.Selenide.*;

import com.xceptance.neodymium.scripting.template.selenide.page.HomePage;

public class FOpenHomepage
{

    public static HomePage flow()
    {
        clearBrowserCookies();
        return open("https://localhost:8443/posters/", HomePage.class);
    };
}
