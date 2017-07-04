package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi.promo;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.pageobjectutils.chiller.actionpublishing.Action;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by Ivan_Karnilau on 8/31/2016.
 */

/**
 * TC15942
 *
 * 1. Go to CMS for IOS brand
 * Admin panel is present
 * 2. Go to create new Promo page (node/add/promo)
 * New Promo page is opened
 * 3. Create Promo
 * Promo is created
 * 4. Publish
 * Promo is published
 * Validation    Check message attributes
 * There are attributes:
 * action = 'post'
 * entityType = 'promo'
 */

public class TC15942_CheckPromoMessageAttributes extends BaseAuthFlowTest {

    @Autowired
    @Qualifier("withMediaPromo")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        content = contentTypeCreationStrategy.createContentType();
    }

    @Test(groups = {"promo_publishing", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = true)
    public void promoPublishing(String brand) {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //create event with whole data
        rokuBackEndLayer.createAndPublishContentType(content);

        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "Status message is not presented after saving",
                "Status message is presented after saving", webDriver);

        String url = rokuBackEndLayer.getLogURL(brand);

        //Get Actual Post Request
        LocalApiJson promoJson = requestHelper.getLocalApiJsons(url, ConcertoApiPublishingTypes.PROMO).get(0);
        List<LocalApiJson> imageJsons = requestHelper.getLocalApiJsons(url, ConcertoApiPublishingTypes.FILE_IMAGE);

        softAssert.assertTrue(promoJson.getAttributes().getAction() != null, "The action message attribute are not present for promo",
                "The action message attribute are present for promo");

        softAssert.assertTrue(promoJson.getAttributes().getEntityType() != null, "The entityType message attribute are not present for promo",
                "The entityType message attribute are present for promo");

        String promoAction = promoJson.getAttributes().getAction().getStringValue();

        softAssert.assertEquals(Action.POST.getAction(), promoAction, "The action message attribute are not matched for promo",
                "The action message attribute are matched for promo");

        String entityType = promoJson.getAttributes().getEntityType().getStringValue();
        softAssert.assertEquals(ItemTypes.PROMO.getEntityType(), entityType, "The entityType message attribute are not matched for promo",
                "The entityType message attribute are matched for promo");


        for (LocalApiJson imageJson : imageJsons) {
            softAssert.assertTrue(imageJson.getAttributes().getAction() != null,
                    "The action message attribute are not present for image",
                    "The action message attribute are present for image");

            softAssert.assertTrue(imageJson.getAttributes().getEntityType() != null,
                    "The entityType message attribute are not present for image",
                    "The entityType message attribute are present for image");

            String imageAction = imageJson.getAttributes().getAction().getStringValue();

            softAssert.assertEquals(Action.POST.getAction(), imageAction,
                    "The action message attribute are not matched for image",
                    "The action message attribute are matched for image");

            String imageEntityType = imageJson.getAttributes().getEntityType().getStringValue();
            softAssert.assertEquals(ItemTypes.IMAGE.getEntityType(), imageEntityType,
                    "The entityType message attribute are not matched for image",
                    "The entityType message attribute are matched for image");
        }
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");

    }

    @AfterMethod(alwaysRun = true)
    public void deleteContent() {
        rokuBackEndLayer.deleteContentType(content);
    }

}
