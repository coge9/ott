package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.contenttype.series;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.SeriesPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.pageobjectutils.chiller.actionpublishing.Action;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Aleksandra_Lishaeva on 5/23/16.
 */
public class TC14669_CheckSeriesMessageAttributes extends BaseAuthFlowTest {

    /**
     * Steps:
     * 1.Go To CMS as editor
     * Verify: The editor Panel is present
     * <p>
     * 2.Go To Content and select an Series
     * Verify:The Edit Series Page is opened
     * <p>
     * 3.Click 'Publish' button and send POST request to Amazon API
     * Verify: The POST request is send to the Amazon queue<br/>
     * API Log present 'success' status message per request
     * <p>
     * 4.Go To Amazon consol and analize Header of POST request
     * Verify: There are attributes:
     * action = 'post'
     * entityType = 'series
     */


    @Autowired
    @Qualifier("defaultSeries")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        content = contentTypeCreationStrategy.createContentType();
    }

    @Test(groups = {"series_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void testSeriesPublishingAttributes(String brand) {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //create event with whole data
        SeriesPage editPage = (SeriesPage) rokuBackEndLayer.createContentType(content);

        //publishing
        editPage.publish();
        String url = editPage.getLogURL(brand);
        softAssert.assertTrue(editPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        //Get Actual Post Request
        LocalApiJson localApiJson = requestHelper.getSingleLocalApiJson(url);

        softAssert.assertTrue(localApiJson.getAttributes().getAction() != null, "The action message attribute are not present",
                "The action message attribute are present");

        softAssert.assertTrue(localApiJson.getAttributes().getEntityType() != null, "The entityType message attribute are not present",
                "The entityType message attribute are present");

        String action = localApiJson.getAttributes().getAction().getStringValue();

        softAssert.assertEquals(Action.POST.getAction(), action, "The action message attribute are not matched",
                "The action message attribute are matched");

        String entityType = localApiJson.getAttributes().getEntityType().getStringValue();
        softAssert.assertEquals(ItemTypes.SERIES.getEntityType(), entityType, "The entityType message attribute are not matched",
                "The entityType message attribute are matched");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");

    }

    @AfterMethod(alwaysRun = true)
    public void deleteContent() {
        rokuBackEndLayer.deleteContentType(content);
    }

}
