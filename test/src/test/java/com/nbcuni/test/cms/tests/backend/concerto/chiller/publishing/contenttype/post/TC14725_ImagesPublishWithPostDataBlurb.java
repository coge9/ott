package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.post;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.PostPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
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
 * Create Post with required fields and blurb info (image)
 * Publish them
 * Verify publish report
 *
 */


public class TC14725_ImagesPublishWithPostDataBlurb extends BaseAuthFlowTest {

    private Content content;

    @Autowired
    @Qualifier("requiredPostImageBlurb")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;


    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        content = contentTypeCreationStrategy.createContentType();
    }

    @Test(groups = {"post_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void testEventPublishing(String brand) {
        Post post = (Post) content;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //pre-condition
        PostPage postPage = (PostPage) rokuBackEndLayer.createContentType(post);
        postPage.publish();
        String url = postPage.getLogURL(brand);
        softAssert.assertTrue(postPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        //Get Actual Post Request
        LocalApiJson localApiJson = requestHelper.getLocalApiJsons(url, ConcertoApiPublishingTypes.POST).get(0);
        softAssert.assertTrue(RokuServiceJsonValidator.getInstance().validatePostBySchema(localApiJson.getRequestData().toString()),
                "The validation has failed for POST content type", "The validation has passed for POST content type");

        softAssert.assertTrue(requestHelper.getLocalApiJsons(url, ConcertoApiPublishingTypes.FILE_IMAGE).isEmpty(),
                "There is recursive Image POST request",
                "There is no recursive Image POST request");
        softAssert.assertAll();
    }

}
