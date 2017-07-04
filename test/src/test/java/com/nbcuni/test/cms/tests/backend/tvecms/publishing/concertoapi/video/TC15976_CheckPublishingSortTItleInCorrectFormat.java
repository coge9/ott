package com.nbcuni.test.cms.tests.backend.tvecms.publishing.concertoapi.video;

import com.nbcuni.test.cms.backend.tvecms.MPXLayer;
import com.nbcuni.test.cms.backend.tvecms.RokuBackEndLayer;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.video.GlobalVideoEntity;
import com.nbcuni.test.cms.core.BaseAuthFlowTest;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.tests.backend.tvecms.StaticBrandsProvider;
import com.nbcuni.test.cms.utils.SimpleUtils;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuVideoJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes.SerialApiPublishingTypes;
import com.nbcuni.test.cms.utils.transformers.VideoJsonTransformer;
import com.nbcuni.test.cms.verification.roku.RokuVideoJsonVerificator;
import com.nbcuni.test.webdriver.Utilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Aliaksei_Klimenka1 on 9/1/2016.
 */
public class TC15976_CheckPublishingSortTItleInCorrectFormat extends BaseAuthFlowTest {

    /**
     * Pre-conditon:
     * 1.Prepare list of Video names witch have specific words we must delete or replace.
     * 2.Go to Content. There is a Video within MPX.
     * 3.Get MPX id of the Video
     * Steps:
     * 1. Go to MPX and find the Video by ID
     * Verify: Video is present.
     * 2. Update Video title with word from our list created in pre-conditon and click "save".
     * Verify: title value is updated.
     * 3. Return to CMS.
     * Verify: CMS panel is present.
     * 4. Update video by id with MPX Updater
     * Verify: The Update passed successfully.
     *         Status message is present.
     *         Status message contain a link on Publish log.
     * 5. Get post request and check sortTitle field.
     * Verify: sortTitle field is correct. All specific words are replaced/deleted.
     */


    private List<String> specificWords = new ArrayList<>();
    private String oldTitle;
    private String newTitle;
    private String mpxID;


    @BeforeMethod(alwaysRun = true)
    public void initBuisnessObject() {
        specificWords.add("AN ");
        specificWords.add("The ");
        specificWords.add("a ");
        specificWords.add("Episode Seven ");
        newTitle = specificWords.get(SimpleUtils.getRandomInt(specificWords.size()));
    }


    @Test(groups = {"video_publishing"}, dataProviderClass = StaticBrandsProvider.class, dataProvider = "brandHasIOSDataProvider", enabled = false)
    public void testIngestedVideoPublishing(String brand) {

        rokuBackEndLayer = new RokuBackEndLayer(webDriver, brand, aid);
        mainRokuAdminPage = rokuBackEndLayer.openAdminPage();

        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_VIDEO);
        oldTitle = mpxLayer.getAssetTitle();
        mpxID = mpxLayer.getAssetID();
        newTitle = newTitle + oldTitle;
        mpxLayer.updateAssetTitle(newTitle);

        rokuBackEndLayer.updateMPXAssetById(mpxID);
        String url = mainRokuAdminPage.getLogURL(brand);
        softAssert.assertTrue(mainRokuAdminPage.isStatusMessageShown(), "The status message is not shown after publishing", "The status message is shown after publishing", webDriver);
        //publishing

        GlobalVideoEntity globalVideoEntity = rokuBackEndLayer.getVideo(mpxLayer.getAssetTitle());
        RokuVideoJson expectedVideoJson = VideoJsonTransformer.forSerialApi(globalVideoEntity);

        //Get Actual Post Request
        RokuVideoJson actualVideoJson = requestHelper.getSingleParsedResponse(url, SerialApiPublishingTypes.VIDEO);
        softAssert.assertEquals(expectedVideoJson, actualVideoJson, "The actual data is not matched with expected", "The JSON data is matched", new RokuVideoJsonVerificator());
        softAssert.assertAll();
        Utilities.logInfoMessage("Test passed");
    }

    @AfterMethod(alwaysRun = true)
    public void renameVideoOnMPX() {
        MPXLayer mpxLayer = new MPXLayer(brand, ContentType.TVE_VIDEO);
        mpxLayer.updateAssetTitle(oldTitle);
    }

}
