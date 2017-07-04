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
public class TC13933_CreatePersonWithReqFieldsAsEditor extends BaseAuthFlowTest {

    private PersonEntity personEntity = null;

    /**
     * Step 1: Go To CMS as Editor
     * Verify: Editor panel is present
     *
     * Step 2: Navigate to the Dashboard->Metadata->Add Person
     * Verify: The page with next fields is present:
     * Field:Title - "New Person"
     *
     * Step 3: Fill required field 'First Name' and 'save as draft'
     * Verify: Title is filled.
     * The new content type PersonFactory is created in draft state within CMS
     * */
    @Autowired
    @Qualifier("defaultPerson")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        content = contentTypeCreationStrategy.createContentType();
    }

    @Test(groups = {"person"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void createPersonWithRequiredFields(final String brand) {
        this.brand = brand;

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPageAsEditor();

        //Step 2
        rokuBackEndLayer.createContentType(content);

        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        contentPage.searchByType(ContentType.TVE_PERSON).searchByTitle(content.getTitle()).apply();
        Assertion.assertTrue(contentPage.isContentPresent(), "The search result on given Person name is empty");
        Utilities.logInfoMessage("The new item Person is present within Content page");
    }

    @AfterMethod(alwaysRun = true)
    public void deletePersonTC13933() {
        rokuBackEndLayer.deleteContentType(content);
    }
}
