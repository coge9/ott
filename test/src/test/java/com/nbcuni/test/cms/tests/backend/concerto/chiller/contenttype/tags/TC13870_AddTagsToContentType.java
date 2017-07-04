package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.tags;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Aliaksei_Dzmitrenka
 * Step 1: Create Media Gallery
 * Step 2: Add tags
 * Step 3: Check that tags was added
 */


public class TC13870_AddTagsToContentType extends BaseAuthFlowTest {

    private Content content;

    @Autowired
    @Qualifier("defaultMediaGallery")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        content = contentTypeCreationStrategy.createContentType();
    }

    @Test(groups = {"tags"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void creationMediaGallery(final String brand) {
        //Step 1
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        ContentTypePage contentTypePage = rokuBackEndLayer.createContentType(content);
        //Step 2
        contentTypePage.onAssociationsTab().enterTags(content.getAssociations().getTags());
        contentTypePage.saveAsDraft();

        softAssert.assertTrue(contentTypePage.isStatusMessageShown(), "Status message is not presented",
                "Status message is presented", webDriver);
        softAssert.assertFalse(contentTypePage.isErrorMessagePresent(), "Error message is presented",
                "Error message is not presented", webDriver);
        //Step 3
        softAssert.assertEquals(content.getAssociations().getTags(), contentTypePage.onAssociationsTab().getTags(), "Entered tags are not present", "All tags was added", webDriver);

        softAssert.assertAll();
    }
}
