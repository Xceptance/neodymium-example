package posters.pageobjects.utility;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import io.qameta.allure.Step;

public class PriceHelper
{
    public final static String CURRENCY = "$";

    private final static DecimalFormat decimalFormat = new DecimalFormat("##0.00", new DecimalFormatSymbols(Locale.US));

    /// ========== format string ========== ///

    @Step("remove $ from '{price}'")
    public static String removeCurrency(String price)
    {
        return price.substring(1);
    }

    @Step("add $ to '{price}'")
    public static String addCurrency(String price)
    {
        return CURRENCY + price;
    }

    @Step("get formatted price")
    public static String format(double input)
    {
        return addCurrency(decimalFormat.format(input));
    }

    /// ========== calculations for price summary ========== ///

    /**
     * @return unitPrice * quantity
     */
    @Step("calculate total product price")
    public static String calculateTotalProductPrice(String unitPrice, String quantity)
    {
        double res = (double) (Math.round((Double.valueOf(removeCurrency(unitPrice)) * Double.valueOf(quantity)) * 100)) / 100;
        return format(res);
    }

    /**
     * @return minuend - subtrahend
     */
    @Step("calculate difference")
    public static String substract(String minuend, String subtrahend)
    {
        double res = (double) (Math.round((Double.valueOf(removeCurrency(minuend)) - Double.valueOf(removeCurrency(subtrahend))) * 100)) / 100;
        return format(res);
    }

    /**
     * @return oldSubtotal + totalProductPrice
     */
    // TODO - after fixing issue: put together with calculateSubtotalMiniCart()
    @Step("adds total product price to sum")
    public static double calculateSubtotalPlaceOrderPage(double oldSubtotal, String totalProductPrice)
    {
        return (double) (Math.round((oldSubtotal + Double.valueOf(removeCurrency(totalProductPrice))) * 100)) / 100;
    }

    /**
     * @return (shippingCosts + subtotal) * 0.06
     */
    @Step("calculate tax")
    public static String calculateTax(String shippingCosts, String subtotal)
    {
        double totalPrice = (double) (Math.round((Double.valueOf(removeCurrency(shippingCosts)) + Double.valueOf(removeCurrency(subtotal))) * 100)) / 100;
        double tax = (double) (Math.round((Double.valueOf(totalPrice) * 0.06) * 100)) / 100;
        return format(tax);
    }

    /**
     * @return subtotal + shippingCosts + tax
     */
    @Step("calculate grand total price")
    public static String calculateGrandTotal(String subtotal, String shippingCosts, String tax)
    {
        double grandTotal = (double) (Math.round((Double.valueOf(removeCurrency(subtotal)) + Double.valueOf(removeCurrency(shippingCosts))
                                                  + Double.valueOf(removeCurrency(tax))) * 100)) / 100;
        return format(grandTotal);
    }

    /// ========== calculations for mini cart ========== ///

    /**
     * @return totalCount + productCount
     */
    @Step("add productCount to totalCount")
    public static int calculateTotalCountMiniCart(int totalCount, String productCount)
    {
        return (totalCount + Integer.parseInt(productCount));
    }

    /**
     * @return oldSubtotal + totalProductPrice
     */
    @Step("adds total product price to sum")
    public static double calculateSubtotalMiniCart(double oldSubtotal, String totalProductPrice)
    {
        return (double) (Math.round((oldSubtotal + Double.valueOf(removeCurrency(totalProductPrice))) * 100)) / 100;
    }
}
