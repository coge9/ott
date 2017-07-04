package com.nbcuni.test.cms.backend.tvecms.pages.ottpage;

import com.nbcuni.test.cms.backend.tvecms.block.page.MachineNameBlock;
import com.nbcuni.test.cms.backend.tvecms.pages.ConfirmationPage;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Slug;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
import com.nbcuni.test.cms.elements.*;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.pageobjectutils.tvecms.panelizer.PanelizerTemplates;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.support.FindBy;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by aleksandra_lishaeva on 8/28/15.
 */
public class AddNewPage extends MainRokuAdminPage {

    public static final String PAGE_TITLE = "Add tve page";
    public static final String PAGE_EDIT_TITLE = "Edit %s";

    @FindBy(xpath = "//*[@id='edit-field-ott-page-add-app-und']/preceding-sibling::label")
    private Label platformLabel;

    @FindBy(id = "edit-advanced-options")
    private ExpanderWithLink advancedOptions;

    @FindBy(id = "edit-title")
    private TextField title;

    @FindBy(id = "edit-title-machine-name-suffix")
    private MachineNameBlock machineNameBlock;

    @FindBy(id = "edit-machine-name")
    private TextField machineNameField;

    @FindBy(id = "edit-alias")
    private TextField alias;

    @FindBy(id = "edit-field-ott-page-add-app-und")
    private DropDownList addPlatform;

    @FindBy(id = "edit-panelizer-page-manager-name")
    private DropDownList panelizerDDL;

    @FindBy(id = "edit-submit")
    private Button save;

    public AddNewPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public DropDownList elementAddPlatform() {
        return addPlatform;
    }

    public Label elementPlatformLabel() {
        return platformLabel;
    }

    public AddNewPage editMachineName(String machineName) {
        if (machineName != null) {
            if (machineNameBlock.isVisible()) {
                machineNameBlock.edit();
            }
            this.setMachineName(machineName);
        }
        return this;
    }

    public AddNewPage setMachineName(String machineName) {
        Utilities.logInfoMessage("Type Machine Name: " + machineName);
        this.advancedOptions.expand();
        this.machineNameField.enterText(machineName);
        return this;
    }

    public AddNewPage selectPlatform(CmsPlatforms platform) {
        if (platform != null) {
            Utilities.logInfoMessage("Select Platform: " + platform.getAppName());
            try {
                addPlatform.selectIgnoreCaseFromDropDown(platform.getAppName());
            } catch (NoSuchElementException e) {
                Assertion.fail(e.getMessage());
            }
        }
        return this;
    }

    public AddNewPage selectPlatform(String platform) {
        if (platform != null) {
            Utilities.logInfoMessage("Select Platform: " + platform);
            try {
                addPlatform.selectIgnoreCaseFromDropDown(platform);
            } catch (NoSuchElementException e) {
                Assertion.fail(e.getMessage());
            }
        }
        return this;
    }

    public void save() {
        this.save.click();
    }

    public String getTitle() {
        return this.title.getValue();
    }

    public AddNewPage setTitle(String title) {
        if (title != null) {
            Utilities.logInfoMessage("Type Title: " + title);
            this.title.enterText(title);
        }
        return this;
    }

    public AddNewPage setAlias(Slug alias) {
        if (alias.getSlugValue() != null) {
            Utilities.logInfoMessage("Type Alias: " + alias);
            this.advancedOptions.expand();
            pause(1);
            this.alias.enterText(alias.getSlugValue());
        }
        return this;
    }

    public EditPageWithPanelizer submit() {
        WebDriverUtil.getInstance(webDriver).scrollPageDown();
        save();
        WaitUtils.perform(webDriver).waitForPageLoad();
        return new EditPageWithPanelizer(webDriver, aid);
    }

    public ConfirmationPage delete() {
        return delete();
    }

