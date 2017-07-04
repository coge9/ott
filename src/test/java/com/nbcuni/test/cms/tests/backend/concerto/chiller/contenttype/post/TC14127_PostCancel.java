package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.post;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.chiller.pages.contenttype.PostPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 07-Apr-16.
 */

/**
 * TC14127
 *
 * 1. Go to CMS as admin
 * user should be logged in
 * 2. Navigate to add new Series
 * Add new Episode page is opened
 * Validation    Click cancel
 * Content page is opened
 */
public class TC14127_PostCancel extends BaseAuthFlowTest {

    @Test(groups = {"post"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void postCancel(final String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        ContentTypePage contentTypePage = mainRokuAdminPage.openPage(PostPage.class, brand);
        contentTypePage.cancel();
        softAssert.assertTrue(mainRokuAdminPage.isPageOpened(), "Dashboard is not opened",
                "Dashboard is opened", webDriver);

        softAssert.assertAll();
    }
}
