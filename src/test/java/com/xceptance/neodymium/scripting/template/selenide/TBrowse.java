/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide;

import org.junit.Test;

import com.xceptance.multibrowser.TestTargets;
import com.xceptance.neodymium.scripting.template.selenide.flow.FOpenHomepage;
import com.xceptance.neodymium.scripting.template.selenide.page.PCategory;
import com.xceptance.neodymium.scripting.template.selenide.page.PHome;

/**
 * @author pfotenhauer
 */
@TestTargets(
{
  "Chrome_1024x768"
})

public class TBrowse extends BasicTest
{
    @Test
    public void test()
    {

        // Page types to use
        PHome homePage;
        PCategory categoryPage;

        // Goto homepage
        homePage = new FOpenHomepage().flow();
        homePage.validate();
        homePage.footer().validate();

        // Goto category
        final String categoryName = homePage.topNav().getSubCategoryNameByIndex(1, 1);
        categoryPage = homePage.topNav().clickSubCategoryByIndex(1, 1);

    }
}
