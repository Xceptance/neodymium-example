/**
 * 
 */
package posters.pageObjects.utility;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * @author pfotenhauer
 */
public class PriceHelper
{
    public final static String CURRENCY = "$";

    // Calculate units * price per unit
    // calculates price per 1 unit * amount, rounds it to 2 decimals (to avoid things like 19.9 instead of 19.90)
    public static String computeRowPrice(String unitPrice, String quantity)
    {
        float res = (float) (Math.round(Float.valueOf(removeCurrency(unitPrice)) * Float.valueOf(quantity) * 100)) / 100;
        return format(res);
    }

    // Subtracts the old subtotal from the new one, rounds the result to 2 decimals (to avoid things like 19.9
    // instead of 19.90) and compares it to the price of the new item
    public static String subtractFromPrice(String from, String value)
    {
        float res = (float) (Math.round((Float.valueOf(removeCurrency(from)) - Float.valueOf(removeCurrency(value))) * 100)) / 100;
        return format(res);
    }

    private static String format(float input)
    {
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
        decimalFormat.setMinimumFractionDigits(2);
        decimalFormat.setMaximumFractionDigits(2);
        return addCurrency(decimalFormat.format(input));
    }

    public static String removeCurrency(String price)
    {
        return price.substring(1);
    }

    /**
     * @param price
     * @return
     */
    public static String addCurrency(String price)
    {
        return CURRENCY + price;
    }
}
