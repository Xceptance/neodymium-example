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

    // TODO - understand use
    // Subtracts the old subtotal from the new one, rounds the result to 2 decimals (to avoid things like 19.9
    // instead of 19.90) and compares it to the price of the new item
    public static String subtractFromPrice(String from, String value)
    {
        double res = (double) (Math.round((Double.valueOf(removeCurrency(from)) - Double.valueOf(removeCurrency(value))) * 100)) / 100;
        return format(res);
    }
}
