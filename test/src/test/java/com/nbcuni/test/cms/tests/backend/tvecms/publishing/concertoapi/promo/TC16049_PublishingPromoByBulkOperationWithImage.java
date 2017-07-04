package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi.promo;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.GlobalPromoEntity;
import com.nbcuni.test.cms.collectservices.promo.ContentApiPromoDataCollector;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.images.ImagesJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.utils.transformers.ImageTransformer;
import com.nbcuni.test.cms.verification.tvecms.concertoapi.ImageConcertoVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 9/20/2016.
 */

/**
 * TC16049
 *
 * 1. Go To CMS
 * CMS Panel is present
 * 2. Create new Promo with media image
 * Promo with image is created
 * 3. Publish promo by bulk operation
 * Promo with image is published
 * Validation    Check publish log for image file
 * The Scheme matched scheme below http://private-e3bfd-concertoapiingestmaster.apiary-mock.com/image
 */

public class TC16049_PublishingPromoByBulkOperationWithImage extends BaseAuthFlowTest {

    @Autowired
    @Qualifier("withMediaPromo")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;


    @BeforeMethod(alwaysRun = true)
    public void init() {
        content = contentTypeCreationStrategy.createContentType();
    }

    @Test(groups = {"promo_publishing", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = true)
    public void promoPublishing(String brand) {

        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        rokuBackEndLayer.createContentType(content);

        rokuBackEndLayer.publishContentTypesByBulkOperation(content);
        String url = rokuBackEndLayer.getLogURL(brand);

        GlobalPromoEntity globalPromoEntity = new ContentApiPromoDataCollector(brand).collectPromoInfo(content.getTitle());
        ImagesJson expectedImagesJson = ImageTransformer.getConcertoApiForPromo(globalPromoEntity.getImageSource(), brand);
        ImagesJson actualImagesJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.FILE_IMAGE);

        softAssert.assertEquals(expectedImagesJson, actualImagesJson, "Image jsons are not equals",
                "Image jsons are equals", new ImageConcertoVerificator());

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteContent() {
        try {
            rokuBackEndLayer.deleteContentType(content);
        } catch (Exception e) {
            Utilities.logWarningMessage("Couldn't delete the content");
        }
    }
}
