package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.role;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.actionpublishing.Action;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.ContentTypeDeleteJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 08-Jul-16.
 */

/**
 * TC15301
 *
 * Pre-Conditions:
 * 1. Login in CMS D7 as admin
 * 2. Create Role with all required fields
 *
 * Steps
 * 1.
 * Go to content page
 * Content page is opened
 * 2.
 * Delete Role by bulk operation
 * Role is deleted.
 * Publish delete message:
 * Request data:
 * uuid: e29a1f78-cdfc-4022-9a77-ab721dd370da
 * itemType: "role"
 * There are attributes:
 * action = 'delete'
 * entityType = 'roles'
 */
public class TC15301_PublishingDeleteMessage_DeleteRoleByBulkOperation extends BaseAuthFlowTest {

    private Content role;

    @Autowired
    @Qualifier("defaultRole")
    private ContentTypeCreationStrategy roleCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        role = roleCreationStrategy.createContentType();
    }

    @Test(groups = {"role_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void testRoleDeletePublishing(String brand) {

        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        rokuBackEndLayer.createContentType(role).publish();
        rokuBackEndLayer.deleteContentTypesByBulkOperation(role);

        String url = rokuBackEndLayer.getLogURL(brand);

        ContentTypeDeleteJson expectedDeleteJson = new ContentTypeDeleteJson(role);

        ContentTypeDeleteJson actualDeleteJson = requestHelper.getDeleteResponses(url, ConcertoApiPublishingTypes.ROLE).get(0);

        LocalApiJson localApiJson = requestHelper.getSingleLocalApiJson(url);
        String action = localApiJson.getAttributes().getAction().getStringValue();

        softAssert.assertEquals(Action.DELETE.getAction(), action, "The action message attribute are not matched",
                "The action message attribute are matched");

        String entityType = localApiJson.getAttributes().getEntityType().getStringValue();
        softAssert.assertEquals(role.getType().getEntityType(), entityType, "The entityType message attribute are not matched",
                "The entityType message attribute are matched");

        softAssert.assertEquals(expectedDeleteJson, actualDeleteJson, "The actual data is not matched", "The JSON data is matched");

        softAssert.assertAll();

    }
}
