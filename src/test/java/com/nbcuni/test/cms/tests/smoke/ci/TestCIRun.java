package com.nbcuni.test.cms.tests.smoke.ci;

import com.nbcuni.test.cms.core.BaseAuthFlowTest;

/**
 * Created by Aleksandra_Lishaeva on 4/15/16.
 */
public class TestCIRun extends BaseAuthFlowTest {

    //@Test(groups = {"ci_smoke"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkThatAdminCouldCreateNewPage(final String brand) {
     /*   rokuBackEndLayer = new RokuBackEndLayer(cs, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        softAssert.assertTrue(true, "Test is not passed on CI", "Test is passed on CI", cs);
        softAssert.assertAll();*/
    }
}
