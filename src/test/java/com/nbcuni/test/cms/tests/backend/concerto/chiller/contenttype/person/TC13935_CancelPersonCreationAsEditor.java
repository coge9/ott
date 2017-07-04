package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.person;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.PersonPage;
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
public class TC13935_CancelPersonCreationAsEditor extends BaseAuthFlowTest {

    private PersonEntity personEntity = null;

    /**
     * Step 1: Go To CMS as Editor
     * Verify: Editor panel is present
     *
     * Step 2: Navigate to the Dashboard->Metadata->Add Person
     * Verify: The page with next fields is present:
     * Field:Title - "New Person"
     *
     * Step 3: Click 'cancel' button
     * Verify: The Dashboard Page is present
     * */

    @Test(groups = {"person"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void cancelCreation(final String brand) {
        this.brand = brand;

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPageAsEditor();

        //Step 2
        PersonPage personPage = mainRokuAdminPage.openPage(PersonPage.class, brand);

        //Step 3
        personPage.cancel();
        Assertion.assertContains(mainRokuAdminPage.getPageTitle(), "Metadata", "The page is not Dashboard after edit cancelation");
        Utilities.logInfoMessage("The opened page is Dashboard after person cancelation by Editor");
    }
}
