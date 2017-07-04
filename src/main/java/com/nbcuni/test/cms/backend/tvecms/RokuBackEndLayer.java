package com.nbcuni.test.cms.backend.tvecms;

import com.nbcuni.test.cms.backend.chiller.block.assetlibrary.FileBlock;
import com.nbcuni.test.cms.backend.chiller.pages.assetlibrary.AssetLibraryPage;
import com.nbcuni.test.cms.backend.chiller.pages.assetlibrary.EditMultipleFilesPage;
import com.nbcuni.test.cms.backend.chiller.pages.collections.CollectionAbstractPage;
import com.nbcuni.test.cms.backend.chiller.pages.collections.CollectionsContentPage;
import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ChillerVideoPage;
import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.chiller.pages.contenttype.DevelPage;
import com.nbcuni.test.cms.backend.chiller.pages.contenttype.EpisodePage;
import com.nbcuni.test.cms.backend.chiller.pages.migration.ImportPage;
import com.nbcuni.test.cms.backend.chiller.pages.migration.MigrationPage;
import com.nbcuni.test.cms.backend.theplatform.MPXLoginControl;
import com.nbcuni.test.cms.backend.theplatform.MPXMetadata;
import com.nbcuni.test.cms.backend.theplatform.MPXSearch;
import com.nbcuni.test.cms.backend.tvecms.pages.*;
import com.nbcuni.test.cms.backend.tvecms.pages.apiinstances.ApiInstancesPage;
import com.nbcuni.test.cms.backend.tvecms.pages.apiinstances.InstanceEditForm;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.MpxUpdaterPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.ModulesPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.dynamic.DynamicModulePage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.AddNewPage;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.PageWithPanelizer;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.TVEPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.*;
import com.nbcuni.test.cms.backend.tvecms.pages.platform.AddPlatformPage;
import com.nbcuni.test.cms.backend.tvecms.pages.platform.TvePlatformsPage;
import com.nbcuni.test.cms.backend.tvecms.pages.queue.AddQueuePage;
import com.nbcuni.test.cms.backend.tvecms.pages.queue.QueuesListingPage;
import com.nbcuni.test.cms.backend.tvecms.pages.taxonomy.AddMvpdTermPage;
import com.nbcuni.test.cms.backend.tvecms.pages.taxonomy.AddTaxonomyTermPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.apiinstances.ApiInstanceEntity;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.Collection;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.assetlibrary.FilesMetadata;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.ChannelReference;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.episode.Episode;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.event.Event;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.media.MediaImage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.Season;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.GlobalProgramEntity;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.Series;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.video.GlobalVideoEntity;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.video.Video;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.factory.PromoWithRequiredFields;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.Module;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.dynamic.Dynamic;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featurecarousel.FeatureCarouselForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featurecarousel.factory.CreateFactoryFeatureCarousel;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featureshow.FeatureShowModule;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featureshow.factory.CreateFeatureShowModule;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.platform.PlatformEntity;
import com.nbcuni.test.cms.bussinesobjects.tvecms.platform.factory.CreateFactoryPlatform;
import com.nbcuni.test.cms.bussinesobjects.tvecms.taxonomy.TaxonomyTerm;
import com.nbcuni.test.cms.collectservices.program.ContentApiProgramDataCollector;
import com.nbcuni.test.cms.collectservices.program.ProgramDataCollector;
import com.nbcuni.test.cms.collectservices.video.ContentApiVideoDataCollector;
import com.nbcuni.test.cms.collectservices.video.UiVideoDataCollector;
import com.nbcuni.test.cms.collectservices.video.VideoDataCollector;
import com.nbcuni.test.cms.pageobjectutils.chiller.CustomBrandNames;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.pageobjectutils.entities.content.PublishState;
import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.Platform;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.pageobjectutils.tvecms.ContentFormat;
import com.nbcuni.test.cms.pageobjectutils.tvecms.VoidShelf;
import com.nbcuni.test.cms.pageobjectutils.tvecms.concerto.AdvancedIngestionOptions;
import com.nbcuni.test.cms.pageobjectutils.tvecms.panelizer.PanelizerTemplates;
import com.nbcuni.test.cms.pageobjectutils.tvecms.shelf.CuratedListType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.shelf.ShelfType;
import com.nbcuni.test.cms.utils.*;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.images.ImagesJson;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.NodeApi;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.jsonclasses.GlobalNodeJson;
import com.nbcuni.test.cms.utils.jsonparsing.mvpd.MvpdEntitlementsServiceHelper;
import com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.entities.MvpdFeed;
import com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.entities.MvpdFromJson;
import com.nbcuni.test.cms.utils.jsonparsing.mvpd.update.enums.MvpdEntitlementsServiceVersion;
import com.nbcuni.test.cms.utils.jsonparsing.services.registryservice.RegistryServiceEntity;
import com.nbcuni.test.cms.utils.jsonparsing.services.registryservice.RegistryServiceHelper;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.RequestHelper;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.*;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.utils.logging.TestRuntimeException;
import com.nbcuni.test.cms.utils.mpx.builders.MpxAssetUpdateBuilder;
import com.nbcuni.test.cms.utils.mpx.objects.MpxAsset;
import com.nbcuni.test.cms.utils.transformers.ProgramJsonTransformer;
import com.nbcuni.test.cms.utils.transformers.VideoJsonTransformer;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.cms.verification.chiller.ImageVerificator;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.apache.commons.collections.CollectionUtils;
import org.testng.SkipException;

import java.util.*;
import java.util.stream.Collectors;


public class RokuBackEndLayer {
    public static final String DELETE = "Delete ";
    public static final String USER_IS_NOT_ON_ADMIN = "User is not on admin";
    public static final String MAIN_ADMIN_PAGE_IS_OPENED_ON_BRAND = "Main admin page is opened on brand ";
    public static final String STATUS_MESSAGE_IS_NOT_SHOWN = "Status message is not shown";
    public static final String THE_GIVEN_MODULE_TYPE = "The given module type: ";
    public static final String DOESN_T_MATCHED_WITH_SWITCH_CASE_CRITERIA = " doesn't matched with switch case criteria";
    public static final String ASSET_TYPE_IS_NOT_EXIST = "Asset Type is not exist";
    private static final String SHELF_MANAGER_QUEUE_TYPE = "Add Program &amp; Video";
    private CustomWebDriver webDriver;
    private AppLib aid;
    private NodeApi nodeApi;
    private String brand;
    private MainRokuAdminPage mainRokuAdminPage;
    private ContentTypePage contentTypePage;
    private ContentPage contentPage;
    private WebDriverUtil driverUtil;
    private String taxonomyMvpdTermName = "mvpd";
    private MPXLoginControl login;
    private String programToAdd;
    private VideoDataCollector videoDataCollector;
    private VideoDataCollector videoDataCollectorFromApi;
    private ProgramDataCollector programDataCollectorFromApi;

    public RokuBackEndLayer(CustomWebDriver webDriver, String brand, AppLib aid) {
        this.webDriver = webDriver;
        this.brand = brand;
        this.aid = aid;
        nodeApi = new NodeApi(brand);
        mainRokuAdminPage = new MainRokuAdminPage(webDriver, aid);
        driverUtil = WebDriverUtil.getInstance(webDriver);
        videoDataCollector = new UiVideoDataCollector(webDriver, aid, brand);
        videoDataCollectorFromApi = new ContentApiVideoDataCollector(brand);
        programDataCollectorFromApi = new ContentApiProgramDataCollector(brand);
    }

    // Roku Admin Steps

