package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi.page;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.TVEPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.pageobjectutils.chiller.actionpublishing.Action;
import com.nbcuni.test.cms.pageobjectutils.tvecms.PublishInstance;
import com.nbcuni.test.cms.pageobjectutils.tvecms.brands.RokuBrandNames;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.RequestHelper;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by Aliaksei_Klimenka1 on 8/25/2016.
 */
public class TC15826_PublishingDeleneMessage_DeletePagesByBulkOperation extends BaseAuthFlowTest {

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

    private TVEPage tvePage;
    private PageForm pageForm;


    @Test(groups = {"page_publishing", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasConcertoAPIDataProvider")
    public void checkDeletingPage(String brand) {

        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 2
        pageForm = CreateFactoryPage.createDefaultPage().setPlatform(RokuBrandNames.getBrandByName(brand).getPlatformMatcher().getConcertoPlatforms().get(0));
        pageForm = rokuBackEndLayer.createPage(pageForm);

        //Step 3
        EditPageWithPanelizer editPage = new EditPageWithPanelizer(webDriver, aid);
        editPage.elementPublishBlock().publishByTabName(PublishInstance.DEV);

        //Step 4
        tvePage = mainRokuAdminPage.openOttPage(brand);
        tvePage.checkAnItem(pageForm.getTitle());
        tvePage.executeDelete();

        //Step 5
        String url = rokuBackEndLayer.getLogURL(brand);
        //Get Actual Post Request
        RequestHelper requestHelper = new RequestHelper();
        List<LocalApiJson> actualMetadata = requestHelper.getLocalApiJsons(url, ConcertoApiPublishingTypes.PAGE);
        Assertion.assertTrue(!actualMetadata.isEmpty(), "It's impossible to get POST attributes, result empty: " + actualMetadata);

        String action = actualMetadata.get(0).getAttributes().getAction().getStringValue();
        softAssert.assertEquals(Action.DELETE.getAction(), action, "The action message attribute are not matched",
                "The action message attribute are matched");
        String entityType = actualMetadata.get(0).getAttributes().getEntityType().getStringValue();
        softAssert.assertEquals(ItemTypes.COLLECTIONS.getEntityType(), entityType, "The entityType message attribute are not matched",
                "The entityType message attribute are matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test is passed");
    }
}
