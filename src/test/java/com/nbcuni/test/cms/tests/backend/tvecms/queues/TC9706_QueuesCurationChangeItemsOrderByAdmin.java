package com.nbcuni.test.cms.tests.backend.tvecms.queues;

import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.backend.tvecms.pages.queue.AddQueuePage;
import com.nbcuni.test.cms.backend.tvecms.pages.queue.QueuesListingPage;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.Assertion;
import com.nbcuni.test.cms.utils.SimpleUtils;
import com.nbcuni.test.cms.utils.SoftAssert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;

public class TC9706_QueuesCurationChangeItemsOrderByAdmin extends BaseAuthFlowTest {


    /**
     * TC9706 - P7: Roku CMS: Queues curation: Change items order
     *
     * Precondition: create queue.
     *
     * Step 1: Click on "edit" next to test queue
     * Step 2: Change items order in "Queue List" with drag/drop functionality
     * Step 3: Click on "Save queue" button 
     * Step 4: Click on "edit" next to test queue 
     * Step 5: Check "Queue Items" table
     *
     */


    private final String name = "AQA_" + SimpleUtils.getRandomString(5);
    List<String> expectedItems;
    private int initSize = 0;

    public void createNewQueuTC9706() {
        // Precondition
        initSize = new Random().nextInt(6) + 1;
        expectedItems = rokuBackEndLayer.createQueueWithRandomAssets(name, initSize);
    }

    @Test(groups = {"rokuQueue"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandDataProvider", enabled = true)
    public void changeItemOrderByAdmin(String brand) {
        this.brand = brand;
        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();
        createNewQueuTC9706();
        QueuesListingPage queueList = mainRokuAdminPage
                .openQueueContentCreationPage(brand);
        Assertion.assertTrue(queueList.isItemExist(name), "Queue with name "
                + name + " was not created");
        // Step 1
        AddQueuePage addQueue = queueList.openEditQueuePage(name);
        // Step 2
        addQueue.dragAndDropElement(2, 3);
        String secondItem = addQueue.getFieldValueByIndex(2);
        Assertion.assertEquals(secondItem, expectedItems.get(2), "Item was not moved by Drag and Drop");
        expectedItems = addQueue.getAssets();
        // Step 3
        addQueue.clickSaveQueueButton();
        // Assertion.assertTrue(addQueue.isStatusMessageShown(), "Status message is not shown", webDriver);
        // Step 4
        addQueue = queueList.openEditQueuePage(name);
        // Step 5
        List<String> actualItems = addQueue.getAssets();
        Assertion.assertEquals(actualItems.size(), initSize,
                "Number of assets in queue is wrong");
        SoftAssert softAssert = new SoftAssert();
        for (int i = 0; i < expectedItems.size(); i++) {
            softAssert.assertEquals(actualItems.get(i), expectedItems.get(i),
                    "Items by index " + i + " is wrong");
        }
        softAssert.assertAll();
    }
}
