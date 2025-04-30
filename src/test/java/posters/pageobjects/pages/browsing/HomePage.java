package posters.pageobjects.pages.browsing;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import java.io.File;
import java.time.Duration;

import org.checkerframework.checker.units.qual.m;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;

public class HomePage extends AbstractBrowsingPage
{


    private SelenideElement menu = $("#mainWin #potalMainMenu");
    private SelenideElement menuLabSession = $("#mainWin #potalMainMenu #potalMainMenu_praktikasRepsonsive-tab b");
    private SelenideElement menuMyProjects = $("#mainWin #potalMainMenu #potalMainMenu_projectRepsonsive-tab b");
    private SelenideElement newProject = $("#mainWin #my-id6 #potalMainMenu_projectRepsonsive .newPLeft");
    private SelenideElement projectSelect = $("#mainWin #my-id6 #potalMainMenu_projectRepsonsive #new_project_select");
    private SelenideElement projectRegisterButton = $("#mainWin #my-id6 #potalMainMenu_projectRepsonsive .newPBtn");
    private SelenideElement projectContent = $("#mainWin #my-id6 ");
    private SelenideElement projectRegisterModal = $("#generalModalWin .uk-modal-dialog");
    private SelenideElement projectRegisterModalTitle = $("#generalModalWin .uk-modal-dialog h4");
    private SelenideElement projectRegisterModalWindow = $("#generalModalWinForm");
    private SelenideElement modalUploadSection = $("#generalModalWinForm .js-upload");
    private SelenideElement modalCloseButton = $("#generalModalWinForm .uk-modal-footer .uk-modal-close");
    private SelenideElement modalSubmitButton = $("#generalModalWinForm .uk-modal-footer .uk-button-primary");
    private SelenideElement loadingModal = $("#generalLoadWin .lds-ring");
    private SelenideElement fileUploadLink = $("#generalModalWinForm  .js-upload div > input"); 
    private SelenideElement fileUploadLoadingModal = $("[style='z-index: 1012; display: block;']");
    private SelenideElement registrationSubmissionModal = $("[uk-icon='icon: check']");
    private SelenideElement submittedProjectNotesText = $("#saveNotesTextarea-67");
       
    private SelenideElement submittedProjectTitle = $("#uk-accordion-72-title-0");
    private SelenideElement submittedProjectContent = $("#uk-accordion-72-content-0");    
    private SelenideElement submittedProjectDetails = $("#uk-accordion-72-content-0 > div.uk-child-width-1-3\\@m.uk-grid-small.uk-grid-match.uk-grid > div.uk-first-column > div");
    private SelenideElement submittedProjectFileDetails = $("#uk-accordion-72-content-0 > div.uk-child-width-1-3\\@m.uk-grid-small.uk-grid-match.uk-grid > div:nth-child(2) > div");
    private SelenideElement submittedProjectNotes = $("#uk-accordion-72-content-0 > div.uk-child-width-1-3\\@m.uk-grid-small.uk-grid-match.uk-grid > div:nth-child(3) > div");
    private SelenideElement submittedProjectNotesButton = $("#saveNotes-67");
    private ElementsCollection submittedProjectDetailsHeadings = $$("div.uk-child-width-1-3\\@m.uk-grid-small.uk-grid-match.uk-grid > div.uk-first-column > div b");
    
    private SelenideElement submittedProjectNotesSuccessMsg = $("body > div.uk-notification.uk-notification-top-center > div");

    private SelenideElement registerationFormWindow = $("#generalModalWinForm");
    private SelenideElement registerationFormCloseButton = $("#generalModalWin > div > button");
    
    
    

    @Override
    @Step("ensure this is a home page")
    public HomePage isExpectedPage()
    {
        super.isExpectedPage();
        $(".uk-section [src=\"imgs/logo.png\"]").should(exist);
        $(".uk-margin > .uk-grid .uk-width-3-4").should(exist);
        return this;
    }
    @Step("validate Project Details After Submission")
    public void validateSubmissionAfterUpload()
    {
        submittedProjectTitle.shouldHave(text(Neodymium.localizedText("homePage.submittedProjectTitle")));
        submittedProjectTitle.click();
        submittedProjectContent.shouldBe(visible);
        submittedProjectDetails.shouldBe(visible);
        submittedProjectFileDetails.shouldBe(visible);
        submittedProjectNotes.shouldBe(visible);
        //submittedProjectDetailsHeadings.shouldHave(size(13));

        for (int i = 0; i < submittedProjectDetailsHeadings.size(); i++) 
        {
            submittedProjectDetailsHeadings.get(i).should(exist);
        }
        submittedProjectNotes.click();
        //submittedProjectNotesText.should(exist);
        //submittedProjectNotesButton.scrollTo().should(exist);
        //submittedProjectNotesSuccessMsg.should(visible);

        submittedProjectDetailsHeadings.findBy(exactText(Neodymium.localizedText("homePage.project.start"))).should(exist);
        submittedProjectDetailsHeadings.findBy(exactText(Neodymium.localizedText("homePage.project.end"))).should(exist);
        submittedProjectDetailsHeadings.findBy(exactText(Neodymium.localizedText("homePage.project.title"))).should(exist);
        submittedProjectDetailsHeadings.findBy(text(Neodymium.localizedText("homePage.project.description"))).should(exist);
        submittedProjectDetailsHeadings.findBy(text(Neodymium.localizedText("homePage.project.presentation"))).should(exist);

        
    }

    

