/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.component;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;

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
}
