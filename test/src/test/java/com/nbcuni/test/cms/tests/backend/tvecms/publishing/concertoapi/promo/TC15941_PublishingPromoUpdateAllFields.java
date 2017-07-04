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
 * TC15941
 *
 * 1. Go to CMS for IOS brand
 * Admin panel is present
 * 2. Go to create new Promo page (node/add/promo)
 * New Promo page is opened
 * 3. Create Promo with all fields
 * Publish
 * Promo is created and published
 * 4. Update all fields.
 * Publish
 * Promo is updated and published.
 * Validation    Check promo json
 */

public class TC15941_PublishingPromoUpdateAllFields extends BaseAuthFlowTest {

    private Content updatedContent;

    @Autowired
    @Qualifier("withAllPromo")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;


    public void init(String brand) {
        content = contentTypeCreationStrategy.createContentType();
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        ContentPage contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        MediaContentPage mediaContentPage = contentPage.openRandomEditAssetPage();
        String title = mediaContentPage.getTitle();
        Link link = LinkFactory.createLinkWithContent();
        link.setUrlContentItem(title);
        link.setUuid(mediaContentPage.openDevelPage().getUuid());
        ((Promo) content).getLinks().addLink(link);

        updatedContent = contentTypeCreationStrategy.createContentType();
        contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        mediaContentPage = contentPage.openRandomEditOTTVideoPage();
        String updatedTitle1 = mediaContentPage.getTitle();
        Link updatedLink1 = LinkFactory.createLinkWithContent();
        updatedLink1.setUrlContentItem(updatedTitle1);
        updatedLink1.setUuid(mediaContentPage.openDevelPage().getUuid());
        updatedLink1.setItemType("Video");
        ((Promo) updatedContent).getLinks().addLink(updatedLink1);

        contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        mediaContentPage = contentPage.openRandomEditOTTProgramPage();
        String updatedTitle2 = mediaContentPage.getTitle();
        Link updatedLink2 = LinkFactory.createLinkWithContent();
        updatedLink2.setUrlContentItem(updatedTitle2);
        updatedLink2.setUuid(mediaContentPage.openDevelPage().getUuid());
        updatedLink2.setItemType("Series");
        ((Promo) updatedContent).getLinks().addLink(updatedLink2);
    }

    @Test(groups = {"promo_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = true)
    public void promoPublishing(String brand) {

        init(brand);

        rokuBackEndLayer.createAndPublishContentType(content);
        rokuBackEndLayer.updateContent(content, updatedContent);
        ContentTypePage contentTypePage = rokuBackEndLayer.editContentType(updatedContent);

        Content expectedContent = contentTypePage.getPageData();
        ((Promo) expectedContent).setLinks(((Promo) updatedContent).getLinks());
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
        rokuBackEndLayer.deleteContentType(updatedContent);
    }
}
