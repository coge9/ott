package com.nbcuni.test.cms.tests.backend.tvecms.video;

import com.nbcuni.test.cms.backend.chiller.block.contenttype.video.MPXInfoTab;
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
 * TC10011
 *
 * Step 1: Go to brand sitae as admin
 *
 * Step 2: Go to Content (/admin/content)
 *
 * Step 3: Choose Episode asset
 * Click Edit next to it
 *
 * Step 4: Navigate to MPX Info tab
 *
 * Validation; Check fields editable
 * Verify: All fields is non-editable
 */

public class TC10011_MPXInfoTabMPXFileEntityNonEditable extends BaseAuthFlowTest {

    @Test(groups = {"video_node"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkMPXInformationTab(String brand) {
        Utilities.logInfoMessage("Check MPX Info tab");

        SoftAssert softAssert = new SoftAssert();

        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        rokuBackEndLayer.openRandomContentAsset(ContentType.TVE_VIDEO);
        EditTVEVideoContentPage editTVEVideoContentPage = new EditTVEVideoContentPage(webDriver, aid);

        MPXInfoTab additionalInformationTab = editTVEVideoContentPage.onMPXInfoTab();

        additionalInformationTab.verifyElements(softAssert).assertAll();
    }
}
