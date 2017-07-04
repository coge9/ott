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
 * Created by Ivan_Karnilau on 09-Nov-15.
 */

/**
 * TC10016
 *
 * Step 1: Go to brand sitae as admin
 *
 * Step 2: Go to Content (/admin/content)
 *
 * Step 3: Choose Episode asset
 * Click Edit next to it
 *
 * Step 4: Open Roku Images tab
 *
 * Validation: Check MPX media thumbnail source
 * Verify: MPX media thumbnail source is present and view only
 */

public class TC10016_ImagesMPXMedia3TileThumbnailSource extends BaseAuthFlowTest {

    //Lyoha: Disabled. Not actual anymore
    @Test(groups = {"video_node"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = false)
    public void checkMPXMedia3TileThumbnailSource(String brand) {
        Utilities.logInfoMessage("Check MPX media 3-tile thumbnail source block");

        SoftAssert softAssert = new SoftAssert();

        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

//      Step 1 - 3
        rokuBackEndLayer.openRandomContentAsset(ContentType.TVE_VIDEO);
        EditTVEVideoContentPage editTVEVideoContentPage = new EditTVEVideoContentPage(webDriver, aid);

//        VideoRokuImagesTab imagesTab = editTVEVideoContentPage.onRokuImagesTab();
//
//        softAssert.assertTrue(imagesTab.elementThreeTitleArea().isVisible(),
//                "3 tile source is not present", "3 tile source is present", webDriver);
//
//        softAssert.assertTrue(imagesTab.elementThreeTileProgramArea().isVisible(),
//                "3 tile program source is not present", "3 tile program source is present", webDriver);
    }
}
