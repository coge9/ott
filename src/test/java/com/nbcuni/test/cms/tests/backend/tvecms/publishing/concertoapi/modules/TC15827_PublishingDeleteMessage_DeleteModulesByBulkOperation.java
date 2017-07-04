package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi.modules;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.module.ModulesPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.Module;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.pageobjectutils.chiller.actionpublishing.Action;
import com.nbcuni.test.cms.pageobjectutils.tvecms.brands.RokuBrandNames;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.ContentTypeDeleteJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Created by Ivan_Karnilau on 3/1/2017.
 */
public class TC15827_PublishingDeleteMessage_DeleteModulesByBulkOperation extends BaseAuthFlowTest {

    /**
     * Pre-conditions:
     * 1. Login in CMS
     * 2. Make sure concerto API instance is configured on brand
     * 3. Make sure Ios platform is configured on brand
     * 4. Create Page with required fields.
     * Steps:
     * 1. Navigate TVE Pages
     * Verify: TVE Pages is opened.
     * <p>
     * 2. Delete the page we have created in pre-conditions by bulk operation.
     * Verify: Page is deleted successfully. Statue message is present. Delete message sent to Concerto API.
     * <p>
     * 3. Check Post request for node.
     */

    private PageForm pageForm;
    private Shelf shelf;

    @Test(groups = {"module_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider")
    public void checkDeleteModulePConcerto(String brand) {

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //pre-condition get Module
        shelf = EntityFactory.getShelfsList().get(2);

        //Step 2
        rokuBackEndLayer.createModule(shelf);

        //Step 3

        pageForm = CreateFactoryPage.createDefaultPage().setPlatform(RokuBrandNames.getBrandByName(brand).getPlatformMatcher().getConcertoPlatforms().get(0));
        rokuBackEndLayer.createPage(pageForm);

        //Step 4
        EditPageWithPanelizer editPage = rokuBackEndLayer.openEditOttPage(pageForm.getTitle());
        pageForm = editPage.updateFields(Arrays.<Module>asList(shelf));
        editPage.save();
        editPage.elementPublishBlock().publishByTabName();

        ModulesPage modulesPage = mainRokuAdminPage.openOttModulesPage(brand);
        modulesPage.checkAnItem(shelf.getTitle());
        modulesPage.executeDelete();
        //Step 5
        String url = rokuBackEndLayer.getLogURL(brand);
        //Get Actual Post Request
        ContentTypeDeleteJson actualDeleteJson = requestHelper.getDeleteResponses(url, ConcertoApiPublishingTypes.MODULE).get(0);
        ContentTypeDeleteJson expectedDeleteJson = new ContentTypeDeleteJson(shelf);

        softAssert.assertEquals(expectedDeleteJson, actualDeleteJson, "Delete jsons are not matched", "Delete jsons are matched");

        LocalApiJson actualMetadata = requestHelper.getSingleLocalApiJson(url);
        String action = actualMetadata.getAttributes().getAction().getStringValue();
        softAssert.assertEquals(Action.DELETE.getAction(), action, "The action message attribute are not matched",
                "The action message attribute are matched");
        String entityType = actualMetadata.getAttributes().getEntityType().getStringValue();
        softAssert.assertEquals(ItemTypes.COLLECTIONS.getEntityType(), entityType, "The entityType message attribute are not matched",
                "The entityType message attribute are matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }
}
