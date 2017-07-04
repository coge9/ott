package com.nbcuni.test.cms.tests.backend.concerto.chiller.publishing.images;

import com.nbcuni.test.cms.backend.chiller.pages.assetlibrary.AssetLibraryPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.apiinstances.ApiInstancesPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.apiinstances.APIType;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.List;

public class TC14418_CheckSeveralApiInstancePresence extends BaseAuthFlowTest {
    /**
     * Pre-condition:
     * 1. Login in TVE CMS
     * 2. Go to API instance page
     * 3. Create new amazon instance if required
     *
     * Step 1: Go to Asset library
     * Step 2: Open any random image for edit
     * Step 3: Publish this image
     */
    String apiInstanceForCreation;

    @Test(groups = {"image_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = false)
    public void test14418(String brand) {
        //Pre-condition
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        ApiInstancesPage apiInstancesPage = mainRokuAdminPage.openPage(ApiInstancesPage.class, brand);
        String apiInstanceForCreation = apiInstancesPage.createAmazonInstanceIfOnlyOnePresent();
        apiInstancesPage = mainRokuAdminPage.openPage(ApiInstancesPage.class, brand);
        List<String> expectedInstances = apiInstancesPage.getNamesOfEnabledInstances(APIType.AMAZON);

        //Step 1-3
        AssetLibraryPage assetLibraryPage = mainRokuAdminPage.openPage(AssetLibraryPage.class, brand);
        boolean verifyConfirmation = assetLibraryPage.clickRandomAsset().getActionBlock().clickPublishAndCheckConfirmation(apiInstanceForCreation, expectedInstances);
        softAssert.assertTrue(verifyConfirmation, "Not all items are correct in ApiInstance list", webDriver);
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteCreatedApiInstance() {
        try {
            if (apiInstanceForCreation != null) {
                rokuBackEndLayer.deleteApiInstnace(apiInstanceForCreation);
            }
        } catch (Exception e) {
            Utilities.logSevereMessageThenFail("This instance can't be deleted!");
        }
    }

}
