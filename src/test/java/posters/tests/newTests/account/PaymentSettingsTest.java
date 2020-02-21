package posters.tests.newTests.account;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.xceptance.neodymium.module.statement.testdata.DataSet;
import com.xceptance.neodymium.util.DataUtils;
import com.xceptance.neodymium.util.Neodymium;

import posters.dataobjects.Payments;
import posters.dataobjects.User;
import posters.flows.RegisterUserAndAddPaymentFlow;
import posters.pageobjects.pages.user.PaymentSettingsPage;
import posters.tests.AbstractTest;

public class PaymentSettingsTest extends AbstractTest
{
    private Payments payments;

    private User user;

    @Before
    public void dataSetUp()
    {
        payments = DataUtils.get(Payments.class);
        user = User.createRandomUser();
    }

    @Ignore
    @DataSet(id = "add/delete payment")
    @Test
    public void testAddPayment()
    {
        PaymentSettingsPage paymentSettingsPage = RegisterUserAndAddPaymentFlow.addPayment(user, payments.getCreditCard());

        paymentSettingsPage.validatePayment(payments.getCreditCard());
        paymentSettingsPage.validateStructure();
    }

    @Ignore

    @DataSet(id = "edit payment")
    @Test
    public void testEditPayment()
    {
        PaymentSettingsPage paymentSettingsPage = RegisterUserAndAddPaymentFlow.addPayment(user, payments.getCreditCard());

        paymentSettingsPage.editPayment(payments.getCreditCard().getCardNumber(), payments.getNewCreditCard());
        paymentSettingsPage.validatePayment(payments.getNewCreditCard());
    }

    @DataSet(id = "add/delete payment")
    @Test
    public void testDeletePayment()
    {
        PaymentSettingsPage paymentSettingsPage = RegisterUserAndAddPaymentFlow.addPayment(user, payments.getCreditCard());

        paymentSettingsPage.deletePayment(payments.getCreditCard().getCardNumber()).confirmDelete(user.getPassword());

        paymentSettingsPage.successMessage.validateSuccessMessage(Neodymium.localizedText("AccountPages.validation.addressDeletedSuccessMessage"));
        paymentSettingsPage.validatePaymentDoesntExist(payments.getCreditCard().getCardNumber());
    }
}
