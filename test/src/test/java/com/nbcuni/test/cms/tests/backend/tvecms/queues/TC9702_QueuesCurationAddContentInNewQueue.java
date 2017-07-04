package com.nbcuni.test.cms.tests.backend.tvecms.queues;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.queue.AddQueuePage;
import com.nbcuni.test.cms.backend.tvecms.pages.queue.QueuesListingPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.SimpleUtils;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;

public class TC9702_QueuesCurationAddContentInNewQueue extends BaseAuthFlowTest {

    /**
     * TC9702 - P7: Roku CMS: Queues curation: Add content in new queue
     *
     *
     * Step 1: Click on "Add queue" 
     * Step 2: Fill "Title" field 
     * Step 3: Click on autocomplete field in "Queue Items" line 
     * Step 4: Start to type content name 
     * Step 5: Click on needed content name 
     * Step 6: Click on "Save queue" button 
     * Step 7: Click on "edit" next to test queue
     * Step 8: Check "Queue Items" table 
     *
     */

    private final String queueType = "Add Program &amp; Video";
    private final String name = "AQA_" + SimpleUtils.getRandomString(5);

    @Test(groups = "rokuQueue")
    public void createNewQueu() {
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        // step 1
        QueuesListingPage queueList = mainRokuAdminPage.openQueueContentCreationPage(brand);
        AddQueuePage addQueue = queueList.clickAddQueueByType(queueType);
        // step 2
        addQueue.enterTitle(name);
        // step 3-5
        int numberOfAssets = new Random().nextInt(6) + 1;
        addQueue.fillWithRandomAssets(numberOfAssets);
        List<String> expectedItems = addQueue.getAssets();
        // step 6
        addQueue.clickSaveQueueButton();
        // Assertion.assertTrue(addQueue.isStatusMessageShown(), "Status message is not shown", webDriver);
        Assertion.assertTrue(queueList.isItemExist(name), "Queue with name " + name + " was not created");
        // step 7
        addQueue = queueList.openEditQueuePage(name);
        // step 8
        Assertion.assertEqualsObject(addQueue.getAssets(), expectedItems, "Items on page are not equals to added items");
    }
}