    public MainRokuAdminPage openAdminPage(String userName, String password) {
        LoginPage loginPage = new LoginPage(webDriver, aid);
        mainRokuAdminPage = new MainRokuAdminPage(webDriver, aid);
        mainRokuAdminPage = loginPage.loginAsSpecifiedUser(userName, password, brand);
        if (!mainRokuAdminPage.isAdminPanelPresent()) {
            driverUtil.attachScreenshot();
            Assertion.fail(USER_IS_NOT_ON_ADMIN);
        }
        Utilities.logInfoMessage(MAIN_ADMIN_PAGE_IS_OPENED_ON_BRAND + brand);
        return mainRokuAdminPage;
    }

    public MainRokuAdminPage openAdminPage() {
        LoginPage loginPage = new LoginPage(webDriver, aid);
        mainRokuAdminPage = new MainRokuAdminPage(webDriver, aid);
        mainRokuAdminPage = loginPage.loginAsAdmin(brand);

        if (!mainRokuAdminPage.isAdminPanelPresent()) {
            driverUtil.attachScreenshot();
            Assertion.fail(USER_IS_NOT_ON_ADMIN);
        }
        Utilities.logInfoMessage(MAIN_ADMIN_PAGE_IS_OPENED_ON_BRAND + brand);
        return mainRokuAdminPage;
    }

    /**
     * The method to Open CMS under Senior Editor Role
     *
     * @return - CMS dashboard Page object
     */
    public MainRokuAdminPage openAdminPageAsEditor() {
        LoginPage loginPage = new LoginPage(webDriver, aid);
        mainRokuAdminPage = loginPage.loginAsEditor(brand);

        if (!mainRokuAdminPage.isAdminPanelPresent()) {
            driverUtil.attachScreenshot();
            Assertion.fail(USER_IS_NOT_ON_ADMIN);
        }
        Utilities.logInfoMessage(MAIN_ADMIN_PAGE_IS_OPENED_ON_BRAND + brand);
        return mainRokuAdminPage;
    }

    /**
     * The method to Open CMS under Senior Editor Role
     *
     * @return - CMS dashboard Page object
     */
    public MainRokuAdminPage openAdminPageAsSeniorEditor() {
        LoginPage loginPage = new LoginPage(webDriver, aid);
        mainRokuAdminPage = loginPage.loginAsSeniorEditor(brand);

        if (!mainRokuAdminPage.isAdminPanelPresent()) {
            driverUtil.attachScreenshot();
            Assertion.fail(USER_IS_NOT_ON_ADMIN);
        }
        Utilities.logInfoMessage(MAIN_ADMIN_PAGE_IS_OPENED_ON_BRAND + brand);
        return mainRokuAdminPage;
    }

    public List<String> createQueueWithRandomAssets(String name,
                                                    int numberOfAssets) {
        QueuesListingPage queueList = mainRokuAdminPage
                .openQueueContentCreationPage(brand);
        AddQueuePage addQueue = queueList
                .clickAddQueueByType(SHELF_MANAGER_QUEUE_TYPE);
        addQueue.enterTitle(name);
        addQueue.fillWithRandomAssets(numberOfAssets);
        List<String> result = addQueue.getAssets();
        addQueue.clickSaveQueueButton();
        return result;
    }

    //  ----    MVPD Header related methods  ------

    public void createTaxonomyMvpdTerm(String name, String id, String logoPath) {
        mainRokuAdminPage.openTaxonomyPage(brand).clickAddTermsLinkForTerm(
                taxonomyMvpdTermName);
        AddMvpdTermPage addMvpdTerm = new AddMvpdTermPage(webDriver, aid);
        addMvpdTerm.fillValuesAndSave(name, id, logoPath);
        Assertion.assertTrue(addMvpdTerm.isStatusMessageShown(),
                STATUS_MESSAGE_IS_NOT_SHOWN);
    }

    public void deleteTaxonomyMvpdTerm(String termName) {
        mainRokuAdminPage.openTaxonomyPage(brand)
                .clickListTermsLinkForTerm(taxonomyMvpdTermName)
                .clickEditLinkForTerm(termName);
        AddMvpdTermPage addMvpdTerm = new AddMvpdTermPage(webDriver, aid);
        addMvpdTerm.deleteTerm();
    }

    public Map<String, Map<String, String>> getExpectedMvpdsForHeaderGeneration(
            String... appName) {
        String appTitle = CmsPlatforms.ROKU.getAppName();
        if (appName != null && appName.length > 0) {
            appTitle = appName[0];
        }
        AddPlatformPage page = mainRokuAdminPage.openTvePlatformPage(brand)
                .clickEditPlatform(appTitle);
        String url = page.getEntitlementsServiceUrl();
        MvpdEntitlementsServiceHelper helper = new MvpdEntitlementsServiceHelper(
                MvpdEntitlementsServiceVersion.V2, Platform.ROKU, brand);
        MvpdFeed feed = helper.parseJson(url);
        String domain = feed.getFilePath();
        Map<String, MvpdFromJson> map = feed.getMvpds();
        Map<String, Map<String, String>> mapForHeaderGeneration = new HashMap<>();

        for (MvpdFromJson value : map.values()) {
            if (value.getAppLoggedInImage() != null) {
                StringBuilder stringBuilder = new StringBuilder(value.getAppLoggedInImage());
                if (domain != null) {
                    stringBuilder.append(domain);
                }
                Map<String, String> entry = new HashMap<>(1);
                entry.put(value.getId().trim(), stringBuilder.toString());
                mapForHeaderGeneration.put(value.getDisplayName(), entry);
            }
        }
        return mapForHeaderGeneration;
    }

    public String getRandomMvpdForHeaderGeneration(String... appName) {
        Map<String, Map<String, String>> mvpds = getExpectedMvpdsForHeaderGeneration(appName);
        Set<String> ids = mvpds.keySet();
        int index = new Random().nextInt(ids.size());
        String mpvdName = (String) ids.toArray()[index];
        String mpvdId = (String) mvpds.get(mpvdName).keySet().toArray()[0];
        return mpvdId + ";" + mpvdName;
    }

    // ----  MPX related methods -----

    public void loginAndSearchAssetInMPX(final String fileName) {
        webDriver.get(Config.getInstance().getMPXUrl());
        login = new MPXLoginControl(webDriver, aid, Config.getInstance());
        login.login(Config.getInstance().getRokuMPXUsername(brand), Config
                .getInstance().getRokuMPXPassword(brand));
        final MPXSearch mpxSearch = new MPXSearch(Config.getInstance());
        mpxSearch.enterSearchTxt(fileName);
        mpxSearch.clickSearchByTitleLnk();
    }

    public void updateMpxData(Map<String, String> data) {
        final MPXMetadata metadata = new MPXMetadata(Config.getInstance());
        metadata.giveFocusToMediaItem();
        metadata.updateRokuEpisodeData(data);
        login.signOut();
        webDriver.manage().deleteAllCookies();
    }

    public List<String> getLatestEpisodes(String program, int maxCount) {
        List<MpxAsset> latestEpisodesAssets = new MPXLayer(brand).getLatestEpisodes(program, maxCount);
        List<String> latestEpisodesTitles = new ArrayList<>();
        contentPage = mainRokuAdminPage.openContentPage(brand);
        for (MpxAsset asset : latestEpisodesAssets) {
            EditTVEVideoContentPage videoContentPage = contentPage.openEditTVEVideoPage(asset.getTitle());
            latestEpisodesTitles.add(videoContentPage.onImagesTab().elementOneTileArea().getImageSourceName());
            videoContentPage.clickCancel();
            if (latestEpisodesTitles.size() == maxCount) break;
        }
        return latestEpisodesTitles;
    }

    public List<MpxAsset> getMPXRelatedEpisodes(String program, int maxCount) {
        return new MPXLayer(brand).getLatestEpisodes(program, maxCount);
    }

