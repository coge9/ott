package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.series;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.chiller.pages.contenttype.SeriesPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 11-Apr-16.
 */

/**
 * TC13906
 *
 * 1. Go to CMS as admin
 * user should be logged in
 * 2. Navigate to add new Series
 * Add new Series page is opened
 * Validation    Click cancel
 * Content page is opened
 */
public class TC13906_SeriesCancel extends BaseAuthFlowTest {

    @Test(groups = {"series"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void mediaGalleryCancel(final String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        ContentTypePage contentTypePage = (ContentTypePage) mainRokuAdminPage.openPage(SeriesPage.class, brand);

        softAssert.assertTrue(contentTypePage.cancel().isPageOpened(), "Content page is not opened",
                "Content page is opened", webDriver);

        softAssert.assertAll();
    }
}
