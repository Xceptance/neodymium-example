/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.component;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import com.xceptance.neodymium.scripting.template.selenide.page.user.PAccountOverView;
import com.xceptance.neodymium.scripting.template.selenide.page.user.PLogin;
import com.xceptance.neodymium.scripting.template.selenide.utility.Settings;

/**
 * @author pfotenhauer
 */
public class CUserMenu extends BasicComponent
{

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.neodymium.scripting.template.selenide.component.BasicComponent#isComponentAvailable()
     */
    @Override
    protected boolean isComponentAvailable()
    {
        return $("#showUserMenu").exists();
    }

    /**
     * 
     */
    public void validateNotLoggedIn()
    {
        $("#userMenu .goToLogin").should(exist);
    }

    public void openUserMenu()
    {
        // Click the mini cart icon
        $("#showUserMenu").click();
        // Wait for mini cart to appear
        // Wait for the mini cart to show
        $("#userMenu").waitUntil(visible, Settings.timeout);
    }

    /**
     * 
     */
    public void closeUserMenu()
    {
        // Click the mini cart icon again
        $("#showUserMenu").click();
        // Move the mouse out of the area
        $("a#brand").hover();
        // Wait for mini cart to disappear
        // Wait for the mini cart to disappear
        $("#userMenu").waitUntil(not(visible), Settings.timeout);
    }

    /**
     * 
     */
    public PLogin openLogin()
    {
        openUserMenu();
        $("#userMenu .goToLogin").click();
        return page(PLogin.class);
    }

    /**
     * 
     */
    public PAccountOverView openAccountOverview()
    {
        openUserMenu();
        $("#userMenu .goToAccountOverview").click();
        return page(PAccountOverView.class);
    }
}
