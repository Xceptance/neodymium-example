package com.xceptance.neodymium.scripting.template.selenide.component;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import com.xceptance.neodymium.scripting.template.selenide.page.browsing.CategoryPage;
import com.xceptance.neodymium.scripting.template.selenide.page.browsing.NoHitsPage;

public class Search extends AbstractComponent
{

    @Override
    protected boolean exists()
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
        $("#header-search-trigger").scrollTo().click();
    }
}
