package com.nbcuni.test.cms.tests.backend.tvecms.publishing.serialapi.video;

import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.PublishInstance;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.tests.backend.tvecms.mpx.BaseMPXAccountSet;
import org.testng.annotations.Test;

/**
 * Created by Ivan on 19.11.2015.
 */

/**
 * TC10146
 *
 * Step 1: go to Roku CMS
 *
 * Step 2: go to /admin/config/media/theplatform
 * remove all MPX content
 *
 * Step 3: Add MPX account
 * Ingest content
 *
 * Step 4: go to content page
 * click "edit" next to test OTT Video
 *
 * Step 5: GET test video's data from API
 *
 * Validation: Verify response from service
 */
public class TC10146_AutomaticPublisingAfterIngestNewVideoNode extends BaseMPXAccountSet {

    @Test(groups = {"roku_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider")
    public void checkAutomaticPublishing(String brand) {
        mainRokuAdminPage.runAllCron(brand);
        backEndLayer.openRandomContentAsset(ContentType.TVE_VIDEO, brand);

        EditTVEVideoContentPage videoContentPage = new EditTVEVideoContentPage(webDriver, aid);

        softAssert.assertFalse(videoContentPage.elementPublishBlock().isPublishEnable(PublishInstance.DEV), "Publish button is enable",
                "Publish button is disable");
        softAssert.assertAll();
    }
}
