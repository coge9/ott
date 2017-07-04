package com.nbcuni.test.cms.tests.backend.tvecms.modules.shelf;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.Promo;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.Module;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.brands.RokuBrandNames;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.collection.CollectionJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.concerto.PromoJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.verification.roku.ModuleIosVerificator;
import com.nbcuni.test.cms.verification.roku.ios.PromoVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Created by Ivan_Karnilau on 9/15/2016.
 */

/**
 * TC15944
 *
 * Pre-Conditions:
 * Create new Promo [promo_name]
 *
 * 1. Go to CMS for IOS brand
 * Admin panel is present
 * 2. Go to create new Custom module (admin/ott/modules/add/shelf)
 * Create new module page is opened
 * 3. Fill required fields and add [promo_name]
 * Save
 * Module is created.
 * Promo is added.
 * 4. Create IOS page and add module from step 3
 * Publish
 * IOS page is published
 * 5. Check publish log
 * Promo is published
 */

public class TC15944_AddPromoToCustomModule extends BaseAuthFlowTest {

    @Autowired
    @Qualifier("withRequiredPromo")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;
    private PageForm pageForm = null;
    private DraftModuleTab draftModuleTab;
    private Shelf shelf;

    @BeforeMethod(alwaysRun = true)
    public void init() {
        content = contentTypeCreationStrategy.createContentType();
    }

    @Test(groups = {"module_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider")
    public void checkPromoWithCustomModulePublishing(String brand) {

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        ContentTypePage contentTypePage = rokuBackEndLayer.createContentType(content);
        content = contentTypePage.getPageData();

        //pre-condition get Module
        shelf = EntityFactory.getShelfsList().get(0);

        //Step 2
        shelf.setAssets(Arrays.asList(content.getTitle()));
        shelf = (Shelf) rokuBackEndLayer.createModule(shelf);

        //Step 3

        pageForm = CreateFactoryPage.createDefaultPage().setPlatform(RokuBrandNames.getBrandByName(brand).getPlatformMatcher().getRandomConcertoPlatform());
        rokuBackEndLayer.createPage(pageForm);

        //Step 4
        EditPageWithPanelizer editPage = rokuBackEndLayer.openEditOttPage(pageForm.getTitle());
        pageForm = editPage.updateFields(Arrays.<Module>asList(shelf));
        editPage.save();

        //Step 5
        draftModuleTab = mainRokuAdminPage.openOttModulesPage(brand).clickEditLink(shelf.getTitle());

        //Step 6
        draftModuleTab.elementPublishBlock().publishByTabName();
        softAssert.assertTrue(draftModuleTab.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);
        softAssert.assertFalse(draftModuleTab.isErrorMessagePresent(), "The error message is present after publishing with message" + mainRokuAdminPage.getErrorMessage(),
                "The error message is not present after publishing Shelf with message", webDriver);

        //Step 7
        String url = draftModuleTab.getLogURL(brand);
        //Get Expected result

        shelf.setContents(Arrays.asList(content));
        CollectionJson expectedCollectionJson = new CollectionJson(shelf);

        //Get Actual Post Request
        CollectionJson actualCollectionJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.MODULE);
        softAssert.assertTrue(new ModuleIosVerificator().verify(expectedCollectionJson, actualCollectionJson), "The actual data is not matched", "The JSON data is matched");

        PromoJson actualJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.PROMO);
        PromoJson expectedJson = new PromoJson((Promo) content);

        softAssert.assertTrue(new PromoVerificator().verify(expectedJson, actualJson), "The actual data is not matched",
                "The JSON data is matched");

        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

    @AfterMethod(alwaysRun = true)
    public void cleanUpContent() {
        try {
            rokuBackEndLayer.deleteModule(shelf.getTitle());
            rokuBackEndLayer.deleteOttPage(pageForm.getTitle());
            rokuBackEndLayer.deleteContentType(content);
        } catch (Throwable e) {
            Utilities.logInfoMessage("Error in tear down method, " + e.getMessage());
        }
    }
}
