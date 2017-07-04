package com.nbcuni.test.cms.tests.backend.tvecms.queues;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
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

public class TC9743_CheckThatEditingContentInTheQueueReflectedInShelf extends BaseAuthFlowTest {

    private final String name = "AQA_" + SimpleUtils.getRandomString(3);
    private List<String> expectedAssets;
    private DraftModuleTab shelfPage;

    /**
     * TC9743 - P7: P7: Roku CMS: Queues curation: Check that editing content in the queue reflected in shelf
     *
     * Precondition: Create shelf with added content
     *
     * Step 1: Go to Queue list.
     * Step 2: Find queue related to shelf created in precondition.
     * Step 3: Click edit button.
     * Step 4: Verify all content in the queue.
     * Step 5: Delete all items in the queue
     * Step 6: Fill queue with new items
     * Step 7: Click on "Save queue" button 
     * Step 8: Go to modules page
     * Step 9: Click edit button for shelf created in precondition
     * Step 10: Verify content in the shelf
     *
     */

    @BeforeClass(alwaysRun = true)
    public void editedContentInQueueReflectedInShelf() {
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
    public void addQueue() {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        // step 1
        QueuesListingPage queueList = mainRokuAdminPage.openQueueContentCreationPage(brand);
        // step 2
        Assertion.assertTrue(queueList.isQueueExist(name), "Queue " + name + " not exists");
        // step 3
        AddQueuePage queue = queueList.openEditQueuePage(name);
        // step 4
        List<String> actualAssets = queue.getAssets();
        Assertion.assertEqualsObject(actualAssets, expectedAssets, "Asset list in queue don't equal to shelf assets");
        // step 5-6
        queue.fillWithRandomAssets(5);
        expectedAssets = queue.getAssets();
        // step 7
        queue.clickSaveQueueButton();
        // Assertion.assertTrue(queue.isStatusMessageShown(), "Status message is not shown", webDriver);
        // step 8
        ModulesPage modulesPage = mainRokuAdminPage.openOttModulesPage(brand);
        // step 9
        shelfPage = modulesPage.clickEditLink(name);
        // step 10
        actualAssets = shelfPage.getAssets();
        Assertion.assertEqualsObject(actualAssets, expectedAssets, "Asset list in shelf don't equal to cheched queue");
    }

}
