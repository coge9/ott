package com.nbcuni.test.cms.tests.backend.tvecms.content.ios;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.MediaContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.Promo;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.links.Link;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.links.factory.LinkFactory;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 8/29/2016.
 */

/**
 * TC15934
 *
 * 1. Go to CMS for IOS brand
 * Admin panel is present
 * 2. Go to create new Promo page (node/add/promo)
 * New Promo page is opened
 * 3. Fill all filds:
 * Basic tab
 * Title
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
 * Promo is created. All data is corrected
 */
public class TC15934_CreatePromoContentTypeWithAllFields extends BaseAuthFlowTest {

    @Autowired
    @Qualifier("withAllPromo")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    public void init(String brand) {
        content = contentTypeCreationStrategy.createContentType();
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        ContentPage contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        MediaContentPage mediaContentPage = contentPage.openRandomEditOTTVideoPage();
        String title = mediaContentPage.getTitle();
        Link link = LinkFactory.createLinkWithContent();
        link.setUrlContentItem(title);
        link.setUuid(mediaContentPage.openDevelPage().getUuid());
        ((Promo) content).getLinks().addLink(link);
    }

    @Test(groups = {"promo_ios"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider")
    public void createPromoContentTypeWithRequiredFields(final String brand) {

        init(brand);

        ContentTypePage contentTypePage = rokuBackEndLayer.createContentType(content);
        softAssert.assertFalse(contentTypePage.isErrorMessagePresent(), "Error message is presented",
                "Error message is not presented", webDriver);

        ContentPage contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        contentPage.searchByType(ContentType.TVE_PROMO).searchByTitle(content.getTitle()).apply();

        softAssert.assertTrue(contentPage.isContentPresent(), "The search result on given Series name is empty", webDriver);

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteContent() {
        rokuBackEndLayer.deleteContentType(content);
    }
}
