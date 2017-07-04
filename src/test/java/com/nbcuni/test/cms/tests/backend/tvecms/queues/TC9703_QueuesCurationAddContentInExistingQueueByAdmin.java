package com.nbcuni.test.cms.tests.backend.tvecms.queues;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.queue.AddQueuePage;
import com.nbcuni.test.cms.backend.tvecms.pages.queue.QueuesListingPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.SimpleUtils;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;

public class TC9703_QueuesCurationAddContentInExistingQueueByAdmin extends
        BaseAuthFlowTest {

    /**
     * TC9703 - P7: Roku CMS: Queues curation: Add content in existing queue
     *
     * Precondition: create queue.
     *
     * Step 1: Click on "edit" next to test queue
     * Step 2: Click on empty autocomplete field in "Queue Items" line 
     * Step 3: Start to type content name 
     * Step 4: Click on needed content name 
     * Step 5: Click on "Save Queue" button 
     * Step 6: Click on "edit" next to test queue
     * Step 7: Check "Queue Items" table
     *
     */

    private final String name = "AQA_" + SimpleUtils.getRandomString(5);
    private int initSize = 0;

    public void createNewQueuTC9703() {
        // Precondition
        initSize = new Random().nextInt(6) + 1;
        rokuBackEndLayer.createQueueWithRandomAssets(name, initSize);
    }

    @Test(groups = "rokuQueue", dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void addContentInQueue(final String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        createNewQueuTC9703();
        // step 1
        QueuesListingPage queueList = mainRokuAdminPage
                .openQueueContentCreationPage(brand);
        Assertion.assertTrue(queueList.isItemExist(name), "Queue with name "
                + name + " was not created");
        AddQueuePage addQueue = queueList.openEditQueuePage(name);
        // step 2-4
        addQueue.addRandomAssets(1);
        List<String> expectedItems = addQueue.getAssets();
        // step 5
        addQueue.clickSaveQueueButton();
        // Assertion.assertTrue(addQueue.isStatusMessageShown(), "Status message is not shown", webDriver);
        // step 6
        addQueue = queueList.openEditQueuePage(name);
        // step 7
        Assertion.assertEquals(addQueue.getAssets().size(), initSize + 1,
                "Number of assets in queue is wrong");
        Assertion.assertEqualsObject(addQueue.getAssets(), expectedItems,
                "Items on page are not equals to added items");
    }

}
