package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.person;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata.person.PersonEntity;
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
 * Created by Aleksandra_Lishaeva on 4/11/16.
 */
public class TC13908_CreatePersonWithAllFieldsAsAdmin extends BaseAuthFlowTest {

    private PersonEntity personEntity = null;

    /**
     * Step 1: Go To CMS as admin
     * Verify: Admin panel is present
     * <p/>
     * Step 2: Navigate to the Dashboard->Metadata->Add Person
     * Verify: The page with next fields is present:
     * Field:Title - "New Person"
     * <p/>
     * Step 3: Fill title
     * Verify: Title is filled
     * <p/>
     * Step 4: Go to tab - General Information
     * Verify: Next set of fields are present: Prefix (text field)
     * First Name (text field) - required
     * Middle Name (text field) -
     * Last Name (text field) -
     * Suffix (text field) -
     * Bio (text box)
     * <p/>
     * Step 5: Fill all the fields and 'save as draft'
     * Verify: The new content type Person is created as draft in CMS
     */

    @Autowired
    @Qualifier("withAllFieldsPerson")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        content = contentTypeCreationStrategy.createContentType();
    }

    @Test(groups = {"person"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void createPersonWithAllFields(final String brand) {
        this.brand = brand;

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step 2-4
        rokuBackEndLayer.createContentType(content);

        //Step 5
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        contentPage.searchByType(ContentType.TVE_PERSON).searchByTitle(content.getTitle()).apply();
        Assertion.assertTrue(contentPage.isContentPresent(), "The search result on given Person name is empty");
        Utilities.logInfoMessage("The new item Person is present within Content page");
    }

    @AfterMethod(alwaysRun = true)
    public void deletePersonTC13908() {
        try {
            rokuBackEndLayer.deleteContentType(content);
        } catch (Throwable e) {
            Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
        }
    }
}
