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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Aleksandra_Lishaeva on 4/11/16.
 */
public class TC13936_DeletePersonAsAdmin extends BaseAuthFlowTest {

    private PersonEntity personEntity = null;

    /**
     * Pre-Condition:
     * Craete a Person
     * <p/>
     * Step 1: Go To CMS as admin
     * Verify: Admin panel is present
     * <p/>
     * Step 2: Navigate to the Content
     * Verify: List Of items is present
     * <p/>
     * Step 3: Find the Person ,created in pre-condition and delete
     * Verify: The content item is deleted
     */

    @Autowired
    @Qualifier("defaultPerson")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        content = contentTypeCreationStrategy.createContentType();
    }

    public void createPerson() {
        rokuBackEndLayer.createContentType(content);
    }

    @Test(groups = {"person"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void deletePerson(final String brand) {
        this.brand = brand;

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        createPerson();

        //Step 2
        ContentPage contentPage = rokuBackEndLayer.deleteContentType(content);

        //Step 3
        contentPage.searchByType(ContentType.TVE_PERSON).searchByTitle(content.getTitle()).apply();
        Assertion.assertFalse(contentPage.isContentPresent(), "The content item still present", webDriver);
        Utilities.logInfoMessage("The content item was deleted successfully");
    }

}
