package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.uuid;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.chiller.pages.contenttype.DevelPage;
import com.nbcuni.test.cms.backend.chiller.pages.contenttype.PersonPage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by alekca on 12.05.2016.
 */
public class TC14447_CheckUUIDforPerson extends BaseAuthFlowTest {

    /**
     * Pre-Conditions:Make sure drupal 'Devel' module is switch on
     Steps:
     Input    Expected Result
     1.Go To CMS as Admin
     Verify: The admin panel is present

     2.Create a Person content types
     Verify: Person has created
     Edit Person Page is present

     3.Look into tab Devel
     Verify: There is 'uuid' field with value like '0c3cf275-b64b-412f-8adf-7063f1238c18'

     4.Make sure there is no any UUID field at the UI
     Verify: There is no any UUID field
     * */

    @Autowired
    @Qualifier("defaultPerson")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        content = contentTypeCreationStrategy.createContentType();
    }

    @Test(groups = {"uuid"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = true)
    public void checkUUID(String brand) {
        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 2
        ContentTypePage contentTypePage = rokuBackEndLayer.createContentType(content);

        softAssert.assertFalse(contentTypePage.isErrorMessagePresent(), "Error message is presented",
                "Error message is not presented", webDriver);

        ContentPage contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        contentPage.searchByType(ContentType.TVE_PERSON).apply();

        Assertion.assertTrue(contentPage.isContentPresent(), "The search result on given Person name is empty", webDriver);
        ContentTypePage page = contentPage.clickEditLink(PersonPage.class, content.getTitle());

        //Step 3
        DevelPage develPage = page.openDevelPage();

        //Step 4
        softAssert.assertTrue(develPage.uuidIsPresent(), "The uuid field is not present", "The uuid field is present and not empty");
        softAssert.assertAll();
        Utilities.logInfoMessage("Test is passed");
    }

    @AfterMethod(alwaysRun = true)
    public void deletePersonTC14447() {
        rokuBackEndLayer.deleteContentType(content);
    }
}
