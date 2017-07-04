package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.extrenallinks;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.chiller.pages.contenttype.RolePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Aleksandra_Lishaeva on 4/18/16.
 */
public class TC14044_ExternalLinks_UserAbleToAddNewLinksForRole extends BaseAuthFlowTest {

    private ContentTypePage contentTypePage;

    /**
     * Pre-Condition:
     *Create a role item within CMS
     *Fill fields 'Link title'="Link title 1" and 'Link URL'="Link Url1"
     *
     * Step 1: Go To CMS as Editor
     * Verify: Editor Panel is present
     * <p/>
     * Step 2: Go to Content, find the Role created in pre-condition and opened on edit
     * Verify: The Edit page of Role is present
     * <p/>
     *
     * Step 3: Go to 'Role Link' tab
     * Verify: The Role edit page is opened
     *
     * Step 4: Check Tab 'Role Links' with fields: 'Link Title', 'Link URL'
     * Verify: The tab 'Links' is present
     * There are 2 input fields 'Link Title', 'Link URL'
     * <p/>
     * Step 5: Click add items
     * Verify: New fields are present
     */

    @Autowired
    @Qualifier("externalLinkRole")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObjectFirst() {
        content = contentTypeCreationStrategy.createContentType();
    }


    @Test(groups = {"role", "externalLinks"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void checkExternalLinkSet(final String brand) {

        //Step 1
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPageAsEditor();

        //Step 2-4
        contentTypePage = rokuBackEndLayer.createContentType(content);
        softAssert.assertFalse(contentTypePage.isErrorMessagePresent(), "Error message is presented",
                "Error message is not presented", webDriver);

        ContentPage contentPage = mainRokuAdminPage.openPage(ContentPage.class, brand);
        contentPage.searchByType(ContentType.TVE_ROLE).apply();

        softAssert.assertTrue(contentPage.isContentPresent(), "The search result on given Role name is empty", webDriver);
        ContentTypePage page = contentPage.clickEditLink(RolePage.class, content.getTitle());

        softAssert.assertTrue(content.verifyObject(page.getExternalLinksData())
                , "The external links info is not matched with set"
                , "The external links info are matched with expected", webDriver);
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteContentTC14044() {
        try {
            rokuBackEndLayer.deleteContentType(content);
        } catch (Throwable e) {
            Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
        }
    }
}
