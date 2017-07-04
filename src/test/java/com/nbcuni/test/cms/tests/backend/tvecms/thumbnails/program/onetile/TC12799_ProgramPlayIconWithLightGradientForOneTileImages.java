package com.nbcuni.test.cms.tests.backend.tvecms.thumbnails.program.onetile;

import com.nbcuni.test.cms.backend.tvecms.MPXLayer;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
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
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.thumbnails.ImageFiles;
import com.nbcuni.test.cms.utils.thumbnails.rokuimages.MpxProgramMetadata;
import com.nbcuni.test.cms.utils.thumbnails.rokuimages.ProgramOneTileShowPageSource;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Aliaksei_Dzmitrenka
 * Precondition:
 *  Set old image with 1973x918 size.
 *  Set tested image with 5x5 size.
 * Steps:
 *  Open AQA_OTT_PROGRAM edit page
 *  Open RokuImages tab
 *  Open preview for 1 tile program source
 *  Get url of images
 *  Open preview for 1 tile source
 *  Get url of images
 *  Compare expected and actual images
 *
 */


public class TC12799_ProgramPlayIconWithLightGradientForOneTileImages extends BaseAuthFlowTest {


    private MainRokuAdminPage mainRokuAdminPage;
    private EditTVEProgramContentPage programContentPage;


    public void checkInitialThumbnails(String brand) {
        Utilities.logInfoMessage("Check Initial Thumbnails");

        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_PROGRAM);
        mpxLayer.updateSourceWithOldData(ProgramOneTileShowPageSource.getSourcePerBrand(brand));
        mpxLayer.updateProgramMetadata(MpxProgramMetadata.getOldMetadata());
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        mainRokuAdminPage.openMpxUpdaterPage(brand).updateAsset(config.getRokuMPXProgramID(brand, Instance.STAGE));
    }

    @Test(groups = {"program_thumbnails"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkOneTileThumbnailIsGeneratedForProgram(String brand) {
        Utilities.logInfoMessage("Check that 1tile thumbnail is generated for OTT program");
        checkInitialThumbnails(brand);
        SoftAssert softAssert = new SoftAssert();
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        programContentPage = contentPage.openEditOTTProgramPage(Config.getInstance().getMPXTestShowName());
        programContentPage.typeHeadline(MpxProgramMetadata.getOldMetadata().getHeadLine());
        programContentPage.typeCTA(MpxProgramMetadata.getOldMetadata().getFeatureCTA());
        programContentPage.selectTemplateStyle(TemplateStyle.LIGHT);
        programContentPage.clickSave();

        //Getting actual result
        Map<CustomImageTypes, String> thumbnails = programContentPage.onRokuImagesTab().getOneTileProgramTwoCustomLinks();
        thumbnails.putAll(programContentPage.onRokuImagesTab().getOneTileProgramOneCustomLinks());

        //Comporation
        ImageFiles images = new ImageFiles(brand);
        for (Entry<CustomImageTypes, String> entry : thumbnails.entrySet()) {
            CustomImageTypes key = entry.getKey();
            String value = entry.getValue();
            softAssert.assertTrue(ImageUtil.compareScreenshotAndUrl(images.getProgramOneTileThumbnail(key, TemplateStyle.LIGHT),
                    value, 100.0), "Images '" + key.getName() + "' are not equals", "Images '" + key.getName() + "' are equals");
        }

        softAssert.assertAll();
    }

}
