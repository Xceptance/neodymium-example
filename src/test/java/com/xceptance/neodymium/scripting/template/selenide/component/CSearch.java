package com.xceptance.neodymium.scripting.template.selenide.component;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import com.xceptance.neodymium.scripting.template.selenide.page.CategoryPage;
import com.xceptance.neodymium.scripting.template.selenide.page.NoHitsPage;

public class CSearch extends BasicComponent
{

    @Override
    protected boolean isComponentAvailable()
    {
        return $("#searchForm > #s").exists();
    }

    public NoHitsPage noResult(String searchTerm)
    {
        search(searchTerm);
        return page(NoHitsPage.class);
    }

    public CategoryPage categoryPageResult(String searchTerm)
    {
        search(searchTerm);
        return page(CategoryPage.class);
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
