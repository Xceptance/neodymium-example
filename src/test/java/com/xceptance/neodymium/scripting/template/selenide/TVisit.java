package com.xceptance.neodymium.scripting.template.selenide;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.xceptance.multibrowser.TestTargets;
import com.xceptance.neodymium.scripting.template.selenide.flow.FOpenHomepage;
import com.xceptance.neodymium.scripting.template.selenide.page.PHome;
import com.xceptance.xrunner.XCRunner;

@TestTargets(
{
  "Chrome_1024x768", "FF_1024x768"
})
@RunWith(XCRunner.class)
public class TVisit extends BasicTest
{
    @Parameter
    public String data;

    @Parameters(name = "{index}::{0}")
    public static Collection<Object[]> getTestData()
    {
        List<Object[]> data = new LinkedList<>();

        data.add(new Object[]
        {
          "a"
        });
        data.add(new Object[]
        {
          "b"
        });

        return data;
    }

    @Test
    public void test()
    {
        PHome homePage = new FOpenHomepage().flow();
        homePage.validate();
        homePage.footer().validate();
    }

    @Test
    public void test1()
    {

    }

    @Test
    public void test2()
    {

    }
}
