package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi.recursive;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featurecarousel.FeatureCarouselForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featurecarousel.factory.CreateFactoryFeatureCarousel;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featureshow.FeatureShowModule;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featureshow.factory.CreateFeatureShowModule;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.pageobjectutils.tvecms.PublishInstance;
import com.nbcuni.test.cms.pageobjectutils.tvecms.shelf.ShelfType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.collection.CollectionJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Aleksandra_Lishaeva on 7/19/16.
 */
public class TC15523_RecursiveIOSModulePublishingUpdatesAsEditor extends BaseAuthFlowTest {

    /**
     * Pre-Conditions:
     * Go to CMS as admin and create :
     * Make sure there is an Amazon endpoint configured as secondary instance for Serial API Endpoint
     * Platform IOS is configured
     * Log As Admin
     * Create A page with IOS Platform
     * <p>
     * Steps:
     * 1.Go to CMS as editor
     * Verify: Editor Panel is present
     * <p>
     * 2.Go to the Module list and create:
     * Featured Carousel1,Featured Carousel2
     * Featured Show1,Featured Show2
     * Shelf 1,Shelf2 modules
     * Verify: The modules are saved
     * <p>
     * 3.Go to Page list and open page created in pre-condition
     * Verify: The Page isb assign to the IOS Platform
     * <p>
     * 4.Assign the modules with post-fix''1" ,created in step #2, to the page and save
     * Verify: The Page is saved with added modules with postfix '1'
     * <p>
     * 5.Publish Module to API
     * Verify: The POST request is sent to API:
     * There are 4 POST requests: for  Page and all assigned modules
     * <p>
     * 6.Replace modules from the page and updated new one with postfix '2' - save Page
     * Verify:  The Page is updated by new modules
     * <p>
     * 7.Publish Page to API
     * Verify: The POST request is sent to API:
     * There are 4 POST requests: for  Page and all assigned modules
     * <p>
     * 8.Review POST request for Page
     * Verify: The scheme is matched with contract below:
     * http://private-e3bfd-concertoapiingestmaster.apiary-mock.com/list
     * <p>
     * The listItems contains the UUIDs of new added modules
     * <p>
     * 9.Check metadata and scheme for each of added modules
     * Verify: The scheme and data within POST request match module's data and scheme below:
     * http://private-e3bfd-concertoapiingestmaster.apiary-mock.com/list
     **/

    private static PageForm pageForm = null;
    private FeatureCarouselForm featureCarousel;
    private FeatureCarouselForm featureCarousel2;
    private FeatureShowModule featureShowModule;
    private FeatureShowModule featureShowModule2;
    private List<CollectionJson> actualCollectionJsonList = new ArrayList<>();
    private Shelf shelf;
    private Shelf shelf2;

    @Test(groups = {"recursive_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider")
    public void chcekPageIOSPublish(String brand) {

        //Step Pre-condition
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        pageForm = rokuBackEndLayer.createPage(CmsPlatforms.IOS);

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPageAsEditor();

        //Step 2
        createModules();

        //Step 3
        EditPageWithPanelizer editPage = rokuBackEndLayer.openEditOttPage(pageForm.getTitle());
        editPage.cleanUpAllModules().save();

        //Step 4
        pageForm.setModules(Arrays.asList(featureCarousel, featureShowModule, shelf));
        pageForm = rokuBackEndLayer.updatePage(pageForm);

        //Step 5
        editPage.elementPublishBlock().publishByTabName(PublishInstance.DEV);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);
        softAssert.assertFalse(editPage.isErrorMessagePresent(), "The error message is present after publishing with message" + mainRokuAdminPage.getErrorMessage(),
                "The error message is not present after publishing Page with message", webDriver);

        //Step 6
        editPage.cleanUpAllModules().save();
        pageForm.setModules(Arrays.asList(featureCarousel2, featureShowModule2, shelf2));
        pageForm = rokuBackEndLayer.updatePage(pageForm);


        //Step 7
        editPage.elementPublishBlock().publishByTabName(PublishInstance.DEV);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after re-publishing", "The status message is shown after re-publishing recursive", webDriver);
        softAssert.assertFalse(editPage.isErrorMessagePresent(), "The error message is present after re-publishing recursive with message" + mainRokuAdminPage.getErrorMessage(),
                "The error message is not present after re-publishing recursive Page with message", webDriver);
        String url = editPage.getLogURL(brand);
        //Get Expected result
        List<CollectionJson> expectedCollectionList = Arrays.asList(
                new CollectionJson(pageForm),
                new CollectionJson(featureShowModule2),
                new CollectionJson(featureCarousel2),
                new CollectionJson(shelf2));

        //Get Actual Post Request
        actualCollectionJsonList = requestHelper.getParsedResponse(url, ConcertoApiPublishingTypes.COLLECTIONS);

        softAssert.assertReflectEquals(expectedCollectionList, actualCollectionJsonList, "The actual list of data is not matched with expected",
                "The list of JSONs is matched with expected");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

    @AfterMethod(alwaysRun = true)
    public void cleanUpContent() {
        try {
            rokuBackEndLayer.openAdminPage();
            rokuBackEndLayer.deleteOttPage(pageForm.getTitle());
            rokuBackEndLayer.deleteModule(shelf.getTitle());
            rokuBackEndLayer.deleteModule(shelf2.getTitle());
            rokuBackEndLayer.deleteModule(featureCarousel.getTitle());
            rokuBackEndLayer.deleteModule(featureCarousel2.getTitle());
            rokuBackEndLayer.deleteModule(featureShowModule.getTitle());
            rokuBackEndLayer.deleteModule(featureShowModule2.getTitle());
        } catch (Throwable e) {
            Utilities.logSevereMessage("Error in tear down method, " + Utilities.convertStackTraceToString(e));
        }
    }

    public void createModules() {
        //Step create the module to update page
        shelf = EntityFactory.getShelfsList().get(1);
        featureCarousel = CreateFactoryFeatureCarousel.createRandomFeatureCarousel();
        featureShowModule = CreateFeatureShowModule.createUpdatedModuleForEditor();

        shelf2 = (Shelf) rokuBackEndLayer.getModulesStrategyForConcerto(ShelfType.SHELF);
        featureCarousel2 = (FeatureCarouselForm) rokuBackEndLayer.getModulesStrategyForConcerto(ShelfType.FEATURE_CAROUSEL);
        featureShowModule2 = (FeatureShowModule) rokuBackEndLayer.getModulesStrategyForConcerto(ShelfType.FEATURE_SHOWS);

        shelf = (Shelf) rokuBackEndLayer.createModule(shelf);
        featureShowModule = (FeatureShowModule) rokuBackEndLayer.createModule(featureShowModule);
        featureShowModule2 = (FeatureShowModule) rokuBackEndLayer.createModule(featureShowModule2);
        featureCarousel = (FeatureCarouselForm) rokuBackEndLayer.createModule(featureCarousel);
        featureCarousel2 = (FeatureCarouselForm) rokuBackEndLayer.createModule(featureCarousel2);
    }
}
