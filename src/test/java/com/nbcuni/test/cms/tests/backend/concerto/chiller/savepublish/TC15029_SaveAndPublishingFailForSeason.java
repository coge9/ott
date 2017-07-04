package com.nbcuni.test.cms.tests.backend.concerto.chiller.savepublish;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.apiinstances.ApiInstanceConstant;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.season.Season;
import com.nbcuni.test.cms.pageobjectutils.entities.content.PublishState;
import com.nbcuni.test.cms.pageobjectutils.tvecms.ResponseData;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Aleksandra_Lishaeva on 6/22/16.
 */
public class TC15029_SaveAndPublishingFailForSeason extends SetupAmazonEndpoint {
    /**
     * Pre-condition: Create situation when publishing to SQS can not be performed:
     * go to /admin/config/system/aws-queue
     * set Region as US West 2
     * 1. Go to CMS
     * Verify: user is in CMS
     * <p/>
     * 2.Click on Content -> New <content type>
     * Verify: "Create <content type>" page is opened
     * <p/>
     * 3.Fill all required fields
     * Verify: all required fields are filled
     * <p/>
     * 4.click on "Save and Publish" button
     * Verify: publishing dialog is displayed
     * <p/>
     * 5.Choose API Service instance. Click on "Publish" button
     * verify: node is created but node is NOT published
     * <p/>
     * 6.Go to Content list. Check node's state
     * Verify: state is "Not Published"
     * <p/>
     * 7.Open created node
     * Verify: node's page is opened
     * <p/>
     * 8.click on "Save and Publish" button
     * Verify: publishing dialog is displayed
     * <p/>
     * 9.Choose API Service instance. click on "Publish" button
     * Verify: node is created but node is NOT published
     * <p/>
     * 10.Go to Content list
     * Verify:state is "Not Published"
     * <p/>
     * 11.Open created in part #1 of precondition node
     * Verify: node's page is opened
     * <p/>
     * 12. Click on "Save and Publish" button
     * Verify: publishing dialog is displayed
     * <p/>
     * 13.Choose API Service instance
     * Verify: node is saved but node is NOT published
     * <p/>
     * 14.Go to Content list
     * Verify:state is "Not Published"
     */

    private String endpoint;
    private Content series;
    private Content season;

    @Autowired
    @Qualifier("defaultSeason")
    private ContentTypeCreationStrategy seasonCreationStrategy;


    @Autowired
    @Qualifier("defaultSeries")
    private ContentTypeCreationStrategy seriesCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        season = seasonCreationStrategy.createContentType();
        series = seriesCreationStrategy.createContentType();
        ((Season) season).getSeasonInfo().setProgram(series.getTitle());
    }

    @Test(groups = {"save_publish"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = false)
    public void savePublishFailSeason(final String brand) {
        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Pre-condition
        endpoint = setupAmazonEndpoint(brand);
        rokuBackEndLayer.createContentType(series);

        //Step 2-3
        ContentTypePage contentTypePage = rokuBackEndLayer.createContentType(season);

        //Step 4-5
        contentTypePage.publish(endpoint);
        softAssert.assertTrue(mainRokuAdminPage.isErrorMessagePresent(), "Error message is not presented",
                "Error message is presented", webDriver);

        //Step 6
        ContentPage contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        softAssert.assertEquals(PublishState.NOT_PUBSLISHED.getStateValue(), contentPage.getNodePublishState(season.getTitle()), "The node state is Published"
                , "The node state is not Published", webDriver);

        //Step 7
        rokuBackEndLayer.editContentType(season);
        contentTypePage.publish(endpoint);

        //Step 8-9
        softAssert.assertTrue(mainRokuAdminPage.isErrorMessagePresent(), "Error message is not presented",
                "Error message is presented", webDriver);

        //Step 10
        mainRokuAdminPage.openPage(ContentPage.class, brand);
        softAssert.assertEquals(PublishState.NOT_PUBSLISHED.getStateValue(), contentPage.getNodePublishState(season.getTitle()), "The node state is Published"
                , "The node state is not Published", webDriver);

        //Step 11
        rokuBackEndLayer.editContentType(season);
        contentTypePage.publish(ApiInstanceConstant.AMAZON_API);

        //Step 12-13
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "Status message is not presented",
                "Status message is presented", webDriver);
        softAssert.assertFalse(mainRokuAdminPage.isErrorMessagePresent(), "Error message is presented",
                "Error message is not presented", webDriver);

        String url = mainRokuAdminPage.getLogURL(brand);
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "The status message is not shown after publishing"
                , "The status message is shown after publishing", webDriver);

        // Get Actual Post Request
        LocalApiJson localApiJson = requestHelper.getSingleLocalApiJson(url);

        softAssert.assertEquals(localApiJson.getResponseStatus(), ResponseData.SUCCESS.getResponseStatus(), "The action message attribute are not present",
                "The action message attribute are present");

        //Step 14
        mainRokuAdminPage.openPage(ContentPage.class, brand);
        softAssert.assertEquals(PublishState.PUBLISHED.getStateValue(), contentPage.getNodePublishState(season.getTitle()), "The node state is not Published"
                , "The node state is Published", webDriver);

        softAssert.assertAll();
        Utilities.logInfoMessage("Test has passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteSeasonTC15029() {
        try {
            rokuBackEndLayer.deleteContentType(season);
            rokuBackEndLayer.deleteContentType(series);
            rokuBackEndLayer.deleteApiInstnace(endpoint);
        } catch (Throwable e) {
            Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
        }
    }
}
