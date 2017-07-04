package com.nbcuni.test.cms.tests.backend.tvecms.video;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 26-Oct-15.
 */

/**
 * TC 10014
 *
 * Step 1: Go to brand site as admin
 *
 * Step 2: Go to Content (/admin/content)
 *
 * Step 3: Choose Episode asset
 * Click Edit next to it
 *
 * Validation: Check  Feature Carousel CTA
 * Verify:  Feature Carousel CTA field is present
 */

public class TC10014_VideoFeatureCarouselCTA extends BaseAuthFlowTest {

    @Test(groups = {"video_node"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkFeatureCarouselCTABlock(String brand) {
        Utilities.logInfoMessage("Check Feature Carousel CTA block");

        SoftAssert softAssert = new SoftAssert();

        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

//      Step 1 - 3
        rokuBackEndLayer.openRandomContentAsset(ContentType.TVE_VIDEO);
        EditTVEVideoContentPage editTVEVideoContentPage = new EditTVEVideoContentPage(webDriver, aid);

//      Validation
        softAssert.assertTrue(editTVEVideoContentPage.isFeatureCarouselCTAEnable(), "Feature Carousel CTA isn't enable",
                "Feature Carousel CTA is enable");

        softAssert.assertAll();
    }
}