    public void updateMPXAssetById(String mpxId) {
        MpxUpdaterPage updaterPage = mainRokuAdminPage.openMpxUpdaterPage(brand);
        updaterPage.setAssetIdIntoField(mpxId);
        updaterPage.clickUpdateAssetByMpxId();
        Assertion.assertTrue(mainRokuAdminPage.isStatusMessageShown(),
                "The status message is not shown after MPX Updater Ingest by ID", webDriver);
        Utilities.logInfoMessage("The Asset with ID: " + mpxId + " has ingested");
    }

    // ---   OTT Page's methods  -----
    public PageForm createPage(PageForm pageForm) {
        Utilities.logInfoMessage("Create New Page : " + pageForm.getTitle());
        AddNewPage addNewPage = mainRokuAdminPage.openAddNewPage(brand);
        return addNewPage.createPage(pageForm);
    }

    public PageForm createPage(CmsPlatforms... appId) {
        PageForm pageForm = CreateFactoryPage.createDefaultPageWithAlias();
        if (appId != null && appId.length > 0) {
            pageForm.setPlatform(appId[0]);
        }
        return createPageWithVerification(pageForm);
    }

    public PageForm createPage(String appId) {
        PageForm pageForm = CreateFactoryPage.createDefaultPageWithAlias();
        if (appId != null) {
            pageForm.setStringPlatform(appId);
        }
        return createPageWithVerification(pageForm);
    }

    private PageForm createPageWithVerification(PageForm pageForm) {
        createPage(pageForm);
        Assertion.assertTrue(mainRokuAdminPage.isStatusMessageShown(),
                STATUS_MESSAGE_IS_NOT_SHOWN);
        TVEPage tvePage = mainRokuAdminPage.openPage(TVEPage.class, brand);
        Assertion.assertTrue(tvePage.isPageExist(pageForm.getTitle()),
                "Page was not created");
        return pageForm;
    }

    public PageForm updatePage(PageForm pageForm) {
        EditPageWithPanelizer editPage = openEditOttPage(pageForm.getTitle());
        PageForm pageFormUpdated = editPage.updateFields(pageForm.getModules());
        editPage.save();
        pageFormUpdated.setRevision(editPage.getRevisionEntity());
        pageFormUpdated.setModules(pageForm.getModules());
        Assertion.assertTrue(editPage.isStatusMessageShown(),
                STATUS_MESSAGE_IS_NOT_SHOWN);
        return pageFormUpdated;
    }

    public EditPageWithPanelizer openEditOttPage(String pageName, PanelizerTemplates... panelizerTemplates) {
        TVEPage tvePage = mainRokuAdminPage.openPage(TVEPage.class, brand);
        tvePage.clickEdit(pageName);
        WaitUtils.perform(webDriver).waitForPageLoad();
        return panelizerTemplates.length != 0 ? new EditPageWithPanelizer(webDriver, aid, panelizerTemplates[0]) :
                new EditPageWithPanelizer(webDriver, aid);
    }

    public void updateMpxData(Map<String, String> data, Boolean... doSignOut) {
        final MPXMetadata metadata = new MPXMetadata(Config.getInstance());
        metadata.giveFocusToMediaItem();
        metadata.updateRokuEpisodeData(data);
        if (doSignOut.length == 0 || doSignOut[0]) {
            login.signOut();
            webDriver.manage().deleteAllCookies();
        }
    }

    public TVEPage deleteOttPage(String pageTitle) {
        Utilities.logInfoMessage("Delete Ott Page: " + pageTitle);
        TVEPage tvePage = mainRokuAdminPage.openPage(TVEPage.class, brand);
        tvePage.clickDelete(pageTitle).clickSubmit();
        return new TVEPage(webDriver, aid);
    }

    // -----   Modules ---------
    public void deleteModule(String moduleName) {
        Utilities.logInfoMessage("Delete Module: " + moduleName);
        ModulesPage modulesPage = mainRokuAdminPage.openOttModulesPage(brand);
        try {

            ConfirmationPage deleteConfirmationPage = modulesPage.clickDeleteLink(moduleName);
            deleteConfirmationPage.clickSubmit();
        } catch (Exception e) {
            Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
        }
    }

    public DraftModuleTab openModuleEdit(Module module) {
        Utilities.logInfoMessage("Open Edit Page of module : " + module.getTitle());
        ModulesPage modulesPage = mainRokuAdminPage.openOttModulesPage(brand);
        return modulesPage.clickEditLink(module.getTitle());
    }

    public Module createModule(Module module) {

        DraftModuleTab draftModuleTab = null;
        if (module instanceof Shelf) {
            draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
            draftModuleTab.createShelf((Shelf) module);
        } else if (module instanceof FeatureCarouselForm) {
            draftModuleTab = mainRokuAdminPage.openAddFeatureCarouselPage(brand);
            draftModuleTab.createFeatureCarousel((FeatureCarouselForm) module);
        } else if (module instanceof FeatureShowModule) {
            draftModuleTab = mainRokuAdminPage.openAddFeatureShowPage(brand);
            draftModuleTab.createFeatureShowModule((FeatureShowModule) module);
        } else if (module instanceof Dynamic) {
            DynamicModulePage dynamicModulePage = mainRokuAdminPage.openPage(DynamicModulePage.class, brand);
            dynamicModulePage.create((Dynamic) module);
        }

        return module;
    }

    /**
     * This method is used to create a module by type with pre-set items for queue
     * and their metadata (uuid and type) for IOS module publishing info
     *
     * @param shelfType type of TVE module to create
     * @return returns Module entity with set List of items for JSON and metadata for module creation
     */
    public Module getModulesStrategyForConcerto(ShelfType shelfType) {

        List<Content> contents = new ArrayList<>();

        //open content page,get random published program
        GlobalNodeJson nodeProgramJson = nodeApi.getRandomPublishedProgramNode();
        contents.add(ProgramJsonTransformer.getGlobalProgramEntity(nodeProgramJson, brand));
        programToAdd = nodeProgramJson.getTitle();

        switch (shelfType) {
            case SHELF:
                Shelf shelf = EntityFactory.getShelfsList().get(0);
                return setModuleContent(shelf, contents);
            case FEATURE_CAROUSEL:
                FeatureCarouselForm carouselForm = CreateFactoryFeatureCarousel.createRandomFeatureCarousel();
                return setModuleContent(carouselForm, contents);
            case FEATURE_SHOWS:
                FeatureShowModule showModule = CreateFeatureShowModule.createDefault();
                List<String> assetsList = Arrays.asList(nodeProgramJson.getTitle());
                showModule.setAssets(assetsList).setAssetsCount(assetsList.size()).setContents(contents);
                return showModule;
            case PLACEHOLDER:
                throw new TestRuntimeException(THE_GIVEN_MODULE_TYPE + shelfType + DOESN_T_MATCHED_WITH_SWITCH_CASE_CRITERIA);
        }
        throw new TestRuntimeException(THE_GIVEN_MODULE_TYPE + shelfType + DOESN_T_MATCHED_WITH_SWITCH_CASE_CRITERIA);
    }

