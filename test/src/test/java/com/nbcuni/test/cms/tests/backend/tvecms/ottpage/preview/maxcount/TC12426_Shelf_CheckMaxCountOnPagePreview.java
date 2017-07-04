package com.nbcuni.test.cms.tests.backend.tvecms.ottpage.preview.maxcount;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.PreviewPage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SimpleUtils;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Aleksandra_Lishaeva on 2/5/16.
 */
public class TC12426_Shelf_CheckMaxCountOnPagePreview extends BaseAuthFlowTest {

    private PageForm pageInfo;
    private DraftModuleTab draftModuleTab;
    private MainRokuAdminPage mainRokuAdminPage;
    private String program;
    private List<String> imagesNames;
    private Shelf shelf;
    private int maxCount;

    /**
     * Pre-Condition:
     * 1 Create a Shelf module.
     * 2 Create a Page
     * 3 Assign shelf to the Page
     *
     * Step 1: Go To roku CMS as admin
     * Verify: Admin panel is present
     *
     * Step 2: Go to shelf created in precondition
     * Verify: Edit Shelf Page is present
     *
     * Step 3: Assign a Program to the Shelf
     * Verify: The Program is displayed in the content list
     *
     * Step 4: Check 'show latest' checkbox
     * Verify: Program in the content list marked as show latest
     *
     * Step 5: Set max count =2 and Save
     * Verify: The settings for the content list are saved
     *
     * Step 6: Go to the edit of Page created in precondition (with new panelizer view)
     * Verify: The Edit Page is present.There is button "Preview"
     *
     * Step 7: Click 'Preview' button
     * Verify: The Preview page is opened. The Shelf info is present
     *
     * Step 8. Check Latest episode for tea Shelf
     * Verify: There are 2 thumbnails for the latest episodes(episodes that are have more latest Updated date) related to Program in the content list of the Shelf
     */

    public void tc12426PreCondition() {
        //get random program
        ContentPage contentPage = mainRokuAdminPage.openContentPage(brand);
        program = contentPage.getRandomAsset(ContentType.TVE_PROGRAM);

        //Set maxCount based on count of related episodes for the program
        maxCount = SimpleUtils.getRandomInRangeWithLength(rokuBackEndLayer.getMPXRelatedEpisodes(program, 1).size(), 5, 1);
        imagesNames = rokuBackEndLayer.getLatestEpisodes(program, maxCount);

        // Create test Shelf module
        shelf = EntityFactory.getShelfsList().get(0);
        shelf.setAssets(Arrays.asList(program));
        draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);

        //create page and assign shelf
        draftModuleTab.createShelf(shelf);
        pageInfo = rokuBackEndLayer.createPage(CmsPlatforms.ROKU);
        EditPageWithPanelizer editPage = rokuBackEndLayer.addModule(pageInfo, shelf);
        editPage.save();
    }

    @Test(groups = {"max_count"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = false)
    public void checkShelfMaxCountOnPreview(final String brand) {
        this.brand = brand;

        //Step 1 Login
        SoftAssert softAssert = new SoftAssert();
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        //Step Pre-condition
        tc12426PreCondition();

        //Step 2
        draftModuleTab = rokuBackEndLayer.openModuleEdit(shelf);

        //Step 3-4
        draftModuleTab.checkLatestEpisodeByName(program);
        softAssert.assertTrue(draftModuleTab.isMaxCountPresent(program), "The Max Count field is not visible for :" + program, "Max Count field is present for :" + program, webDriver);

        //Step 5
        draftModuleTab.setMaxCount(program, String.valueOf(maxCount));
        draftModuleTab.clickSave();

        //Step 6
        EditPageWithPanelizer editPage = rokuBackEndLayer.openEditOttPage(pageInfo.getTitle());

        //Step 7
        PreviewPage previewPage = editPage.clickPreviewLink();

        //Step 8
        List<String> modulesNames = Arrays.asList(shelf.getTitle());
        List<String> actualModules = previewPage.getTitles();
        softAssert.assertEquals(modulesNames, actualModules, "Modules are not corrected", "Modules are corrected");
        softAssert.assertEquals(imagesNames, previewPage.getAllImagesNames(), "Images are not corrected", "Images are corrected");
        softAssert.assertAll("Test failed");
        Utilities.logInfoMessage("Test passed");
    }
}
