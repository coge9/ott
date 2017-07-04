package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.video;

import com.nbcuni.test.cms.backend.chiller.block.contenttype.video.MPXInfoTab;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.ContentTabs;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by alekca on 13.05.2016.
 */
public class TC14513_CheckChillerVideoFieldsForOtherBrands extends BaseAuthFlowTest {

    /**
     * Steps:
     * 1.Go To CMS of brand differ from chiller (e.g. usa)as editor
     * Verify: The Editor panel is present
     * <p>
     * 2.Go To Content page
     * Verify:There is a list of TVE Video
     * <p>
     * 3.Select a video on edit and check custom sections is not present
     * Verify:-Media field is not present
     * -Association section is not present
     * -Promotional section is not present
     * <p>d
     * 4.check Android and Roku images tabs are present
     * Verify: The Android and Roku Images tabs are present
     * <p>
     * 5.check Head line,CTA text and gradient are present
     * Verify:the fields are absent
     */

    @Test(groups = {"chiller_video", "roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = true)
    public void checkChillerCustomVideoFields(String brand) {
        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        List<String> platformsList = mainRokuAdminPage.openTvePlatformPage(brand).getAllPlatformTitlesOnPage();

        //Step 2
        ContentPage contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        contentPage.searchByType(ContentType.TVE_VIDEO).apply();
        String title = contentPage.getFullTitleOfFirstElement();

        //Step 3
        EditTVEVideoContentPage videoPage = contentPage.clickEditLink(EditTVEVideoContentPage.class, title);
        softAssert.assertFalse(videoPage.isTabPresent(ContentTabs.ASSOCIATIONS), "The Association tab is present",
                "The Association tab is not present", webDriver);
        softAssert.assertFalse(videoPage.isTabPresent(ContentTabs.PROMOTIONAL), "The Promotional tab is present",
                "The Promotional tab is not present", webDriver);
        softAssert.assertFalse(videoPage.isTabPresent(ContentTabs.MEDIA), "The Media tab is present",
                "The Media tab is not present", webDriver);


        softAssert.assertTrue(videoPage.isTabPresent(ContentTabs.URL_PATH_SETTINGS), "The Slug tab is present",
                "The Slug tab is not present", webDriver);
        softAssert.assertTrue(videoPage.isTabPresent(ContentTabs.MPX_INFO), "The MPX Info tab is not present",
                "The MPX Info tab is present", webDriver);

        //Step 4
        if (platformsList.contains(CmsPlatforms.ROKU.getAppName())) {
            softAssert.assertTrue(videoPage.isTabPresent(ContentTabs.ROKU_IMAGES), "The Roku Images tab is not present",
                    "The Roku Images tab is present", webDriver);
        }
        if (platformsList.contains(CmsPlatforms.ANDROID.getAppName())) {
            softAssert.assertTrue(videoPage.isTabPresent(ContentTabs.ANDROID_IMAGES), "The Android Images tab is not present",
                    "The Android Images tab is present", webDriver);
        }
        if (platformsList.contains(CmsPlatforms.IOS.getAppName())) {
            softAssert.assertTrue(videoPage.isTabPresent(ContentTabs.IOS_IMAGES), "The iOS Images tab is not present",
                    "The iOS Images tab is present", webDriver);
        }
        if (platformsList.contains(CmsPlatforms.APPLETV.getAppName())) {
            softAssert.assertTrue(videoPage.isTabPresent(ContentTabs.APPLETV_IMAGES), "The AppleTV Images tab is not present",
                    "The AppleTV Images tab is present", webDriver);
        }
        if (platformsList.contains(CmsPlatforms.FIRETV.getAppName())) {
            softAssert.assertTrue(videoPage.isTabPresent(ContentTabs.FIRETV_IMAGES), "The FireTV Images tab is not present",
                    "The FireTV Images tab is present", webDriver);
        }
        if (platformsList.contains(CmsPlatforms.XBOXONE.getAppName())) {
            softAssert.assertTrue(videoPage.isTabPresent(ContentTabs.XBOXONE_IMAGES), "The Xbox One Images tab is not present",
                    "The Xbox One Images tab is present", webDriver);
        }
        //TODO: fix step 5 "verifyElements" - currently uses locator as String
        //Step 5
        MPXInfoTab mpxInfoTab = videoPage.onMPXInfoTab();
        softAssert.assertTrue(mpxInfoTab.verifyElements(softAssert).getTempStatus()
                , "The Mpx Info tab is not contain all elements",
                "The Mpx Info tab contains all elements", webDriver);
        softAssert.assertAll();

        //Step 6
        softAssert.assertTrue(videoPage.isFeatureCarouselCTAPresent(),
                "The Feature carousel CTA field is not present",
                "The Feature carousel CTA field is present", webDriver);

        softAssert.assertTrue(videoPage.isHeadlinePresent(),
                "The Headline field is not present",
                "The Headline field is present", webDriver);

        softAssert.assertTrue(videoPage.isTemplateStylePresent(),
                "The Template style field is not present",
                "The Template style field is present", webDriver);
        softAssert.assertAll();

        Utilities.logInfoMessage("The test is passed");
    }
}
