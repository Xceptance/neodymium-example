package posters.flows;

import posters.dataobjects.CreditCard;
import posters.dataobjects.User;
import posters.pageobjects.pages.user.PaymentSettingsPage;

public class RegisterUserAndAddPaymentFlow
{
    public static PaymentSettingsPage addPayment(User user, CreditCard creditCard)
    {
        return RegisterAndLoginFlow.registerAndLogin(user).userMenu.openAccountOverview().openPaymentSettings().addPayment(creditCard).isExpectedPage();
    }
}
