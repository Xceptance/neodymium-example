package posters.pageobjects.pages.user;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;

public class MyAddressesPage extends AbstractBrowsingPage
{
    private SelenideElement title = $("#titleAddressOverview");

    @Override
    @Step("ensure this is a personal data page")
    public MyAddressesPage isExpectedPage()
    {
        super.isExpectedPage();
        title.should(exist);
        return this;
    }

    /// ----- validate my addresses page ----- ///

    @Override
    @Step("validate personal data page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // validate title
        title.shouldHave(exactText(Neodymium.localizedText("MyAddressesPage.title"))).shouldBe(visible);

        // validate shipping addresses overview
        $("#titleDelAddr").shouldHave(exactText(Neodymium.localizedText("MyAddressesPage.headlines.shipAddr"))).shouldBe(visible);
        $("#linkAddNewShipAddr").shouldHave(exactText(Neodymium.localizedText("General.button.addNewShipAddr"))).shouldBe(visible);

        // validate billing addresses overview
        $("#titleBillAddr").shouldHave(exactText(Neodymium.localizedText("MyAddressesPage.headlines.billAddr"))).shouldBe(visible);
        $("#linkAddNewBillAddr").shouldHave(exactText(Neodymium.localizedText("General.button.addNewBillAddr"))).shouldBe(visible);
    }
}