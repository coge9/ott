package com.nbcuni.test.cms.tests.smoke.chiller.smoke.publishing.metadata;

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

public class TC14755_ValidatePersonScheme extends BaseAuthFlowTest {
    /**
     * Pre-condition:
     * 1. Login in CMS as admin
     * 2. Create Person with all required fields
     * 3. Open created Person for edit
     * <p>
     * Step 1: Click on Publish button
     * Verify: Link to the publishing log is present. The API log present 'success' status message of POST request
     * <p>
     * Step 2: Verify publishing log
     * The JSON scheme of send Person is matched with scheme available by URL below:
     * http://docs.concertoapiingestmaster.apiary.io/#reference/person/post-person-in-json-schema-format/generate-person-body-in-json-schema-format
     */


    @Autowired
    @Qualifier("defaultPerson")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;


    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        content = contentTypeCreationStrategy.createContentType();
    }

    @Test(groups = {"person_publishing", "roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void validatePersonScheme(String brand) {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        //create person with data
        ContentTypePage contentTypePage = rokuBackEndLayer.createContentType(content);

        //publishing
        contentTypePage.publish();
        String url = contentTypePage.getLogURL(brand);
        softAssert.assertTrue(contentTypePage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        //Get Actual Post Request
        LocalApiJson localApiJson = requestHelper.getSingleLocalApiJson(url);
        softAssert.assertTrue(RokuServiceJsonValidator.getInstance().validatePersonBySchema(localApiJson.getRequestData().toString()), "The validation has failed", "The validation has passed", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");

    }

    @AfterMethod(alwaysRun = true)
    public void deleteContent() {
        try {
            rokuBackEndLayer.deleteContentType(content);
        } catch (Throwable e) {
            Utilities.logSevereMessage("Unable to perform tear-down method. " + Utilities.convertStackTraceToString(e));
        }
    }
}

