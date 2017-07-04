package com.nbcuni.test.cms.tests.backend.tvecms.publishing.serialapi.video;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.MpxFileMediaPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.Instance;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.PublishInstance;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuVideoJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.cms.verification.roku.RokuVideoJsonVerificator;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 23-Nov-15.
 */

/**
 * TC10416
 *
 * Step 1: Go to CMS as admin
 *
 * Step 2: Choose and edit [ott video name]
 *
 * Step 3: Publish video [ott video name]
 *
 * Step 4: Go to Content page
 *
 * Step 5: Find [ott video name] and delete
 *
 * Step 6: Go to Content->Files page
 *
 * Step 7: Find [ott video name] and delete
 *
 * Step 8: Go to 'Content->MPX updater'
 *
 * Step 9: Enter MPX ID number for deleted video and click 'Update'
 *
 * Step 10: Choose and publish [ott video name]
 *
 * Validation: Republish deleted published OTT Video
 * Expected result: Status message is presented. Error message isn't presented.
 */

public class TC10416_APIPublishForEntityUpdateRepublishOTTVideoAfterCMSUpdate extends BaseAuthFlowTest {

    private String assetID = Config.getInstance().getRokuMPXVideoID(brand, Instance.STAGE);

    @Test(groups = {"roku_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider")
    public void checkPublishVideo(String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        SoftAssert softAssert = new SoftAssert();

        String title = rokuBackEndLayer.openRandomContentAsset(ContentType.TVE_VIDEO, brand);

        EditTVEVideoContentPage videoContentPage = new EditTVEVideoContentPage(webDriver, aid);

        String id = videoContentPage.onMPXInfoTab().collectMetaData().get("ID");
        videoContentPage.elementPublishBlock().publishByTabName(PublishInstance.DEV);

        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        contentPage.clickDeleteButton(title);

        MpxFileMediaPage fileMediaPage = mainRokuAdminPage.openMpxMediaPage(brand);
        fileMediaPage.clickDeleteButton(title);

        mainRokuAdminPage.openMpxUpdaterPage(brand).runUpdaterByMPXID(id);

        RokuVideoJson actualMetadata = requestHelper.getSingleParsedResponse(mainRokuAdminPage.getLogURL(brand), SerialApiPublishingTypes.VIDEO);

        RokuVideoJson expectedMetadata = rokuBackEndLayer.getVideoDataForSerialApi(title);

        softAssert.assertEquals(expectedMetadata, actualMetadata, "Metadata is not correct in publish json",
                "Metadata is correct in publish json", new RokuVideoJsonVerificator());

        softAssert.assertAll();
    }

}
