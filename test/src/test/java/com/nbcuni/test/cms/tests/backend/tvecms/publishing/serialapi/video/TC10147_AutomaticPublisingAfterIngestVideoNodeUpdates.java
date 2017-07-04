package com.nbcuni.test.cms.tests.backend.tvecms.publishing.serialapi.video;

import com.nbcuni.test.cms.backend.tvecms.MPXLayer;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.Instance;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.SimpleUtils;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuVideoJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.cms.verification.roku.RokuVideoJsonVerificator;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 23-Nov-15.
 */

/**
 * TC10147
 *
 * Step 1: Update any video at mxp
 *
 * Step 2: Run cron
 *
 * Step 3: Make get request to API service
 *
 * Validation: Verify response from service
 * Expected result: MPX data should be changed according updates
 */

public class TC10147_AutomaticPublisingAfterIngestVideoNodeUpdates extends BaseAuthFlowTest {

    private String newTitle = SimpleUtils.getRandomString(7);
    private String oldTitle;

    @Test(groups = {"roku_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider")
    public void checkAutomaticPublishing(String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        SoftAssert softAssert = new SoftAssert();

        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        EditTVEVideoContentPage videoContentPage = contentPage.openEditOTTVideoPageById(Config.getInstance()
                .getRokuMPXVideoID(brand, Instance.STAGE));
        RokuVideoJson expectedMetadata = videoContentPage.getVideoObjectFromNodeMetadata();
        expectedMetadata.setTitle(newTitle);

        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_VIDEO);
        oldTitle = mpxLayer.getAssetTitle();
        mpxLayer.updateAssetTitle(newTitle);

        mainRokuAdminPage.runCron(brand);
        RokuVideoJson actualMetadata = requestHelper.getSingleParsedResponse(mainRokuAdminPage.getLogURL(brand), SerialApiPublishingTypes.VIDEO);
        softAssert.assertEquals(expectedMetadata, actualMetadata, "Metadata is not correct in publish json",
                "Metadata is correct in publish json", new RokuVideoJsonVerificator());

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod() {
        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_VIDEO);
        mpxLayer.updateAssetTitle(oldTitle);
    }
}
