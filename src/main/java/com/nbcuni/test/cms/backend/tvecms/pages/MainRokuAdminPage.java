package com.nbcuni.test.cms.backend.tvecms.pages;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.DevelPage;
import com.nbcuni.test.cms.backend.tvecms.pages.apiinstances.ApiInstancesPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.MpxFileMediaPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.MpxUpdaterPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.log.LogPage;
import com.nbcuni.test.cms.backend.tvecms.pages.log.OTTLogPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.ModulesPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.AddNewPage;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.TVEPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.PanelizerListPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.PanelizerManagerPage;
import com.nbcuni.test.cms.backend.tvecms.pages.people.PeoplePage;
import com.nbcuni.test.cms.backend.tvecms.pages.permission.PermissionRolesPage;
import com.nbcuni.test.cms.backend.tvecms.pages.platform.AddPlatformPage;
import com.nbcuni.test.cms.backend.tvecms.pages.platform.TvePlatformsPage;
import com.nbcuni.test.cms.backend.tvecms.pages.queue.QueuesListingPage;
import com.nbcuni.test.cms.backend.tvecms.pages.taxonomy.TaxonomyPage;
import com.nbcuni.test.cms.backend.tvecms.pages.taxonomy.TaxonomyTermListPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.elements.Label;
import com.nbcuni.test.cms.elements.Link;
import com.nbcuni.test.cms.pageobjectutils.Page;
import com.nbcuni.test.cms.pageobjectutils.html.HtmlAttributes;
import com.nbcuni.test.cms.pageobjectutils.mvpd.MenuItem;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.utils.*;
import com.nbcuni.test.cms.utils.logging.TestRuntimeException;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainRokuAdminPage extends Page {

    public static final String TITLE = "Dashboard";
    public static final String PAGE_TITLE_TEMPLATE = "%s | %s";
    private static final String ID_ADMIN_MENU_WRAPPER = ".//*[@id='admin-menu-wrapper']";
    private static final String RUN_CRON_LINK = "admin/reports/status/run-cron";
    private static final String QUEUE_LINK = "admin/content/queues";
    private static final String THE_PLATFORM_MPX_SETTING_LINK = "admin/config/media/theplatform";
    private static final String CRON_LINK = "admin/config/system/cron";
    private static final String MPX_MEDIA_LINK = "admin/content/file/mpxmedia";
    private static final String DATE_AND_TIME_LINK = "admin/config/regional/date-time";
    private static final String SITE_INFORMATION_LINK = "admin/config/system/site-information";
    private static final String CONTENT_LINK = "admin/content";
    private static final String FILE_LINK = "admin/content/file";
    private static final String LOG_OUT_LINK = "user/logout";
    private static final String PERFORMANCE_SETTINGS_LINK = "admin/config/development/performance";
    private static final String ADMIN_MENU_ID = "admin-menu-wrapper";
    private static final String USER_NAME_LINK_XPATH = "//li[contains(@class,'admin-menu-account')]/a";
    private static final String HOME_ICON_XPATH = "//span[@class='admin-menu-home-icon']/..";
    private static final String FLUSH_ALL_CACHE_XPATH = "//li[contains(@class,'admin-menu-icon ')]/ul/li[@class='expandable']/a[@class='admin-menu-destination']";
    private static final String PAGE_TITLE = "//h1[@class='page-title']";
    private static final String LOG_LINK = "admin/reports/dblog";
    private static final String OTT_LOG_LINK = "admin/ott/log";
    private static final String OTT_PAGE = "admin/ott/pages";
    private static final String ADD_SHELF_PAGE = "admin/ott/modules/add/shelf";
    private static final String ADD_VOID_SHELF_PAGE = "admin/ott/modules/add/void";
    private static final String ADD_FEATURE_SHOW_PAGE = "admin/ott/modules/add/feature_shows";
    private static final String ADD_FEATURE_CAROUSEL_PAGE = "admin/ott/modules/add/feature_carousel";
    private static final String OTT_MODULE = "admin/ott/modules";
    private static final String PERMISSION_ROLES_PAGE_LINK = "admin/people/permissions/roles";
    private static final String PEOPLE_PAGE_LINK = "admin/people";
    private static final String OTT_APPS_PAGE_LINK = "admin/ott/apps";
    private static final String TAXONOMY_PAGE_LINK = "admin/structure/taxonomy";
    private static final String TAXONOMY_VIEWPORTS_LINK = "admin/structure/taxonomy/viewports";
    private static final String OTT_HEADER_IMAGE_GENERATION_LINK = "admin/ott/image_generation/header";
    private static final String ADD_APPS_PAGE = "admin/ott/apps/add";
    private static final String MPX_UPDATER = "admin/content/ingest/settings";
    private static final String ADVANCED_INGEST_OPTION = "admin/content/ingest/advanced-settings";
    private static final String PROGRESS_BAR_XPATH = "//*[@id='updateprogress']";
    private static final String OTT_LOG_PAGE_LINK_XPATH = "//a[contains(@href,'/admin/ott/log?session_id')]";
    private static final String EDIT_NODE_LINK = "node/%s/edit";
    private static final String PANELIZER_MANAGMENT_LINK = "admin/ott/pages/manage/ott_page/panelizer/page_manager";
    private static final String PANELIZER_LIST_LINK = "admin/config/content/panelizer";
    private static final String API_INSTANCES_LINK = "admin/config/services/api-services-instances";
    private static final String ADD_NEW_PAGE_LINK = "admin/ott/pages/add";
    private static final String UUID_PATTERN = "_uuid/%s";
    private static String nodeUrlRegex = "(.*\\/(node|file)\\/\\d+\\/)";
    @FindBy(xpath = ".//ul[contains(@class, 'tabs')]//a[contains(@href,'devel')]")
    public Link devel;
    @FindBy(xpath = ".//ul[@class='tabs primary']//a[contains(text(),'Edit')]")
    public Link edit;
    @FindBy(id = "entity_revision_id")
    public Label entityRevision;
    @FindBy(id = "entity_uuid")
    public Label entityUuid;
    private WebDriverUtil driverUtil = null;
    private WaitUtils waitUtils = null;

    public MainRokuAdminPage(final CustomWebDriver webDriver, final AppLib aid) {
        super(webDriver, aid);
        driverUtil = WebDriverUtil.getInstance(webDriver);
        waitUtils = WaitUtils.perform(webDriver);
    }

    public String getNodeUrl() {
        return RegexUtil.getFirstSubstringByRegex(webDriver.getCurrentUrl(), nodeUrlRegex);
    }

    //Methods related to devel page
    public DevelPage openDevelPage() {
        if (!devel.isVisible()) {
            return null;
        }
        WebDriverUtil.getInstance(webDriver).click(devel.element());
        waitUtils.waitForPageLoad();
        return new DevelPage(webDriver, aid);
    }

    public void openEditPage() {
        if (edit.isPresent()) {
            edit.click();
            return;
        } else {
            webDriver.get(this.getNodeUrl() + "edit");
        }
        waitUtils.waitForPageLoad();
    }

    public Content updateRevisionAndUuidFromDevel(Content content) {
        DevelPage develPage = openDevelPage();
        if (develPage == null) {
            return content;
        }
        int revision = Integer.parseInt(develPage.getVid());
        content.getGeneralInfo().setRevision(revision);
        content.getGeneralInfo().setUuid(develPage.getUuid());
        openEditPage();
        return content;
    }

    public boolean isAdminPanelPresent() {
        return driverUtil.isElementPresent(ID_ADMIN_MENU_WRAPPER, 3);
    }

    public ThePlatformMpxSettingsPage openThePlatformMpxSettingsPage(final String brand) {
        webDriver.get(Config.getInstance().getRokuHomePage(brand) + THE_PLATFORM_MPX_SETTING_LINK);
        return new ThePlatformMpxSettingsPage(webDriver, aid);
    }

    public ContentPage openContentPage(final String brand) {
        Utilities.logInfoMessage("Open content page");
        webDriver.get(Config.getInstance().getRokuHomePage(brand) + CONTENT_LINK);
        if (driverUtil.isAlertPresent()) {
            driverUtil.acceptAlert(5);
        }
        waitUtils.waitForPageLoad();
        return new ContentPage(webDriver, aid);
    }

    public ContentFilesPage openContentFilePage(final String brand) {
        Utilities.logInfoMessage("Open Content File Page");
        webDriver.get(Config.getInstance().getRokuHomePage(brand) + FILE_LINK);
        return new ContentFilesPage(webDriver, aid);
    }

    public QueuesListingPage openQueueContentCreationPage(final String brand) {
        Utilities.logInfoMessage("Open Queue page");
        webDriver.get(Config.getInstance().getRokuHomePage(brand) + QUEUE_LINK);
        return new QueuesListingPage(webDriver, aid);
    }

    public CronPage openCronPage(final String brand) {
        Utilities.logInfoMessage("Open Cron Page");
        webDriver.get(Config.getInstance().getRokuHomePage(brand) + CRON_LINK);
        return new CronPage(webDriver, aid);
    }

    public MpxFileMediaPage openMpxMediaPage(final String brand) {
        Utilities.logInfoMessage("Open MPX Media Page");
        webDriver.get(Config.getInstance().getRokuHomePage(brand) + MPX_MEDIA_LINK);
        return new MpxFileMediaPage(webDriver, aid);
    }

    public PerformanceSettingsPage openPerformanceSettingsPage(final String brand) {
        Utilities.logInfoMessage("Open Performance setting page");
        webDriver.get(Config.getInstance().getRokuHomePage(brand) + PERFORMANCE_SETTINGS_LINK);
        return new PerformanceSettingsPage(webDriver, aid);
    }

    public DateAndTimePage openDateAndTimePage(final String brand) {
        Utilities.logInfoMessage("Open data and time page");
        webDriver.get(Config.getInstance().getRokuHomePage(brand) + DATE_AND_TIME_LINK);
        return new DateAndTimePage(webDriver, aid);
    }

    public SiteInformationPage openSiteInformationPage(final String brand) {
        Utilities.logInfoMessage("Open site info page");
        webDriver.get(Config.getInstance().getRokuHomePage(brand) + SITE_INFORMATION_LINK);
        return new SiteInformationPage(webDriver, aid);
    }

    public OTTLogPage openOTTLogPage(final String brand) {
        Utilities.logInfoMessage("Open OTT Log page");
        webDriver.get(Config.getInstance().getRokuHomePage(brand) + OTT_LOG_LINK);
        return new OTTLogPage(webDriver, aid);
    }

    public LogPage openLogPage(final String brand) {
        Utilities.logInfoMessage("Open Logging page");
        webDriver.get(Config.getInstance().getRokuHomePage(brand) + LOG_LINK);
        return new LogPage(webDriver, aid);
    }

    public TVEPage openOttPage(final String brand) {
        Utilities.logInfoMessage("Open OTT Page");
        webDriver.get(Config.getInstance().getRokuHomePage(brand) + OTT_PAGE);
        WaitUtils.perform(webDriver).waitForPageLoad();
        return new TVEPage(webDriver, aid);
    }

    public DraftModuleTab openAddShelfPage(final String brand) {
        Utilities.logInfoMessage("Open Add Shelf Page");
        webDriver.get(Config.getInstance().getRokuHomePage(brand) + ADD_SHELF_PAGE);
        WaitUtils.perform(webDriver).waitForPageLoad();
        return new DraftModuleTab(webDriver, aid);
    }

    public DraftModuleTab openAddFeatureCarouselPage(final String brand) {
        Utilities.logInfoMessage("Open Add Feature Carousel Page");
        webDriver.get(Config.getInstance().getRokuHomePage(brand) + ADD_FEATURE_CAROUSEL_PAGE);
        waitUtils.waitForPageLoad();
        return new DraftModuleTab(webDriver, aid);
    }

    public DraftModuleTab openAddVoidShelfPage(final String brand) {
        Utilities.logInfoMessage("Open Add Void Shelf Page");
        webDriver.get(Config.getInstance().getRokuHomePage(brand) + ADD_VOID_SHELF_PAGE);
        waitUtils.waitForPageLoad();
        return new DraftModuleTab(webDriver, aid);
    }

    public DraftModuleTab openAddFeatureShowPage(final String brand) {
        Utilities.logInfoMessage("Open Add Feature Show Page");
        webDriver.get(Config.getInstance().getRokuHomePage(brand) + ADD_FEATURE_SHOW_PAGE);
        return new DraftModuleTab(webDriver, aid);
    }

    public TaxonomyPage openTaxonomyPage(final String brand) {
        Utilities.logInfoMessage("Open Taxonomy Page");
        webDriver.get(Config.getInstance().getRokuHomePage(brand) + TAXONOMY_PAGE_LINK);
        waitUtils.waitForPageLoad();
        return new TaxonomyPage(webDriver, aid);
    }

    public TaxonomyTermListPage openTaxonomyViewportsPage(final String brand) {
        Utilities.logInfoMessage("Open Taxonomy list of ViewPorts Page");
        webDriver.get(Config.getInstance().getRokuHomePage(brand) + TAXONOMY_VIEWPORTS_LINK);
        waitUtils.waitForPageLoad();
        return new TaxonomyTermListPage(webDriver, aid);
    }

    public OTTHeaderImageGenerationPage openOTTHeaderImageGenerationPage(final String brand) {
        Utilities.logInfoMessage("Open OTT Header Image Generation Page");
        webDriver.get(Config.getInstance().getRokuHomePage(brand) + OTT_HEADER_IMAGE_GENERATION_LINK);
        waitUtils.waitForPageLoad();
        return new OTTHeaderImageGenerationPage(webDriver, aid);
    }

    public ModulesPage openOttModulesPage(final String brand) {
        Utilities.logInfoMessage("Open OTT Modules Page");
        webDriver.get(Config.getInstance().getRokuHomePage(brand) + OTT_MODULE);
        waitUtils.waitForPageLoad();
        return new ModulesPage(webDriver, aid);
    }

    public void openEditEntityPageByUuid(String uuid, String brand) {
        Utilities.logInfoMessage("Open Edit Entity page by UUID [" + uuid + "]");
        webDriver.get(Config.getInstance().getRokuHomePage(brand) + String.format(UUID_PATTERN, uuid));
    }

    public PermissionRolesPage openPermissionRolesPage(final String brand) {
        Utilities.logInfoMessage("Open Permission Roles page");
        webDriver.get(Config.getInstance().getRokuHomePage(brand) + PERMISSION_ROLES_PAGE_LINK);
        return new PermissionRolesPage(webDriver, aid);
    }

    public PeoplePage openPeoplePage(final String brand) {
        Utilities.logInfoMessage("Open People page");
        webDriver.get(Config.getInstance().getRokuHomePage(brand) + PEOPLE_PAGE_LINK);
        return new PeoplePage(webDriver, aid);
    }

    public AddPlatformPage openAddPlatformPage(final String brand) {
        Utilities.logInfoMessage("Open Add Apps Page");
        webDriver.get(Config.getInstance().getRokuHomePage(brand) + ADD_APPS_PAGE);
        waitUtils.waitForPageLoad();
        return new AddPlatformPage(webDriver, aid);
    }

    public TvePlatformsPage openTvePlatformPage(final String brand) {
        Utilities.logInfoMessage("Open TVE Apps Page");
        webDriver.get(Config.getInstance().getRokuHomePage(brand) + OTT_APPS_PAGE_LINK);
        waitUtils.waitForPageLoad();
        return new TvePlatformsPage(webDriver, aid);
    }

    public MpxUpdaterPage openMpxUpdaterPage(final String brand) {
        Utilities.logInfoMessage("Open MPX Updater Page");
        webDriver.get(Config.getInstance().getRokuHomePage(brand) + MPX_UPDATER);
        WaitUtils.perform(webDriver).waitForPageLoad();
        return new MpxUpdaterPage(webDriver, aid);
    }

    public AdvancedIngestOptionPage openAdvancedIngestOptions(final String brand) {
        Utilities.logInfoMessage("Open Advamced Ingest Options Page");
        webDriver.get(Config.getInstance().getRokuHomePage(brand) + ADVANCED_INGEST_OPTION);
        waitUtils.waitForPageLoad();
        return new AdvancedIngestOptionPage(webDriver, aid);
    }

    public PanelizerManagerPage openPanelizerManagementPage(final String brand) {
        Utilities.logInfoMessage("Open Panelizer Management Page");
        webDriver.get(Config.getInstance().getRokuHomePage(brand) + PANELIZER_MANAGMENT_LINK);
        return new PanelizerManagerPage(webDriver, aid);
    }

    public PanelizerListPage openPanelizerListage(final String brand) {
        Utilities.logInfoMessage("Open Panelizer List Page");
        webDriver.get(Config.getInstance().getRokuHomePage(brand) + PANELIZER_LIST_LINK);
        return new PanelizerListPage(webDriver, aid);
    }

    public ApiInstancesPage openApiInstancesPage(final String brand) {
        Utilities.logInfoMessage("Open API instances list Page");
        webDriver.get(Config.getInstance().getRokuHomePage(brand) + API_INSTANCES_LINK);
        waitUtils.waitForPageLoad();
        return new ApiInstancesPage(webDriver, aid);
    }

    public AddNewPage openAddNewPage(String brand) {
        Utilities.logInfoMessage("Open Add new Page");
        webDriver.get(Config.getInstance().getRokuHomePage(brand) + ADD_NEW_PAGE_LINK);
        waitUtils.waitForPageLoad();
        return new AddNewPage(webDriver, aid);
    }

    public String getTitle(final String user, final String brand) {
        Utilities.logInfoMessage("Getting page title");
        return String.format(PAGE_TITLE_TEMPLATE, Config.getInstance().getProperty(user + ".name"), Config.getInstance()
                .getEnvDependantProperty(brand + ".name"));
    }

    public String getUserName() {
        Utilities.logInfoMessage("Get user name");
        return webDriver.getText(USER_NAME_LINK_XPATH);
    }

    public boolean isLogOutLinkPresent() {
        return webDriver.isVisible(MenuItem.LOGOUT.getXPath());
    }

    public void logOut(final String brand) {
        if (driverUtil.isElementPresent(ID_ADMIN_MENU_WRAPPER)) {
            webDriver.get(Config.getInstance().getRokuHomePage(brand) + LOG_OUT_LINK);
            waitUtils.waitTitlePresent();
        }
    }

    public void killAllContent(final String brand) {
        Utilities.logInfoMessage("Kill all content on brand: " + brand);
        Utilities.logInfoMessage("Kill all MPX content on " + brand + " brand site");
        ThePlatformMpxSettingsPage settingsPage = openThePlatformMpxSettingsPage(brand);
        settingsPage.killContent(brand);
        MpxFileMediaPage mediaPage = openMpxMediaPage(brand);
        Assertion.assertFalse(mediaPage.areFilesPresent(), "The files still present after deletion the content", webDriver);
        webDriver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
    }

    public String getPageTitle() {
        return webDriver.getText(PAGE_TITLE);
    }

    public void runCron(final String brand) {
        Utilities.logInfoMessage("Run cron by link");
        pause(2);
        webDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);
        Utilities.logInfoMessage("User on " + brand + " brand site");
        try {
            webDriver.get(Config.getInstance().getRokuHomePage(brand) + RUN_CRON_LINK);
        } catch (final TimeoutException e) {
            Utilities.logSevereMessageThenFail("Page was not loaded during default timeout");
        }
        webDriver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
        waitUtils.waitForPageLoad();
    }

    public void runCron(String brand, int count) {
        for (int i = 0; i < count; i++) {
            this.runCron(brand);
        }
    }

    public void runAllCron(final String brand) {
        Utilities.logInfoMessage("Run cron by link");

        int currentCountContent;
        int previewCountContent;
        do {
            previewCountContent = openContentPage(brand).getCountEntries();
            runCron(brand);
            currentCountContent = openContentPage(brand).getCountEntries();
        } while (currentCountContent - previewCountContent != 0);
        webDriver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
    }

    public boolean isProgressBarPresent() {
        return driverUtil.isElementPresent(PROGRESS_BAR_XPATH, 5);
    }

    public void flushAllCache() {
        Utilities.logInfoMessage("Full cache ");
        final WebElement homeIcon = webDriver.findElement(By.xpath(HOME_ICON_XPATH));
        new Actions(webDriver).moveToElement(homeIcon).perform();
        final WebElement flushLink = webDriver.findElement(By.xpath(FLUSH_ALL_CACHE_XPATH));
        driverUtil.click(flushLink);
        waitUtils.waitForPageLoad();
    }

    @Override
    public List<String> verifyPage() {
        Utilities.logInfoMessage("Verify page " + getPageUrl() + " for missed elements");
        final ArrayList<String> missedElements = new ArrayList<>();
        final Class<?> thisClass = this.getClass();
        final Field[] fields = thisClass.getDeclaredFields();
        for (final Field field : fields) {
            try {
                if (field.getType().equals(String.class) && field.getName().contains("_XPATH")) {
                    final String fieldLocator = field.get(this).toString();
                    if (!webDriver.isVisible(fieldLocator)) {
                        missedElements.add("Element: " + field.getName() + ", Locator: " + fieldLocator);
                        WebDriverUtil.getInstance(webDriver).attachScreenshot();
                        missedElements.trimToSize();
                    }
                }
            } catch (final IllegalArgumentException | IllegalAccessException e) {
                Utilities.logSevereMessageThenFail(e.getClass().getName() + ": " + Utilities.convertStackTraceToString(e));
            }
        }
        return missedElements;
    }

    private WebElement getMenuItem(final WebElement element, final MenuItem item) {
        new Actions(webDriver).moveToElement(element).perform();
        return webDriver.findElement(By.xpath(item.getXPath()));
    }

    @SuppressWarnings("unused")
    private void navigateMenu(final MenuItem... items) {
        try {
            if (null != items && items.length > 0) {
                final WebElement menu = webDriver.findElement(By.id(ADMIN_MENU_ID));
                WebElement currentElement = menu.findElement(By.xpath(items[0].getXPath()));
                for (int i = 1; i < items.length; i++) {
                    currentElement = getMenuItem(currentElement, items[i]);
                }
                driverUtil.click(currentElement);
            }
        } catch (final Exception e) {
            Utilities.logSevereMessageThenFail(Utilities.convertStackTraceToString(e));
        }
    }

    public String getLogPageUrl() {
        if (driverUtil.isElementPresent(OTT_LOG_PAGE_LINK_XPATH)) {
            return webDriver.getAttribute(OTT_LOG_PAGE_LINK_XPATH, HtmlAttributes.HREF.get());
        }
        driverUtil.attachScreenshot();
        Utilities.logSevereMessage("The OTT LOG Page link is not present in the status message");
        return null;
    }

    public String getLogURL(final String brand) {
        String sessionId = getSessionIdFromLogUrl();
        return Config.getInstance().getAqaAPIUrlBuilderBySession(brand, sessionId);
    }

    public boolean isLogUrlPresent() {
        return driverUtil.isElementPresent(OTT_LOG_PAGE_LINK_XPATH);
    }

    public String getSessionIdFromLogUrl() {
        try {
            String url = getLogPageUrl();
            return url.substring(url.indexOf('=') + 1);
        } catch (Exception e) {
            WebDriverUtil.getInstance(webDriver).attachScreenshot();
            throw new TestRuntimeException("Impossible to catch publish URL: " + e.getMessage());
        }
    }

    public MainRokuAdminPage openNodeEditPageById(final String brand, String nodeId) {
        Utilities.logInfoMessage("Open Edit Node Page without lock");
        webDriver.get(Config.getInstance().getRokuHomePage(brand) + String.format(EDIT_NODE_LINK, nodeId));
        ContentType type = ContentType.TVE_VIDEO;
        if (getPageTitle().contains(ContentType.TVE_VIDEO.get())) {
            type = ContentType.TVE_VIDEO;
        }
        if (getPageTitle().contains(ContentType.TVE_PROGRAM.get())) {
            type = ContentType.TVE_PROGRAM;
        }
        switch (type) {
            case TVE_PROGRAM: {
                return new EditTVEProgramContentPage(webDriver, aid);
            }
            case TVE_VIDEO: {
                return new EditTVEVideoContentPage(webDriver, aid);
            }
            default:
                throw new TestRuntimeException("Could identify a time of opened page by type: " + type);
        }
    }

    public <T extends Page> T openPage(Class<T> clazz, String brand) {
        Utilities.logInfoMessage("Open " + clazz.getName());
        String finalUrl = Config.getInstance().getRokuHomePage(brand) + RokuPageMapping.getPageMappingByClass(clazz).getUrl();
        webDriver.get(finalUrl);
        waitUtils.waitForPageLoad();
        return ReflectionUtils.getInstance(clazz, webDriver, aid);
    }

    public String getUuidEntity() {
        if (entityUuid.isPresent()) {
            return WebDriverUtil.getInstance(webDriver).getText(entityUuid.element());
        } else {
            Utilities.logSevereMessage("UUID is not present");
            return null;
        }
    }

    public String getRevisionEntity() {
        if (entityRevision.isPresent()) {
            return WebDriverUtil.getInstance(webDriver).getText(entityRevision.element());
        } else {
            Utilities.logSevereMessage("Revision is not present");
            return null;
        }
    }

    @Override
    public boolean isPageOpened() {
        return this.getPageTitle().equalsIgnoreCase(TITLE);
    }
}