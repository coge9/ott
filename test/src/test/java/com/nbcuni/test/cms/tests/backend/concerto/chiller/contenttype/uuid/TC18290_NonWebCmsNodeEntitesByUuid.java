package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.uuid;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.MediaContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * @Lyoha
 */
public class TC18290_NonWebCmsNodeEntitesByUuid extends BaseAuthFlowTest {

    /**
     *
     Steps:
     Input    Expected Result
     1.Go To CMS as Admin
     Verify: The admin panel is present

     2.Go to asset content page
     Verify: User is on content page

     3.Open random entity edit page
     Verify: User is on edit content page

     3.Look into tab Devel
     Verify: get uuid

     4.Open entity page by getted UUID
     Verify: the same content edit page opened
     * */

    @Test(groups = {"uuid"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider", enabled = true)
    public void checkUUID(String brand) {
        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        MediaContentPage page = mainRokuAdminPage.openContentPage(brand).openRandomEditAssetPage();
        String expectedUrl = webDriver.getCurrentUrl();
        String uuid = page.openDevelPage().getUuid();
        rokuBackEndLayer.openEditEntityPageByUuid(uuid);

        softAssert.assertContains(expectedUrl, webDriver.getCurrentUrl(), "User was not redirected by uuid", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test is passed");
    }
}
