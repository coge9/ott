package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.post;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.PostPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.post.Post;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.post.PostJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.verification.chiller.PostVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Pre-Conditions:
 * <p>
 * 1. Login in CMS as admin
 * <p>
 * 2. Create Post with all required fields
 * <p>
 * 3. Open created post for edit
 *
 * Steps:
 * <p>
 * 1. Click on Publish button
 * Verify: Link to the publishing log is present.
 * The API log present 'success' status message of POST request
 * <p>
 * 2. Verify publishing log
 * Verify: All fields are present and values are correct according http://private-anon-96b5bee80-concertoapiingestmaster.apiary-mock.com/posts
 */
public class TC14732_PublishPostWithRequiredFields extends BaseAuthFlowTest {

    @Autowired
    @Qualifier("requiredPost")
    private ContentTypeCreationStrategy postCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        content = postCreationStrategy.createContentType();
    }

    @Test(groups = {"post_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void createPost(final String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        PostPage postPage = (PostPage) rokuBackEndLayer.createContentType(content).publish();
        String url = mainRokuAdminPage.getLogURL(brand);

        //setSlug
        content.setSlugInfo(postPage.onSlugTab().getSlug());

        PostJson actualPostJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.POST);
        PostJson expectedPostJson = new PostJson().getObject((Post) content);

        softAssert.assertTrue(new PostVerificator().verify(expectedPostJson, actualPostJson), "The actual data is not matched",
                "The JSON data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteContentTypes() {
        rokuBackEndLayer.deleteContentType(content);
    }

}
