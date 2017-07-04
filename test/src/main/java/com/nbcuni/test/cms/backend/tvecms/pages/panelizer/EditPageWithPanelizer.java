package com.nbcuni.test.cms.backend.tvecms.pages.panelizer;

import com.nbcuni.test.cms.backend.tvecms.block.ModuleInfoBlock;
import com.nbcuni.test.cms.backend.tvecms.block.PanelizerContentBlock;
import com.nbcuni.test.cms.backend.tvecms.block.panelizer.contentblock.LayoutPanelizerBlock;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Slug;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.Module;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
import com.nbcuni.test.cms.elements.*;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.pageobjectutils.tvecms.panelizer.PanelizerTemplates;
import com.nbcuni.test.cms.utils.*;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Aleksandra_Lishaeva on 12/23/15.
 */
public class EditPageWithPanelizer extends MainRokuAdminPage {

    private String newTitle;
    private String pageId = null;
    private Slug newalias = new Slug();
    private static final String ADD_CONTTENT_POP_UP = "//div[@id='modal-content']";
    private PublishBlock publishBlock;
    private LayoutPanelizerBlock panelizerBlock;
    //page elements

    @FindBy(id = "edit-alias")
    private TextField alias;

    @FindBy(className = "machine-name-value")
    private Label machineName;

    @FindBy(xpath = "//input[@id='edit-no-blocks']")
    private CheckBox disableBlock;

    @FindBy(xpath = "//div[@class='ctools-dropdown-container-wrapper']//a[contains(text(),'Add module')]")
    private Link addContent;

    @FindBy(id = "edit-cancel")
    private Button cancel;

    @FindBy(xpath = ".//input[contains(@id,'edit-title')]")
    private TextField title;

    @FindBy(xpath = ".//input[contains(@id,'panels-dnd-save')]")
    private Button save;

    @FindBy(xpath = "//label[contains(@for,'page-add-app')]")
    private Label platformLabel;

    @FindBy(xpath = ".//*[contains(@id,'edit-field-ott-page-add-app')]")
    private DropDownList platforms;

    @FindBy(xpath = "//ul[@class='ott-page-preview-edit-links']/li[contains(@class,'edit')]/a")
    private Link edit;

    @FindBy(xpath = "//ul[@class='ott-page-preview-edit-links']/li[contains(@class,'preview')]/a")
    private Link preview;

    @FindBy(xpath = "//*[contains(@id,'edit-page-tabs')]")
    private LinkGroup pageTab;

    @FindBy(id = "edit-advanced-options")
    private ExpanderWithLink advancedOptions;

    @FindBy(xpath = "//*[contains(@id,'panel-region-row')]")
    private List<PanelizerContentBlock> panelizerContentBlock;

    @FindBy(xpath = ".//div[contains(@id,'panel-pane')]")
    private List<ModuleInfoBlock> moduleInfoBlocks;

    @FindBy(id = "panels-dnd-main")
    private WebElement panelBlock;

