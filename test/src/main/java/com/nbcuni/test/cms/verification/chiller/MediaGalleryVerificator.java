package com.nbcuni.test.cms.verification.chiller;

import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.mediagallery.MediaGalleryJson;
import com.nbcuni.test.cms.verification.Verificator;
import com.nbcuni.test.webdriver.Utilities;

import java.util.List;

/**
 * Created by Dzianis_Kulesh on 5/20/2016.
 */
public class MediaGalleryVerificator extends BaseVerificatorForPublishing implements Verificator<Object, Object> {

    @Override
    public boolean verify(Object expectedObj, Object actualObj) {
        //The soft assertions is used to verify pattern for list of elements and do not use custom flag
        SoftAssert softAssert = new SoftAssert();
        Utilities.logWarningMessage("Verify objects started");

        MediaGalleryJson expected = (MediaGalleryJson) expectedObj;
        MediaGalleryJson actual = (MediaGalleryJson) actualObj;
        List<String> categoryUuids = actual.getCategories();
        verifyUuids(softAssert, categoryUuids, "category");

        List<String> tagsUuid = actual.getTags();
        verifyUuids(softAssert, tagsUuid, "tag");

        Utilities.logInfoMessage("Data verification started: Expected data: [" + expected.getDatePublished() + "]" + " Actual data: [" + actual.getDatePublished() + "]");
        softAssert.assertTrue(Math.abs(expected.getDatePublished() - actual.getDatePublished()) < 2000,
                "The actual published time :" + actual.getDatePublished() + " does not matched with expected: " + expected.getDatePublished()
                , "The actual published time is matched with expected");

        for (int i = 0; i < actual.getMediaGalleryItems().size(); i++) {
            String actualCaption = actual.getMediaGalleryItems().get(i).getCaption();
            if (!actualCaption.isEmpty()) {
                expected.getMediaGalleryItems().get(i).setCaption(actualCaption);
            }
        }

        MediaGalleryJson clone = (MediaGalleryJson) actual.clone();
        clone.setCategories(expected.getCategories());
        clone.setTags(expected.getTags());
        clone.setDatePublished(expected.getDatePublished());
        return softAssert.getTempStatus() && clone.verifyObject(expected);
    }

    @Override
    public boolean verify(Object o) {
        throw new UnsupportedOperationException("This type of validation is not supported in current implementation");
    }
}
