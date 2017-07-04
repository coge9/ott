package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.role;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.RolePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata.role.RoleEntity;
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
 * 2. Create Role with all fields
 * 3. Open created post for edit
 * <p>
 * Steps:
 * 1. Click on Publish button
 * Verify: Link to the publishing log is present. The API log present 'success' status message of POST request
 * 2. Update role: Fill all fields and click on Save button
 * Verify: Status message is present with text '[role] has been saved.'
 * 3. Verify publishing log
 * Verify: All fields are present and values are correct according http://private-anon-96b5bee80-concertoapiingestmaster.apiary-mock.com/role
 */
public class TC14815_CheckRolePublishingAfterUpdating extends BaseAuthFlowTest {
    Content content;
    Content contentExpected;

    @Autowired
    @Qualifier("defaultRole")
    private ContentTypeCreationStrategy contentTypeCreationStrategyFirst;

    @Autowired
    @Qualifier("withAllFieldsRole")
    private ContentTypeCreationStrategy contentTypeCreationStrategySecondary;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        content = contentTypeCreationStrategyFirst.createContentType();
        contentExpected = contentTypeCreationStrategySecondary.createContentType();
    }

    @Test(groups = {"role_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void testPublishingUpdate(String brand) {
        //pre-condition
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        rokuBackEndLayer.openAdminPage();
        rokuBackEndLayer.createContentType(content);
        RolePage editPage = new RolePage(webDriver, aid);
        //Step 1
        editPage.publish();
        editPage = (RolePage) rokuBackEndLayer.updateContent(content, contentExpected);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "Status message is not presented",
                "Status message is presented", webDriver);
        //update uuid and revision
        contentExpected.getGeneralInfo().setUuid(content.getGeneralInfo().getUuid());
        editPage.updateRevisionAndUuidFromDevel(contentExpected);

        //Get Expected result
        MetadataJson expectedRole = editPage.getMetadataForPublishing((RoleEntity) contentExpected);

        //publishing
        editPage.publish();
        String url = editPage.getLogURL(brand);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        //Get Actual Post Request;
        MetadataJson actualRole = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.ROLE);
        softAssert.assertTrue(new MetadataValidator().verify(expectedRole, actualRole), "The actual data is not matched", "The JSON data is matched");

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