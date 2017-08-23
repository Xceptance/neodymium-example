package com.xceptance.neodymium.scripting.template.selenide;

import org.junit.Test;

import com.xceptance.multibrowser.TestTargets;
import com.xceptance.neodymium.scripting.template.selenide.flow.FOpenHomepage;
import com.xceptance.neodymium.scripting.template.selenide.page.HomePage;

@TestTargets(
{
  "Chrome_1024x768", "FF_1024x768"
})
public class TVisit extends BasicTest
{

    @Test
    public void test()
    {
        HomePage homePage = FOpenHomepage.flow();
        homePage.validate();
        homePage.footer().validate();
    }
}
