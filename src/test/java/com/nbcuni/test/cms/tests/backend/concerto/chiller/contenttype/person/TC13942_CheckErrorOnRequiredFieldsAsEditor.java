package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.person;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata.person.PersonEntity;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.MessageConstants;
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
public class TC13942_CheckErrorOnRequiredFieldsAsEditor extends BaseAuthFlowTest {

    private PersonEntity personEntity = null;

    /**
     * Step 1: Go To CMS as Editor
     * Verify: Editor panel is present
     *
     * Step 2: Navigate to the Dashboard->Metadata->Add PersonFactory
     * Verify: The page with next fields is present:
     * Field:Title - "New PersonFactory"
     *
     * Step 3: Do not fill required field 'First Name' and 'save as draft'
     * Verify: Error message is present 'Please fill out required field'
     * */
    @Autowired
    @Qualifier("emptyPerson")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        content = contentTypeCreationStrategy.createContentType();
    }

    public void createPerson() {
        rokuBackEndLayer.createContentType(content);
    }

    @Test(groups = {"person"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void checkRequiredFieldAsEditor(final String brand) {
        this.brand = brand;

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPageAsEditor();

        //Step 2
        rokuBackEndLayer.createContentType(content);
        Assertion.assertTrue(mainRokuAdminPage.isErrorMessagePresent(), "The error message is not present", webDriver);
        Utilities.logInfoMessage("The error message is present on trying to save content item Person without required fields");

        Assertion.assertTrue(mainRokuAdminPage.getErrorMessage().contains(MessageConstants.MISS_REQUIRED_FIELDS), "The error message is not contain proper text", webDriver);
        Utilities.logInfoMessage("The Error message is matched with expected");
    }

}
