package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.role;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.RolePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata.person.PersonEntity;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

/**
 * Created by Aleksandra_Lishaeva on 4/11/16.
 */
public class TC13993_CancelRoleCreationAsEditor extends BaseAuthFlowTest {

    private PersonEntity personEntity = null;

    /**
     * Step 1: Go To CMS as Editor
     * Verify: Editor panel is present
     * <p/>
     * Step 2: Navigate to the Dashboard->Metadata->Add Role
     * Verify: The page with next fields is present:
     * Field:Title - "New Role"
     * <p/>
     * Step 3: Click 'cancel' button
     * Verify: The Dashboard Page is present
     */

    @Test(groups = {"role"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void cancelCreation(final String brand) {
        this.brand = brand;

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPageAsEditor();

        //Step 2
        RolePage rolePage = mainRokuAdminPage.openPage(RolePage.class, brand);

        //Step 3
        rolePage.cancel();
        Assertion.assertContains(mainRokuAdminPage.getPageTitle(), "Metadata", "The page is not Dashboard after edit Role cancelation");
        Utilities.logInfoMessage("The opened page is Dashboard after role cancelation by Editor");
    }
}
