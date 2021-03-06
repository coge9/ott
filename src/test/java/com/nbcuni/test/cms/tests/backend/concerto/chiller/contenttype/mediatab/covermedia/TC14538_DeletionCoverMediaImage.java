package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.mediatab.covermedia;

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
 *
 * @author Aliaksei_Dzmitrenka
 *
 * Steps:
 * Step 1: create media gallery (MG)
 * Step 2: go to edit page for created MG
 * Step 3: upload cover image (on media tab)
 * Step 4: save
 * Step 5: make sure image was uploaded
 *
 */


public class TC14538_DeletionCoverMediaImage extends BaseAuthFlowTest {

    private Content content;

    @Autowired
    @Qualifier("defaultMediaGallery")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        content = contentTypeCreationStrategy.createContentType();
    }

    @Test(groups = {"media_tab"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider", enabled = false)
    public void creationMediaGallery(final String brand) {
        //Step 1
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        ContentTypePage contentTypePage = rokuBackEndLayer.createContentType(content);
        //Step 2-4
        contentTypePage.onMediaTab().onCoverMediaBlock().uploadImage();
        contentTypePage.saveAsDraft();
        //Step 5
        contentTypePage.onMediaTab().onCoverMediaBlock().removeCoverImage();
        contentTypePage.saveAsDraft();

        softAssert.assertFalse(contentTypePage.onMediaTab().onCoverMediaBlock().isCoverImagePresent(), "Image was NOT deleted", "Image was deleted", webDriver);

        softAssert.assertAll();
    }
}
