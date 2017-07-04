package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi.promo;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.MediaContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.Promo;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.links.Link;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.links.factory.LinkFactory;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.concerto.PromoJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.verification.roku.ios.PromoVerificator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 8/31/2016.
 */

/**
 * TC15940
 *
 * 1. Go to CMS for IOS brand
 * Admin panel is present
 * 2. Go to create new Promo page (node/add/promo)
 * New Promo page is opened
 * 3. Create Promo with all fields
 * Promo is created
 * 4. Publish
 * Promo is published
 * Validation    Check promo json
 */

public class TC15940_PublishingPromoAllFields extends BaseAuthFlowTest {

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
        String title1 = mediaContentPage.getTitle();
        Link link1 = LinkFactory.createLinkWithContent();
        link1.setUrlContentItem(title1);
        link1.setUuid(mediaContentPage.openDevelPage().getUuid());
        link1.setItemType("Video");
        ((Promo) content).getLinks().addLink(link1);

        contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        mediaContentPage = contentPage.openRandomEditOTTProgramPage();
        String title2 = mediaContentPage.getTitle();
        Link link2 = LinkFactory.createLinkWithContent();
        link2.setUrlContentItem(title2);
        link2.setUuid(mediaContentPage.openDevelPage().getUuid());
        link2.setItemType("Series");
        ((Promo) content).getLinks().addLink(link2);
    }

    @Test(groups = {"promo_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = true)
    public void promoPublishing(String brand) {

        init(brand);

        ContentTypePage contentTypePage = rokuBackEndLayer.createContentType(content);
        Content expectedContent = contentTypePage.getPageData();
        ((Promo) expectedContent).setLinks(((Promo) content).getLinks());
        contentTypePage.publish();
        softAssert.assertTrue(contentTypePage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        String url = rokuBackEndLayer.getLogURL(brand);

        PromoJson actualJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.PROMO);
        PromoJson expectedJson = new PromoJson((Promo) expectedContent);

        softAssert.assertTrue(new PromoVerificator().verify(expectedJson, actualJson), "The actual data is not matched",
                "The JSON data is matched");

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteContent() {
        rokuBackEndLayer.deleteContentType(content);
    }
}
