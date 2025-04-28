package posters.pageobjects.components;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.SelenideAddons;
import io.qameta.allure.Step;

import java.time.Duration;

import org.junit.Assert;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LocaleMenu extends AbstractComponent
{
    private SelenideElement showLocaleMenu = $("#show-locale-menu");

    private SelenideElement localeMenu = $("#locale-menu");

    @Override
    @Step("validate availability Locale Menu")
    public void assertComponentAvailable()
    {
        Assert.assertTrue(SelenideAddons.optionalWaitUntilCondition(showLocaleMenu, exist));
    }

    @Step("validate locale menu")
    public void validateStructure()
    {
        openLocaleMenu();
        validateDropdown();
        closeLocalMenu();
    }

    @Step("validate dropdown locale menu")
    private void validateDropdown()
    {
        localeMenu.findAll("a").findBy(exactText("en-US")).shouldBe(visible);
        localeMenu.findAll("a").findBy(exactText("en-UK")).shouldBe(visible);
        localeMenu.findAll("a").findBy(exactText("de-DE")).shouldBe(visible);
    }

    @Step("open locale menu")
    public void openLocaleMenu()
    {
        showLocaleMenu.click();
        localeMenu.shouldBe(visible);
    }

    @Step("close locale menu")
    public void closeLocalMenu()
    {
        $("#top-demo-disclaimer").click();
        localeMenu.shouldBe(not(visible), Duration.ofMillis(9000));
    }

    @Step("change locale to {locale}")
    public void changeLocale(String locale)
    {
        openLocaleMenu();
        localeMenu.findAll("a").findBy(exactText(locale)).click();
    }
}
