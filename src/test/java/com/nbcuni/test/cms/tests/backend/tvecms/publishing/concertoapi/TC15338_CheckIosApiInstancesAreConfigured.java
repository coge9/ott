package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import org.testng.annotations.Test;

/**
 * Created by Alena_Aukhukova on 7/11/2016.
 */
public class TC15338_CheckIosApiInstancesAreConfigured extends BaseAuthFlowTest {
    /**
     * Created by Alena_Aukhukova on 7/6/2016.
     */

    /**
     * Pre-condition:
     * Login in CMS
     * <p/>
     * Step 1: Navigate to /admin/config/services/api-services-instances
     * Verify: There are two enabled instances: Development and Amazon
     * [Amazon] instance set as 'Secondary instance' for Development
     */
    @Test(groups = {"ios_publishing", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider")
    public void checkTC(String brand) {
        //Pre-condition
        this.brand = brand;
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        softAssert.assertTrue(rokuBackEndLayer.isIosApiInstancesConfigured(), "IOS Concerto API instance isn't " +
                "configured on brand", "IOS Concerto API instance is configured on brand", webDriver);

        softAssert.assertAll();

    }
}