    public EditPageWithPanelizer(CustomWebDriver webDriver, AppLib aid, PanelizerTemplates panelizerTemplates) {
        this(webDriver, aid);
        try {
            Constructor constr = panelizerTemplates.getClazz().getConstructor(WebElement.class);
            constr.setAccessible(true);
            panelizerBlock = (LayoutPanelizerBlock) constr.newInstance(panelBlock);
            publishBlock = new PublishBlock(webDriver);
        } catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
            Utilities.logSevereMessageThenFail(Utilities.convertStackTraceToString(e));
        }
    }

    public EditPageWithPanelizer(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
        publishBlock = new PublishBlock(webDriver);
    }

    public LinkGroup elementPageTab() {
        return pageTab;
    }

    public PublishBlock elementPublishBlock() {
        return publishBlock;
    }

    public String getTitle() {
        return title.getValue();
    }

    public EditPageWithPanelizer setTitle(String... panelizerTitle) {
        if (panelizerTitle.length > 0) {
            title.enterText(panelizerTitle[0]);
        } else {
            newTitle = "AQAPageTitlePanelizer" + SimpleUtils.getRandomString(2);
            title.enterText(newTitle);
        }
        return this;
    }

    public String getNewTitle() {
        return newTitle;
    }

    public String getPlatformLabel() {
        return platformLabel.getText();
    }

    public List<ModuleInfoBlock> getModuleInfoBlocks() {
        return moduleInfoBlocks;
    }

    public Slug getAlias() {
        newalias.setAutoSlug(false).setSlugValue(alias.getValue());
        return newalias;
    }

    public EditPageWithPanelizer setAlias(Slug slug) {
        if (!slug.isObjectNull()) {
            advancedOptions.expand();
            alias.enterText(slug.getSlugValue());
        }
        return this;
    }

    public List<Module> getModules() {
        Utilities.logInfoMessage("Getting the shelf ids of the page assigned");
        List<Module> modules = new ArrayList<>();
        for (ModuleInfoBlock block : moduleInfoBlocks) {
            modules.add(block.getModuleInfo());
        }
        return modules;
    }

    public EditPageWithPanelizer setModules(List<Module> shelf) {
        for (Module sh : shelf) {
            setModule(sh.getTitle());
        }
        return this;
    }

    public String save() {
        save.click();
        WaitUtils.perform(webDriver).waitForPageLoad();
        return newTitle;
    }

    public List<String> getModulesName() {
        Utilities.logInfoMessage("Getting the modules Name of the page assigned");
        List<String> moduleName = new ArrayList<>();
        for (ModuleInfoBlock block : moduleInfoBlocks) {
            moduleName.add(block.getModuleName());
        }
        return moduleName;
    }

    public PageForm getAllFieldsForPage() {
        PageForm text = new PageForm();
        text.setTitle(getTitle()).
                setAlias(getAlias()).
                setModules(getModules()).
                setTime(DateUtil.getCurrentDate(DateUtil.UTC_TIME_FORMAT, "UTC")).
                setPlatform(CmsPlatforms.getPlatform(platforms.getSelectedValue())).
                setPageID(getPageId());
        Utilities.logInfoMessage("Get text values of all fields of the OTT page");
        return text;
    }

    public void checkDisableBlock(boolean status) {
        Utilities.logInfoMessage("Set disable drupal block to: " + status);
        if (status) {
            disableBlock.check();
        } else disableBlock.uncheck();
    }

    public EditPageWithPanelizer setAllRequiredFields(PageForm pageForm) {
        setTitle(pageForm.getTitle());
        setAlias(pageForm.getAlias());
        return this;
    }

    public EditPageWithPanelizer setModule(String shelf) {
        webDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        for (PanelizerContentBlock block : panelizerContentBlock) {
            if (!block.isInfoBlockVisible()) {
                block.clickAddContent();
                break;
            }
        }
        webDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        ModuleAddPage addPage = new ModuleAddPage(webDriver, aid);
        Assertion.assertTrue(addPage.verifyPage().isEmpty(), "Some elements are missed from the Page", webDriver);
        Utilities.logInfoMessage("All elements are present at the select a module Page within Pop-up menu");
        addPage.selectShelf(shelf);
        addPage.clickFinish();
        WaitUtils.perform(webDriver).waitForPageLoad();
        pause(2);
        return this;
    }

    /**
     * Setter to the all fields for a page
     *
     * @param shelves - list of modules to update
     * @return page from entity
     */
    public PageForm updateFields(List<Module> shelves) {
        PageForm pageForm = CreateFactoryPage.createDefaultPageWithAlias();
        setAllRequiredFields(pageForm);
        setModules(shelves);
        pageForm.setModules(getModules()).setPageID(getPageId()).setPlatform(CmsPlatforms.valueOf(platforms.getSelectedValue().toUpperCase())).setTime(DateUtil.getCurrentDate(DateUtil.UTC_TIME_FORMAT, "UTC"));
        pageForm.setUuid(getUuidEntity());
        Utilities.logInfoMessage("Set random values to text fields and " + shelves + " to the shelf");
        return pageForm;
    }

    public ModuleAddPage clickAddContent() {
        Utilities.logInfoMessage("Add New Content");
        panelizerContentBlock.get(0).clickAddContent();
        Assertion.assertTrue(webDriver.isVisible(ADD_CONTTENT_POP_UP), "The pop-up Menu is not Present", webDriver);
        Utilities.logInfoMessage("The pop-up menu is present");
        return new ModuleAddPage(webDriver, aid);
    }

    public boolean isAddContentPresent() {
        Utilities.logInfoMessage("Check rather Add Content Link Present");
        panelizerContentBlock.get(0).clickBlockActionLink();
        return addContent.isPresent();
    }

    public PanelizerContentBlock getModuleBlock(String shelfName) {
        try {
            for (PanelizerContentBlock block : panelizerContentBlock) {
                if (block.getModuleName().equalsIgnoreCase(shelfName)) {
                    return block;
                }
            }
        } catch (NullPointerException e) {
            Utilities.logSevereMessage("The block is not present more");
        }
        return null;
    }

    public List<String> getContentBlockNames() {
        List<String> titles = new ArrayList<>();
        for (PanelizerContentBlock contentBlock : panelizerContentBlock) {
            String name = contentBlock.getModuleName();
            if (name != null) {
                titles.add(name);
            }
        }
        return titles;
    }

    public List<PanelizerContentBlock> getContentBlocks() {
        return panelizerContentBlock;
    }

    public void cancel() {
        cancel.click();
    }

    public boolean isPlatformsDropDown() {
        return platforms.isPresent();
    }

    public String getPlatform() {
        return platforms.getSelectedValue();
    }

    public Map<String, Set<String>> getPagesTitlesMap() {
        Map<String, Set<String>> pagesTitlesMap = new HashMap<>();
        List<String> platformsNames = platforms.getValuesToSelect();
        for (String platformName : platformsNames) {
            platforms.selectFromDropDown(platformName);
            if (WebDriverUtil.getInstance(webDriver).isAlertPresent()) {
                WebDriverUtil.getInstance(webDriver).acceptAlert(5);
            }
            WaitUtils.perform(webDriver).waitForPageLoad();
            if (!isWarningMessagePresent()) {
                pagesTitlesMap.put(platformName, this.getPageTabNames());
            } else {
                pagesTitlesMap.put(platformName, new HashSet<String>());
            }
        }
        return pagesTitlesMap;
    }

    public Set<String> getPageTabNames() {
        Set<String> pageTabNames = new HashSet<>();
        for (WebElement pagesTab : this.pageTab.getAllLinkElements()) {
            pageTabNames.add(pagesTab.getText());
        }
        return pageTabNames;
    }

    public SoftAssert checkLayout(SoftAssert softAssert) {
        return panelizerBlock.checkLayout(softAssert);
    }

    public boolean isEditLinkPresent() {
        return edit.isPresent();
    }

    public boolean isPreviewLinkPresent() {
        return preview.isPresent();
    }

    public void clickEditLink() {
        edit.click();
        WaitUtils.perform(webDriver).waitForPageLoad();
    }

    public PreviewPage clickPreviewLink() {
        preview.click();
        WaitUtils.perform(webDriver).waitForPageLoad();
        return new PreviewPage(webDriver, aid);
    }

    public String getPageId() {
        return machineName.getText();
    }

    public EditPageWithPanelizer cleanUpModules(List<Module> modules) {
        for (PanelizerContentBlock contentBlock : panelizerContentBlock) {
            for (Module module : modules) {
                if (module.getTitle().equalsIgnoreCase(contentBlock.getModuleName()))
                    contentBlock.deleteBlock();
                break;
            }
        }
        return this;
    }

    public EditPageWithPanelizer cleanUpAllModules() {
        for (ModuleInfoBlock contentBlock : moduleInfoBlocks) {
            contentBlock.deleteBlock();
        }
        return this;
    }
}
