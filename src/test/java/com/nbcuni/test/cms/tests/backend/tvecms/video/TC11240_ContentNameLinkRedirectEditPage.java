package com.nbcuni.test.cms.tests.backend.tvecms.video;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.EditTVEVideoContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by Aleksandra_Lishaeva on 3/14/16.
 */
public class TC11240_ContentNameLinkRedirectEditPage extends BaseAuthFlowTest {


    /**
     * Step 1. Login in Roku CMS as Admin
     * Verify: Admin Panel is present
     *
     * Step 2. Go to /admin/content
     * Verify: Content list is present
     *
     * Step 3: Choose 'TVE Video' filter
     * Verify: Video content is shown
     *
     * Step 4: Choose random TVE Video item and click on title
     * Verify: Current TVE Video is opened for edit
     * */

    @Test(groups = {"video_node"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider")
    public void checkNodeRedirectLink(String brand) {
        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 2
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);

        //Step 3
        contentPage.searchByType(ContentType.TVE_VIDEO).apply();

        //Step 4
        MainRokuAdminPage page = contentPage.clickOnFirstElement();
        Assertion.assertTrue(page instanceof EditTVEVideoContentPage, "The opened page is not TVE Video");
        Utilities.logInfoMessage("The opened page is Edit TVE Video Page");
    }
}
