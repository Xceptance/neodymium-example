package com.xceptance.neodymium.scripting.template.selenide.component;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import com.xceptance.neodymium.scripting.template.selenide.page.PCategory;
import com.xceptance.neodymium.scripting.template.selenide.page.PNoHits;

public class CSearch extends BasicComponent
{

    @Override
    protected boolean isComponentAvailable()
    {
        return $("#searchForm > #s").exists();
    }

    public PNoHits noResult(String searchTerm)
    {
        search(searchTerm);
        return page(PNoHits.class);
    }

    public PCategory categoryPageResult(String searchTerm)
    {
        search(searchTerm);
        return page(PCategory.class);
    }

    private void search(String searchTerm)
    {
        openSearch();
        $("#searchForm > #s").val(searchTerm).pressEnter();
    }

    public void openSearch()
    {
        $("#header-search-trigger").click();
    }
}
