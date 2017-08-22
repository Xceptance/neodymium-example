package com.xceptance.neodymium.scripting.template.selenide;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.xceptance.multibrowser.AnnotationRunner;
import com.xceptance.multibrowser.TestTargets;
import com.xceptance.neodymium.scripting.template.selenide.flow.FOpenHomepage;
import com.xceptance.neodymium.scripting.template.selenide.page.HomePage;

@TestTargets(
{
  "Chrome_1024x768", "FF_1024x768"
})
@RunWith(AnnotationRunner.class)
public class TVisit
{

    @Test
    public void test()
    {
        HomePage homePage = FOpenHomepage.flow();
        homePage.validate();
        homePage.footer().validate();
    }
}