    /**
     * This method is used to create a module with Rules(Latest Episode)
     * by type with pre-set programs for queue
     * and their metadata (uuid and type) for IOS module publishing info
     *
     * @param shelfType       -   type of TVE module to create
     * @param programsForRule - list of programs for each Rule will be set
     * @param numberLatest - number of latest episodes.
     * @return returns Module entity with set List of items for JSON and metadata for module creation
     */
    public synchronized Module getRulesStrategyForConcerto(ShelfType shelfType, List<String> programsForRule, int numberLatest) {
        List<Content> contents = new ArrayList<>();
        for (String program : programsForRule) {
            contents.addAll(getLatestEpisodesForProgram(program, numberLatest));
        }
        List<String> assetsList = new ArrayList<>();
        assetsList.addAll(programsForRule);
        if (assetsList.isEmpty()) {
            Assertion.fail("Could not get a published TVE Video related to the programs: " + programsForRule);
        }
        switch (shelfType) {
            case SHELF:
                Shelf shelf = EntityFactory.getShelfsList().get(0);
                shelf.setAssets(assetsList).setAssetsCount(assetsList.size()).setContents(contents);
                return shelf;
            case FEATURE_CAROUSEL:
                FeatureCarouselForm carouselForm = CreateFactoryFeatureCarousel.createRandomFeatureCarousel();
                carouselForm.setAssets(assetsList).setAssetsCount(assetsList.size()).setContents(contents);
                return carouselForm;
            case FEATURE_SHOWS:
            case PLACEHOLDER:
                throw new TestRuntimeException("Current Module type " + shelfType + " does not support rules, please call correct one");
        }

        throw new TestRuntimeException("The given module type: " + shelfType + " doesn't matched with switch case criteria");
    }

    private List<Content> getLatestEpisodesForProgram(String program, int numberLatest) {
        List<Content> videos = new ArrayList<>();

        contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        contentPage.searchByType(ContentType.TVE_VIDEO)
                .searchByRelatedProgram(program)
                .searchByPublishedState(PublishState.YES).apply();
        if (contentPage.isContentPresent()) {
            List<String> episodes = new LinkedList<>();
            List<String> allEpisodes = contentPage.filterByContentFormat(ContentFormat.FULL_EPISODE);

            for (int i = 1; i <= numberLatest && i <= allEpisodes.size(); i++) {
                int random = new Random().nextInt(allEpisodes.size());
                episodes.add(allEpisodes.get(random));
                allEpisodes.remove(random);
            }
            for (String episode : episodes) {
                videos.add(0, updateEpisodeForLatest(episode));
            }
        }
        return videos;
    }

    private Content updateEpisodeForLatest(String episode) {
        GlobalNodeJson globalNodeJson = nodeApi.getNodeByName(episode);
        int seasonNumber = Integer.parseInt(updateDate(DateUtil.getCurrentYear()) +
                updateDate(DateUtil.getCurrentMonth()) +
                updateDate(DateUtil.getCurrentDay()));

        int episodeNumber = Integer.parseInt(updateDate(DateUtil.getCurrentHour()) +
                updateDate(DateUtil.getCurrentMinutes()) +
                updateDate(DateUtil.getCurrentSecond()));
        new MPXLayer(brand)
                .mpxAssetUpdaterWithoutChecking(new MpxAssetUpdateBuilder(globalNodeJson.getMpxAsset().getId())
                        .updateSeasonNumber(seasonNumber)
                        .updateEpisodeNumber(episodeNumber));

        return VideoJsonTransformer.getGlobalVideoEntity(globalNodeJson, brand);
    }

    private String updateDate(int date) {
        String dateString = String.valueOf(date);
        return dateString.length() == 1 ? "0" + dateString : dateString;
    }

    /**
     * @param module   - created by factory module(feature carousel, shelf)
     * @param contents - Contents list of items that was added from the caller method
     * @return - turn back module with set data for assets(full episode and promo), their count and data for Json file
     */
    private Module setModuleContent(Module module, List<Content> contents) {
        GlobalNodeJson videoFullEpisode = nodeApi.getFullEpisodeVideoNode();
        contents.add(VideoJsonTransformer.getGlobalVideoEntity(videoFullEpisode, brand));

        Content promo = new PromoWithRequiredFields().createContentType();
        createContentType(promo);
        contents.add(promo);

        List<String> assetsList = Arrays.asList(programToAdd, videoFullEpisode.getTitle(), promo.getTitle());
        module.setAssets(assetsList).setAssetsCount(assetsList.size()).setContents(contents);
        return module;
    }

    /**
     * @return - Platform set entities for CMS(Android,Roku)
     */
    public PlatformEntity createPlatform() {
        PlatformEntity platformEntity = CreateFactoryPlatform.createDefaultPlatform();
        this.createPlatform(platformEntity).clickSaveButton();
        return platformEntity;
    }

    public AddPlatformPage createPlatform(PlatformEntity platformEntity) {
        AddPlatformPage addPlatformPage = mainRokuAdminPage.openAddPlatformPage(brand);
        TvePlatformsPage tvePlatformsPage = addPlatformPage.createAndSavePlatform(platformEntity);
        addPlatformPage = tvePlatformsPage.clickEditPlatform(platformEntity.getName());
        DevelPage develPage = addPlatformPage.openDevelPage();
        platformEntity.setUuid(develPage.getUuid());
        platformEntity.setRevision(develPage.getVid());
        develPage.openEditPage();
        return new AddPlatformPage(webDriver, aid);
    }

    public TvePlatformsPage deleteCreatedPlatform(String platformName) {
        TvePlatformsPage tvePlatformsPage = mainRokuAdminPage.openTvePlatformPage(brand);
        if (tvePlatformsPage.isPlatformPresent(platformName)) {
            tvePlatformsPage.clickDelete(platformName);
        }
        return tvePlatformsPage;
    }

    public void updateImages(String firstDimension, String secondDimension) {
        final MPXMetadata metadata = new MPXMetadata(Config.getInstance());
        metadata.openFilesArea().exchangeImagesDimension(firstDimension,
                secondDimension);
        login.signOut();
        webDriver.manage().deleteAllCookies();

    }

    // ------ Assets Content -----
    public List<CuratedListItemJson> collectItemsInfo(List<String> assetsIds) {
        List<CuratedListItemJson> items = new ArrayList<>();
        for (String assetId : assetsIds) {
            CuratedListItemJson item = new CuratedListItemJson();
            GlobalNodeJson nodeJson = nodeApi.getNodeById(assetId);
            if (nodeJson.getType().equalsIgnoreCase(ContentType.TVE_PROGRAM.getNodeApiName())) {
                item.setId(nodeJson.getMpxAsset().getGuid());
                item.setItemType("program");
                items.add(item);
            } else if (nodeJson.getType().equalsIgnoreCase(ContentType.TVE_VIDEO.getNodeApiName())) {
                item.setId(nodeJson.getMpxAsset().getGuid());
                item.setItemType("video");
                items.add(item);
            }
        }
        return items;

    }

    public List<CuratedListItemJson> collectItemsInfoByTitle(List<String> assetTitles) {
        List<CuratedListItemJson> items = new ArrayList<>();
        if (assetTitles.isEmpty()) {
            throw new TestRuntimeException("The assetTitle array is: " + assetTitles);
        }
        for (String asset : assetTitles) {
            CuratedListItemJson item = new CuratedListItemJson();
            GlobalNodeJson nodeJson = nodeApi.getNodeByName(asset);
            if (nodeJson.getType().equalsIgnoreCase(ContentType.TVE_PROGRAM.getNodeApiName())) {
                item.setId(nodeJson.getMpxAsset().getGuid());
                item.setItemType("program");
                items.add(item);
            } else if (nodeJson.getType().equalsIgnoreCase(ContentType.TVE_VIDEO.getNodeApiName())) {
                item.setId(nodeJson.getMpxAsset().getGuid());
                item.setItemType("video");
                items.add(item);
            }
        }
        return items;

    }

    public RokuQueueJson getObject(List<LatestEpisodeJson> hybridItemsList, List<CuratedListItemJson> curatedItemList, Shelf shelf, PageForm... pageInfo) {
        List<ContentItemListObject> itemList = new ArrayList<>();
        if (hybridItemsList != null) {
            itemList.addAll(hybridItemsList);
        }
        if (curatedItemList != null) {
            itemList.addAll(curatedItemList);
        }
        List<String> platforms = new ArrayList<>();
        if (pageInfo.length == 0) {
            platforms.add(CmsPlatforms.ROKU.getAppId(brand));
        } else {
            for (PageForm page : pageInfo) {
                platforms.add(page.getPlatform().getAppId(brand));
            }
        }
        return new RokuQueueJson().getObject(itemList, CuratedListType.HYBRID, shelf, platforms);
    }

