package com.nbcuni.test.cms.tests.backend.tvecms.platform;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.platform.AddPlatformPage;
import com.nbcuni.test.cms.backend.tvecms.pages.platform.TvePlatformsPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.platform.PlatformEntity;
import com.nbcuni.test.cms.bussinesobjects.tvecms.platform.factory.CreateFactoryPlatform;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by Alena_Aukhukova on 11/30/2015.
 */
public class TC10000_CheckEditExistedPlatform extends BaseAuthFlowTest {

    private PlatformEntity platformEntity;
    private MainRokuAdminPage mainRokuAdminPage;
    private TvePlatformsPage tvePlatformsPage;
    private RokuBackEndLayer backEndLayer;
    private AddPlatformPage addPlatformPage;

    /**
     * Step 1: Go to Roku CMS and login
     * Step 2: Go to OTT -> Platforms
     * Step 3: Click "Add platforms" link
     * Step 4: Enter all mandatory fields
     * Step 5: Click "Save platform" button
     * Verify: Error Message isn't Present. Status message: "Platform "[platform title]" has been saved."
     * Step 6: Open created platform for edit
     * Step 7: Update name, machine name, viewports
     * Step 8: Click "Save platform" button
     * Verify: Error Message isn't Present. Status message: "Platform "[platform title]" has been saved."
     * Step 9: Open updated platform for edit
     * Verify: Name is updated; Machine name is updated; viewports is updated
     */
    @Test(groups = {"master_config"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkTc10000(final String brand) {
        Utilities.logInfoMessage("Check that user is able to edit platform");
        //Step 1 Go to Roku CMS and login
        backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = backEndLayer.openAdminPage();
        //Step 2-5
        platformEntity = CreateFactoryPlatform.createDefaultPlatform();
        addPlatformPage = mainRokuAdminPage.openAddPlatformPage(brand);
        tvePlatformsPage = addPlatformPage.createAndSavePlatform(platformEntity);

        softAssert.assertFalse(tvePlatformsPage.isErrorMessagePresent(), "Error Message is Present");
        softAssert.assertEquals(tvePlatformsPage.getExpectedMessageAfterSaving(platformEntity.getName()), tvePlatformsPage.getStatusMessage(), "Status message text");
        //6-8

        PageForm defaultHomepage = backEndLayer.createPage(platformEntity.getName());
        PageForm defaultAllShowsPage = backEndLayer.createPage(platformEntity.getName());

        platformEntity.setDefaultHomepage(defaultHomepage);
        platformEntity.setDefaultAllShowsPage(defaultAllShowsPage);

        tvePlatformsPage = mainRokuAdminPage.openTvePlatformPage(brand);
        addPlatformPage = tvePlatformsPage.clickEditPlatform(platformEntity.getName());
        platformEntity = CreateFactoryPlatform.updateCreatedPlatform(platformEntity);
        tvePlatformsPage = addPlatformPage.editPlatformAndSave(platformEntity);

        softAssert.assertFalse(tvePlatformsPage.isErrorMessagePresent(), "Error Message is Present");
        softAssert.assertEquals(tvePlatformsPage.getExpectedMessageAfterSaving(platformEntity.getName()), tvePlatformsPage.getStatusMessage(), "Status message text");
        //Step 9
        tvePlatformsPage.clickEditPlatform(platformEntity.getName());
        softAssert.assertEquals(platformEntity, addPlatformPage.getPlatformInfo(), "Platform values");
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteApp() {
        backEndLayer.deleteCreatedPlatform(platformEntity.getName());
        backEndLayer.deleteOttPage(platformEntity.getDefaultHomepage().getTitle());
        backEndLayer.deleteOttPage(platformEntity.getDefaultAllShowsPage().getTitle());
    }
}
