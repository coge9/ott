package com.nbcuni.test.cms.verification.chiller;

import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.video.VideoJson;
import com.nbcuni.test.cms.verification.Verificator;
import com.nbcuni.test.webdriver.Utilities;

import java.util.List;

/**
 * Created by Dzianis_Kulesh on 5/20/2016.
 */
public class VideoVerificator implements Verificator {

    private String uuidPattern = "[a-z,0-9]{8}-[a-z,0-9]{4}-[a-z,0-9]{4}-[a-z,0-9]{4}-[a-z,0-9]{12}";

    @Override
    public boolean verify(Object expectedObj, Object actualObj) {
        //The soft assertions is used to verify pattern for list of elements and do not use custom flag
        SoftAssert softAssert = new SoftAssert();
        Utilities.logWarningMessage("Verify objects started");

        VideoJson expected = (VideoJson) expectedObj;
        VideoJson actual = (VideoJson) actualObj;

        List<String> categoryUuids = actual.getCategories();
        for (String category : categoryUuids) {
            softAssert.assertTrue(category.matches(uuidPattern), "The category: " + category + " does not meet pattern" + uuidPattern);
        }
        for (String tag : actual.getTags()) {
            softAssert.assertTrue(tag.matches(uuidPattern), "The tag: " + tag + " does not meet pattern" + uuidPattern);
        }

        VideoJson clone = (VideoJson) actual.clone();
        clone.setCategories(expected.getCategories());
        clone.setTags(expected.getTags());
        return softAssert.getTempStatus() && clone.verifyObject(expected);
    }

    @Override
    public boolean verify(Object o) {
        throw new UnsupportedOperationException("This type of validation is not supported in current implementation");
    }
}