    /**
     * @param hybridItemsList - list of assets that added to cintent list with rule
     * @param curatedItemList - list of assets without rules
     * @param type            - type of List POST to API
     * @param shelf           -shelf object
     * @param pageInfo        - page form entity
     * @return entity of RokuQueueJson
     */
    public RokuQueueJson
    getObject(List<String> hybridItemsList, List<String> curatedItemList, CuratedListType type, Shelf shelf, PageForm... pageInfo) {
        List<ContentItemListObject> itemList = new ArrayList<>();
        if (hybridItemsList != null) {
            itemList.addAll(collectLatestEpisodeInfo(hybridItemsList));
        }
        if (curatedItemList != null) {
            itemList.addAll(collectItemsInfoByTitle(curatedItemList));
        }
        List<String> platforms = new ArrayList<>();
        if (pageInfo.length == 0) {
            platforms.add(CmsPlatforms.ROKU.getAppId(brand));
        } else {
            for (PageForm pageForm : pageInfo) {
                platforms.add(pageForm.getPlatform().getAppId(brand));
            }
        }
        return new RokuQueueJson().getObject(itemList, type, shelf, platforms);
    }

    /**
     * @param hybridItemsList - list of assets that added to cintent list with rule
     * @param curatedItemList - list of assets without rules
     * @param type            - type of List POST to API
     * @param shelf           -Feature carousel object
     * @param pageInfo        - page form entity
     * @return entity of RokuQueueJson
     */
    public RokuQueueJson getObject(List<String> hybridItemsList, List<String> curatedItemList, CuratedListType type, FeatureCarouselForm shelf, PageForm... pageInfo) {
        List<ContentItemListObject> itemList = new ArrayList<>();
        if (hybridItemsList != null) {
            itemList.addAll(collectLatestEpisodeInfo(hybridItemsList));
        }
        if (curatedItemList != null) {
            itemList.addAll(collectItemsInfoByTitle(curatedItemList));
        }
        List<String> platforms = new ArrayList<>();
        if (pageInfo.length == 0) {
            platforms.add(CmsPlatforms.ROKU.getAppId(brand));
        } else {
            for (PageForm page : pageInfo) {
                platforms.add(page.getPlatform().getAppId(brand));
            }
        }
        return new RokuQueueJson().getObject(itemList, type, shelf, platforms);
    }

    /**
     * @param hybridItemsList - list of assets that added to cintent list with rule
     * @param curatedItemList - list of assets without rules
     * @param type            - type of List POST to API
     * @param shelf           -Feature carousel object
     * @param pageInfo        - page form entity
     *
     * @return - roku queue json object
     */
    public RokuQueueJson getObject(List<String> hybridItemsList, List<String> curatedItemList, CuratedListType type, VoidShelf shelf, PageForm... pageInfo) {
        List<ContentItemListObject> itemList = new ArrayList<>();
        if (hybridItemsList != null) {
            itemList.addAll(collectLatestEpisodeInfo(hybridItemsList));
        }
        if (curatedItemList != null) {
            itemList.addAll(collectItemsInfo(curatedItemList));
        } else {
            itemList.addAll(new ArrayList<ContentItemListObject>());
        }
        List<String> platforms = new ArrayList<>();
        if (pageInfo.length == 0) {
            platforms.add(CmsPlatforms.ROKU.getAppId(brand));
        } else {
            for (PageForm page : pageInfo) {
                platforms.add(page.getPlatform().getAppId(brand));
            }
        }
        return new RokuQueueJson().getObject(itemList, type, shelf, platforms);
    }

    /**
     * @param assetsTitles - list of program's titles for each check box 'show latest'
     *                     is checked within rather Shelf or Featured Carousel modules
     * @return object of Latest Episode Json
     */
    public List<LatestEpisodeJson> collectLatestEpisodeInfo(List<String> assetsTitles) {
        List<LatestEpisodeJson> latestEpisodeJsons = new ArrayList<>();
        if (assetsTitles.isEmpty()) {
            throw new TestRuntimeException("The assetIds array is: " + assetsTitles);
        }
        for (String title : assetsTitles) {
            String guid = nodeApi.getProgramNodeByName(title).getMpxAsset().getGuid();
            LatestEpisodeJson latestEpisodeJson = new LatestEpisodeJson().getRuleObject(guid);
            latestEpisodeJsons.add(latestEpisodeJson);
        }
        return latestEpisodeJsons;

    }

    /**
     * @param assetsTitle -  program's titles for each check box 'show latest'
     *                    is checked within rather Shelf or Featured Carousel modules
     * @param maxCount    - maxCount set to shelf
     * @return object of Latest Episode Json
     */
    public LatestEpisodeJson collectLatestEpisodeInfo(String assetsTitle, int maxCount) {
        String guid = getGuid(assetsTitle);
        return new LatestEpisodeJson().getRuleObject(guid, maxCount);
    }

    public String getGuid(String assetTitle) {
        return nodeApi.getNodeByName(assetTitle).getMpxAsset().getGuid();
    }

    /**
     * Method allow to get Program UUID from the UI based on its title.
     *
     * @param assetTitle - title of the Program.
     * @return show guid
     */
    public String getShowGuid(String assetTitle) {
        String guid;
        try {
            guid = nodeApi.getProgramNodeByName(assetTitle).getMpxAsset().getGuid();
        } catch (Exception e) {
            // in case of any exception (most likely program not found in CMS) just return empty String.
            guid = "";
        }
        return guid;
    }

    public String deleteRandomContentAsset(ContentType assetType, String... brand) {
        contentPage = mainRokuAdminPage.openContentPage(brand[0]);
        String randomAssetTitle = contentPage.getRandomAsset(assetType);
        String mpxId = contentPage.getMPXId(randomAssetTitle);
        ContentFilesPage contentFilesPage = mainRokuAdminPage.openContentFilePage(brand[0]);
        contentFilesPage.searchByTitle(randomAssetTitle).apply();
        if (!contentFilesPage.isContentPresent()) {
            WebDriverUtil.getInstance(webDriver).attachScreenshot();
            Utilities.logSevereMessage("There are no content matched with search criteria");
            throw new TestRuntimeException("There are no content matched with search criteria");
        }
        contentFilesPage.clickDeleteButton();
        return mpxId;
    }

    public String openRandomContentAsset(ContentType assetType, String... brand) {
        mainRokuAdminPage = this.openAdminPage();
        String currentBrand = brand.length == 0 ? this.brand : brand[0];
        contentPage = mainRokuAdminPage.openContentPage(currentBrand);
        contentPage.searchByType(assetType).searchByPublishedState(PublishState.YES).apply();
        WaitUtils.perform(webDriver).waitForPageLoad();

        switch (assetType) {
            case TVE_VIDEO: {
                List<String> assets = contentPage.filterByContentFormat(ContentFormat.FULL_EPISODE);
                if (!assets.isEmpty()) {
                    String title = assets.get(new Random().nextInt(assets.size()));
                    contentPage.openEditTVEVideoPage(title);
                    return title;
                }
                break;
            }
            case TVE_PROGRAM: {
                int numberOfElements = contentPage.getCountEntriesOfPage();
                if (numberOfElements == 0) {
                    WebDriverUtil.getInstance(webDriver).attachScreenshot();
                    Utilities.logSevereMessage("There are NO programs on the site.");
                    throw new SkipException("There are NO programs on the site.");
                }
                String title = contentPage.getFullTitleOfElement(new Random().nextInt(numberOfElements) + 1);
                contentPage.openEditOTTProgramPage(title);
                return title;
            }
            default: {
                Utilities.logSevereMessageThenFail(ASSET_TYPE_IS_NOT_EXIST);
            }
        }
        throw new TestRuntimeException(ASSET_TYPE_IS_NOT_EXIST);
    }

