package com.nbcuni.test.cms.tests.backend.tvecms.permission;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.module.ModulesPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Slug;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.database.EntityFactory;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by Aleksandra_Lishaeva on 1/18/17.
 */
public class TC17171_EditorCouldNotAddContentToLatestEpisodeCustomModule extends BaseAuthFlowTest {

    public final String LATEST_EPISODES = "latestEpisodes";
    private Shelf shelf;

    /**
     * Pre-Conditions:
     * Go to CMS as Admin
     * Create a Custom module  with alias 'latestEpisodes' and save.
     * Log out
     * <p>
     * Steps:
     * 1.Go to CMS as Editor
     * Verify: User is logged in to CMS with Editor permission.
     * <p>
     * 2.Go to Module list page
     * Verify: There a feature carousel module with alias 'latestEpisodes '
     * <p>
     * 3.Open module on Edit and add content
     * Verify: Edit module page is opened
     * It's impossible to add any content to the module
     * with alias 'latestEpisodes'.
     * Link 'Add content' is not available
     * <p>
     * 4.Repeat the same test's steps with 'Feature carousel' module type.
     * Verify: It's impossible to add any content to the module with alias 'latestEpisodes'.
     * Link 'Add content' is not available
     */

    @Test(groups = {"permissions", "regression"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void checkNotEmptyLatestEpisodeCustomModule(String brand) {

        //pre-condition
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);

        //Pre-condition Step 1
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        ModulesPage modulesPage = mainRokuAdminPage.openOttModulesPage(brand);
        modulesPage.searchByAlias(LATEST_EPISODES);
        shelf = (Shelf) EntityFactory.getShelfsList().get(0).setAssetsCount(0);
        if (modulesPage.getTable().getNumberOfRows() == 0) {
            shelf.setSlug(new Slug().setAutoSlug(false).setSlugValue(LATEST_EPISODES));
            shelf = (Shelf) rokuBackEndLayer.createModule(shelf);
        } else {
            modulesPage = mainRokuAdminPage.openOttModulesPage(brand);
            shelf.setTitle(modulesPage.searchByAlias(LATEST_EPISODES).getTitle());
        }

        mainRokuAdminPage.logOut(brand);

        mainRokuAdminPage = rokuBackEndLayer.openAdminPageAsEditor();
        modulesPage = mainRokuAdminPage.openOttModulesPage(brand);
        DraftModuleTab draftModuleTab = modulesPage.clickEditLink(shelf.getTitle());

        Assertion.assertFalse(draftModuleTab.isAddAnotherItemPresent(), "There is link for Add content", webDriver);
        mainRokuAdminPage.logOut(brand);
        Utilities.logInfoMessage("There no link 'Add content' for Editor");

    }

    @AfterMethod(alwaysRun = true)
    public void deleteOttPageAndShelfTC16996() {
        try {
            rokuBackEndLayer.openAdminPage();
            rokuBackEndLayer.deleteModule(shelf.getTitle());
        } catch (Throwable e) {
            Utilities.logWarningMessage("Unable to perform tear down method - " + e.getMessage());
        }
    }
}
