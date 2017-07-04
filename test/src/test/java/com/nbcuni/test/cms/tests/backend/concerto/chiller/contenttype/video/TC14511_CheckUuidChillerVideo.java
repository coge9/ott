package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.video;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ChillerVideoPage;
import com.nbcuni.test.cms.backend.chiller.pages.contenttype.DevelPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by alekca on 13.05.2016.
 */
public class TC14511_CheckUuidChillerVideo extends BaseAuthFlowTest {

    /**
     * Steps:
     * 1.Go To chiller CMS as editor
     * Verify: The Editor panel is present
     * <p>
     * 2.Go To Content page
     * Verify:There is a list of TVE Video
     * <p>
     * 3.Select a video on edit
     * Verify:The Edit TVE Video page is present
     * <p>
     * 4.Click on Devel tab
     * Verify:The devel page is opned
     * -list of fields on table is present
     * <p>
     * 5.check uuid field is generated
     * Verify: The uuid field is generated
     */

    @Test(groups = {"chiller_video", "uuid"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void checkChillerVideoUuid(String brand) {

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 2
        ContentPage contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        contentPage.searchByType(ContentType.TVE_VIDEO).apply();
        String title = contentPage.getFullTitleOfFirstElement();

        //Step 3
        ChillerVideoPage chillerVideoPage = contentPage.clickEditLink(ChillerVideoPage.class, title);

        //Step 4
        DevelPage develPage = chillerVideoPage.openDevelPage();

        //Step 5
        softAssert.assertTrue(develPage.uuidIsPresent(), "The uuid field is not present", "The uuid field is present and not empty");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test is passed");
    }
}
