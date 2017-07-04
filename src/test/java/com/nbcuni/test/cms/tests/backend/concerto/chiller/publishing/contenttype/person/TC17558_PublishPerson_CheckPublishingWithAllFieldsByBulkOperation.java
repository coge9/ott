package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.person;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.PersonPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata.person.PersonEntity;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.metadata.MetadataJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.verification.chiller.MetadataValidator;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 3/2/2017.
 */

/**
 * Pre-Conditions:
 * 1. Login in CMS as admin
 * 2. Create Person with all fields
 * 3. Open metadata page
 * Steps:
 * 1. Publish person by bulk operation
 * Link to the publishing log is present.
 * The API log present 'success' status message of POST request
 * 2. Verify publishing log
 * All fields are present and values are correct according http://private-anon-96b5bee80-concertoapiingestmaster.apiary-mock.com/person
 */

public class TC17558_PublishPerson_CheckPublishingWithAllFieldsByBulkOperation extends BaseAuthFlowTest {

    @Autowired
    @Qualifier("defaultPerson")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        content = contentTypeCreationStrategy.createContentType();
    }

    @Test(groups = {"person_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void testPersonPublishing(String brand) {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        rokuBackEndLayer.openAdminPage();

        //pre-condition
        PersonPage editPage = (PersonPage) rokuBackEndLayer.createContentType(content).publish();

        //Get Expected result
        MetadataJson expectedPerson = editPage.getMetadataForPublishing((PersonEntity) content);

        //Step 1
        rokuBackEndLayer.publishContentTypesByBulkOperation(content);

        String url = rokuBackEndLayer.getLogURL(brand);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        //Get Actual Post Request
        MetadataJson actualPerson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.PERSON);
        softAssert.assertTrue(new MetadataValidator().verify(expectedPerson, actualPerson), "The actual data is not matched", "The JSON data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");

    }

    @AfterMethod(alwaysRun = true)
    public void deleteCreatedContentType() {
        try {
            rokuBackEndLayer.deleteContentType(content);
        } catch (Exception e) {
            Utilities.logSevereMessage("Couldn't clean up te content");
        }
    }
}
