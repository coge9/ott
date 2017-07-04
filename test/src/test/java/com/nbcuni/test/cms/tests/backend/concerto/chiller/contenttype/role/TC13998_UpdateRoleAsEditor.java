package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.role;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.RolePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Aleksandra_Lishaeva on 4/11/16.
 */
public class TC13998_UpdateRoleAsEditor extends BaseAuthFlowTest {

    private RolePage rolePage = null;
    private Content contentExpected = null;

    /**
     * Pre-Condition:
     * Craete a Role
     * <p/>
     * Step 1: Go To CMS as editor
     * Verify: Editor panel is present
     * <p/>
     * Step 2: Navigate to the Content
     * Verify: List Of items is present
     * <p/>
     * Step 3: Find the Role ,created in pre-condition and edit
     * Verify: The Edit Form of Role is opened
     * <p/>
     * Step 4: Update fields below and save:
     * Title - "New Role" Prefix (new value)
     * First Name (new value)
     * Middle Name (new value)
     * Last Name (new value)
     * Suffix (new value)
     * Bio (new value)
     * Verify:The Role item is updated by values above
     */

    @Autowired
    @Qualifier("defaultRole")
    private ContentTypeCreationStrategy contentTypeCreationStrategyFirst;
    @Autowired
    @Qualifier("withAllFieldsRole")
    private ContentTypeCreationStrategy contentTypeCreationStrategySecondary;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObjectFirst() {
        content = contentTypeCreationStrategyFirst.createContentType();
    }

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObjectSecond() {
        contentExpected = contentTypeCreationStrategySecondary.createContentType();
    }

    public void createRole() {
        rokuBackEndLayer.createContentType(content);
    }

    @Test(groups = {"role"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void updateRole(final String brand) {
        this.brand = brand;

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPageAsEditor();
        createRole();

        //Step 2-4
        rolePage = (RolePage) rokuBackEndLayer.updateContent(content, contentExpected);
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "The status message is not shown after updates"
                , "The status message is shown after updated saving", webDriver);

        softAssert.assertTrue(contentExpected.verifyObject(rolePage.getMetadataInfo()), "The Role entities are not matched after updates saving"
                , "The Role entities, actual and expected are matched after updates saving", webDriver);
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteRoleTC13998() {
        try {
            rokuBackEndLayer.deleteContentType(contentExpected);
        } catch (Throwable e) {
            Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
        }
    }
}
