package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.post;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.validators.RokuServiceJsonValidator;
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
 * Verify: The JSON scheme of send Event is matched with scheme available by URL below:
 * http://private-anon-96b5bee80-concertoapiingestmaster.apiary-mock.com/posts
 */
public class TC14737_ValidatePostScheme extends BaseAuthFlowTest {

    @Autowired
    @Qualifier("requiredPost")
    private ContentTypeCreationStrategy postCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        content = postCreationStrategy.createContentType();
    }

    @Test(groups = {"post_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void validatePostScheme(final String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        rokuBackEndLayer.createContentType(content).publish();
        String url = mainRokuAdminPage.getLogURL(brand);

        LocalApiJson localApiJson = requestHelper.getLocalApiJsons(url, ConcertoApiPublishingTypes.POST).get(0);

        softAssert.assertTrue(RokuServiceJsonValidator.getInstance().validatePostBySchema(localApiJson.getRequestData().toString()),
                "The validation has failed", "The validation has passed", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteContentTypes() {
        rokuBackEndLayer.deleteContentType(content);
    }

}