    /**
     * The method to get the List of  Programs (series) from the content by the given size
     *
     * @param count - count of assets to get, could be restricted by real size of the existing content type size
     * @return - list of assets' titles
     */
    public List<String> getPublishedPrograms(int count) {
        Map<String, String> entries = nodeApi.getAllPublishedPrograms().getEntity();
        List<String> allPrograms = entries.values().stream().collect(Collectors.toList());
        List<String> programsToAdd = new ArrayList<>();
        for (int i = 0; i < count && i <= allPrograms.size(); i++) {
            programsToAdd.add(allPrograms.get(i));
        }
        return programsToAdd;
    }


    // -------- Api Instance Configuration ------

    public ApiInstanceEntity createNewApiInstance(ApiInstanceEntity entity) {
        Utilities.logInfoMessage("Create New Api Instance : " + entity.getTitle());
        ApiInstancesPage apiInstancesPage = mainRokuAdminPage.openApiInstancesPage(brand);
        InstanceEditForm instanecEditForm = apiInstancesPage.clickAddNewItem();
        instanecEditForm.createApiInstance(entity);
        return entity;
    }

    public void deleteApiInstnace(String instance) {
        Utilities.logInfoMessage("Delete Api Instance logic: " + instance);
        ApiInstancesPage apiInstancesPage = mainRokuAdminPage.openApiInstancesPage(brand);
        apiInstancesPage.deleteInstance(instance).clickSubmit();
    }

    public boolean isIosApiInstancesConfigured() {
        Utilities.logInfoMessage("Check that Concerto API instance is configured on brand...");
        ApiInstancesPage apiInstancesPage = mainRokuAdminPage.openApiInstancesPage(brand);
        return apiInstancesPage.isIosBrandConfigured();
    }

    // ------ Panelizer methods -----
    public String createPanelizer(PanelizerManagerPage panelizerManagerPage) {
        Utilities.logInfoMessage("Create new Panrelizer");
        AddPanelizerPage addPanelizer = panelizerManagerPage.clickAddPanelizer();
        String panelizerName = addPanelizer.inputTitle().save();
        Assertion.assertTrue(addPanelizer.isStatusMessageShown(),
                "Status message is not shown");
        mainRokuAdminPage.openPanelizerManagementPage(brand);
        return panelizerName;
    }

    public EditPageWithPanelizer openEditRandomPageWithPanelizer() {
        TVEPage tvePage = mainRokuAdminPage.openPage(TVEPage.class, brand);
        List<String> pageTitles = tvePage.getAllPageTitlesOnPage();
        String pageTitle = pageTitles.get(new Random().nextInt(pageTitles.size()));
        tvePage.clickEdit(pageTitle);
        WaitUtils.perform(webDriver).waitForPageLoad();
        return new EditPageWithPanelizer(webDriver, aid);
    }

    // ------ PreView Page of OTT Page ----
    public PageWithPanelizer openViewPageWithPanelizer(String pageTitle) {
        TVEPage tvePage = mainRokuAdminPage.openPage(TVEPage.class, brand);
        tvePage.clickEdit(pageTitle);
        return new PageWithPanelizer(webDriver, aid);
    }

    //  ------- Add module To Page using Panelizer ------
    public EditPageWithPanelizer addModule(PageForm pageInfo, Module module) {
        Utilities.logInfoMessage("Add module: " + module.getTitle() + " to the Page: " + pageInfo.getTitle());

        EditPageWithPanelizer editPageWithPanelizer = new EditPageWithPanelizer(webDriver, aid);
        openEditOttPage(pageInfo.getTitle());
        editPageWithPanelizer.setModule(module.getTitle());
        Assertion.assertTrue(editPageWithPanelizer.getModuleBlock(module.getTitle()).isBlockPresent(), "The Module wasn't added", webDriver);
        Utilities.logInfoMessage("The Module block : " + module.getTitle() + " is present");
        return editPageWithPanelizer;
    }

    public EditPageWithPanelizer updateModule(Shelf shelfToUpdate, Shelf shelfNew) {
        Utilities.logInfoMessage("Update module: " + shelfToUpdate.getTitle() + " on the Page: ");
        EditPageWithPanelizer editPageWithPanelizer = new EditPageWithPanelizer(webDriver, aid);
        ModuleAddPage addPage = editPageWithPanelizer.getModuleBlock(shelfToUpdate.getTitle()).editBlock();
        addPage.selectShelf(shelfNew.getTitle()).clickFinish();
        Assertion.assertTrue(editPageWithPanelizer.getModuleBlock(shelfNew.getTitle()).isBlockPresent(), "The Module wasn't added", webDriver);
        Utilities.logInfoMessage("The Module block : " + shelfNew.getTitle() + " is present");
        return editPageWithPanelizer;
    }

    public String createPanelizerTemplate(PanelizerTemplates panelizerTemplate) {
        PanelizerManagerPage panelizerManagerPage = mainRokuAdminPage.openPanelizerManagementPage(brand);
        String panelizerName = this.createPanelizer(panelizerManagerPage);
        LayoutPanelizerPage layoutPanelizerPage = panelizerManagerPage.clickLayoutForPanelizer(panelizerName);
        layoutPanelizerPage.createTemplate(panelizerTemplate);

        return panelizerName;
    }

    public ContentTypePage openContentTypePage(Content content) {
        return (ContentTypePage) mainRokuAdminPage.openPage(content.getPage(), brand);
    }

    public ContentTypePage openRandomContentTypePage() {
        mainRokuAdminPage.openContentPage(brand).clickEditLinkForRandomAsset();
        return ReflectionUtils.getInstance(EpisodePage.class, webDriver, aid);
    }

    public void openEditEntityPageByUuid(String uuid) {
        mainRokuAdminPage.openEditEntityPageByUuid(uuid, brand);
    }


    // ------ Chiller's Content TYpe logic -----
    public CollectionAbstractPage createCollection(Collection collection) {
        CollectionAbstractPage collectionPage = (CollectionAbstractPage) mainRokuAdminPage.openPage(collection.getPage(), brand);
        collectionPage.createCollection(collection);
        return collectionPage;
    }

    public CollectionAbstractPage createAndPublishCollection(Collection collection) {
        CollectionAbstractPage collectionPage = (CollectionAbstractPage) mainRokuAdminPage.openPage(collection.getPage(), brand);
        collectionPage.createAndPublishCollection(collection);
        return collectionPage;
    }

    public CollectionsContentPage deleteCollection(Collection collection) {
        CollectionsContentPage collectionsPage = mainRokuAdminPage.openPage(CollectionsContentPage.class, brand);
        collectionsPage.deleteCollection(collection);
        return collectionsPage;
    }

    public CollectionAbstractPage editCollection(Collection collection) {
        CollectionsContentPage collectionsPage = mainRokuAdminPage.openPage(CollectionsContentPage.class, brand);
        return (CollectionAbstractPage) collectionsPage.clickEditLink(collection.getPage(), collection);
    }

    public CollectionAbstractPage updateCollection(Collection collectionOld, Collection collectionNew) {
        Utilities.logInfoMessage("Update collection item: " + collectionOld.getTitle());
        CollectionAbstractPage collectionListPage = editCollection(collectionOld);
        collectionListPage.createCollection(collectionNew);
        return collectionListPage;
    }

    public ContentTypePage createAndPublishContentType(Content content) {
        contentTypePage = openContentTypePage(content);
        contentTypePage.createAndPublish(content);
        return contentTypePage;
    }