    @Step("validate approved project with details")
    public void validateProjectDetails()
    {
        submittedProjectTitle.shouldHave(text("Test Project-X"));
        submittedProjectTitle.click();

        registerationFormWindow.shouldBe(visible);
        registerationFormCloseButton.shouldBe(visible).click();
        for (int i = 0; i < submittedProjectDetailsHeadings.size(); i++) 
        {
            submittedProjectDetailsHeadings.get(i).should(exist);
        }
    } 
    
    @Step("wait for loading modal to vanish")
    public void waitForPageLoad()
    {
        loadingModal.should(disappear);
    }  

    @Step("wait for loading modal to vanish")
    public void waitForFileUpload()
    {

        fileUploadLoadingModal.should(disappear);
    }
    
    @Step("wait for loading modal to vanish")
    public void waitForSubmission()
    {
        modalSubmitButton.should(disappear);
    } 

    @Step("open register tab")
    public void openRegistrationTab()
    {
        menuMyProjects.click();
        
    }    
    
    @Step("register project tab")
    public void openRegistrationModal()
    {
        projectRegisterButton.click();
    }

    @Step("upload files for registering project")
    public void fileUpload()
    {
        File tempFile = null;
        
        // Step 1: Dynamically create the file
        try
        {
            tempFile = File.createTempFile("test_upload", ".pdf");
            System.out.println("Temporary file created at: " + tempFile.getAbsolutePath());

            SelenideElement fileInput = $("#generalModalWinForm .js-upload div > [type=\"file\"]");
            fileInput.uploadFile(tempFile);
            waitForFileUpload();
            $("#uploadedFiles").shouldHave(text("The following files will be sent when submitting:"));
            modalCloseButton.shouldBe(visible);
            modalSubmitButton.shouldBe(visible);

        }

        catch (Exception e)
        {
            e.printStackTrace();

            throw new RuntimeException("Failed to create temporary file");
        }

        finally
        {
            if (tempFile != null && tempFile.exists()) 
            {
                if (tempFile.delete()) {
                    System.out.println("Temporary file deleted: " + tempFile.getAbsolutePath());
                } else {
                    System.err.println("Failed to delete temporary file: " + tempFile.getAbsolutePath());
                }
            }
        }
    
    }

    @Step("submit file and close registraation modal")
    public void submitFile()
    {
        modalSubmitButton.click(ClickOptions.usingJavaScript());
        loadingModal.should(appear);
        loadingModal.should(disappear , Duration.ofSeconds(15));
        Selenide.sleep(5000); // Fallback wait (replace with dynamic wait if possible)

    }

    /// ========== validate content homepage ========== ///
    @Step("validate registration Modal")
    public void validateRegistrationModal()
    {

        projectRegisterModal.should(exist);
        projectRegisterModalTitle.shouldHave(exactText(Neodymium.localizedText("homePage.projectRegisterModalTitle")));
        projectRegisterModalTitle.click();
        projectRegisterModalWindow.should(exist);
        modalUploadSection.should(exist);
        modalSubmitButton.shouldHave(exactText(Neodymium.localizedText("button.projectModalSubmitButton")));
        modalCloseButton.shouldHave(exactText(Neodymium.localizedText("button.projectModalCancelButton")));
        
    }   

    @Step("validate successful login of user '{firstName}' on home page")
    public void validateSuccessfulLogin(String firstName, String lastName)
    {
        // validate {firstName} in user menu
        header.userMenu.validateLoggedInUserName(firstName, lastName);
    }   

    @Step("validate Project Section")
    public void validateProjectSection()
    {
        projectContent.should(exist);
        newProject.shouldHave(exactText(Neodymium.localizedText("homePage.newProject")));
        projectSelect.should(exist);
        projectRegisterButton.shouldHave(exactText(Neodymium.localizedText("button.projectRegisterButton")));
        
    }

    @Override
    @Step("validate structure home page")
    public void validateStructure()
    {
        super.validateStructure();

        menu.should(exist);
        menuLabSession.shouldHave(exactText(Neodymium.localizedText("homePage.menuLab")));
        menuMyProjects.shouldHave(exactText(Neodymium.localizedText("homePage.menuProjects")));
        
        waitForPageLoad();
        openRegistrationTab();
        //validate projects section
        validateProjectSection();
    }

    /// ========== homepage navigation ========== ///

    @Step("reload homepage")
    public HomePage openHomePage()
    {
        $("#header-brand").click(ClickOptions.usingJavaScript());
        return new HomePage().isExpectedPage();
    }
}
