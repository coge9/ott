package com.nbcuni.test.cms.tests.backend.tvecms.publishing.serialapi.curatedlist;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.Module;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.CreateFactoryModule;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.pageobjectutils.tvecms.shelf.CuratedListType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.shelf.ShelfType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuQueueJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Created by Ivan Karnilau on 3/15/17.
 */

/**
 * Pre-Conditions:
 * 1. Create new Roku/Android page
 * 2. Create new Custom module and add items
 * 3. Add created module to created page
 *
 * Steps:
 * 1. Open edit page for module
 * Edit page is opened
 * 2. Uncheck "Enable" checkbox for random item
 * Checkbox is unchecked
 * 3. Publish module
 * Module is published
 * Validation    Check publish log for list of module for Serial API
 * Massive "items" doesn't contain exluded item
 */

public class TC17677_PublishCustomModuleRokuWithUncheckedItem extends BaseAuthFlowTest {

    private static PageForm pageForm = null;
    private DraftModuleTab draftModuleTab;
    private Shelf shelf;

    @Test(groups = {"module_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider")
    public void checkAllFieldsModulePublishingIOS(String brand) {

        //Step 1

        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //pre-condition get Module
        shelf = (Shelf) rokuBackEndLayer.getModulesStrategyForConcerto(ShelfType.SHELF);
        CreateFactoryModule.disableRandomAsset(shelf);


        //Step 2
        shelf = (Shelf) rokuBackEndLayer.createModule(shelf);

        //Step 3

        pageForm = CreateFactoryPage.createDefaultPage().setPlatform(CmsPlatforms.ROKU);
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
        String url = rokuBackEndLayer.getLogURL(brand);
        //Get Expected result
        RokuQueueJson expectedQueueJson = new RokuQueueJson().getObject(brand, CuratedListType.CURATED, shelf, pageForm);
        //Get Actual Post Request
        RokuQueueJson actualQueueJson = requestHelper.getSingleParsedResponse(url, SerialApiPublishingTypes.LIST);

        softAssert.assertEquals(expectedQueueJson, actualQueueJson, "The actual data is not matched", "The JSON data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

    @AfterMethod(alwaysRun = true)
    public void cleanUpContent() {
        try {
            rokuBackEndLayer.deleteModule(shelf.getTitle());
            rokuBackEndLayer.deleteOttPage(pageForm.getTitle());
        } catch (Throwable e) {
            Utilities.logWarningMessage("Error in tear down method, " + e.getMessage());
        }
    }

}
