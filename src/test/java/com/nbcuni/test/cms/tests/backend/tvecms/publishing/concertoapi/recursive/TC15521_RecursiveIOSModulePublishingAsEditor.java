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
public class TC15521_RecursiveIOSModulePublishingAsEditor extends BaseAuthFlowTest {

    /**
     * Pre-Conditions:
     * Make sure:
     * There is a primary (e.g Development ) TVE API Endpoint
     * There is an Amazon endpoint
     * There is configured Platform IOS
     * Amazon Endponit is configured to be point to the primary Development
     * Log as Admin
     * Create Page with IPS Platform
     * <p/>
     * Steps:
     * 1.Go to the CMS As Editor
     * Verify: The Editor Panel is present
     * <p/>
     * 2.Go to the Module list and create:
     * Featured Carousel
     * Featured Show
     * Shelf modules
     * Verify: The modules is saved
     * <p/>
     * 3.Go to Page list and open a Page created in pre-condition
     * Verify: The Page is assign to the IOS Platform
     * <p/>
     * 4.Assign the modules created in step #2 to the page and save
     * Verify: The Page is saved with added modules
     * <p/>
     * 5.Publish Page to API
     * Verify:The POST request is sent to API:
     * There are 4 POST requests: for  Page and all assigned modules
     * <p/>
     * 6.Review POST request for Page
     * Verify:The scheme is matched with contract below:
     * http://private-e3bfd-concertoapiingestmaster.apiary-mock.com/list
     * 7.Check metadata and scheme for each of added modules
     * Verify: The scheme and data within POST request match module's data and scheme below:
     * http://private-e3bfd-concertoapiingestmaster.apiary-mock.com/list
     **/

    private static PageForm pageForm = null;
    private FeatureCarouselForm featureCarousel;
    private FeatureShowModule featureShowModule;
    private List<CollectionJson> actualCollectionJsonList = new ArrayList<>();
    private Shelf shelf;

    @Test(groups = {"recursive_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider")
    public void checkPageIOSPublishEditor(String brand) {

        //Pre-condition
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Create page
        pageForm = rokuBackEndLayer.createPage(CmsPlatforms.IOS);

        //Step 1
        mainRokuAdminPage = rokuBackEndLayer.openAdminPageAsEditor();

        //Step 2
        //Step create the module to update page
        shelf = EntityFactory.getShelfsList().get(0);
        featureCarousel = CreateFactoryFeatureCarousel.createRandomFeatureCarousel();
        featureShowModule = CreateFeatureShowModule.createDefault();
        shelf = (Shelf) rokuBackEndLayer.createModule(shelf);
        featureShowModule = (FeatureShowModule) rokuBackEndLayer.createModule(featureShowModule);
        featureCarousel = (FeatureCarouselForm) rokuBackEndLayer.createModule(featureCarousel);

        //Step 3
        EditPageWithPanelizer editPage = new EditPageWithPanelizer(webDriver, aid);

        //Step 4
        pageForm.setModules(Arrays.asList(featureCarousel, featureShowModule, shelf));
        pageForm = rokuBackEndLayer.updatePage(pageForm);

        //Step 5
        editPage.elementPublishBlock().publishByTabName(PublishInstance.DEV);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);
        softAssert.assertFalse(editPage.isErrorMessagePresent(), "The error message is present after publishing with message" + mainRokuAdminPage.getErrorMessage(),
                "The error message is not present after publishing Shelf with message", webDriver);
        String url = editPage.getLogURL(brand);

        //Step 6 Get Expected result
        List<CollectionJson> expectedCollectionList = Arrays.asList(
                new CollectionJson(pageForm),
                new CollectionJson(featureShowModule),
                new CollectionJson(featureCarousel),
                new CollectionJson(shelf));

        //Step 7 Get Actual Post Request
        actualCollectionJsonList = requestHelper.getParsedResponse(url, ConcertoApiPublishingTypes.COLLECTIONS);

        softAssert.assertReflectEquals(expectedCollectionList, actualCollectionJsonList, "The actual list of data is not matched with expected",
                "The list of JSONs is matched with expected");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

    @AfterMethod(alwaysRun = true)
    public void cleanUpContent() {
        try {
            rokuBackEndLayer.deleteOttPage(pageForm.getTitle());
            rokuBackEndLayer.deleteModule(shelf.getTitle());
            rokuBackEndLayer.deleteModule(featureCarousel.getTitle());
            rokuBackEndLayer.deleteModule(featureShowModule.getTitle());
        } catch (Throwable e) {
            Utilities.logSevereMessage("Error in tear down method, " + Utilities.convertStackTraceToString(e));
        }
    }

}
