package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.video;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.pageobjectutils.chiller.actionpublishing.Action;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by Ivan_Karnilau on 9/23/2016.
 */

/**
 * TC15302
 *
 * Pre-Conditions:
 * 1. Login in CMS D7 as admin
 * 2. Delete MPX account
 *
 * Steps:
 * 1. Check publishing log
 *
 * Expected:
 * All Videos are deleted
 * Publish delete message
 */

public class TC15302_DeleteMessageDeleteMPXAccountForVideo extends BaseAuthFlowTest {

    @Test(groups = {"hard_group"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasConcertoAPIDataProvider", enabled = false)
    public void testVideoDeletePublishing(String brand) {

        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        mainRokuAdminPage.killAllContent(brand);

        String url = rokuBackEndLayer.getLogURL(brand);

        List<LocalApiJson> localApiJsons = requestHelper.getLocalApiJsons(url);

        for (LocalApiJson localApiJson : localApiJsons) {
            String action = localApiJson.getAttributes().getAction().getStringValue();

            softAssert.assertEquals(Action.DELETE.getAction(), action, "The action message attribute are not matched",
                    "The action message attribute are matched");

            String entityType = localApiJson.getAttributes().getEntityType().getStringValue();
            softAssert.assertEquals(ItemTypes.VIDEO.getEntityType(), entityType, "The entityType message attribute are not matched");
        }
        softAssert.assertAll();
    }
}
