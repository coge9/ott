package com.nbcuni.test.cms.tests.backend.tvecms.publishing.serialapi.page;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.chiller.apiinstances.ApiInstanceEntity;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuPageJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.cms.verification.roku.PageVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by Aleksandra_Lishaeva on 1/18/16.
 */
public class TC10176_PagePublishingForCreatedInstance extends BaseAuthFlowTest {

    private PageForm pageInfo;
    private ApiInstanceEntity entity;

    /**
     * Pre-Condition:
     * 1. Create new page.
     * 2. Go to Home » Administration » Configuration » Web services » API Services instances
     * 3. Add new service instance
     * <p/>
     * Step 1: Open created page for editing with new Panelizer block
     * Verify: Page is open for editing
     * <p/>
     * Step 2: Focus on OTT publishing section
     * Verify: There tabs with configured API services instances
     * There is a tab for service instance that was created in precondition
     * <p/>
     * Step 3: Click on tab for service instance created in precondition
     * Verify: Tabs opens. There are two buttons:
     * - Publish to ${instance} button is enabled
     * - Revert to ${instance} button is disabled
     * <p/>
     * Step 4: Click Publish to ${instance} button
     * Verify: Content is successfully published.Successful status message is shown.
     * <p/>
     * Step 5: Go to Reports -> Recent log messages. Review log
     * Verify: 1) Json of AQA API service is present with Post Request and Response from external API Service
     * POST request has been sent successfully. The new ${name} program_video has been created in Services API service.
     * Request Status: OK
     * HTTP Code: 200
     * Response Data: [contains response from API services]
     * Request_data contains Page data that sent in POST request to endpoint
     */

    @Test(groups = {"page_publishing", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider")
    public void checkPagePublishToCreatedInstance(final String brand) {
        this.brand = brand;
        //Pre-Condition
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        pageInfo = rokuBackEndLayer.createPage(CmsPlatforms.ROKU);
        //Step to get configured API instances
        entity = rokuBackEndLayer.createNewApiInstance(new ApiInstanceEntity());

        //Step 1
        EditPageWithPanelizer editPage = rokuBackEndLayer.openEditOttPage(pageInfo.getTitle());

        //Step 2-3
        softAssert.assertTrue(editPage.elementPublishBlock().isPresent(), "The publish block is absent", "The publish block is present itself", webDriver);
        softAssert.assertTrue(editPage.elementPublishBlock().isPublishEnable(entity.getTitle()), "The Publish button is disabled for instance: " + entity.getTitle(), "The publish button is enabled for instance: " + entity.getTitle(), webDriver);
        //Step 4
        editPage.elementPublishBlock().publishToCustomInstance(entity.getTitle());
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "The status message is not shown by publishing", "The status message is shown for publishing", webDriver);

        //Step 5
        String url = editPage.getLogURL(brand);
        RokuPageJson expectedPageJson = new RokuPageJson().getObject(pageInfo, brand);
        RokuPageJson actualPageJson = requestHelper.getSingleParsedResponse(url, SerialApiPublishingTypes.PAGE);
        softAssert.assertEquals(expectedPageJson, actualPageJson, "The data is not matched", "The data is matched", new PageVerificator());
        softAssert.assertAll();
        Utilities.logInfoMessage("Test is passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteOttPage10176() {
        try {
            rokuBackEndLayer.deleteOttPage(pageInfo.getTitle());
            rokuBackEndLayer.deleteApiInstnace(entity.getTitle());
        } catch (Exception e) {
            Utilities.logWarningMessage("This page can't be deleted!");
        }
    }

}
