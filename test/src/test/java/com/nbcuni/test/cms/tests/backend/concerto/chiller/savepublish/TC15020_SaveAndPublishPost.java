package com.nbcuni.test.cms.tests.backend.concerto.chiller.savepublish;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.ActionButtons;
import com.nbcuni.test.cms.pageobjectutils.entities.content.PublishState;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * Created by Aleksandra_Lishaeva on 6/16/16.
 */
public class TC15020_SaveAndPublishPost extends BaseAuthFlowTest {

    /**
     * Steps:
     * 1. Go to CMS
     * Verify: user is in CMS
     * <p/>
     * 2. Click on Content -> New <content type>
     * Verify:"Create <content type>" page is opened
     * <p/>
     * 3. Check page's button
     * Verify: Only three buttons are present: "Save Draft","Save and Publish","Cancel"
     * <p/>
     * 4. Click on "Cancel" button
     * Verify: user is redirected to content list (/admin/ott/content)
     * <p/>
     * 5. Click on Content -> New <content type>
     * Verify: "Create <content type>" page is opened
     * <p/>
     * 6. Fill all required fields
     * Verify: all required fields are filled
     * <p/>
     * 7. Click on "Save Draft" button
     * Verify: node saved
     * <p/>
     * 8.Go to Content list.Check node's state
     * Verify: state is "Not Published"
     * <p/>
     * 9. Open created node
     * Verify: node's page is opened
     * <p/>
     * 10. Click on "Cancel" button
     * Verify: user is redirected to content list (/admin/ott/content)
     * <p/>
     * 11. Open created node
     * Verify: node's page is opened
     * <p/>
     * 12. Click on "Save and Publish" button
     * Verify: publishing dialog is displayed
     * <p/>
     * 13.Choose API Service instance
     * Verify: Click on "Publish" button.Node is published
     * <p/>
     * 14. Go to Content list.check node's state
     * Verify: state is "Published"
     * <p/>
     * 15.Open created node
     * Verify: node's page is opened
     * <p/>
     * 16.click on "Save Draft" button
     * Verify: node saved
     * <p/>
     * 17.Go to Content list.Check node's state
     * Verify: state is "Not Published"
     * <p/>
     * 18. Open created node
     * Verify: Node's page is opened
     * <p/>
     * 19. Click on "Save and Publish" button
     * Verify: publishing dialog is displayed
     * <p/>
     * 20. Click on "Cancel" button
     * Verify: node's page is opened
     * <p/>
     * 21. Go to Content list. Check node's state
     * Verify: state is "Not Published"
     */

    private Content post;

    @Autowired
    @Qualifier("defaultPost")
    private ContentTypeCreationStrategy postCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        post = postCreationStrategy.createContentType();

    }

    @Test(groups = {"save_publish"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void savePublishPost(final String brand) {

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 2
        ContentTypePage contentTypePage = (ContentTypePage) mainRokuAdminPage.openPage(post.getPage(), brand);

        //Step 3
        softAssert.assertTrue(contentTypePage.isActionButtonsPresent(Arrays.asList(ActionButtons.SAVE_AS_DRAFT, ActionButtons.SAVE_AND_PUBLISH, ActionButtons.CANCEL), true)
                , "Not all of action button present", webDriver);
        softAssert.assertTrue(contentTypePage.isActionButtonsPresent(Arrays.asList(ActionButtons.DELETE), false)
                , "The Delete button present on creation the content", webDriver);

        //Step 4
        softAssert.assertTrue(contentTypePage.cancel().isPageOpened(), "Content page is not opened",
                "Content page is opened", webDriver);

        //Step 5-7
        contentTypePage = rokuBackEndLayer.createContentType(post);
        softAssert.assertFalse(contentTypePage.isErrorMessagePresent(), "Error message is presented",
                "Error message is not presented", webDriver);

        //Step 8
        ContentPage contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        softAssert.assertEquals(PublishState.NOT_PUBSLISHED.getStateValue(), contentPage.getNodePublishState(post.getTitle()), "The node state is Published", "The node state is Not Published", webDriver);

        //Step 9
        contentTypePage = rokuBackEndLayer.editContentType(post);

        //Step 10
        softAssert.assertTrue(contentTypePage.cancel().isPageOpened(), "Content page is not opened",
                "Content page is opened", webDriver);

        //Step 11
        contentTypePage = rokuBackEndLayer.editContentType(post);

        //Step 12
        contentTypePage.publish();
        String url = contentTypePage.getLogURL(brand);
        softAssert.assertTrue(contentTypePage.isStatusMessageShown(), "The status message is not shown after publishing"
                , "The status message is shown after publishing", webDriver);

        //Step 13 Get Actual Post Request
        LocalApiJson localApiJson = requestHelper.getSingleLocalApiJson(url);

        softAssert.assertTrue(localApiJson.getAttributes().getAction() != null, "The action message attribute are not present",
                "The action message attribute are present");

        softAssert.assertTrue(localApiJson.getAttributes().getEntityType() != null, "The entityType message attribute are not present",
                "The entityType message attribute are present");

        //Step 14
        contentPage = contentTypePage.cancel();
        softAssert.assertEquals(PublishState.PUBLISHED.getStateValue(), contentPage.getNodePublishState(post.getTitle()), "The node state is not Published"
                , "The node state is Published", webDriver);

        //Step 15
        contentTypePage = rokuBackEndLayer.editContentType(post);

        //Step 16
        contentTypePage.saveAsDraft();

        //Step 17
        contentPage = contentTypePage.cancel();
        softAssert.assertEquals(PublishState.NOT_PUBSLISHED.getStateValue(), contentPage.getNodePublishState(post.getTitle()), "The node state is Published after draft save"
                , "The node state is Not Published after draft saving", webDriver);

        //Step 18
        contentTypePage = rokuBackEndLayer.editContentType(post);

        //Step 19-20
        contentTypePage.publish();

        //Step 21
        contentPage = contentTypePage.cancel();
        softAssert.assertEquals(PublishState.PUBLISHED.getStateValue(), contentPage.getNodePublishState(post.getTitle())
                , "The node state is not Published after draft save"
                , "The node state is Published after draft saving", webDriver);
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteContentTC15020() {
        rokuBackEndLayer.deleteContentType(post);
    }
}
