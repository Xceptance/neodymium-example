package com.xceptance.neodymium.scripting.template.selenide.component;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import com.xceptance.neodymium.scripting.template.selenide.page.NoHitsPage;

public class CSearch extends BasicComponent
{

    @Override
    protected boolean isComponentAvailable()
    {
        return $("#searchForm > #s").exists();
    }

    public NoHitsPage noResult(String text)
    {
        $("#header-search-trigger").click();
        $("#searchForm > #s").val(text).pressEnter();
        return page(NoHitsPage.class);
    }

}
