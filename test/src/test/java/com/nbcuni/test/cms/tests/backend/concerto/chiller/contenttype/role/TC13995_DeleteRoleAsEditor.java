package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.role;

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
public class TC13995_DeleteRoleAsEditor extends BaseAuthFlowTest {

    private PersonEntity personEntity = null;

    /**
     * Pre-Condition:
     * Craete a Role
     * <p/>
     * Step 1: Go To CMS as Editor
     * Verify: Editor panel is present
     * <p/>
     * Step 2: Navigate to the Content
     * Verify: List Of items is present
     * <p/>
     * Step 3: Find the Role ,created in pre-condition and delete
     * Verify: The content item is deleted
     */
    @Autowired
    @Qualifier("defaultRole")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        content = contentTypeCreationStrategy.createContentType();
    }

    public void createRole() {
        rokuBackEndLayer.createContentType(content);
    }

    @Test(groups = {"role"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void deleteRole(final String brand) {
        this.brand = brand;

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPageAsEditor();
        createRole();

        //Step 2
        ContentPage contentPage = rokuBackEndLayer.deleteContentType(content);

        //Step 3
        contentPage.searchByType(ContentType.TVE_ROLE).searchByTitle(content.getTitle()).apply();
        Assertion.assertFalse(contentPage.isContentPresent(), "The content item still present", webDriver);
        Utilities.logInfoMessage("The content item was deleted successfully");
    }

}
