package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.categories;

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

import java.util.List;

/**
 *
 * @author Aliaksei_Dzmitrenka
 * Create content type
 * Step 1: Add categories to just created content type
 * Step 2: Save
 * Step 3: Make sure categories was added
 * Step 4: Deselect categories
 * Step 5: Save
 * Step 6: Make sure categories was deselected
 *
 */

public class TC14410_DeselectCategoriesFromContentType extends BaseAuthFlowTest {

    private Content content;

    @Autowired
    @Qualifier("defaultMediaGallery")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        content = contentTypeCreationStrategy.createContentType();
    }

    @Test(groups = {"categories"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void creationMediaGallery(final String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        ContentTypePage contentTypePage = rokuBackEndLayer.createContentType(content);
        contentTypePage.onAssociationsTab().enterAssociations(content.getAssociations());
        contentTypePage.saveAsDraft();
        contentTypePage.onAssociationsTab().clearCategories();
        List<String> actual = contentTypePage.onAssociationsTab().getSelectedCategories();
        softAssert.assertTrue(actual.isEmpty(), "Categories was not deselected", "Categories was deselected", webDriver);

        softAssert.assertAll();
    }
}
