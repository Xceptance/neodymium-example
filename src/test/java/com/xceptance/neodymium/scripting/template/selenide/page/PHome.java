package com.xceptance.neodymium.scripting.template.selenide.page;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.xceptance.neodymium.scripting.template.selenide.component.CTopNav;

public class PHome extends BasicPage
{

    @Override
    public boolean isExpectedPage()
    {
        return $("#titleIndex").exists();
    }

    public CTopNav topNav()
    {
        return new CTopNav();
    }

    @Override
    public void validate()
    {
        // Verifies the company Logo and name are visible.
        $("a#brand").shouldBe(visible);

        // Verifies the Navigation bar is visible
        $("#categoryMenu .nav").shouldBe(visible);
        // Asserts there's categories in the nav bar.
        $$("#categoryMenu .nav > li.dropdown.header-menu-item").shouldHave(sizeGreaterThan(0));

        // Asserts the first headline is there.
        $("#titleIndex.h2").shouldBe(matchText("[A-Z].{3,}"));
        // Asserts the animated poster rotation is there.
        $("#pShopCarousel").shouldBe(visible);

        // Verifies the "Hot products" section is there.
        $("#main .margin_top20 .h2").shouldBe(matchText("[A-Z].{3,}"));
        // Asserts there's a list of items under "Hot Products".
        $("#productList").shouldBe(visible);
        // Asserts there's at least 1 item in the list.
        $$("#productList > li").shouldHave(sizeGreaterThan(0));
    }
}
