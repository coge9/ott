package com.nbcuni.test.cms.verification.chiller;

import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.event.EventJson;
import com.nbcuni.test.cms.verification.Verificator;
import com.nbcuni.test.webdriver.Utilities;

import java.util.List;

/**
 * Created by Dzianis_Kulesh on 5/20/2016.
 */
public class EventVerificator extends BaseVerificatorForPublishing implements Verificator<EventJson, EventJson> {

    private String uuidPattern = "[a-z,0-9]{8}-[a-z,0-9]{4}-[a-z,0-9]{4}-[a-z,0-9]{4}-[a-z,0-9]{12}";

    @Override
    public boolean verify(EventJson expected, EventJson actual) {
        //The soft assertions is used to verify pattern for list of elements and do not use custom flag
        SoftAssert softAssert = new SoftAssert();
        Utilities.logWarningMessage("Verify objects started");

        String expectedAirTime = expected.getAirTimes();
        String actualAirTime = actual.getAirTimes();
        softAssert.assertEquals(expectedAirTime, actualAirTime, "The airTime: " + expectedAirTime +
                " does not matched actual: " + actualAirTime, "The actual airTime matched with expected");

        verifyUuid(softAssert, actual.getCastCredit(), "cast and credit");
        expected.setCastCredit(actual.getCastCredit());

        List<String> categoryUuids = actual.getCategories();
        for (String category : categoryUuids) {
            softAssert.assertTrue(category.matches(uuidPattern), "The category: " + category + " does not meet pattern" + uuidPattern);
        }
        for (String tag : actual.getTags()) {
            softAssert.assertTrue(tag.matches(uuidPattern), "The tag: " + tag + " does not meet pattern" + uuidPattern);
        }

        EventJson clone = (EventJson) actual.clone();
        clone.setCategories(expected.getCategories());
        clone.setTags(expected.getTags());
        clone.setAirTimes(expected.getAirTimes());
        return softAssert.getTempStatus() && clone.verifyObject(expected);
    }

    @Override
    public boolean verify(EventJson o) {
        throw new UnsupportedOperationException("This type of validation is not supported in current implementation");
    }
}
