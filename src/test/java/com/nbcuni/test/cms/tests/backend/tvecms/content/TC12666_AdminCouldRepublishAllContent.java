package com.nbcuni.test.cms.tests.backend.tvecms.content;

import com.nbcuni.test.cms.backend.chiller.pages.content.SelectInstancePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.LocalApiJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by Aleksandra_Lishaeva on 2/15/16.
 */
public class TC12666_AdminCouldRepublishAllContent extends BaseAuthFlowTest {

    /**
     *Step 1. Go TO CMS As admin
     * Verify: Admin Panel is present
     *
     * Step 2. Go to content page
     * Verify: The list of Videos exist
     *
     * Step 3. Select all items by checkbox
     * Verify: The items per first page are selected
     * The button select all items are present
     *
     * Step 4:Click by 'Select All items' button
     * Verify: Whole content is selected
     *
     * Step 5:  Select operation 'Publisg To API instance'
     * Verify: The New page with API endpoints selecter is present
     *
     * Step 6. Select an Instance and click Next
     * Verify: Status message of publishing is present. There is log to publish log
     *
     * Step 6.    Verify published data with any of available way:Status message, Logs in Roku CMS, API service
     * Verify:  The size of items POST to API matched with selected
     * */

    @Test(groups = {"republishing", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = false)
    public void checkAllContentRePublishing(final String brand) {

        //Step 1
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 2
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);

        //Step 3
        contentPage.checkAllItemsOnThePage();
        String expectedNumberOfAssets = contentPage.getItemsNumber();

        //Step 4
        contentPage.clickSelectAllItemsButton();

        //Step 5
        SelectInstancePage instancePage = contentPage.selectOperation(ContentPage.Operation.PUBLISH_TO_API).clickExecute();

        //Step 6
        contentPage = instancePage.publishToInstance(Config.getInstance().getRokuApiInstance());

        //Step 7
        String url = contentPage.getLogURL(brand);
        softAssert.assertTrue(contentPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);

        List<LocalApiJson> requests = requestHelper.getLocalApiJsons(url, SerialApiPublishingTypes.PROGRAM);
        requests.addAll(requestHelper.getLocalApiJsons(url, SerialApiPublishingTypes.VIDEO));

        softAssert.assertEquals(expectedNumberOfAssets, String.valueOf(requests.size()), "The actual elements size: " + requests.size() + " in POST request is not matched with expected: " + expectedNumberOfAssets,
                "The actual number of assets within to POST request is matched with number of elements per content", webDriver);
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");

    }
}
