package com.nbcuni.test.cms.tests.backend.concerto.chiller.contenttype.mediatab;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.ContentTypeCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.media.MediaImage;
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
 *
 * Steps:
 * Step 1: create media gallery (MG)
 * Step 2: go to edit page for created MG
 * Step 3: upload several images to it (on media tab)
 * Step 4: save
 * Step 5: make sure all images was uploaded
 *
 */


public class TC14208_DeleteImagesFromContentType extends BaseAuthFlowTest {

    private static final int imageCount = 2;
    private Content content;
    @Autowired
    @Qualifier("defaultMediaGallery")
    private ContentTypeCreationStrategy contentTypeCreationStrategy;

    @BeforeMethod(alwaysRun = true)
    public void initBusinessObject() {
        content = contentTypeCreationStrategy.createContentType();
    }

    @Test(groups = {"media_tab"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandChillerDataProvider")
    public void creationMediaGallery(String brand) {
        //Step 1
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        ContentTypePage contentTypePage = rokuBackEndLayer.createContentType(content);
        //Step 2-4
        contentTypePage.onMediaTab().onMediaBlock().addImagesFromLibrary(imageCount);
        contentTypePage.onMediaTab().onMediaBlock().selectRandomUsageForAllImages();
        contentTypePage.saveAsDraft();
        //Step 5
        contentTypePage.onMediaTab().onMediaBlock().removeRandomElement();
        List<MediaImage> expected = contentTypePage.onMediaTab().onMediaBlock().getMediaImages();
        contentTypePage.saveAsDraft();
        List<MediaImage> actual = contentTypePage.onMediaTab().onMediaBlock().getMediaImages();
        //Step 6
        softAssert.assertTrue(expected.size() != imageCount, "Count of images is wrong: expected: [" + imageCount + "], actual [" + expected.size() + "]", "Images count is correct", webDriver);
        softAssert.assertEquals(expected, actual, "All images was NOT added", "All images was added", webDriver);

        softAssert.assertAll();
    }
}
