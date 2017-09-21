/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.page.user;

import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import com.xceptance.neodymium.scripting.template.selenide.page.BasicPage;

/**
 * @author pfotenhauer
 */
public class PAccountOverView extends BasicPage
{
    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.neodymium.scripting.template.selenide.page.PageObject#isExpectedPage()
     */
    @Override
    public boolean isExpectedPage()
    {
        return $("#titleAccountOverview").exists();
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
        // Make sure the headline is there and start with a capital letter
        $("#titleAccountOverview").should(matchText("[A-Z].{3,}"));
        // Order Overview Link
        // Make sure the link to the order history is there and the text starts with a capital letter
        $("#linkOrderOverview").should(matchText("[A-Z].{3,}"));
        // My Addresses Link
        // Make sure the link to the Addresses page is there and the text starts with a capital letter
        $("#linkAddressOverview").should(matchText("[A-Z].{3,}"));
        // Payment Settings Link
        // Make sure the link to the Credit Cards page is there and the text starts with a capital letter
        $("#linkPaymentOverview").should(matchText("[A-Z].{3,}"));
        // Personal Data Link
        // Make sure the link to the Personal Data page is there and the text starts with a capital letter
        $("#linkPersonalData").should(matchText("[A-Z].{3,}"));
    }

    /**
     * @return
     */
    public PPersonalData openPersonalData()
    {
        // Open the personal data page
        // Click on the link to Personal Data
        $("#linkPersonalData").click();
        return page(PPersonalData.class);
    }
}
