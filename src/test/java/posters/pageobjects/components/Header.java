package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchesText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

public class Header extends AbstractComponent
{
    // logo and searchbar
    public SelenideElement header = $("#globalNavigation");
    public SelenideElement brand = $("#brand");
    public SelenideElement searchbar = $("#s");
    public SelenideElement searchBarButton = $("#btnSearch");
    public SelenideElement searchBarButtonIcon = $(".icon-search");
    
    // navigation
    public ElementsCollection navComponents = $$(".has-dropdown");
    public ElementsCollection subNavComponents = $$("ul.dropdown-menu li");
    
    // user menu
    public ElementsCollection userMenu = $$("#userMenu li");
    public SelenideElement user = $("#showUserMenu");
    public SelenideElement userIcon = $(".icon-user2");
    public SelenideElement addUserIcon = $("#userMenu .icon-user-add-outline");
    public SelenideElement logInIcon = $("#userMenu .icon-log-in");
    
    // shopping cart
    public ElementsCollection items = $$("#miniCartMenu .cartMiniHeader");
    public ElementsCollection itemCount = $$("#count_wideView");
    public ElementsCollection subtotal = $$("#miniCartMenu .cartMiniSubOrderTotal ");
    public ElementsCollection viewCart = $$("#miniCartMenu .linkButton");
    public SelenideElement cart = $("#headerCartOverview");
    public SelenideElement cartIcon = $(".icon-shopping-cart");
    
    public ElementsCollection saleBanner = $$(".owl-stage");
    
    // validate availability header
    public void isComponentAvailable()
    {
        header.should(exist);
    }
    
    // validate name of components in navigation
    private void validateNavComponent(String component) 
    {
        navComponents.findBy(exactText(Neodymium.localizedText(component))).shouldBe(visible);
    }

    // validate name of components in sub navigation
    private void validateSubNavComponent(String component, String subComponent) 
    {
        subNavComponents.findBy(matchesText(Neodymium.localizedText(subComponent))).shouldBe(visible);
    }
    
    // validate strings in user menu
    private void validateUserMenu(String text) 
    {
        userMenu.findBy(exactText(Neodymium.localizedText(text))).shouldBe(visible);
    }
    
    // TODO - find better solution
    // validate strings sale banner
    private void validateSaleBanner(String text) 
    {
        try
        {
            Thread.sleep(6500);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        saleBanner.findBy(exactText(Neodymium.localizedText(text))).shouldBe(visible);
    }
    
    public void validateStructure() 
    {
        // validate availability header
        isComponentAvailable();
        
        // validate company logo
        brand.shouldBe(visible);
        
        // validate searchbar
        searchbar.shouldBe(visible);
        searchBarButton.shouldBe(visible);
        searchBarButtonIcon.shouldBe(visible);
        
        // validate navigation bar
        validateNavComponent("header.worldOfNature");
        validateNavComponent("header.dining");
        validateNavComponent("header.transportation");
        validateNavComponent("header.panoramas");

        // validate sub navigation "World Of Nature"
        moveCursor(navComponents.findBy(exactText(Neodymium.localizedText("header.worldOfNature"))));
        validateSubNavComponent("header.worldOfNature", "header.animals");
        validateSubNavComponent("header.worldOfNature", "header.flowers");
        validateSubNavComponent("header.worldOfNature", "header.trees");
        
        // validate sub navigation "Dining"
        moveCursor(navComponents.findBy(exactText(Neodymium.localizedText("header.dining"))));
        validateSubNavComponent("header.worldOfNature", "header.coldCuts");
        validateSubNavComponent("header.worldOfNature", "header.mainDishes");
        validateSubNavComponent("header.worldOfNature", "header.sweets");
        
        // validate sub navigation "Transportation"
        moveCursor(navComponents.findBy(exactText(Neodymium.localizedText("header.transportation"))));
        validateSubNavComponent("header.worldOfNature", "header.airTravel");
        validateSubNavComponent("header.worldOfNature", "header.classicCars");
        validateSubNavComponent("header.worldOfNature", "header.railways");
        
        // validate sub navigation "Panoramas"
        moveCursor(navComponents.findBy(exactText(Neodymium.localizedText("header.panoramas"))));
        validateSubNavComponent("header.worldOfNature", "header.architecture");
        validateSubNavComponent("header.worldOfNature", "header.fireworks");
        validateSubNavComponent("header.worldOfNature", "header.landscapes");
        validateSubNavComponent("header.worldOfNature", "header.xxlPanoramas");
        
        // validate user menu
        userIcon.shouldBe(visible);
        moveCursor(user);
        validateUserMenu("header.userMenu.greeting");
        validateUserMenu("header.userMenu.createAccount");
        validateUserMenu("header.userMenu.signIn");
        addUserIcon.shouldBe(visible);
        logInIcon.shouldBe(visible);
        
        // validate shopping cart menu
        cartIcon.shouldBe(visible);
        itemCount.findBy(matchesText("\\d")).shouldBe(visible);
        moveCursor(cart);
        items.findBy(matchesText(Neodymium.localizedText("header.shoppingCart.itemsInCart"))).shouldBe(visible);
        subtotal.findBy(matchesText(Neodymium.localizedText("header.shoppingCart.subtotal"))).shouldBe(visible);
        subtotal.findBy(matchesText("\\$\\d\\.\\d")).shouldBe(visible);
        viewCart.findBy(exactText(Neodymium.localizedText("header.shoppingCart.viewCart"))).shouldBe(visible);
        
        // validate sale banner
        validateSaleBanner("header.sale.second");
        validateSaleBanner("header.sale.first");
    }
}
