package com.nbcuni.test.cms.backend.tvecms.pages.platform;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.DevelPage;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.platform.PlatformEntity;
import com.nbcuni.test.cms.elements.*;
import com.nbcuni.test.cms.pageobjectutils.entities.Image;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.ImageUtil;
import com.nbcuni.test.cms.utils.SimpleUtils;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.support.FindBy;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Ivan_Karnilau
 * Updated by Alena Aukhukova
 */
public class AddPlatformPage extends MainRokuAdminPage {
    public static final String EXPECTED_TITLE_FOR_PLATFORM_CREATION = "Add platform";
    public static final String EXPECTED_WRONG_IMAGE_ERROR = "The selected file %s cannot be uploaded. Only files with the following extensions are allowed: %s.";
    public static final String NONE = "None";

    @FindBy(xpath = ".//ul[@class='vertical-tabs-list']")
    private TabsGroup tabsGroup;

    //    Basic Information Tab
    @FindBy(xpath = ".//input[@id='edit-name']")
    private TextField name;

    @FindBy(xpath = ".//input[@id='edit-machine-name']")
    private TextField machineName;

    @FindBy(xpath = ".//small[@id='edit-name-machine-name-suffix']//a")
    private Link editMachineNameLink;

    @FindBy(xpath = ".//textarea[contains(@id,'edit-field-ott-settings')]")
    private TextField settings;

    //Viewports
    @FindBy(xpath = "//*[@id='edit-field-ott-app-viewports-und']")
    private CheckBoxGroup viewportBlock;

    //    3rd Party Services Configuration
    @FindBy(xpath = ".//input[contains(@id,'edit-field-ott-mvpd-service-url')]")
    private TextField mvpdEntitlementServicesUrl;

    //    Burn-in Configuration
    @FindBy(xpath = ".//div[@id='edit-field-ott-header-brand-logo']")
    private FileUploader globalHeaderBrandLogo;

    @FindBy(xpath = ".//*[@id='edit-submit']")
    private Button saveButton;

    @FindBy(id = "edit-page-default")
    private DropDownList defaultHomepage;

    @FindBy(id = "edit-page-all-shows")
    private DropDownList defaultAllShowsPage;

    private PublishBlock publishBlock = new PublishBlock(webDriver);

    private WaitUtils waitUtils;

