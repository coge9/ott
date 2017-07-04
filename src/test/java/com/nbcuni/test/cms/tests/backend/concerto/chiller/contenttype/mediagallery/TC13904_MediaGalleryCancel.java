package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.mediagallery;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.chiller.pages.contenttype.MediaGalleryPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 07-Apr-16.
 */

/**
 * TC13904
 *
 * 1. Go to CMS as admin
 * user should be logged in
 * 2. Navigate to add new Series
 * Add new Media Gallery page is opened
 * Validation    Click cancel
 * Content page is opened
 */
public class TC13904_MediaGalleryCancel extends BaseAuthFlowTest {

    @Test(groups = {"media_gallery"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void mediaGalleryCancel(final String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        ContentTypePage contentTypePage = (ContentTypePage) mainRokuAdminPage.openPage(MediaGalleryPage.class, brand);

        softAssert.assertTrue(contentTypePage.cancel().isPageOpened(), "Content page is not opened",
                "Content page is opened", webDriver);

        softAssert.assertAll();
    }
}
