package com.nbcuni.test.cms.tests.backend.tvecms.tabs;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram.EditTVEProgramContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.platform.AddPlatformPage;
import com.nbcuni.test.cms.backend.tvecms.pages.platform.TvePlatformsPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.platform.PlatformEntity;
import com.nbcuni.test.cms.bussinesobjects.tvecms.platform.factory.CreateFactoryPlatform;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.pageobjectutils.tvecms.brands.RokuBrandNames;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SimpleUtils;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aliaksei_Klimenka1 on 8/1/2016.
 */
public class TC15618_Program_CheckTabsAfterCreatingPlatform extends BaseAuthFlowTest {

    /**
     * Pre-Conditions:
     * 1. Go to Roku CMS and login
     * 2. Navigate to the Platforms page.
     * 3. Check if we have not configured ios, android or roku platform anc configure it.
     * 4. Remember configured platforms.
     * Step 1: Navigate to the Content page.
     * Verify: Content Page is opened.
     * Step 2: Open random TVE Program on edit.
     * Verify: Edit TVE Program is opened. There are image tabs only for configured platforms present.
     * Step 3: Navigate to the Platforms page.
     * Step 4: Delete the platform we have created.
     * Step 5: Navigate to the Content page.
     * Verify: Content Page is opened.
     * Step 6: Open random TVE Program on edit.
     * Verify: Edit TVE Program is opened. There are image tabs only for configured platforms present.
     */


    private RokuBackEndLayer rokubackEndLayer;
    private MainRokuAdminPage mainRokuAdminPage;
    private List<String> configuredPlatforms = new ArrayList<>();
    private List<String> unconfiguredPlatforms;

    public void createPlatform(String brand) {
        Utilities.logInfoMessage("Start checking that tabs are present according configured platforms");
        //Pre-condition
        rokubackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokubackEndLayer.openAdminPage();
        configuredPlatforms = mainRokuAdminPage.openTvePlatformPage(brand).getConfiguredPlatforms();
        List<String> allPlatforms = new ArrayList<>();
        for (CmsPlatforms platform : RokuBrandNames.getBrandByName(brand).getPlatformMatcher().getPlatforms()) {
            allPlatforms.add(platform.getAppName());
        }

        unconfiguredPlatforms = SimpleUtils.getBigListStringsWitchNotExistInSmallList(allPlatforms, configuredPlatforms);

        for (String platformName : unconfiguredPlatforms) {
            mainRokuAdminPage.openAddPlatformPage(brand);
            PlatformEntity platformEntity = CreateFactoryPlatform.createDefaultPlatform();
            platformEntity.setName(platformName);
            platformEntity.setMachineName((brand + "-" + platformName).toLowerCase());
            AddPlatformPage addPlatformPage = mainRokuAdminPage.openAddPlatformPage(brand);
            addPlatformPage.createAndSavePlatform(platformEntity);
        }
    }

    @Test(groups = {"master_config"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkTabsArePresentAccordingPlatforms(final String brand) {

        createPlatform(brand);

        if (!unconfiguredPlatforms.isEmpty()) {
            //Step 1
            configuredPlatforms = mainRokuAdminPage.openTvePlatformPage(brand).getConfiguredPlatforms();
            //Step 2
            EditTVEProgramContentPage programContentPage = mainRokuAdminPage.openContentPage(brand).openRandomEditOTTProgramPage();
            List<String> tabNames = programContentPage.elementTabsGroup().getTabsNames();
            //Step 3
            softAssert.assertEquals(getPlatformsByTabNames(tabNames), configuredPlatforms,
                    "Configured platforms and existing images tabs for platforms are not matched after creating platforms",
                    "Configured platforms and existing images tabs for platforms are matched after creating platforms", webDriver);
            //Step 4
            deletePlatform();
            //Step 5
            configuredPlatforms = mainRokuAdminPage.openTvePlatformPage(brand).getConfiguredPlatforms();
            //Step 6
            programContentPage = mainRokuAdminPage.openContentPage(brand).openRandomEditOTTProgramPage();
            tabNames = programContentPage.elementTabsGroup().getTabsNames();
            softAssert.assertEquals(getPlatformsByTabNames(tabNames), configuredPlatforms,
                    "Configured platforms and existing images tabs for platforms are not matched after deleting platforms",
                    "Configured platforms and existing images tabs for platforms are matched after deleting platforms", webDriver);
        }
        softAssert.assertAll();
    }

    public void deletePlatform() {
        TvePlatformsPage tvePlatformsPage = mainRokuAdminPage.openTvePlatformPage(brand);
        for (String platformName : unconfiguredPlatforms) {
            tvePlatformsPage.clickDelete(platformName);
        }
    }

    private List<String> getPlatformsByTabNames(List<String> tabs) {
        List<String> platforms = new ArrayList<>();
        for (String tab : tabs) {
            if (tab.contains("Images")) {
                platforms.add(tab.replace("Images", "").trim());
            }
        }
        return platforms;
    }
}
