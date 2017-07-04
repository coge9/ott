package com.nbcuni.test.cms.tests.backend.concerto.chiller.savepublish;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ChillerVideoPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.ActionButtons;
import com.nbcuni.test.cms.pageobjectutils.entities.content.PublishState;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Created by Aleksandra_Lishaeva on 6/16/16.
 */
public class TC15028_SaveAndPublishUnpublishedVideo extends BaseAuthFlowTest {

    /**
     * Steps:
     * 1. Go to CMS
     * Verify: user is in CMS
     * <p/>
     * 2. Go to Content
     * Verify:There is a video
     * <p/>
     * 3.Open Video
     Verify: node's page is opened

     4.Check page's buttons
     Verify: Only three buttons are present:"Save Draft","Save and Publish","Cancel"

     5.Click on "Cancel" button
     Verify: user is redirected to content list (/admin/ott/content)

     6.Open Video
     Verify: node's page is opened

     7.Click on "Save Draft" button
     Verify: node saved

     8.Go to Content list.check node's state
     Verify: state stays same

     9. Open Video
     verify: node's page is opened

     10.click on "Save and Publish" button
     Verify: publishing dialog is displayed

     11.Choose API Service instance. click on "Publish" button
     Verify: node is published

     12.Go to Content list. Check node's state
     Verify: state stays same

     13.Open Video
     Verify: node's page is opened

     15.Click on "Cancel" button
     Verify: node's page is opened

     16. Go to Content list. Check node's state
     Verify: state stays same
     */


    @Test(groups = {"save_publish"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void saveUnPublishedVideoWorkflow(final String brand) {

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 2
        ContentPage contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        contentPage.searchByType(ContentType.TVE_VIDEO).searchByPublishedState(PublishState.NO).apply();
        String title = contentPage.getFullTitleOfFirstElement();

        //Step 3
        ChillerVideoPage chillerVideoPage = contentPage.clickEditLink(ChillerVideoPage.class, title);

        //Step 4
        softAssert.assertTrue(chillerVideoPage.isActionButtonsPresent(Arrays.asList(ActionButtons.SAVE_AS_DRAFT, ActionButtons.SAVE_AND_PUBLISH, ActionButtons.CANCEL), true)
                , "Not all of action button present", webDriver);
        softAssert.assertTrue(chillerVideoPage.isActionButtonsPresent(Arrays.asList(ActionButtons.DELETE), false)
                , "The Delete button present on creation the content", webDriver);

        //Step 5
        softAssert.assertTrue(chillerVideoPage.cancel().isPageOpened(), "Content page is not opened",
                "Content page is opened", webDriver);

        //Step 6
        mainRokuAdminPage.openPage(ContentPage.class, brand);
        chillerVideoPage = contentPage.clickEditLink(ChillerVideoPage.class, title);

        //Step 7
        chillerVideoPage.saveAsDraft();
        softAssert.assertTrue(chillerVideoPage.isStatusMessageShown(), "Status message is not presented",
                "Status message is presented", webDriver);
        softAssert.assertFalse(chillerVideoPage.isErrorMessagePresent(), "Error message is presented",
                "Error message is not presented", webDriver);

        //Step 8
        contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        softAssert.assertEquals(PublishState.NOT_PUBSLISHED.getStateValue(), contentPage.getNodePublishState(title), "The node state is Published", "The node state is not Published", webDriver);

        //Step 9
        chillerVideoPage = contentPage.clickEditLink(ChillerVideoPage.class, title);

        //Step 10
        softAssert.assertTrue(chillerVideoPage.cancel().isPageOpened(), "Content page is not opened",
                "Content page is opened", webDriver);

        //Step 11
        chillerVideoPage = contentPage.clickEditLink(ChillerVideoPage.class, title);

        //Step 12
        chillerVideoPage.publish();
        String url = chillerVideoPage.getLogURL(brand);
        softAssert.assertTrue(chillerVideoPage.isStatusMessageShown(), "The status message is not shown after publishing"
                , "The status message is shown after publishing", webDriver);

        //Step 13 Get Actual Post Request
        LocalApiJson localApiJson = requestHelper.getSingleLocalApiJson(url);

        softAssert.assertTrue(localApiJson.getAttributes().getAction() != null, "The action message attribute are not present",
                "The action message attribute are present");

        softAssert.assertTrue(localApiJson.getAttributes().getEntityType() != null, "The entityType message attribute are not present",
                "The entityType message attribute are present");

        //Step 14
        contentPage = chillerVideoPage.cancel();
        softAssert.assertEquals(PublishState.NOT_PUBSLISHED.getStateValue(), contentPage.getNodePublishState(title), "The node state is Published"
                , "The node state is not Published", webDriver);

        //Step 15
        chillerVideoPage = contentPage.clickEditLink(ChillerVideoPage.class, title);

        //Step 16
        chillerVideoPage.cancelPublish();
        mainRokuAdminPage.openPage(ContentPage.class, brand);
        softAssert.assertEquals(PublishState.NOT_PUBSLISHED.getStateValue(), contentPage.getNodePublishState(title), "The node state is Published"
                , "The node state is not Published", webDriver);
        softAssert.assertAll();
    }

}