    public ContentTypePage createContentType(Content content, boolean... update) {
        contentTypePage = openContentTypePage(content);
        contentTypePage.create(content);
        if (content.getTitle() != null && (update.length == 0 || update[0])) {
            updateContentByUuid(content);
        }
        return contentTypePage;
    }

    public Content updateContentByUuid(Content content) {
        RegistryServiceHelper serviceHelper = new RegistryServiceHelper(brand);
        RegistryServiceEntity serviceEntity;
        if (content.getType().isHasRegistryService()) {
            serviceEntity = new RegistryServiceEntity(content);
            content.getGeneralInfo().setUuid(serviceHelper.getUuid(serviceEntity));
        }
        getInfoFromDevel(content);

        return content;
    }

    /**
     * Method turn content type with all info from Devel page, uuids, revision, images etc
     *
     * @param content - content type that info you populated within test script
     * @return
     */
    private Content getInfoFromDevel(Content content) {
        DevelPage develPage = mainRokuAdminPage.openDevelPage();
        if (develPage == null) {
            return content;
        }
        if (!content.getType().isHasRegistryService()) {
            String uuid = develPage.getUuid();
            content.getGeneralInfo().setUuid(uuid);
        }
        int revision = Integer.parseInt(develPage.getVid());
        content.getGeneralInfo().setRevision(revision);

        List<MediaImage> mediaImages = content.getMediaImages();
        if (CollectionUtils.isNotEmpty(mediaImages)) {
            content.setMediaImages(develPage.getMediaUuids(content.getMediaImages()));
        }

        content.setPublishedDate(Integer.parseInt(develPage.getChangedDate()));
        mainRokuAdminPage.openEditPage();
        return content;
    }

    /**
     * Method populated channel reference object by Uuids of appropriate content types you would like to now
     *
     * @param contentToUpdate - the content type object, you would like to populate by Uuids of channel references
     * @param relatedContents - the array (set) of related content types to get Uuids for(Episode,Series,Season,Event)
     * @return content
     */
    public Content updateChannelReferenceByUuid(Content contentToUpdate, Content... relatedContents) {
        RegistryServiceHelper serviceHelper = new RegistryServiceHelper(brand);
        RegistryServiceEntity serviceEntity = null;
        for (int i = 0; i < relatedContents.length; i++) {
            if (relatedContents[i] instanceof Series) {
                serviceEntity = new RegistryServiceEntity(ItemTypes.SERIES, relatedContents[i]);
                contentToUpdate.getAssociations().getChannelReferenceAssociations().getChannelReference().setSeries(serviceHelper.getUuid(serviceEntity));
            } else if (relatedContents[i] instanceof Season) {
                serviceEntity = new RegistryServiceEntity(ItemTypes.SEASON, relatedContents[i]);
                contentToUpdate.getAssociations().getChannelReferenceAssociations().getChannelReference().setSeason(serviceHelper.getUuid(serviceEntity));
            } else if (relatedContents[i] instanceof Episode) {
                serviceEntity = new RegistryServiceEntity(ItemTypes.EPISODE, relatedContents[i]);
                contentToUpdate.getAssociations().getChannelReferenceAssociations().getChannelReference().setEpisode(serviceHelper.getUuid(serviceEntity));
            } else if (relatedContents[i] instanceof Event) {
                serviceEntity = new RegistryServiceEntity(ItemTypes.EVENT, relatedContents[i]);
                contentToUpdate.getAssociations().getChannelReferenceAssociations().getChannelReference()
                        .setSeries(serviceHelper.getUuid(serviceEntity))
                        .setItemType(relatedContents[i].getType().getItemType());
            }
        }
        return contentToUpdate;
    }

    public Collection updateChannelReferenceByUuid(Collection collectionToUpdate, Content... relatedContents) {
        RegistryServiceHelper serviceHelper = new RegistryServiceHelper(brand);
        RegistryServiceEntity serviceEntity = null;
        for (int i = 0; i < relatedContents.length; i++) {
            if (relatedContents[i] instanceof Series) {
                serviceEntity = new RegistryServiceEntity(ItemTypes.SERIES, relatedContents[i]);
                collectionToUpdate.getAssociations().getChannelReferenceAssociations().getChannelReference().setSeries(serviceHelper.getUuid(serviceEntity));
            } else if (relatedContents[i] instanceof Event) {
                serviceEntity = new RegistryServiceEntity(ItemTypes.EVENT, relatedContents[i]);
                collectionToUpdate.getAssociations().getChannelReferenceAssociations().getChannelReference().setSeries(serviceHelper.getUuid(serviceEntity));
            } else if (relatedContents[i] instanceof Season) {
                serviceEntity = new RegistryServiceEntity(ItemTypes.SEASON, relatedContents[i]);
                collectionToUpdate.getAssociations().getChannelReferenceAssociations().getChannelReference().setSeason(serviceHelper.getUuid(serviceEntity));
            } else if (relatedContents[i] instanceof Episode) {
                serviceEntity = new RegistryServiceEntity(ItemTypes.EPISODE, relatedContents[i]);
                collectionToUpdate.getAssociations().getChannelReferenceAssociations().getChannelReference().setEpisode(serviceHelper.getUuid(serviceEntity));
            }
        }
        return collectionToUpdate;
    }

    // ------------------

    public ContentTypePage editContentType(Content content) {
        contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        return (ContentTypePage) contentPage.clickEditLink(content.getPage(), content.getTitle());
    }

