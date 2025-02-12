package posters.pageobjects.pages.browsing;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;
import com.xceptance.neodymium.util.SelenideAddons;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class NoHitsPage extends AbstractBrowsingPage<NoHitsPage>
{

    private SelenideElement alert = $(".alert-danger");

    private ElementsCollection categoryImages = $$(".category-tile-image");

    private ElementsCollection categories = $$(".category-tile-title");

    @Override
    @Step("ensure this is a no hits page")
    public NoHitsPage assertExpectedPage()
    {
        return super.assertExpectedPage();
    }

    @Override
    @Step("check if this is a no hits page")
    public boolean isExpectedPage()
    {
        SelenideAddons.optionalWaitUntilCondition(alert, exist);
        return alert.text().contains(Neodymium.localizedText("errorMessage.noProductsFound"));
    }

    @Override
    @Step("validate structure no hits page")
    public NoHitsPage validateStructure()
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

        return this;
    }

    @Step("open homepage from no hits page")
    public HomePage openHomePage()
    {
        $("#header-brand").click(ClickOptions.usingJavaScript());
        return new HomePage().assertExpectedPage();
    }
}
