package posters.pageobjects.pages.browsing;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.dataobjects.Product;

// review: "Product Description:", "Select size:", "Select style:" only selectable with style class
public class ProductDetailPage extends AbstractBrowsingPage
{
    private SelenideElement addToCartButton = $("#btnAddToCart");

    private SelenideElement productName = $("#titleProductName");

    private SelenideElement productPrice = $("#prodPrice");

    private SelenideElement productSize = $("#selectSize");

    @Override
    @Step("ensure this is a product detail page")
    public ProductDetailPage isExpectedPage()
    {
        super.isExpectedPage();
        $("#addToCartForm").should(exist);
        return this;
    }

    /// ----- validate content product detail page ----- ///
    
    public void validateSizeDropdown(String size) 
    {
        productSize.shouldBe(matchText(Neodymium.localizedText(size))).should(exist);
    }
    
    @Step("validate size dropdown")
    public void validateSizeDropdown() 
    {
        productSize.scrollTo().click();
        
        if ($$("#selectSize option").size()>1) 
        {
            validateSizeDropdown("ProductdetailPage.size.16x12");
            validateSizeDropdown("ProductdetailPage.size.32x24");
            validateSizeDropdown("ProductdetailPage.size.64x48");
        }
        else validateSizeDropdown("ProductdetailPage.size.96x32");
        
        productSize.scrollTo().click();
    }
    
    @Step("validate style radio")
    public void validateStyleRadio() 
    {
        $("#selectStyle .radio:nth-of-type(1)").shouldHave(exactText(Neodymium.localizedText("ProductdetailPage.style.matte"))).shouldBe(visible);
        $("#selectStyle .radio:nth-of-type(2)").shouldHave(exactText(Neodymium.localizedText("ProductdetailPage.style.gloss"))).shouldBe(visible);
    }
    
    @Override
    @Step("validate product detail page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // validate image
        $("#prodImg").shouldBe(visible);
        
        // validate title
        productName.shouldHave(matchText("[A-Z].{3,}")).shouldBe(visible);
        
        // validate product price
        productPrice.shouldHave(matchText("\\$\\d{2}\\.\\d{2}")).shouldBe(visible);
        
        // validate product description
        $("#prodDescriptionOverview").shouldBe(visible);
        $("#prodDescriptionDetail").shouldBe(visible);
        
        // validate size selection
        validateSizeDropdown();
        
        // validate style selection
        $("#selectStyle").shouldBe(visible);
        validateStyleRadio();
        
        // validate  print information
        $("#prodPrintInfoTitle").shouldHave(exactText(Neodymium.localizedText("ProductdetailPage.print"))).shouldBe(visible);
        $("#prodPrintInfo").shouldBe(visible);
        
        // validate add to cart button
        addToCartButton.shouldHave(exactText(Neodymium.localizedText("ProductdetailPage.addToCartButton"))).shouldBe(visible);
        $("#btnAddToCart .icon-shopping-cart").shouldBe(visible);
    }
    
    @Step("validate product name on product detail page")
    public void validateProductName(String name)
    {
        productName.shouldHave(exactText(name));
    }
    
    @Step("validate product name page of product '{productName}'")
    public void validate(String productName) 
    {
        validateStructure();
        validateProductName(productName);
    }
    
    /// ----- select product size/style, add to cart ----- ///

    @Step("select size")
    public void setSize(String size)
    {
        productSize.selectOptionContainingText(size);
    }

    @Step("select style")
    public void setStyle(String style)
    {
        $(".radio #finish-" + style).selectRadio(style);
    }
    
    @Step("click add to cart button")
    public void clickAddToCartButton()
    {
        addToCartButton.scrollTo().click();
    }
    
    @Step("add product with size:'{size}' and style:'{style}' to cart")
    public void addToCart(String size, String style)
    {
        setSize(size);
        setStyle(style);
        clickAddToCartButton();
    }
    
    /// ----- get product details ----- ///
    
    @Step("get product name from product detail page")
    public String getProductName()
    {
        return productName.text();
    }
    
    @Step("get product price from product detail page")
    public String getProductPrice()
    {
        return productPrice.text();
    }
    
    @Step("get selected product style from product detail page")
    public String getChosenStyle()
    {
        return $("input[name='finish']:checked").val();
    }

    @Step("get selected product size from product detail page")
    public String getChosenSize()
    {
        return productSize.getSelectedText();
    }
    
    @Step("get product details from product detail page")
    public Product getProduct()
    {
        return new Product(getProductName(), getProductPrice(), getChosenStyle(), getChosenSize(), 1);
    }
    
    // ----------------------------------------------------------------------------------


    @Step("validate the product detail page of '{productName}' and assert visually")
    public void validateAndVisualAssert(String productName)
    {
        validateStructureAndVisual();
        validateProductName(productName);
    }
}
