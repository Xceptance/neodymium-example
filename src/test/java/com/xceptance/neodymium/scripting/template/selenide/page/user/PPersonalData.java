/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.page.user;

import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import com.xceptance.neodymium.scripting.template.selenide.page.browsing.AbstractBrowsingPage;

/**
 * @author pfotenhauer
 */
public class PPersonalData extends AbstractBrowsingPage
{
    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.neodymium.scripting.template.selenide.page.PageObject#isExpectedPage()
     */
    @Override
    public boolean isExpectedPage()
    {
        return $("#titlePersonalData").exists();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.neodymium.scripting.template.selenide.page.PageObject#validateStructure()
     */
    @Override
    public void validateStructure()
    {
        // Headline
        // Makes sure the headline is there and starts with a capital letter
        $("#titlePersonalData").should(matchText("[A-Z].{3,}"));
        // Data
        // Makes sure the form with your user Data is there
        $("#main > div > div.col-sm-12").shouldBe(visible);
        // Delete Account Button
        // Make sure the button to delete your account is there
        $("#btnDeleteAccount").shouldBe(visible);
    }

    /**
     * @return
     */
    public PDeleteAccount openDeleteAccount()
    {
        // Open the delete account page
        // Clicks the button to get to the Delete Account page
        $("#btnDeleteAccount").scrollTo().click();
        return page(PDeleteAccount.class);
    }
}
