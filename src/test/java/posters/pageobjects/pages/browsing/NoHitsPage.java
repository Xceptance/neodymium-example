package posters.pageobjects.pages.browsing;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.ElementsCollection;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;

public class NoHitsPage extends AbstractBrowsingPage
{
    private ElementsCollection categoryImages = $$(".category-tile-image");

    private ElementsCollection categories = $$(".category-tile-title");

    @Override
    @Step("ensure this is a no hits page")
    public NoHitsPage isExpectedPage()
    {
        super.isExpectedPage();
        $(".alert-danger").shouldHave(exactText(Neodymium.localizedText("errorMessage.noProductsFound"))).should(exist);
        return this;
    }

    @Override
    @Step("validate structure no hits page")
    public void validateStructure()
    {
        super.validateStructure();

        // validate error message
        errorMessage.validateErrorMessage(Neodymium.localizedText("errorMessage.noProductsFound"));

        // validate message
        $("h3").shouldHave(exactText(Neodymium.localizedText("noHitsPage.message"))).shouldBe(visible);

        // validate categories
        $("h4").shouldHave(exactText(Neodymium.localizedText("noHitsPage.suggestion"))).shouldBe(visible);
        categories.findBy(exactText(Neodymium.localizedText("header.topNavigation.1.title"))).shouldBe(visible);
        categories.findBy(exactText(Neodymium.localizedText("header.topNavigation.2.title"))).shouldBe(visible);
        categories.findBy(exactText(Neodymium.localizedText("header.topNavigation.3.title"))).shouldBe(visible);
        categories.findBy(exactText(Neodymium.localizedText("header.topNavigation.4.title"))).shouldBe(visible);
        categoryImages.shouldHave(size(4));
    }

    @Step("open homepage from no hits page")
    public HomePage openHomePage()
    {
        $("#header-brand").click(ClickOptions.usingJavaScript());
        return new HomePage().isExpectedPage();
    }
}
