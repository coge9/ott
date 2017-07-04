package com.nbcuni.test.cms.tests.backend.tvecms.publishing.serialapi.video;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.video.GlobalVideoEntity;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.PublishInstance;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SimpleUtils;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuVideoJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.cms.utils.transformers.VideoJsonTransformer;
import com.nbcuni.test.cms.verification.roku.RokuVideoJsonVerificator;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 23-Nov-15.
 */

/**
 * TC10213
 * <p>
 * Step 1: Login into roku cms as Admin
 * <p>
 * Step 2: Go to admin/content
 * <p>
 * Step 3: Choose OTT Video content type [ott_video_name]
 * <p>
 * Step 4: Click on Edit link for OTT Video [ott_video_name]
 * <p>
 * Step 5: Click on Publish button
 * <p>
 * Step 6: Check Request URL for current OTT Video publishing.
 * <p>
 * Step 7: Update any available fields
 * <p>
 * Step 8: Click on Save OTT VIdeo
 * <p>
 * Step 9: Click on Edit link for OTT Video [ott_video_name]
 * <p>
 * Step 10: Click on Publish button
 * <p>
 * Step 11: Check Request URL for current OTT Video publishing.
 */

public class TC10213_PublishingUpdatedOTTVideo extends BaseAuthFlowTest {

    private static final String NEW_CTA = SimpleUtils.getRandomString(6);

    @Test(groups = {"roku_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider")
    public void checkPublishVideo(String brand) {
        this.brand = brand;
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();
        SoftAssert softAssert = new SoftAssert();
        mainRokuAdminPage.runAllCron(brand);
        String title = backEndLayer.openRandomContentAsset(ContentType.TVE_VIDEO, brand);
        EditTVEVideoContentPage videoContentPage = new EditTVEVideoContentPage(webDriver, aid);
        videoContentPage.elementPublishBlock().publishByTabName(PublishInstance.DEV);
        videoContentPage.typeCTA(NEW_CTA);
        videoContentPage.clickSave();
        softAssert.assertTrue(videoContentPage.elementPublishBlock().isPublishEnable(PublishInstance.DEV), "Publish button is disable",
                "Publish button is enable");
        videoContentPage.elementPublishBlock().publishByTabName(PublishInstance.DEV);
        softAssert.assertFalse(videoContentPage.elementPublishBlock().isPublishEnable(PublishInstance.DEV), "Publish button is enable",
                "Publish button is disable");
        String url = videoContentPage.getLogURL(brand);
        GlobalVideoEntity globalVideoObject = backEndLayer.getVideo(title);
        RokuVideoJson expectedData = VideoJsonTransformer.forSerialApi(globalVideoObject);
        RokuVideoJson actualMetadata = requestHelper.getSingleParsedResponse(url, SerialApiPublishingTypes.VIDEO);
        softAssert.assertEquals(expectedData, actualMetadata, "Metadata is not correct in publish json",
                "Metadata is correct in publish json", new RokuVideoJsonVerificator());
        softAssert.assertAll();
    }
}
