package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import io.qameta.allure.Step;
import posters.dataobjects.ShippingAddress;
import posters.pageobjects.pages.checkout.AbstractCheckoutPage;
import posters.pageobjects.pages.checkout.BillingAddressPage;
import posters.pageobjects.pages.checkout.BillingAddresssListPage;
import posters.pageobjects.pages.checkout.PaymentListPage;
import posters.pageobjects.pages.checkout.PaymentPage;

public class NewShippingAddressOverlay extends NewAddressOverlay
{

    @Override
    public void isComponentAvailable()
    {
        $("#formAddAddr").shouldBe(visible);
    }

    private void fillForm(String name, String company, String address, String city,
                          String state, String zip, String country, boolean useForBilling)
    {

        super.fillForm(name, company, address, city,
                       state, zip, country);
        if (useForBilling)
        {
            $("#billEqualShipp-Yes").click();
        }
        else
        {
            $("#billEqualShipp-No").click();
        }
    }

    public NewShippingAddressOverlay fillForm(ShippingAddress shippingAddress)
    {
        fillForm(shippingAddress.getFullName(), shippingAddress.getCompany(), shippingAddress.getAddressLine(), shippingAddress.getCity(),
                 shippingAddress.getState(), shippingAddress.getZip(),
                 shippingAddress.getCountry(), shippingAddress.isUseForBilling());
        return this;
    }

    @Override
    public AbstractCheckoutPage sendForm()
    {
        $("#btnAddAddr").click();
        return new BillingAddresssListPage();
    }

    @Step("enter new shipping address and open billing addess list page")
    public BillingAddresssListPage openBillingAddressListPage(ShippingAddress shippingAddress)
    {
        fillForm(shippingAddress.getFullName(), shippingAddress.getCompany(), shippingAddress.getAddressLine(), shippingAddress.getCity(),
                 shippingAddress.getState(), shippingAddress.getZip(),
                 shippingAddress.getCountry(), shippingAddress.isUseForBilling());
        sendForm();
        return new BillingAddresssListPage().isExpectedPage();
    }

    @Step("enter new shipping address and open payment list page")
    public PaymentListPage openPaymentListPage(ShippingAddress shippingAddress)
    {
        fillForm(shippingAddress.getFullName(), shippingAddress.getCompany(), shippingAddress.getAddressLine(), shippingAddress.getCity(),
                 shippingAddress.getState(), shippingAddress.getZip(),
                 shippingAddress.getCountry(), shippingAddress.isUseForBilling());
        sendForm();
        return new PaymentListPage().isExpectedPage();
    }

    @Step("enter new shipping address and open billing addess page")
    public BillingAddressPage openBillingAddressPage(ShippingAddress shippingAddress)
    {
        fillForm(shippingAddress.getFullName(), shippingAddress.getCompany(), shippingAddress.getAddressLine(), shippingAddress.getCity(),
                 shippingAddress.getState(), shippingAddress.getZip(),
                 shippingAddress.getCountry(), shippingAddress.isUseForBilling());
        sendForm();
        return new BillingAddressPage().isExpectedPage();
    }

    @Step("enter new shipping address and open payment page")
    public PaymentPage openPaymentPage(ShippingAddress shippingAddress)
    {
        fillForm(shippingAddress.getFullName(), shippingAddress.getCompany(), shippingAddress.getAddressLine(), shippingAddress.getCity(),
                 shippingAddress.getState(), shippingAddress.getZip(),
                 shippingAddress.getCountry(), shippingAddress.isUseForBilling());
        sendForm();
        return new PaymentPage().isExpectedPage();
    }

}
