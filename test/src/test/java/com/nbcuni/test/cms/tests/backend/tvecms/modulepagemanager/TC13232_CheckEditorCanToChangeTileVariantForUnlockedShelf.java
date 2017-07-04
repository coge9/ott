package com.nbcuni.test.cms.tests.backend.tvecms.modulepagemanager;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.block.PanelizerContentBlock;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.panelizer.EditPageWithPanelizer;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.bussinesobjects.tvecms.page.PageForm;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by Ivan_Karnilau on 29-Feb-16.
 */

/**
 * TC13232
 *
 * Pre-Conditions:
 * 1. Go to Roku CMS as Admin
 * 2. Create new Shelf [shelfname]
 * 3. Create two new pages [pagename1] and [pagename2]
 * 4. add [shelfname]  in [pagename1] and [pagename2]
 *
 * Steps:
 * 1. Go to /admin/ott/pages
 * "Pages" menu is appeared
 * 2. Open edit page for [pagename1]
 * Edit page is opened
 * 3. Make sure that shelf [shelfname] is unlocked
 * Shelf is unlocked
 * 4. Click edit for [shelfname]
 * Tile variant radio buttons is enabled
 * 5. Logout and login in CMS as Editor
 * User should be logged in
 * Validation    Open edit page for [shelfname]
 * Tile variant radio buttons is enabled
 */
public class TC13232_CheckEditorCanToChangeTileVariantForUnlockedShelf extends BaseAuthFlowTest {
    private MainRokuAdminPage mainRokuAdminPage;
    private PageForm pageForm1;
    private PageForm pageForm2;
    private Shelf shelf1;
    private DraftModuleTab draftModuleTab;


    @Test(groups = {"roku_page", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasRokuDataProvider", enabled = true)
    public void checkEditorCanNotToChangeTileVariantForUnlockedShelf(final String brand) {
        Utilities.logInfoMessage("Check that editor can not to change tile variant for unlocked Shelf");
        SoftAssert softAssert = new SoftAssert();

        //Pre-Conditions:
        RokuBackEndLayer backEndLayer = new RokuBackEndLayer(webDriver, brand, aid);

        //Step 1
        mainRokuAdminPage = backEndLayer.openAdminPage();

        //Step 2-3
        pageForm1 = backEndLayer.createPage();
        pageForm2 = backEndLayer.createPage();

        shelf1 = EntityFactory.getShelfsList().get(0);
        draftModuleTab = mainRokuAdminPage.openAddShelfPage(brand);
        draftModuleTab.createShelf(shelf1);

        //Step 4
        backEndLayer.addModule(pageForm1, shelf1).save();
        backEndLayer.addModule(pageForm2, shelf1).save();

        //Steps:
        //Step 1-2
        EditPageWithPanelizer editPage = backEndLayer.openEditOttPage(pageForm1.getTitle());
        PanelizerContentBlock contentBlock = editPage.getModuleBlock(shelf1.getTitle());

        //Step 3
        contentBlock.unlockModule();

        editPage.save();

        //Step 4
        DraftModuleTab draftModuleTab = backEndLayer.openModuleEdit(shelf1);

        softAssert.assertTrue(draftModuleTab.isTileVariantDsiplayed(), "Tile variant is disable for Admin",
                "Tile variant is enable for Admin");
        softAssert.assertTrue(draftModuleTab.isAliasEnable(), "Alias is disable for Admin",
                "Alias is enable for Admin");

        //Step 5
        mainRokuAdminPage.logOut(brand);
        mainRokuAdminPage = backEndLayer.openAdminPageAsEditor();

        //Validation
        draftModuleTab = backEndLayer.openModuleEdit(shelf1);

        softAssert.assertTrue(draftModuleTab.isTileVariantDsiplayed(), "Tile variant is disable for Editor",
                "Tile variant is enable for Editor");
        softAssert.assertTrue(draftModuleTab.isAliasEnable(), "Alias is disable for Editor",
                "Alias is enable for Editor");

        mainRokuAdminPage.logOut(brand);
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    private void deletePageShelves() {
        try {
            rokuBackEndLayer.openAdminPage();
            rokuBackEndLayer.deleteOttPage(pageForm1.getTitle());
            rokuBackEndLayer.deleteOttPage(pageForm2.getTitle());
            rokuBackEndLayer.deleteModule(shelf1.getTitle());
        } catch (Exception e) {
            Utilities.logInfoMessage("Couldn't delete the objects by reason: " + e.getMessage());
        }
    }
}
