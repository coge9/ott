package com.nbcuni.test.cms.tests.backend.tvecms.thumbnails.program.gradient;

import com.nbcuni.test.cms.backend.tvecms.MPXLayer;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.entities.mvpd.Instance;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CustomImageTypes;
import com.nbcuni.test.cms.pageobjectutils.tvecms.TemplateStyle;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.ImageUtil;
import com.nbcuni.test.cms.utils.thumbnails.ImageFiles;
import com.nbcuni.test.cms.utils.thumbnails.rokuimages.MpxProgramMetadata;
import com.nbcuni.test.cms.utils.thumbnails.rokuimages.ProgramOneTileShowPageSource;
import com.nbcuni.test.cms.utils.thumbnails.rokuimages.ProgramOneTileSource;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * Created by Aleksandra_Lishaeva on 3/14/16.
 */
public class TC10696_DarkGradientAppliedToProgram extends BaseAuthFlowTest {

    private String assetTitle;
    private String assetMPXId;
    private ContentPage contentPage;
    private EditTVEProgramContentPage programContentPage;

    /**
     * Step 1. Go to content page
     * Verify: User is on content page
     * <p/>
     * Step 2. Open edit page for any TVE program
     * Verify: User is on edit program page
     * <p/>
     * Step 3: Select Dark style template
     * Verify: Selected value is Dark
     * <p/>
     * Step 4: Click save
     * Verify: Changes are saved, system message is appeared
     * <p/>
     * Step 5: Go to Roku image tab for this program again
     * Verify: User is on Roku image tab
     * <p/>
     * Step 6: Verify generated images on preview pages
     * Verify: All images that should be with dark gradient
     */

    public void checkInitialThumbnails(String brand) {
        Utilities.logInfoMessage("Check Initial Thumbnails");

        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_PROGRAM);
        mpxLayer.updateSourceWithTestData(ProgramOneTileShowPageSource.getSourcePerBrand(brand));
        mpxLayer.updateProgramMetadata(MpxProgramMetadata.getTestMetadata());
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        mainRokuAdminPage.openMpxUpdaterPage(brand).updateAsset(config.getRokuMPXProgramID(brand, Instance.STAGE));
    }

    @Test(groups = {"program_thumbnails"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider")
    public void checkDarkGradientIsDefault(String brand) {

        //Pre-Condition
        checkInitialThumbnails(brand);

        //Step 1
        contentPage = mainRokuAdminPage.openContentPage(brand);

        //Step 2
        programContentPage = contentPage.openEditOTTProgramPage(Config.getInstance().getMPXTestShowName());
        programContentPage.typeHeadline(MpxProgramMetadata.getTestMetadata().getHeadLine());
        programContentPage.typeShowPageCTA(MpxProgramMetadata.getTestMetadata().getFeatureCTA());
        programContentPage.typeCTA(MpxProgramMetadata.getTestMetadata().getFeatureCTA());

        //Step 3
        programContentPage.selectTemplateStyle(TemplateStyle.DARK);

        //Step 4
        programContentPage.clickSave();
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "The status message is not shown after saving"
                , "The status message is not shown after save", webDriver);

        //Step 5
        //Getting actual result
        Map<CustomImageTypes, String> thumbnails = programContentPage.onRokuImagesTab().getOneTileProgramTwoCustomLinks();
        thumbnails.putAll(programContentPage.onRokuImagesTab().getOneTileProgramOneCustomLinks());

        //Step 6
        ImageFiles images = new ImageFiles(brand);
        for (Map.Entry<CustomImageTypes, String> entry : thumbnails.entrySet()) {
            CustomImageTypes key = entry.getKey();
            String filePath = entry.getValue();
            softAssert.assertTrue(ImageUtil.compareScreenshotAndUrl(images.getProgramOneTileThumbnail(key, TemplateStyle.DARK),
                    filePath, 100.0), "Images '" + key.getName() + "' are not equals", "Images '" + key.getName() + "' are equals");
        }

        softAssert.assertAll();
        Utilities.logInfoMessage("Test is passed");
    }


    @AfterMethod(alwaysRun = true)
    public void setAllBackTC10696() {
        Utilities.logInfoMessage("Set all back");
        contentPage = mainRokuAdminPage.openContentPage(brand);

        //Step 2
        programContentPage = contentPage.openEditOTTProgramPage(Config.getInstance().getMPXTestShowName());
        programContentPage.typeHeadline(MpxProgramMetadata.getOldMetadata().getHeadLine());
        programContentPage.typeCTA(MpxProgramMetadata.getOldMetadata().getFeatureCTA());
        programContentPage.clickSave();
        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_PROGRAM);
        mpxLayer.updateSourceWithOldData(ProgramOneTileSource.getSourcePerBrand(brand));
        mpxLayer.updateProgramMetadata(MpxProgramMetadata.getOldMetadata());
    }
}
