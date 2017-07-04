package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.castcredit;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.EventPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.event.Event;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.ConcertoApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Pre-Conditions:
 * Create TVE Event
 * Steps:
 * <p>
 * 1. Go to CMS
 * Verify: User is in CMS
 * <p>
 * 2.Go to Content list
 * Find TVE Event created in prec.
 * Click "Edit" next to it
 * Verify: Edit Node page is opened
 * <p>
 * 3. Go to "CAST AND CREDITS" block
 * set Person = ""
 * set Role = ""
 * save node
 * Verify: Node is saved
 * <p>
 * 4.Publish node
 * Verify: Node is published
 * <p>
 * 5.Open full publishing report
 * Verify: Full publishing report is published in new tab
 * Validation     check that there is no logs for OBJECT TYPE = cast_credits
 * there is no publishing logs for cast_credits
 */
public class TC14742_DoNotPublishCastCreditEvent extends BaseAuthFlowTest {

    private Event event;

    @Autowired
    @Qualifier("defaultEvent")
    private ContentTypeCreationStrategy eventCreationStrategy;


    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        event = (Event) eventCreationStrategy.createContentType();
    }

    @Test(groups = {"castCredit_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void checkCastCreditEventNotPublished(String brand) {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();


        EventPage editPage = (EventPage) rokuBackEndLayer.createContentType(event);

        //publishing
        editPage.publish();
        String url = editPage.getLogURL(brand);

        //Get Actual Post Request
        List<LocalApiJson> localApiJsons = requestHelper.getLocalApiJsons(url, ConcertoApiPublishingTypes.CAST_CREDIT);

        for (LocalApiJson localApiJson : localApiJsons) {
            softAssert.assertFalse(localApiJson.getRequestOptions().getAttributes().getEntityType().equals(ItemTypes.CAST_CREDIT.getEntityType()),
                    "The cast and credit POST go to API for event without filled fields",
                    "The empty set cast and credit do not POST to API for event");
            softAssert.assertFalse(localApiJson.getRequestData().getAsJsonObject().get("itemType").equals(ItemTypes.CAST_CREDIT.getItemType()),
                    "The current item type is cast and credit",
                    "The current item type is not cast and credit");
        }
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

}
