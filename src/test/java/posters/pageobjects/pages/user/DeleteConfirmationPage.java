package posters.pageobjects.pages.user;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;

public class DeleteConfirmationPage extends AbstractBrowsingPage
{
    private SelenideElement passwordField = $("#password");

    private SelenideElement deleteButton = $x("//button[contains(@id,'btnDelete')]");

    @Override
    public DeleteConfirmationPage isExpectedPage()
    {
        $x("//form[contains(@id,'formDelete')]").shouldBe(visible);
        return this;
    }

    @Override
    public DeleteConfirmationPage validateStructure()
    {
        isExpectedPage();
        passwordField.shouldBe(visible);
        deleteButton.shouldBe(visible);
        return this;
    }

    @Step("confirm delete")
    public void confirmDelete(String password)
    {
        passwordField.setValue(password);
        deleteButton.click();
    }

}
