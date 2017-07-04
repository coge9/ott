package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi.modules.custom;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.Module;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.pageobjectutils.chiller.actionpublishing.Action;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Created by Aleksandra_Lishaeva on 7/12/16.
 */
public class TC15319_CheckMessageAttributesOfCustomModuleConcerto extends BaseAuthFlowTest {

    /**
     * Go to CMS and create a Page with IOS platform!
     * Make sure:
     * <p/>
     * There is a primary (e.g Development )API Endpoint
     * There is an Amazon endpoint
     * Amazon Endponit is configured to be point to the primary Development
     * <p/>
     * Steps:
     * 1.Go to CMS  as user
     * Verify: The CMS User Panel is present
     * <p/>
     * 2.Go to TVE Modules page /admin/ott/modules and create a Custom Module
     * Verify: The custom module has Created
     * <p/>
     * 3.Go to Pages page /admin/ott/pages
     * Verify: The list of Pages is present
     * There is Page craeted in Precondition
     * <p/>
     * 4.Open Page on edit and add created in step #2 custom module. Save
     * Verify: The Pages is saved with added custom module
     * <p/>
     * 5.Go to the Module Edit page created in step #2
     * Verify: The Module Edit Page has Opened
     * There is Enabled Publishing Control
     * Only Primary, not Amazon endpoint is available(e.g Development)
     * <p/>
     * 6.Publish Module to Amazon API by clicking on Primary(Dev endpoint)
     * The POST request has sent
     * <p/>
     * 7.Check Message attributes within the Header of POST request
     * Verify: The Header contains next messages:
     * action = post
     * entityType = lists
     */

    private PageForm pageForm = null;
    private DraftModuleTab draftModuleTab;
    private Shelf shelf;

    @Test(groups = {"module_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider")
    public void checkAttributeMessagesModulePublishingConcerto(String brand) {

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //pre-condition get Module
        shelf = EntityFactory.getShelfsList().get(0);

        //Step 2
        shelf = (Shelf) rokuBackEndLayer.createModule(shelf);

        //Step 3
        pageForm = CreateFactoryPage.createDefaultPage().setPlatform(CmsPlatforms.IOS);
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
        LocalApiJson localApiJson = requestHelper.getLocalApiJsons(url, ConcertoApiPublishingTypes.MODULE).get(0);

        softAssert.assertTrue(localApiJson.getAttributes().getAction() != null, "The action message attribute are not present",
                "The action message attribute are present");

        softAssert.assertTrue(localApiJson.getAttributes().getEntityType() != null, "The entityType message attribute are not present",
                "The entityType message attribute are present");

        String action = localApiJson.getAttributes().getAction().getStringValue();

        softAssert.assertEquals(Action.POST.getAction(), action, "The action message attribute are not matched",
                "The action message attribute are matched");

        String entityType = localApiJson.getAttributes().getEntityType().getStringValue();
        softAssert.assertEquals(ItemTypes.COLLECTIONS.getEntityType(), entityType, "The entityType message attribute are not matched",
                "The entityType message attribute are matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

    @AfterMethod(alwaysRun = true)
    public void cleanUpContent() {
        try {
            rokuBackEndLayer.deleteModule(shelf.getTitle());
            rokuBackEndLayer.deleteOttPage(pageForm.getTitle());
        } catch (Throwable e) {
            Utilities.logSevereMessage("Error in tear down method, " + Utilities.convertStackTraceToString(e));
        }
    }

}
