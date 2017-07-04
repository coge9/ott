package com.nbcuni.test.cms.tests.smoke.ci;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

public class TC17828_Sanity_Test extends BaseAuthFlowTest {

    private RokuBackEndLayer backEndLayer;

    @Test(groups = {"Enrique"}, enabled = true)
    public void test() {
        Utilities.logInfoMessage("Starting Test...");

        //Step 1 Go to Roku CMS and login
        backEndLayer = new RokuBackEndLayer(cs, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();
        Utilities.logInfoMessage("End of Test...");
    }
}
