package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.person;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.PersonPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
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
 * Created by Alena_Aukhukova on 6/17/2016.
 */

/**
 * Pre-condition:
 * 1. Login in CMS as admin
 * 2. Create Person with all fields
 * 3. Open created post for edit
 * <p>
 * Steps:
 * 1. Click on Publish button
 * Verify: Link to the publishing log is present. The API log present 'success' status message of POST request
 * 2. Update person: Fill all fields and click on Save button
 * Verify: Status message is present with text '[person] has been saved.'
 * 3. Verify publishing log
 * Verify: All fields are present and values are correct according http://private-anon-96b5bee80-concertoapiingestmaster.apiary-mock.com/person
 */
public class TC14754_CheckPersonPublishingAfterUpdating extends BaseAuthFlowTest {
    Content content;
    Content contentExpected;

    @Autowired
    @Qualifier("defaultPerson")
    private ContentTypeCreationStrategy contentTypeCreationStrategyFirst;

    @Autowired
    @Qualifier("withAllFieldsPerson")
    private ContentTypeCreationStrategy contentTypeCreationStrategySecondary;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        content = contentTypeCreationStrategyFirst.createContentType();
        contentExpected = contentTypeCreationStrategySecondary.createContentType();
    }

    @Test(groups = {"person_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void testPublishingUpdate(String brand) {
        //pre-condition
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        rokuBackEndLayer.openAdminPage();
        rokuBackEndLayer.createContentType(content);
        PersonPage editPage = new PersonPage(webDriver, aid);
        //Step 1
        editPage.publish();
        editPage = (PersonPage) rokuBackEndLayer.updateContent(content, contentExpected);

        softAssert.assertTrue(editPage.isStatusMessageShown(), "Status message is not presented",
                "Status message is presented", webDriver);
        //update uuid an revision
        editPage.updateRevisionAndUuidFromDevel(contentExpected);

        //Get Expected result
        MetadataJson expectedPerson = editPage.getMetadataForPublishing((PersonEntity) contentExpected);

        //publishing
        editPage.publish();
        String url = editPage.getLogURL(brand);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        //Get Actual Post Request
        MetadataJson actualPerson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.PERSON);
        softAssert.assertTrue(new MetadataValidator().verify(expectedPerson, actualPerson), "The actual data is not matched", "The JSON data is matched");

        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteContentTypes() {
        try {
            rokuBackEndLayer.deleteContentType(contentExpected);
        } catch (Throwable e) {
            Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
        }
    }

}


