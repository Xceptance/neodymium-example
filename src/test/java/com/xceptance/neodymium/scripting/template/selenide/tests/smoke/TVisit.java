package com.xceptance.neodymium.scripting.template.selenide.tests.smoke;

import org.junit.Test;

import com.xceptance.neodymium.multibrowser.Browser;
import com.xceptance.neodymium.scripting.template.selenide.flow.OpenHomePageFlow;
import com.xceptance.neodymium.scripting.template.selenide.page.browsing.PHome;
import com.xceptance.neodymium.scripting.template.selenide.tests.BasicTest;

@Browser(
{
  "Chrome_1024x768"
})
public class TVisit extends BasicTest
{
    @Test
    public void test()
    {
        PHome homePage = new OpenHomePageFlow().flow();
        homePage.validateStructure();
        homePage.footer().validate();
    }
}
