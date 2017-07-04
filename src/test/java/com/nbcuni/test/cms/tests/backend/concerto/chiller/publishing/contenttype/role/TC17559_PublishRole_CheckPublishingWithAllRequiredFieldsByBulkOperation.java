package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.role;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.RolePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
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
 * Created by Ivan_Karnilau on 3/2/2017.
 */

/**
 * Pre-Conditions:
 * 1. Login in CMS as admin
 * 2. Create Role with all required fields
 * 3. Open metadata page
 * Steps:
 * 1. Publish role by bulk operation
 * Link to the publishing log is present.
 * The API log present 'success' status message of POST request
 * 2. Verify publishing log
 * All fields are present and values are correct according
 * http://docs.concertoapiingestmaster.apiary.io/#reference/role/post-role/generate-message-body-to-create-or-update-role
 */

public class TC17559_PublishRole_CheckPublishingWithAllRequiredFieldsByBulkOperation extends BaseAuthFlowTest {

    @Autowired
    @Qualifier("defaultRole")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        content = contentTypeCreationStrategy.createContentType();
    }

    @Test(groups = {"role_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void testRolePublishing(String brand) {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        rokuBackEndLayer.openAdminPage();

        //pre-condition
        RolePage editPage = (RolePage) rokuBackEndLayer.createContentType(content).publish();

        //Get Expected result
        MetadataJson expectedRole = editPage.getMetadataForPublishing((RoleEntity) content);

        //Step 1
        rokuBackEndLayer.publishContentTypesByBulkOperation(content);

        String url = rokuBackEndLayer.getLogURL(brand);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        //Get Actual Post Request
        MetadataJson actualRole = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.ROLE);
        softAssert.assertTrue(new MetadataValidator().verify(expectedRole, actualRole), "The actual data is not matched", "The JSON data is matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");

    }

    @AfterMethod(alwaysRun = true)
    public void deleteCreatedContentType() {
        rokuBackEndLayer.deleteContentType(content);
    }
}
