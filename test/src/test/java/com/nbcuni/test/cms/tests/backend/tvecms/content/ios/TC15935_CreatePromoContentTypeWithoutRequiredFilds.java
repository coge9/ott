package com.nbcuni.test.cms.tests.backend.tvecms.content.ios;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 8/30/2016.
 */

/**
 * TC15935
 *
 * 1. Go to CMS for IOS brand
 * Admin panel is present
 * 2. Go to create new Promo page (node/add/promo)
 * New Promo page is opened
 * 3. Fill all filds:
 * Basic tab
 * Promo kicker
 * Promo title
 * Promo description
 * Links tab
 * Display text
 * URL
 * Any media asset
 * Usage
 * Media tab
 * Upload image
 * Save
 * Promo is not created. Error message is present
 */

public class TC15935_CreatePromoContentTypeWithoutRequiredFilds extends BaseAuthFlowTest {

    @Autowired
    @Qualifier("withoutRequiredPromo")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void init() {
        content = contentTypeCreationStrategy.createContentType();
    }

    @Test(groups = {"promo_ios", "roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider")
    public void createPromoContentTypeWithRequiredFields(final String brand) {

        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        ContentTypePage contentTypePage = rokuBackEndLayer.openContentTypePage(content);
        contentTypePage.create(content);

        softAssert.assertFalse(contentTypePage.isStatusMessageShown(), "Status message is present",
                "Status message is not present", webDriver);
        softAssert.assertTrue(contentTypePage.isErrorMessagePresent(), "Error message is not present",
                "Error message is present", webDriver);

        softAssert.assertAll();
    }
}
