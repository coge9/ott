package com.nbcuni.test.cms.tests.backend.tvecms.queues;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.queue.AddQueuePage;
import com.nbcuni.test.cms.backend.tvecms.pages.queue.QueuesListingPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.SimpleUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;

public class TC9747_QueuesCurationAddMoreThanOneContentItemInExistingQueueAsEditor
        extends BaseAuthFlowTest {

    /**
     * TC9704 - P7: Roku CMS: Queues curation: Add more than one content item in existing queue
     *
     * Precondition: create queue.
     *
     * Step 1: Click on "edit" next to test queue
     * Step 2: Click on empty autocomplete field in "Queue Items" line and select item.
     * Step 3: Click on "Add another item" button 
     * Step 4: Click on empty autocomplete field in "Queue Items" line and select item.
     * Step 5: Repeat steps 3..4 several times 
     * Step 6: Click on "Save Queue" button 
     * Step 7: Click on "edit" next to test queue
     * Step 8: Check "Queue Items" table
     *
     */

    private final String name = "AQA_" + SimpleUtils.getRandomString(5);
    private int initSize = 0;

    @BeforeClass(alwaysRun = true)
    public void createNewQueuTC9704() {
        // Precondition
        initSize = new Random().nextInt(7);
        if (initSize == 0) {
            initSize = 3;
        }
        rokuBackEndLayer.createQueueWithRandomAssets(name, initSize);
    }

    @Test(groups = "rokuQueue")
    public void addMoreThanOneItemToQueueByEditor() {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPageAsEditor();
        // step 1
        QueuesListingPage queueList = mainRokuAdminPage
                .openQueueContentCreationPage(brand);
        Assertion.assertTrue(queueList.isItemExist(name), "Queue with name "
                + name + " was not created");
        AddQueuePage addQueue = queueList.openEditQueuePage(name);
        // step 2-5
        int numberOfAssets = new Random().nextInt(7);
        if (numberOfAssets == 0) {
            numberOfAssets = 3;
        }
        addQueue.addRandomAssets(numberOfAssets);
        List<String> expectedItems = addQueue.getAssets();
        // step 6
        addQueue.clickSaveQueueButton();
        Assertion.assertTrue(addQueue.isStatusMessageShown(), "Status message is not shown", webDriver);
        // step 7
        addQueue = queueList.openEditQueuePage(name);
        // step 8
        Assertion.assertEquals(addQueue.getAssets().size(), initSize
                + numberOfAssets, "Number of assets in queue is wrong");
        Assertion.assertEqualsObject(addQueue.getAssets(), expectedItems,
                "Items on page are not equals to added items");
    }

}
