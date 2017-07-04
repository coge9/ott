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
 * TC 10013
 *
 * Step 1: Go to brand site as admin
 *
 * Step 2: Go to Content (/admin/content)
 *
 * Step 3: Choose Episode asset
 * Click Edit next to it
 *
 * Validation: Check Template style
 * Verify: Should be display "Dark" and "Light" styles.
 * Styles are enabled
 */
public class TC10013_VideoTemplateStyle extends BaseAuthFlowTest {

    @Test(groups = {"video_node"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkTemplateStyleBlock(String brand) {
        Utilities.logInfoMessage("Check Template style block");

        SoftAssert softAssert = new SoftAssert();

        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

//      Step 1 - 3
        rokuBackEndLayer.openRandomContentAsset(ContentType.TVE_VIDEO);
        EditTVEVideoContentPage editTVEVideoContentPage = new EditTVEVideoContentPage(webDriver, aid);

//      Validation
        softAssert.assertTrue(editTVEVideoContentPage.isTemplateStyleEnable(), "Template style isn't enable",
                "Template style is enable", webDriver);

        softAssert.assertAll();
    }
}
