package posters.pageobjects.pages.checkout;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;
import com.xceptance.neodymium.util.SelenideAddons;
import io.qameta.allure.Step;
import posters.tests.testdata.dataobjects.CreditCard;

import java.time.LocalDate;
import java.time.Year;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class GuestPaymentPage extends AbstractCheckoutPage<GuestPaymentPage>
{
    private SelenideElement title = $("#title-payment");

    private SelenideElement creditCardNumber = $("#creditcard-number");

    private SelenideElement creditCardName = $("#name");

    private SelenideElement expirationMonth = $("#expiration-date-month");

    private SelenideElement expirationYear = $("#expiration-date-year");

    private SelenideElement addPaymentButton = $("#btn-add-payment");

    @Override
    @Step("ensure this is a payment page")
    public GuestPaymentPage assertExpectedPage()
    {
        return super.assertExpectedPage();
    }

    @Override
    @Step("check if this is a payment page")
    public boolean isExpectedPage()
    {
        SelenideAddons.optionalWaitUntilCondition(title, exist);
        return title.exists();
    }

    /// ========== validate content guest payment page ========== ///

    @Step("validate process wrap")
    public void validateProcessWrap()
    {
        for (int i = 1; i <= 6; i++)
        {
            $(".progress-step-" + i + " .progress-bubble").shouldHave(exactText(Neodymium.localizedText("checkoutHeader." + i + ".number"))).shouldBe(visible);
            $(".progress-step-" + i + " .progress-bubble-caption").shouldHave(exactText(Neodymium.localizedText("checkoutHeader." + i + ".name")))
                                                                  .shouldBe(visible);
        }
    }

    private void validateFillInHeadlines(String headline)
    {
        $$(".form-group label").findBy(exactText(headline)).shouldBe(visible);
    }

    @Step("validate fill-in form headlines")
    public void validateFillInHeadlines()
    {
        validateFillInHeadlines(Neodymium.localizedText("fillIn.inputDescription.cardNumber"));
        validateFillInHeadlines(Neodymium.localizedText("fillIn.inputDescription.cardHolderName"));
        validateFillInHeadlines(Neodymium.localizedText("fillIn.inputDescription.expirationDate"));
    }

    @Step("validate fill-in form placeholder")
    public void validateFillInPlaceholder()
    {
        creditCardNumber.shouldHave(attribute("placeholder", (Neodymium.localizedText("fillIn.placeholder.cardNumber")))).shouldBe(visible);
        creditCardName.shouldHave(attribute("placeholder", (Neodymium.localizedText("fillIn.placeholder.cardHolderName")))).shouldBe(visible);

        String month = Integer.toString(LocalDate.now().getMonthValue());

        if (LocalDate.now().getMonthValue() < 10)
        {
            month = "0" + Integer.toString(LocalDate.now().getMonthValue());
        }

        expirationMonth.find("[selected]").shouldHave(exactText(month)).shouldBe(visible);
        expirationYear.find("[selected]").shouldHave(exactText(Integer.toString(LocalDate.now().getYear()))).shouldBe(visible);
    }

    @Step("validate month dropdown")
    public void validateMonthDropdown()
    {
        // open dropdown
        expirationMonth.click(ClickOptions.usingJavaScript());

        // validate months
        for (int i = 1; i <= 12; i++)
        {
            $$("#expiration-date-month").findBy(matchText(Neodymium.localizedText("fillIn.dropdown.expireMonth." + i))).shouldBe(visible);
        }
    }

    @Step("validate year dropdown")
    public void validateYearDropdown()
    {
        // open dropdown
        expirationYear.click(ClickOptions.usingJavaScript());
        int currentYear = Year.now().getValue();

        // validate years
        for (int i = 0; i < 11; i++)
        {
            $$("#expiration-date-year").findBy(matchText(String.valueOf(currentYear + i))).shouldBe(visible);
        }
    }

    @Override
    @Step("validate payment page structure")
    public GuestPaymentPage validateStructure()
    {
        super.validateStructure();

        // validate process wrap
        validateProcessWrap();

        // validate title
        title.shouldHave(exactText(Neodymium.localizedText("guestPaymentPage.title"))).shouldBe(visible);

        // validate fill in form headlines
        validateFillInHeadlines();

        // validate placeholder
        validateFillInPlaceholder();

        // validate month dropdown
        validateMonthDropdown();

        // validate year dropdown
        validateYearDropdown();

        // validate "required fields" string
        $(".me-auto").shouldHave(exactText(Neodymium.localizedText("fillIn.inputDescription.requiredFields"))).shouldBe(visible);

        // validate continue button
        addPaymentButton.shouldHave(exactText(Neodymium.localizedText("button.continue"))).shouldBe(visible);

        return this;
    }

    /// ========== send payment form ========== ///

    private PlaceOrderPage goToPlaceOrderPage(String number, String name, String month, String year)
    {
        // fill in form with parameters
        creditCardNumber.val(number);
        creditCardName.val(name);
        expirationMonth.selectOption(month);
        expirationYear.selectOption(year);

        // go to place order page
        addPaymentButton.click(ClickOptions.usingJavaScript());

        return new PlaceOrderPage().assertExpectedPage();
    }

    @Step("fill and send payment form with '{creditCard}'")
    public PlaceOrderPage goToPlaceOrderPage(CreditCard creditCard)
    {
        return goToPlaceOrderPage(creditCard.getCardNumber(), creditCard.getFullName(), creditCard.getExpDateMonth(), creditCard.getExpDateYear());
    }
}
