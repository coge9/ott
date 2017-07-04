package com.nbcuni.test.cms.tests.backend.concerto.chiller.savepublish;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.entities.content.PublishState;
import com.nbcuni.test.cms.pageobjectutils.tvecms.ResponseData;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Aleksandra_Lishaeva on 6/16/16.
 */
public class TC15021_SaveAndPublishNewMediaGallery extends BaseAuthFlowTest {

    /**
     * Steps:
     * 1. Go to CMS
     * Verify: user is in CMS
     * <p/>
     * 2. Click on Content -> New <content type>
     * Verify:"Create <content type>" page is opened
     * <p/>
     * 3. Fill all required fields
     * Verify: all required fields are filled
     * <p/>
     * 4. Click on "Save and Publish" button
     * Verify: publishing dialog is displayed
     * <p/>
     * 5.Choose API Service instance
     * Verify: Click on "Publish" button.Node is published
     * <p/>
     * 6. Go to Content list.check node's state
     * Verify: state is "Published"
     * <p/>
     */

    private Content mediaGallery;


    @Autowired
    @Qualifier("defaultMediaGallery")
    private ContentTypeCreationStrategy mediaGalleryCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        mediaGallery = mediaGalleryCreationStrategy.createContentType();
    }

    @Test(groups = {"save_publish"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void savePublishMediaGallery(final String brand) {

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 2

        ContentTypePage contentTypePage = rokuBackEndLayer.createAndPublishContentType(mediaGallery);
        softAssert.assertTrue(contentTypePage.isStatusMessageShown(), "Status message is not presented",
                "Status message is presented", webDriver);
        softAssert.assertFalse(contentTypePage.isErrorMessagePresent(), "Error message is presented",
                "Error message is not presented", webDriver);

        //Step 3-4
        String url = contentTypePage.getLogURL(brand);
        softAssert.assertTrue(contentTypePage.isStatusMessageShown(), "The status message is not shown after publishing"
                , "The status message is shown after publishing", webDriver);

        //Step 5 Get Actual Post Request
        LocalApiJson localApiJson = requestHelper.getSingleLocalApiJson(url);

        softAssert.assertEquals(localApiJson.getResponseStatus(), ResponseData.SUCCESS.getResponseStatus(), "The action message attribute are not present",
                "The action message attribute are present");

        //Step 6
        ContentPage contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        softAssert.assertEquals(PublishState.PUBLISHED.getStateValue(), contentPage.getNodePublishState(mediaGallery.getTitle()), "The node state is not Published"
                , "The node state is Published", webDriver);

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteContentTC15021() {
        rokuBackEndLayer.deleteContentType(mediaGallery);
    }
}
