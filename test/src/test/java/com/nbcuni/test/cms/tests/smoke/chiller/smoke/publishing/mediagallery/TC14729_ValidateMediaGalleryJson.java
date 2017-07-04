package com.nbcuni.test.cms.tests.smoke.chiller.smoke.publishing.mediagallery;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.validators.RokuServiceJsonValidator;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Aleksandra_Lishaeva on 5/23/16.
 */
public class TC14729_ValidateMediaGalleryJson extends BaseAuthFlowTest {

    /**
     * Pre-Conditions:
     * Make sure there is a Media Gallery content type
     * Steps:
     * <p>
     * 1.Go To CMS as Editor
     * Verify:The Editor panel is present
     * <p>
     * 2.Go to Content and select Media Gallery from pre-condition
     * Verify: The Edit Media Gallery Page is opened
     * <p>
     * 3.Click button 'Publish' and send POST request to the Amazon API
     * Verify: The API log present 'success' status message of POST request
     * <p>
     * 4.Validate Scheme of POST request for Media Gallery
     * Veriy: The JSON scheme of send Media Gallery is matched with scheme available by URL below:
     * http://private-anon-96b5bee80-concertoapiingestmaster.apiary-mock.com/json+schema/event
     */

    @Autowired
    @Qualifier("defaultMediaGallery")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;


    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        content = contentTypeCreationStrategy.createContentType();
    }

    @Test(groups = {"mediagallery_publishing", "roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void validateMediaGalleryScheme(String brand) {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();


        //create event with data
        ContentTypePage contentTypePage = rokuBackEndLayer.createContentType(content);

        //publishing
        contentTypePage.publish();
        String url = contentTypePage.getLogURL(brand);
        softAssert.assertTrue(contentTypePage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        //Get Actual Post Request
        LocalApiJson localApiJson = requestHelper.getSingleLocalApiJson(url);
        softAssert.assertTrue(RokuServiceJsonValidator.getInstance().validateMediaGalleryBySchema(localApiJson.getRequestData().toString()), "The validation has failed", "The validation has passed", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");

    }

    @AfterMethod(alwaysRun = true)
    public void deletePersonTC14729() {
        rokuBackEndLayer.deleteContentType(content);
    }
}
