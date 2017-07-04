package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi.promo;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.Promo;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.concerto.PromoJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.cms.verification.roku.ios.PromoVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 8/31/2016.
 */

/**
 * TC15939
 *
 * 1. Go to CMS for IOS brand
 * Admin panel is present
 * 2. Go to create new Promo page (node/add/promo)
 * New Promo page is opened
 * 3. Create Promo with required fields
 * Promo is created
 * 4. Publish
 * Promo is published
 * Validation    Check promo json
 */

public class TC15939_PublishingPromoRequiredFields extends BaseAuthFlowTest {

    @Autowired
    @Qualifier("withRequiredPromo")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;


    @BeforeMethod(alwaysRun = true)
    public void initBusinessObjectTC15939() {
        content = contentTypeCreationStrategy.createContentType();
    }

    @Test(groups = {"promo_publishing", "roku_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = true)
    public void promoPublishing(String brand) {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //create season with data
        ContentTypePage contentTypePage = rokuBackEndLayer.createContentType(content);
        content = contentTypePage.getPageData();
        contentTypePage.publish();
        softAssert.assertTrue(contentTypePage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        String url = rokuBackEndLayer.getLogURL(brand);

        //Get Actual Post Request
        PromoJson actualJson = requestHelper.getSingleParsedResponse(url, ConcertoApiPublishingTypes.PROMO);
        PromoJson expectedJson = new PromoJson((Promo) content);

        softAssert.assertTrue(new PromoVerificator().verify(expectedJson, actualJson), "The actual data is not matched",
                "The JSON data is matched");

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteContentTC15939() {
        try {
            rokuBackEndLayer.deleteContentType(content);
        } catch (Exception e) {
            Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
        }
    }
}
