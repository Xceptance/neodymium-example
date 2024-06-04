package posters.tests.hackathon;

import org.junit.Test;

import com.codeborne.selenide.Selenide;
import com.xceptance.neodymium.module.statement.browser.multibrowser.Browser;
import com.xceptance.neodymium.util.Neodymium;

import posters.flows.OpenHomePageFlow;

@Browser("Firefox_1400x1000")
@Browser("Chrome_1400x1000")
@Browser("Chrome_1200x768")
@Browser("Firefox_1200x768")
public class GenerateScreenshots
{
    @Test
    public void generateImages()
    {
        OpenHomePageFlow.openCategory("World of Nature", "1");
        Selenide.screenshot("category-page-reference-" + Neodymium.getBrowserProfileName());
        OpenHomePageFlow.openCategory("Dining", "2");
        Selenide.screenshot("diff-category-page-reference-" + Neodymium.getBrowserProfileName());
        OpenHomePageFlow.openSearchResults("bear");
        Selenide.screenshot("search-category-page-reference-" + Neodymium.getBrowserProfileName());
        OpenHomePageFlow.flow();
        Selenide.screenshot("home-page-reference-" + Neodymium.getBrowserProfileName());
    }
}
