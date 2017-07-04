package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi.page;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.ottpage.TVEPage;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.factory.CreateFactoryPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.pageobjectutils.chiller.actionpublishing.Action;
import com.nbcuni.test.cms.pageobjectutils.tvecms.brands.RokuBrandNames;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by Aliaksei_Klimenka1 on 8/24/2016.
 */
public class TC15768_PublishingDeleteMessage_DeletePagesManually extends BaseAuthFlowTest {
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
     * 2. Delete the page we have created in pre-conditions.
     * Verify: Page is deleted successfully. Statue message is present. Delete message sent to Concerto API.
     * <p>
     * 3. Check Post request for node.
     */
    private PageForm pageForm;

    @Test(groups = {"page_publishing", "roku_smoke", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider")
    public void checkDeletingPage(String brand) {

        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        pageForm = CreateFactoryPage.createDefaultPage().setPlatform(RokuBrandNames.getBrandByName(brand).getPlatformMatcher().getRandomConcertoPlatform());
        pageForm = rokuBackEndLayer.createPage(pageForm);
        EditPageWithPanelizer editPage = new EditPageWithPanelizer(webDriver, aid);


        editPage.elementPublishBlock().publishByTabName();
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);
        softAssert.assertFalse(editPage.isErrorMessagePresent(), "The error message is present after publishing with message" + mainRokuAdminPage.getErrorMessage(),
                "The error message is not present after publishing Shelf with message", webDriver);
        TVEPage pageWithPages = rokuBackEndLayer.deleteOttPage(pageForm.getTitle());
        softAssert.assertTrue(pageWithPages.isStatusMessageShown(), "The status message is not shown after deliting page", "The status message is shown after deliting page", webDriver);

        List<LocalApiJson> actualMetadata = requestHelper.getLocalApiJsons(rokuBackEndLayer.getLogURL(brand), ConcertoApiPublishingTypes.PAGE);
        Assertion.assertTrue(!actualMetadata.isEmpty(), "Impossible to get DELETE request to Concerto API");
        String action = actualMetadata.get(0).getAttributes().getAction().getStringValue();
        softAssert.assertEquals(Action.DELETE.getAction(), action, "The action message attribute are not matched",
                "The action message attribute are matched");
        String entityType = actualMetadata.get(0).getAttributes().getEntityType().getStringValue();
        softAssert.assertEquals(ItemTypes.COLLECTIONS.getEntityType(), entityType, "The entityType message attribute are not matched",
                "The entityType message attribute are matched");

        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }
}


