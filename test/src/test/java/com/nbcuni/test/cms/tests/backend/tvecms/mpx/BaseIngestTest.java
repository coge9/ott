package com.nbcuni.test.cms.tests.backend.tvecms.mpx;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.utils.Assertion;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

/**
 * Created by Ivan_Karnilau on 07-Sep-15.
 */
public class BaseIngestTest extends BaseAuthFlowTest {

    protected RokuBackEndLayer backEndLayer;

    @BeforeMethod(alwaysRun = true)
    @Parameters({"brand"})
    public void killContent(final String brandInUse) {
        System.out.print("Log to admin Site");
        brand = brandInUse;
        this.rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        Assertion.assertTrue(mainRokuAdminPage.isAdminPanelPresent(), "User is not on admin");
        mainRokuAdminPage.killAllContent(brand);
        mainRokuAdminPage.logOut(brand);
    }
}
