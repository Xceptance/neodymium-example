package com.xceptance.neodymium.scripting.template.selenide.component;

import static com.codeborne.selenide.Selenide.$;

public class CHeader extends BasicComponent
{

    @Override
    protected boolean isComponentAvailable()
    {
        return $("body > header nav#globalNavigation").exists();
    }
}
