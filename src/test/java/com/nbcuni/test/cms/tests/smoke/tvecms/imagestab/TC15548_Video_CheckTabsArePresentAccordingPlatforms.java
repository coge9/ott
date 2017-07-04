package com.nbcuni.test.cms.tests.smoke.tvecms.imagestab;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.PlatformApi;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aliaksei_Klimenka1 on 7/25/2016.
 */
public class TC15548_Video_CheckTabsArePresentAccordingPlatforms extends BaseAuthFlowTest {

    /**
     * Pre-Conditions Go to Roku CMS and login
     * Step 1: Go to OTT -> Platforms. Remember all configured platforms.
     * Step 2: Navigate to the Content page.
     * Step 3: Open random TVE Video.
     * Verify: There are image tabs only for configured platforms present.
     */

    @Test(groups = {"roku_smoke", "regresssion"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkTabsArePresentAccordingPlatforms(final String brand) {
        Utilities.logInfoMessage("Start checking that tabs are present according configured platforms");
        //Pre-condition
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        //Step 1
        List<CmsPlatforms> platformsList = new PlatformApi(brand).getCmsPlatforms();
        List<String> platformsString = new ArrayList<String>();
        //Step 2
        EditTVEVideoContentPage videoContentPage = mainRokuAdminPage.openContentPage(brand).openRandomEditOTTVideoPage();
        //Step 3
        List<String> tabNames = videoContentPage.elementTabsGroup().getTabsNames();
        List<String> modifiedTabNames = new ArrayList<>();
        tabNames.forEach(item -> modifiedTabNames.add(item.replace(" ", "")));
        //Verifying
        softAssert.assertContains(modifiedTabNames, platformsString,
                "Configured platforms and existing images tabs for platforms are not matched",
                "Configured platforms and existion images tabs for platforms are matched", webDriver);
        softAssert.assertAll();
    }
}