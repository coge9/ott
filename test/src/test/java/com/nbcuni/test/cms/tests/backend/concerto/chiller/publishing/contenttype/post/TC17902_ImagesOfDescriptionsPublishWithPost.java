package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.post;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.PostPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.Post;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.validators.RokuServiceJsonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 *
 * @author Aliaksei_Dzmitrenka
 * Create Post with required fields
 * Upload Images to Long Description
 * Upload Images to Medium Description
 * Add Blurb with am Image
 * save and Publish Post to API
 * Verify publish report
 * 3 uploaded images are post recursevly to API with Post
 *
 */


public class TC17902_ImagesOfDescriptionsPublishWithPost extends BaseAuthFlowTest {

    private Post post;
    private Post defaultPost;

    @Autowired
    @Qualifier("postWithImagesInDescription")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @Autowired
    @Qualifier("requiredPost")
    private ContentTypeCreationStrategy contentTypeCreationStrategyDefault;


    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {

        post = (Post) contentTypeCreationStrategy.createContentType();
        defaultPost = (Post) contentTypeCreationStrategyDefault.createContentType();
    }

    @Test(groups = {"post_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void testEventPublishing(String brand) {

        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //pre-condition
        PostPage postPage = (PostPage) rokuBackEndLayer.createContentType(post);

        //postPage.enterContentTypeData(post);

        postPage.publish();
        String url = postPage.getLogURL(brand);
        softAssert.assertTrue(postPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        //Get Actual Post Request

        LocalApiJson localApiJson = requestHelper.getLocalApiJsons(url, ConcertoApiPublishingTypes.POST).get(0);
        softAssert.assertTrue(RokuServiceJsonValidator.getInstance().validatePostBySchema(localApiJson.getRequestData().toString()),
                "The validation has failed for POST content type", "The validation has passed for POST content type");

        softAssert.assertTrue(requestHelper.getLocalApiJsons(url, ConcertoApiPublishingTypes.FILE_IMAGE).isEmpty(),
                "There is recursive POST request for an image",
                "There is no any recursive POST request for images");
        softAssert.assertAll();
    }


}
