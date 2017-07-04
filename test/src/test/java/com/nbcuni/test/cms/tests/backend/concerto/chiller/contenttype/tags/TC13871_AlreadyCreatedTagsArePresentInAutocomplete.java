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
 * Step 3: Open content page again
 * Step 4: Check that already created tags are present in autocomplete
 */


public class TC13871_AlreadyCreatedTagsArePresentInAutocomplete extends BaseAuthFlowTest {

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
        contentTypePage.onAssociationsTab().enterAssociations(content.getAssociations());
        contentTypePage.saveAsDraft();

        softAssert.assertTrue(contentTypePage.isStatusMessageShown(), "Status message is not presented",
                "Status message is presented", webDriver);
        softAssert.assertFalse(contentTypePage.isErrorMessagePresent(), "Error message is presented",
                "Error message is not presented", webDriver);

        //Step 3
        rokuBackEndLayer.openContentTypePage(content);
        //Step 4
        softAssert.assertTrue(contentTypePage.onAssociationsTab().isTagsPresentInAutocomplete(content.getAssociations().getTags()),
                "Some tags aren't present in tags list field", webDriver);

        softAssert.assertAll();
    }
}