    public PageForm setAllRequiredFields() {
        PageForm pageForm = CreateFactoryPage.createDefaultPageWithAlias();
        pageForm.setPlatform(null);
        setTitle(pageForm.getTitle());
        String selectedAppId = null;
        if (pageForm.getPlatform() == null) {
            elementAddPlatform().selectRandomValueFromDropDown();
            selectedAppId = elementAddPlatform().getSelectedValue();
        } else {
            selectedAppId = pageForm.getPlatform().getAppName();
            elementAddPlatform().selectFromDropDown(selectedAppId);
        }
        setAlias(pageForm.getAlias());
        pageForm.setPlatform(CmsPlatforms.getPlatform(selectedAppId));
        return pageForm;
    }

    public PageForm createPage(PageForm pageForm) {
        this.setTitle(pageForm.getTitle())
                .editMachineName(pageForm.getMachineName())
                .setAlias(pageForm.getAlias())
                .selectPanelizerTemplate(pageForm.getLayout());
        if (pageForm.getStringPlatform() != null) {
            this.selectPlatform(pageForm.getStringPlatform());
        } else {
            this.selectPlatform(pageForm.getPlatform());
        }
        this.save();
        WaitUtils.perform(webDriver).waitForPageLoad();
        EditPageWithPanelizer editPage = new EditPageWithPanelizer(webDriver, aid,
                pageForm.getLayout() != null ? pageForm.getLayout() : PanelizerTemplates.DEFAULT);
        pageForm.setPageID(editPage.getPageId());
        pageForm.setRevision(editPage.getRevisionEntity());
        pageForm.setUuid(editPage.getUuidEntity());
        return pageForm;
    }

    // Setters for text
    // ---------------------------------------------------------------------------//
    public PageForm setRequiredFieldsForNewPageAndSave() {
        PageForm pageForm = setAllRequiredFields();
        save();
        Utilities.logInfoMessage("Set random text values to required fields for New OTT page");
        return pageForm;
    }

    @Override
    public List<String> verifyPage() {
        Utilities.logInfoMessage("Verify page " + getPageUrl() + " for missed elements");
        final ArrayList<String> missedElements = new ArrayList<>();
        final Class<?> thisClass = this.getClass();
        final Field[] fields = thisClass.getDeclaredFields();
        for (final Field field : fields) {
            verifySingleField(missedElements, field);
        }
        return missedElements;
    }

    private void verifySingleField(ArrayList<String> missedElements, Field field) {
        try {
            if (field.getType().equals(String.class) && field.getName().contains("_XPATH")) {
                final String fieldLocator = field.get(this).toString();
                if (!webDriver.isVisible(fieldLocator)) {
                    missedElements.add("Element: " + field.getName() + ", Locator: " + fieldLocator);
                    WebDriverUtil.getInstance(webDriver).attachScreenshot();
                    missedElements.trimToSize();
                }
            }
        } catch (final IllegalArgumentException e) {
            Utilities.logSevereMessageThenFail(e.getClass().getName() + ": " + Utilities.convertStackTraceToString(e));
        } catch (final IllegalAccessException e) {
            Utilities.logSevereMessageThenFail(e.getClass().getName() + ": " + Utilities.convertStackTraceToString(e));
        }
    }

    public boolean isPanelizerPresentAtDDL(String panelizerName) {
        Utilities.logInfoMessage("Check rather panelizer is present within DLL at the OTT Page :" + panelizerName);
        List<String> values = panelizerDDL.getValuesToSelect();
        return values.contains(panelizerName);
    }

    public AddNewPage selectPanelizerTemplate(PanelizerTemplates panelizerTemplate) {
        if (panelizerTemplate != null) {
            Utilities.logInfoMessage("Select Template :" + panelizerTemplate);
            panelizerDDL.selectFromDropDown(panelizerTemplate.getTitle());
        }
        return this;
    }

    public AddNewPage selectPanelizerTemplate(String panelizerTemplate) {
        if (panelizerTemplate != null) {
            Utilities.logInfoMessage("Select Template :" + panelizerTemplate);
            panelizerDDL.selectFromDropDown(panelizerTemplate);
        }
        return this;
    }

}
