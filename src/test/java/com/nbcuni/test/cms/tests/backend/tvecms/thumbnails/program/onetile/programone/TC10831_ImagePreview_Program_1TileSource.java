package com.nbcuni.test.cms.tests.backend.tvecms.thumbnails.program.onetile.programone;

import com.nbcuni.test.cms.backend.tvecms.MPXLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.programsources.rokusources.Program1TileSource;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.thumbnails.rokuimages.ProgramOneTileSource;
import org.testng.annotations.Test;

/**
 * TC10831
 *
 * Step 1: Go to Roku CMS as Editor
 *
 * Step 2: Go to content page
 *
 * Step 3: Open edit program page for any program
 *
 * Step 4: Go to home page feature carousel tab
 *
 * Step 5: Click on "Image preview" link below 1 Tile source image
 *
 * Validation: Verify thumbnails
 * Expected: Preview page should contains relevant custom thumbnail styles only:
 * -1tile_program_1
 */
public class TC10831_ImagePreview_Program_1TileSource extends BaseAuthFlowTest {

    private static final String PROGRAM_TITLE = Config.getInstance().getMPXTestShowName();
    private static final String INITIAL_IMAGE = "1096x510__757928.jpg";

    @Test(groups = {"program_thumbnails"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkImagePreview(String brand) {
        this.brand = brand;
        SoftAssert softAssert = new SoftAssert();

        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_PROGRAM);
        mpxLayer.updateImageDimensionsByTitle(INITIAL_IMAGE, ProgramOneTileSource.getSourcePerBrand(brand).getWidth(),
                ProgramOneTileSource.getSourcePerBrand(brand).getHeight());

        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        mainRokuAdminPage.runCron(brand);

        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        EditTVEProgramContentPage programContentPage = contentPage.openEditOTTProgramPage(PROGRAM_TITLE);

        programContentPage.onRokuImagesTab().elementOneTileArea().verifyThumbnailsPresent(
                new Program1TileSource(), softAssert);

        softAssert.assertAll();
    }
}