    public AddPlatformPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
        waitUtils = WaitUtils.perform(webDriver);
    }

    // ---------- fields for BASIC_INFORMATION Tab -----------

    public TextField elementName() {
        tabsGroup.openTabByName(TabsName.BASIC_INFORMATION.getName());
        return name;
    }

    public TextField elementMachineName() {
        tabsGroup.openTabByName(TabsName.BASIC_INFORMATION.getName());
        editMachineNameLink.click();
        return machineName;
    }

    public TextField elementSettings() {
        tabsGroup.openTabByName(TabsName.BASIC_INFORMATION.getName());
        return settings;
    }

    public CheckBoxGroup elementViewportBlock() {
        tabsGroup.openTabByName(TabsName.BASIC_INFORMATION.getName());
        return viewportBlock;
    }

    // ------------ fields for THIRD_PARTY_SERVICES_CONFIGURATION Tab -----------
    public TextField elementMvpdEntitlementServicesUrl() {
        tabsGroup.openTabByName(TabsName.THIRD_PARTY_SERVICES_CONFIGURATION.getName());
        return mvpdEntitlementServicesUrl;
    }

    // ----------------- fields for BURNIN_CONFIGURATION Tab ------------
    public FileUploader elementGlobalHeaderBrandLogo() {
        tabsGroup.openTabByName(TabsName.BURNIN_CONFIGURATION.getName());
        return globalHeaderBrandLogo;
    }

    public DropDownList elementDefaultHomepage() {
        tabsGroup.openTabByName(TabsName.BASIC_INFORMATION.getName());
        return defaultHomepage;
    }

    public DropDownList elementAllShowsPage() {
        tabsGroup.openTabByName(TabsName.BASIC_INFORMATION.getName());
        return defaultAllShowsPage;
    }

    public void setViewPorts(Map<String, Boolean> viewportsData) {
        elementViewportBlock().setCheckBoxGroup(viewportsData);
    }

    public List<String> getViewportNames() {
        return elementViewportBlock().getLabelNames();
    }

    public TvePlatformsPage createAndSavePlatform(PlatformEntity platformEntity) {
        typeName(platformEntity.getName());
        typeMachineName(platformEntity.getMachineName());
        if (platformEntity.getDefaultHomepage() != null) {
            selectDefaultHomepage(platformEntity.getDefaultHomepage().getTitle());
        }
        if (platformEntity.getDefaultAllShowsPage() != null) {
            selectDefaultAllShowsPage(platformEntity.getDefaultAllShowsPage().getTitle());
        }
        setViewPorts(platformEntity.getViewPorts());
        typeSettings(platformEntity.getSettings());
        typeMvpdEntitlementServicesUrl(platformEntity.getMvpdEntitlementServiceUrl());
        Image image = platformEntity.getGlobalHeaderBrandLogoImage();
        if (image.getPath() == null) {
            uploadGlobalHeaderBrandLogo(image);
        } else {
            uploadGlobalHeaderBrandLogo(image.getPath());
        }
        clickSaveButton();
        return new TvePlatformsPage(webDriver, aid);
    }

    /**
     * Edit Name and Machine name fields
     * @param platformEntity - entity of platform
     * @return - add platform page
     */
    public AddPlatformPage editPlatformWithUpdating(PlatformEntity platformEntity) {
        TvePlatformsPage tvePlatformsPage = this.editPlatformAndSave(platformEntity);
        AddPlatformPage addPlatformPage = tvePlatformsPage.clickEditPlatform(platformEntity.getName());
        DevelPage develPage = addPlatformPage.openDevelPage();
        platformEntity.setUuid(develPage.getUuid());
        platformEntity.setRevision(develPage.getVid());
        openEditPage();
        return new AddPlatformPage(webDriver, aid);
    }

    public TvePlatformsPage editPlatformAndSave(PlatformEntity platformEntity) {
        typeName(platformEntity.getName());
        typeMachineName(platformEntity.getMachineName());
        selectDefaultHomepage(platformEntity.getDefaultHomepage().getTitle());
        selectDefaultAllShowsPage(platformEntity.getDefaultAllShowsPage().getTitle());
        typeSettings(platformEntity.getSettings());
        setViewPorts(platformEntity.getViewPorts());
        clickSaveButton();
        return new TvePlatformsPage(webDriver, aid);
    }

    //    Basic Information Tab
    public void typeName(String value) {
        elementName().enterText(value);
    }

    public void typeMachineName(String value) {
        elementMachineName().enterText(value);
    }

    public void selectDefaultHomepage(String homepage) {
        if (homepage == null) {
            return;
        }
        elementDefaultHomepage().selectFromDropDown(homepage);
    }

    public void selectDefaultAllShowsPage(String allShowsPage) {
        if (allShowsPage == null) {
            return;
        }
        elementAllShowsPage().selectFromDropDown(allShowsPage);
    }

    public void typeSettings(String value) {
        elementSettings().enterText(value);
    }

    //    3rd Party Services Configuration
    public void typeMvpdEntitlementServicesUrl(String value) {
        elementMvpdEntitlementServicesUrl().enterText(value);
    }

    //    Burn-in Configuration
    public void uploadGlobalHeaderBrandLogo(String value) {
        elementGlobalHeaderBrandLogo().uploadFile(value);
    }

    public void uploadGlobalHeaderBrandLogo(Image image) {
        elementGlobalHeaderBrandLogo().uploadRandomImageFile(image.getWidth(), image.getHeight(), image.getExtension());
    }

    public TvePlatformsPage clickSaveButton() {
        saveButton.click();
        return new TvePlatformsPage(webDriver, aid);
    }

    public String getEntitlementsServiceUrl() {
        return elementMvpdEntitlementServicesUrl().getValue();
    }

    public String uploadRandomImageWithWrongExtension(FileUploader fileUploader, Image image) {
        String fileName = "TEMP_FILE" + System.currentTimeMillis() + image.getExtension();
        String resultPath = Config.getInstance().getPathToTempFiles() + fileName;
        File file = new File(resultPath);
        fileUploader.setFileName(file.getAbsolutePath());
        file.delete();
        return fileName;
    }

    public String getExpectedWrongImageError(String filename, String wrongExtention) {
        return String.format(EXPECTED_WRONG_IMAGE_ERROR, filename, wrongExtention);
    }

    public PublishBlock elementPublishBlock() {
        return publishBlock;
    }

    /**
     * Get all fields except images
     *
     * @return PlatformEntity object with info about platform
     */
    public PlatformEntity getPlatformInfo() {
        PlatformEntity platformEntity = new PlatformEntity();
        platformEntity.setName(elementName().getValue())
                .setMachineName(elementMachineName().getValue())
                .setSettings(elementSettings().getValue())
                .setViewPorts(elementViewportBlock().getCheckBoxGroup())
                .setMvpdEntitlementServiceUrl(elementMvpdEntitlementServicesUrl().getValue());
        String imageURL = globalHeaderBrandLogo.getURLUploadedFile();
        BufferedImage fileImage = ImageUtil.getImageFromUrl(imageURL);
        Image image = new Image().setHeight(fileImage.getHeight()).setWidth(fileImage.getWidth()).setExtension("." +
                imageURL.split("\\.")[imageURL.split("\\.").length - 1]);
        platformEntity.setGlobalHeaderBrandLogoImage(image);
        platformEntity.setDefaultHomepage(new PageForm().setTitle(elementDefaultHomepage().getSelectedValue().equals(NONE) ?
                null : elementDefaultHomepage().getSelectedValue()));
        platformEntity.setDefaultAllShowsPage(new PageForm().setTitle(elementAllShowsPage().getSelectedValue().equals(NONE) ?
                null : elementAllShowsPage().getSelectedValue()));
        return platformEntity;
    }

    @Override
    public List<String> verifyPage() {
        Utilities.logInfoMessage("Verify page " + getPageUrl() + " for missed elements");
        List<String> missedElements = new ArrayList<String>();
        if (!elementName().isVisible()) {
            missedElements.add(name.toString());
        }
        this.typeName(SimpleUtils.getRandomString(3));
        if (!elementMachineName().isVisible()) {
            missedElements.add(machineName.toString());
        }
        if (!elementViewportBlock().isVisible()) {
            missedElements.add(viewportBlock.toString());
        }
        if (!elementMvpdEntitlementServicesUrl().isVisible()) {
            missedElements.add(mvpdEntitlementServicesUrl.toString());
        }
        if (!elementGlobalHeaderBrandLogo().isVisible()) {
            missedElements.add(globalHeaderBrandLogo.toString());
        }
        if (!EXPECTED_TITLE_FOR_PLATFORM_CREATION.equals(getPageTitle())) {
            missedElements.add(EXPECTED_TITLE_FOR_PLATFORM_CREATION);
        }
        if (missedElements.isEmpty()) {
            Utilities.logInfoMessage("All elements are presented");
        } else {
            Utilities.logSevereMessageThenFail("There are missed elements: " + missedElements);
        }
        return missedElements;
    }

    private enum TabsName {
        BASIC_INFORMATION("Basic information"),
        THIRD_PARTY_SERVICES_CONFIGURATION("3rd Party Services Configuration"),
        BURNIN_CONFIGURATION("Burn-in Configuration");
        String name;

        TabsName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
