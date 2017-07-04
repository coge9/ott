package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.video;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ChillerVideoPage;
import com.nbcuni.test.cms.backend.chiller.pages.contenttype.DevelPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.MpxUpdaterPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.video.Video;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.actionpublishing.Action;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.ContentTypeDeleteJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 07-Jul-16.
 */

/**
 * TC15281
 *
 * Pre-Conditions:
 * 1. Login in CMS D7 as admin
 * 2. Choose Video
 *
 * Steps
 * 1.
 * Go to content page
 * Content page is opened
 * 2.
 * Delete Video manually on content list screen
 * Video is deleted.
 * Publish delete message:
 * Request data:
 * uuid: e29a1f78-cdfc-4022-9a77-ab721dd370da
 * itemType: "video"
 * There are attributes:
 * action = 'delete'
 * entityType = 'videos'
 */
public class TC15291_PublishingDeleteMessage_DeleteVideoManually extends BaseAuthFlowTest {

    private Video video = new Video();

    @Test(groups = {"video_publishing", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void testVideoDeletePublishing(String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        ContentPage contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        contentPage.searchByType(ContentType.TVE_VIDEO).apply();
        String title = contentPage.getRandomAsset(ContentType.TVE_VIDEO);
        video.getGeneralInfo().setTitle(title);

        //Step 3
        ChillerVideoPage chillerVideoPage = contentPage.clickEditLink(ChillerVideoPage.class, title);
        video.getMpxAsset().setId(chillerVideoPage.getMpxInfo().getId());

        //Step 4
        DevelPage develPage = chillerVideoPage.openDevelPage();
        video.getGeneralInfo().setUuid(develPage.getUuid());

        rokuBackEndLayer.deleteContentType(video);

        String url = rokuBackEndLayer.getLogURL(brand);

        ContentTypeDeleteJson expectedDeleteJson = new ContentTypeDeleteJson(video);

        ContentTypeDeleteJson actualDeleteJson = requestHelper.getDeleteResponses(url, ConcertoApiPublishingTypes.VIDEO).get(0);

        LocalApiJson localApiJson = requestHelper.getSingleLocalApiJson(url);
        String action = localApiJson.getAttributes().getAction().getStringValue();

        softAssert.assertEquals(Action.DELETE.getAction(), action, "The action message attribute are not matched",
                "The action message attribute are matched");

        String entityType = localApiJson.getAttributes().getEntityType().getStringValue();
        softAssert.assertEquals(video.getType().getEntityType(), entityType, "The entityType message attribute are not matched",
                "The entityType message attribute are matched");

        softAssert.assertEquals(expectedDeleteJson, actualDeleteJson, "The actual data is not matched", "The JSON data is matched");

        softAssert.assertAll();

    }

    @AfterMethod(alwaysRun = true)
    public void ingestVideo() {
        mainRokuAdminPage.openPage(MpxUpdaterPage.class, brand).updateAsset(video.getMpxAsset().getId());
    }
}
