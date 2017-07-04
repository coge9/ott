package com.nbcuni.test.cms.tests.backend.tvecms.queues;

import com.nbcuni.test.cms.backend.tvecms.pages.module.ModulesPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.tabs.DraftModuleTab;
import com.nbcuni.test.cms.backend.tvecms.pages.queue.AddQueuePage;
import com.nbcuni.test.cms.backend.tvecms.pages.queue.QueuesListingPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.SimpleUtils;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class TC9742_CheckThatAddingContentToTheQueueReflectedInShelf extends BaseAuthFlowTest {

    private final String name = "AQA_" + SimpleUtils.getRandomString(3);
    private List<String> expectedAssets;
    private DraftModuleTab shelfPage;

    /**
     * TC9742 - P7: Roku CMS: Queues curation: Check that adding content to the queue reflected in shelf
     *
     * Precondition: Create shelf with added content
     *
     * Step 1: Go to Queue list.
     * Step 2: Find queue related to shelf created in precondition.
     * Step 3: Click edit button.
     * Step 4: Verify all content in the queue.
     * Step 5: Add several elements into queue
     * Step 6: Click on "Save queue" button 
     * Step 7: Go to modules page
     * Step 8: Click edit button for shelf created in precondition
     * Step 9: Verify content in the shelf
     *
     */


    @BeforeClass(alwaysRun = true)
    public void createEmptyShelf() {
        // Precondition
        shelfPage = mainRokuAdminPage.openAddShelfPage(brand);
        shelfPage.fillTitle(name);
        shelfPage.addRandomAssets(3);
        expectedAssets = shelfPage.getAssets();
        shelfPage.clickSave();
        // Assertion.assertTrue(shelfPage.isStatusMessageShown(), "Status message is not shown", webDriver);
        for (String asset : expectedAssets) {
            Utilities.logInfoMessage("asset : - " + asset);
        }
    }

    @Test(groups = "rokuQueue")
    public void addedContentInQueueReflectedInShelf() {
        // step 1
        QueuesListingPage queueList = mainRokuAdminPage.openQueueContentCreationPage(brand);
        // step 2
        Assertion.assertTrue(queueList.isQueueExist(name), "Queue " + name + " not exists");
        // step 3
        AddQueuePage queue = queueList.openEditQueuePage(name);
        // step 4
        List<String> actualAssets = queue.getAssets();
        Assertion.assertEqualsObject(actualAssets, expectedAssets, "Asset list in queue don't equal to shelf assets");
        // step 5
        queue.addRandomAssets(3);
        expectedAssets = queue.getAssets();
        // step 6
        queue.clickSaveQueueButton();
        // Assertion.assertTrue(queue.isStatusMessageShown(), "Status message is not shown", webDriver);
        // step 7
        ModulesPage modulesPage = mainRokuAdminPage.openOttModulesPage(brand);
        // step 8
        shelfPage = modulesPage.clickEditLink(name);
        // step 9
        actualAssets = shelfPage.getAssets();
        Assertion.assertEqualsObject(actualAssets, expectedAssets, "Asset list in shelf don't equal to cheched queue");
    }

}
