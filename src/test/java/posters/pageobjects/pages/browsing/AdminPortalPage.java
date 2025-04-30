package posters.pageobjects.pages.browsing;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.switchTo;

import java.time.Duration;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.components.LoginHeader;

public class AdminPortalPage extends AbstractBrowsingPage 
{
    public LoginHeader loginHeader = new LoginHeader();
    
    private SelenideElement regWindowTitle = $("#acceptNewRegWin > div > div > h4");
    private SelenideElement regWindowBody = $("#acceptNewRegForm > div.uk-modal-body.uk-overflow-auto");
    private SelenideElement regWindowDecline = $("#acceptNewRegForm > div.uk-modal-footer.uk-text-right > button:nth-child(1)");
    private SelenideElement regWindowApprove = $("#acceptNewRegForm > div.uk-modal-footer.uk-text-right > button:nth-child(2)");

    private SelenideElement header = $("#ext-gen19");
    private SelenideElement mainPanelApp = $("#mainPanelApp #ext-gen30 #ext-gen54");
    private SelenideElement adminUserGridRow = $("#bjt_grid_body_adminUserGrid2 > div.x-grid3-row.canceled.x-grid3-row-first");
    private SelenideElement adminUserGridCell = $("#bjt_grid_body_adminUserGrid2 > div.x-grid3-row.canceled.x-grid3-row-first > table > tbody > tr > td.x-grid3-col.x-grid3-cell.x-grid3-td-4 > div > span");
    private SelenideElement contextMenuOption = $(".x-menu-floating #ext-comp-1247");

    private SelenideElement projectEditWindow = $("#projectEditWin");
    private SelenideElement gridRowProjectDetails = $("#bjt_grid_body_adminUserGrid2 > div.x-grid3-row.processing.x-grid3-row-first");
    private SelenideElement contextMenuOptionDetails = $("#ext-comp-1247");
    private SelenideElement projectName = $("#ext-comp-1255");
    private SelenideElement startDate = $("#planned_start");
    private SelenideElement endDate = $("#envenddate");
    private SelenideElement researchType = $("#type_of_research");
    private SelenideElement tasksButton = $("#ext-gen693");
    private SelenideElement tasksButtonSaveDetails = $("#ext-comp-1251");
    private SelenideElement tasksButtonCancelProject = $("#ext-comp-1254");
    private SelenideElement tasksButtonCancelNow = $("#projectEditWinCancelWin button");

    private SelenideElement gridRowProjectAccepted = $("#bjt_grid_body_adminUserGrid2 > div.x-grid3-row.realinprogress.x-grid3-row-first");
    private SelenideElement contextMenuOptionAccepted = $("#ext-comp-1248");



    @Override
    @Step("ensure this is the portal home page")
    public AdminPortalPage isExpectedPage()
    {
        super.isExpectedPage();
        $("#ext-gen19").should(exist).shouldBe(visible);
        $("#ext-gen32").should(exist).shouldBe(visible);
        return this;
    }

    @Step("approve/decline")
    public void approveProject()
    {
        adminUserGridRow.shouldBe(visible);
        adminUserGridCell.shouldBe(visible).shouldHave(exactText(Neodymium.localizedText("adminPortal.projectPage.studentName")));

        adminUserGridRow.contextClick();
        contextMenuOption.click();
        validateRegisterWindow();
        
        // Switch into the iframe containing the registration window
        switchTo().frame($("#accept_or_decline_window iframe"));

        // Click the approve button on the registration window
        regWindowApprove.click();
        regWindowApprove.should(disappear);
        // Switch back to the default content from the iframe
        switchTo().defaultContent();
    }

    @Step("Confirm Details of Project in Admin Portal")
    public void  confirmDetails()
    {
        gridRowProjectDetails.contextClick();
        contextMenuOptionDetails.click();

        projectEditWindow.shouldBe(visible);
        projectName.shouldBe(visible);
        startDate.shouldBe(visible);
        endDate.shouldBe(visible);
        researchType.shouldBe(visible);

        projectName.val("Test Project-X");
        startDate.val("2025-15-01");
        endDate.val("2025-16-31");

        researchType.click();
        $("#ext-gen714 > div:nth-child(2)").click();
        //researchType.val("Test Research");

        tasksButton.click();
        tasksButtonSaveDetails.click();

        projectEditWindow.should(disappear, Duration.ofSeconds(15));
    }

        
    @Step("cancel project")
    public void cancelProject()
    {
        gridRowProjectAccepted.contextClick();
        contextMenuOptionAccepted.click();

        tasksButton.shouldBe(visible).click();
        tasksButtonCancelProject.shouldBe(visible).click();
        tasksButtonCancelNow.shouldBe(visible).click();
    }

    @Step("everything admin portal")
    public AdminPortalPage sendLoginForm(String email, String password)
    {

        $("#ext-gen9 #ext-comp-1197").should(exist).shouldBe(visible);
        $("#ext-gen9 #ext-comp-1197 #ext-gen42").shouldBe(visible);

        // fill out the login form
        $("#loginUsername").val(email);
        $("#loginPwd").val(password);

        // click on the Sign In button.
        $("#ext-gen37").click();

        return new AdminPortalPage().isExpectedPage();
    }

    @Step("everything admin portal")
    public void validateRegisterWindow()
    {
        // Switch into the iframe containing the registration window
        switchTo().frame($("#accept_or_decline_window iframe"));

        // Validate that the registration window title exists and is visible
        regWindowTitle.should(exist).shouldBe(visible);
        
        // Validate that the registration window body is visible
        regWindowBody.shouldBe(visible);
        
        // Switch back to the default content from the iframe
        switchTo().defaultContent();
    }

    @Step("Decline Project in Admin Portal")
    public void declineProject()
    {
        // Switch into the iframe containing the registration window
        switchTo().frame($("#accept_or_decline_window iframe"));

        // Click the close button on the registration window
        regWindowDecline.click();

         // Switch back to the default content from the iframe
         switchTo().defaultContent();       
    }
      
    @Override
    @Step("validate structure home page")
    public void validateStructure()
    {
        mainPanelApp.click();
        

        //$("#bjt_grid_body_adminUserGrid2 > div.x-grid3-row.complete.x-grid3-row-first").shouldBe(visible).should(exist);
       
    }
}
