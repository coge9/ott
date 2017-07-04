package com.nbcuni.test.cms.tests.smoke.chiller.smoke.content;

import com.nbcuni.test.cms.backend.chiller.block.contenttype.AssociationsBlock;
import com.nbcuni.test.cms.backend.chiller.block.contenttype.PromotionalBlock;
import com.nbcuni.test.cms.backend.chiller.block.contenttype.media.MediaBlock;
import com.nbcuni.test.cms.backend.chiller.block.contenttype.video.MPXInfoTab;
import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ChillerVideoPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.ContentTabs;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import org.testng.annotations.Test;

/**
 * Created by alekca on 13.05.2016.
 */
public class TC14512_CheckFieldsForChillerVideo extends BaseAuthFlowTest {

    /**
     * Steps:
     * 1.Go To chiller CMS as editor
     * Verify: The Editor panel is present
     * <p>
     * 2.Go To Content page
     * Verify:There is a list of TVE Video
     * <p>
     * 3.Select a video on edit and check custom sections is present
     * Verify:-Media field is present
     * -Association section is present
     * -Promotional section is present
     * <p>
     * 4.check Association section
     * Verify:Next fields are present:
     * -categories
     * -channel
     * -tags
     * <p>
     * 5.check fields on Promotional section
     * Verify: Promotional field is present:
     * Promotional title.
     * promotional description
     * upload image button
     * upload from asset libarary button
     * <p>
     * 6.Check slug fields are present per Page
     * Verify: slug fields are present
     * <p>
     * 7.check Android and Roku images tabs are not present
     * Verify: The Android and Roku Images tabs are absent
     * <p>
     * 8.check Head line,CTA text and gradient are basent
     * Verify:the fields are absent
     */

    @Test(groups = {"chiller_video", "roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void checkChillerCustomVideoFields(String brand) {

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 2
        ContentPage contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        contentPage.searchByType(ContentType.TVE_VIDEO).apply();
        String title = contentPage.getFullTitleOfFirstElement();

        //Step 3
        ChillerVideoPage chillerVideoPage = contentPage.clickEditLink(ChillerVideoPage.class, title);
        softAssert.assertTrue(chillerVideoPage.isTabPresent(ContentTabs.ASSOCIATIONS), "The Association tab is not present",
                "The Association tab is present", webDriver);
        softAssert.assertTrue(chillerVideoPage.isTabPresent(ContentTabs.PROMOTIONAL), "The Promotional tab is not present",
                "The Promotional tab is present", webDriver);
        softAssert.assertTrue(chillerVideoPage.isTabPresent(ContentTabs.MEDIA), "The Media tab is not present",
                "The Media tab is present", webDriver);
        softAssert.assertTrue(chillerVideoPage.isTabPresent(ContentTabs.MPX_INFO), "The MPX Info tab is not present",
                "The MPX Info tab is present", webDriver);
        softAssert.assertTrue(chillerVideoPage.isTabPresent(ContentTabs.URL_PATH_SETTINGS), "The Slug tab is not present",
                "The Slug tab is present", webDriver);

        //Step 7
        softAssert.assertFalse(chillerVideoPage.isTabPresent(ContentTabs.ROKU_IMAGES), "The Roku Images tab is present",
                "The Roku Images tab is not present", webDriver);
        softAssert.assertFalse(chillerVideoPage.isTabPresent(ContentTabs.ANDROID_IMAGES), "The Android Images tab is present",
                "The Android Images tab is not present", webDriver);

        //Step 4
        AssociationsBlock associationsBlock = chillerVideoPage.onAssociationsTab();
        softAssert.assertTrue(associationsBlock.verifyPage().isEmpty(), "The Association tab is not contain all elements",
                "The Association tab is present", webDriver);

        //Step 5
        PromotionalBlock promotionalBlock = chillerVideoPage.onPromotionalTab();
        softAssert.assertTrue(promotionalBlock.verifyPage().isEmpty(), "The Promotional tab is not contain all elements",
                "The Promotional tab contains all lements", webDriver);

        //Step 6
        MediaBlock mediaBlock = chillerVideoPage.onMediaTab().onMediaBlock();
        softAssert.assertTrue(mediaBlock.verifyPage().isEmpty(), "The Media tab is not contain all elements",
                "The Media tab contains all elements", webDriver);
        softAssert.assertAll();

        //Step 8
        MPXInfoTab mpxInfoTab = chillerVideoPage.onMpxInfoTab();
        softAssert.assertTrue(mpxInfoTab.verifyElements(softAssert).getTempStatus()
                , "The Mpx Info tab is not contain all elements",
                "The Mpx Info tab contains all elements", webDriver);
        softAssert.assertAll();

    }
}