    public ContentPage deleteContentType(Content content) {
        Utilities.logInfoMessage(DELETE + content.getTitle());
        contentPage = mainRokuAdminPage.openContentPage(brand);
        contentPage.searchByType(content.getType().getContentType()).apply();
        contentPage.clickDeleteButton(content.getTitle());
        Assertion.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "Status message is not shown by item delete");
        Utilities.logInfoMessage("The content item: " + content.getTitle() + " is deleted");
        return contentPage;
    }

    public ContentPage deleteContentTypesByBulkOperation(Content content, Content... contents) {
        contentPage = mainRokuAdminPage.openContentPage(brand);
        Utilities.logInfoMessage(DELETE + content.getTitle());
        contentPage.searchByTitle(content.getTitle()).apply();
        contentPage.checkItemWithoutSearch(content.getTitle(), content.getType().getContentType().get());
        for (Content optionalContent : contents) {
            Utilities.logInfoMessage(DELETE + optionalContent.getTitle());
            contentPage.checkItemWithoutSearch(optionalContent.getTitle(), optionalContent.getType().getContentType().get());
        }
        contentPage.selectOperation(ContentPage.Operation.DELETE_ITEM).clickExecute();
        contentPage.confirmExecute();
        return contentPage;
    }

    public ContentPage publishContentTypesByBulkOperation(Content content, Content... contents) {
        contentPage = mainRokuAdminPage.openContentPage(brand);
        Utilities.logInfoMessage("Publish " + content.getTitle());
        contentPage.checkItemWithoutSearch(content.getTitle(), content.getType().getContentType().get());
        for (Content optionalContent : contents) {
            Utilities.logInfoMessage("Publish " + optionalContent.getTitle());
            contentPage.checkItemWithoutSearch(optionalContent.getTitle(), optionalContent.getType().getContentType().get());
        }
        contentPage.selectOperation(ContentPage.Operation.PUBLISH_TO_API).clickExecute().publishToAllInstances();
        return contentPage;
    }

    public ContentTypePage updateContent(Content contentOld, Content contentNew) {
        Utilities.logInfoMessage("Update content item: " + contentOld.getTitle());
        contentTypePage = editContentType(contentOld);
        contentTypePage.create(contentNew);
        return contentTypePage;
    }

    public ContentTypePage updateContentExternalLinks(Content content) {
        Utilities.logInfoMessage("Update External links for content item: " + content.getTitle());
        contentTypePage = editContentType(content);
        contentTypePage.enterExternalLinksData(content.getExternalLinksInfo()).saveAsDraft();
        return contentTypePage;
    }

    //-------------------- Migration realted logic -----------------------

    public ImportPage performMigration(MigrationPage.Groups groups, ImportPage.Task task) {
        MigrationPage migrationPage = mainRokuAdminPage.openPage(MigrationPage.class, brand);
        ImportPage importPage = migrationPage.selectGroupToImport(groups);
        if (task.equals(ImportPage.Task.ALL)) {
            importPage.selectAllTasks();
            importPage.performImport();
        } else {
            importPage.importTask(task);
        }
        return importPage;
    }

    // -------- Methods for Asset Library content ------
    public EditMultipleFilesPage openForEditImageFromAssetLibrary(String imageName) {
        AssetLibraryPage assetLibraryPage = mainRokuAdminPage.openPage(AssetLibraryPage.class, brand);
        assetLibraryPage.filterByName(imageName);
        WaitUtils.perform(webDriver).waitForPageLoad();
        FileBlock fileBlock = assetLibraryPage.getFiles().get(0);
        fileBlock.click();
        return new EditMultipleFilesPage(webDriver, aid);
    }

    public boolean checkImagesPublishingByUpload(FilesMetadata... metaData) {
        SoftAssert softAssert = new SoftAssert();
        int i = 0;
        for (FilesMetadata expectedFilesMetadata : metaData) {
            AssetLibraryPage assetLibraryPage = mainRokuAdminPage.openPage(AssetLibraryPage.class, brand);
            EditMultipleFilesPage editMultipleFilesPage = assetLibraryPage.clickAssetByIndex(i);
            editMultipleFilesPage.enterFilesMetadata(expectedFilesMetadata);
            editMultipleFilesPage.save();
            String url = mainRokuAdminPage.getLogURL(brand);
            ImagesJson expectedImage = editMultipleFilesPage.updateMetadataForPublishing(expectedFilesMetadata);
            ImagesJson actualImageJson = new RequestHelper().getSingleParsedResponse(url, ConcertoApiPublishingTypes.FILE_IMAGE);
            softAssert.assertEquals(expectedImage, actualImageJson, "Images were not equal", "Images are equal", new ImageVerificator(), webDriver);
            i++;
        }
        return softAssert.getTempStatus();
    }

    public String getLogURL(final String brand) {
        String sessionId = getSessionId();
        return Config.getInstance().getAqaAPIUrlBuilderBySession(brand, sessionId);
    }

    private String getSessionId() {
        mainRokuAdminPage = new MainRokuAdminPage(webDriver, aid);
        String url = mainRokuAdminPage.getLogPageUrl();
        if (url != null) {
            return url.substring(url.indexOf('=') + 1);
        } else {
            return mainRokuAdminPage.openOTTLogPage(brand).getLatestSessionId();
        }
    }

    public boolean checkContentIsPresent(Content content) {
        SoftAssert softAssert = new SoftAssert();
        contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        contentPage.searchByType(content.getType().getContentType()).searchByTitle(content.getTitle()).apply();
        softAssert.assertTrue(contentPage.isContentOnlyOne(),
                "The search result by name " + content.getTitle() + "and type" + content.getType() + " is not 1", webDriver);
        return softAssert.getTempStatus();
    }

    public boolean checkCollectionIsPresent(Collection collection) {
        SoftAssert softAssert = new SoftAssert();
        CollectionsContentPage collectionsContentPage = mainRokuAdminPage.openPage(CollectionsContentPage.class, brand);
        softAssert.assertTrue(collectionsContentPage.isCollectionPresent(collection), "Collection with title " +
                collection.getTitle() + " and type " + collection.getCollectionInfo().getType() + " is not present", webDriver);
        return softAssert.getTempStatus();
    }

    /**
     * Method searches the video by the given name and then collect Video object required fields, including channel references.
     *
     * @param assetTitle - title of the video asset to search at the content
     * @return - metadata Video object for the searched video
     */
    public Video getVideoData(String assetTitle) {
        contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        EditTVEVideoContentPage editPage = contentPage.openEditTVEVideoPage(assetTitle);
        //setSlug
        Video video = editPage.getVideo();
        contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        if (video.getMpxAsset().getSeriesTitle() != null) {
            EditTVEProgramContentPage programContentPage = contentPage.openEditOTTProgramPage(video.getMpxAsset().getSeriesTitle());
            String programUuid = programContentPage.openDevelPage().getUuid();
            ChannelReference channelReference = new ChannelReference();
            channelReference.setSeries(programUuid);
            video.getAssociations().getChannelReferenceAssociations().setChannelReference(channelReference);
        } else {
            Utilities.logSevereMessage("The relates series name is empty, taht's why Channel reference object are not created ");
        }
        return video;
    }

    /**
     * Method searches the  random video and then collect Video object required fields, including channel references.
     *
     * @return metadata Video object for a video
     */
    public Video getRandomVideoDataChiller() {
        contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        String assetTitle = contentPage.getRandomAsset(ContentType.TVE_VIDEO);
        ChillerVideoPage chillerVideoPage = contentPage.clickEditLink(ChillerVideoPage.class, assetTitle);
        return chillerVideoPage.getPageData();
    }

    public RokuVideoJson getVideoDataForSerialApi(String videoTitle) {
        contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        return contentPage.getVideoObject(videoTitle, brand);
    }


    public Map<AdvancedIngestionOptions, Boolean> setAdvancedIngestionOptions(Map<AdvancedIngestionOptions, Boolean> options) {
        AdvancedIngestOptionPage advanceIngestionOptionsPage = mainRokuAdminPage.openAdvancedIngestOptions(brand);
        Map<AdvancedIngestionOptions, Boolean> initialOptions = advanceIngestionOptionsPage.getStatusForAllOptions();
        options.keySet().forEach(option -> advanceIngestionOptionsPage.setStatusForOption(option, options.get(option)));
        advanceIngestionOptionsPage.saveSettings();
        return initialOptions;
    }


    public GlobalVideoEntity getVideo(String videoTitle) {
        if (brand.equals("chiller")) {
            return videoDataCollector.collectVideoInfo(videoTitle);
        }
        return videoDataCollectorFromApi.collectVideoInfo(videoTitle);
    }

    public GlobalProgramEntity getProgram(String programTitle) {
        return programDataCollectorFromApi.collectProgramInfo(programTitle);
    }

    public GlobalProgramEntity getRandomProgram() {
        return programDataCollectorFromApi.collectRandomProgramInfo();
    }

    public GlobalVideoEntity getRandomVideo() {
        return videoDataCollectorFromApi.collectRandomVideoInfo();
    }

    private String getTaxonomyTermUuid(String termName, TaxonomyTerm term) {
        navigateToTaxonomyTerm(termName, term);
        AddTaxonomyTermPage addTermPage = new AddTaxonomyTermPage(webDriver, aid);
        return addTermPage.openDevelPage().getUuid();
    }

    public String getTaxonomyTermUuid(TaxonomyTerm term) {
        return getTaxonomyTermUuid(term.getTaxonomyType().getName(), term);
    }

    private AddTaxonomyTermPage navigateToTaxonomyTerm(String termName, TaxonomyTerm term) {
        String partUrl = termName + "/" + term.getTitle();
        partUrl = CustomBrandNames.CHILLER.equals(CustomBrandNames.getBrandByName(brand)) ? partUrl.replaceAll(" ", "-") :
                partUrl.replaceAll(" ", "");
        webDriver.get(Config.getInstance().getRokuHomePage(brand) + partUrl);
        mainRokuAdminPage.openEditPage();
        return new AddTaxonomyTermPage(webDriver, aid);
    }
}
