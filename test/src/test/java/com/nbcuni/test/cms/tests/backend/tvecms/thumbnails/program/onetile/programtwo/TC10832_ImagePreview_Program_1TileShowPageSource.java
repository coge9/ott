package com.nbcuni.test.cms.tests.backend.tvecms.thumbnails.program.onetile.programtwo;

import com.nbcuni.test.cms.backend.tvecms.MPXLayer;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.programsources.rokusources.Program1TileShowPageSource;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.thumbnails.rokuimages.ProgramOneTileShowPageSource;
import org.testng.annotations.Test;

/**
 * TC10832
 *
 * Step 1: Go to Roku CMS as Editor
 *
 * Step 2: Go to content page
 *
 * Step 3: Open edit program page for any program
 *
 * Step 4: Go to image tab
 *
 * Step 5: Click on "Image preview" link below 1 Tile show page source image
 *
 * Validation: Verify thumbnails
 * Expected: Preview page should contains relevant custom thumbnail styles only:
 * -1tile_program_2
 */

public class TC10832_ImagePreview_Program_1TileShowPageSource extends BaseAuthFlowTest {

    private static final String PROGRAM_TITLE = Config.getInstance().getMPXTestShowName();
    private static final String INITIAL_IMAGE = "1973x918_initial.jpeg";

    @Test(groups = {"program_thumbnails"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkImagePreview(String brand) {

        this.brand = brand;
        SoftAssert softAssert = new SoftAssert();

        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_PROGRAM);
        mpxLayer.updateImageDimensionsByTitle(INITIAL_IMAGE, ProgramOneTileShowPageSource.getSourcePerBrand(brand).getWidth(),
                ProgramOneTileShowPageSource.getSourcePerBrand(brand).getHeight());

        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        mainRokuAdminPage.runCron(brand);

        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        EditTVEProgramContentPage programContentPage = contentPage.openEditOTTProgramPage(PROGRAM_TITLE);

        programContentPage.onRokuImagesTab().elementOneTitleArea().verifyThumbnailsPresent(
                new Program1TileShowPageSource(), softAssert);

        softAssert.assertAll();
    }
}
