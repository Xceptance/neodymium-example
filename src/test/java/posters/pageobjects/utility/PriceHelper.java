package posters.pageobjects.utility;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import io.qameta.allure.Step;

public class PriceHelper
{
    public final static String CURRENCY = "$";

    private final static DecimalFormat decimalFormat = new DecimalFormat("##0.00", new DecimalFormatSymbols(Locale.US));

    @Step("remove $ from {price}")
    public static String removeCurrency(String price)
    {
        return price.substring(1);
    }
    
    @Step("add $ to {price}")
    public static String addCurrency(String price)
    {
        return CURRENCY + price;
    }
    
    @Step("get formatted {price}")
    public static String format(double input)
    {
        return addCurrency(decimalFormat.format(input));
    }
    
    @Step("calculate ({unitPrice} * {quantity} * 100) / 100 -> ensure two decimal places")
    public static String computeRowPrice(String unitPrice, String quantity)
    {
        double res = (double) (Math.round(Double.valueOf(removeCurrency(unitPrice)) * Double.valueOf(quantity) * 100)) / 100;
        return format(res);
    }

    @Step("calculate difference")
    public static String subtractFromPrice(String minuend, String subtrahend)
    {
        double res = (double) (Math.round((Double.valueOf(removeCurrency(minuend)) - Double.valueOf(removeCurrency(subtrahend))) * 100)) / 100;
        return format(res);
    }
    
    @Step("calculate tax")
    public static String calculateTax(String shippingCosts, String subtotal) 
    {
        double totalPrice = (double) (Math.round((Double.valueOf(removeCurrency(shippingCosts)) + Double.valueOf(removeCurrency(subtotal))) * 100)) / 100;
        double tax = (double) (Math.round((Double.valueOf(totalPrice) * 0.06) * 100)) / 100;
        return format(tax);
    }
}
