package com.nbcuni.test.cms.tests.backend.tvecms.content.ios;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.Promo;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 8/30/2016.
 */

/**
 * TC15937
 *
 * 1. Go to CMS for IOS brand
 * Admin panel is present
 * 2. Go to create new Promo page (node/add/promo)
 * New Promo page is opened
 * 3. Create Promo
 * Promo is created
 * 4. Open for edit promo and update all fields
 * Promo is updated
 */

public class TC15937_UpdatePromoContentType extends BaseAuthFlowTest {

    private Content updatedContent;

    @Autowired
    @Qualifier("withAllPromo")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void init() {
        content = contentTypeCreationStrategy.createContentType();
        updatedContent = contentTypeCreationStrategy.createContentType();
    }

    @Test(groups = {"promo_ios", "roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider")
    public void createPromoContentTypeWithRequiredFields(final String brand) {

        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        rokuBackEndLayer.createContentType(content);

        rokuBackEndLayer.updateContent(content, updatedContent);
        ContentTypePage contentTypePage = rokuBackEndLayer.editContentType(updatedContent);
        Content actualContent = contentTypePage.getPageData();
        ((Promo) actualContent).getImage().setUrl(((Promo) updatedContent).getImage().getUrl());

        softAssert.assertFalse(contentTypePage.isErrorMessagePresent(), "Error message is presented",
                "Error message is not presented", webDriver);

        softAssert.assertEquals(updatedContent, actualContent, "Promo data is not matched", "Promo data is matched");

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteContentTC15937() {
        try {
            rokuBackEndLayer.deleteContentType(updatedContent);
        } catch (Exception e) {
            Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
        }
    }
}
