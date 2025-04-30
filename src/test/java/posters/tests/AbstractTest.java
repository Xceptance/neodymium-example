package posters.tests;

import java.lang.invoke.LambdaMetafactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xceptance.neodymium.common.browser.Browser;
import static com.codeborne.selenide.Selenide.$;

import posters.flows.OpenLoginPageFlow;
import posters.pageobjects.pages.user.LoginPage;

@Browser("Chrome_1200x768")
//@Browser("Firefox_1200x768")
public abstract class AbstractTest
{
    

    
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractTest.class);
}
