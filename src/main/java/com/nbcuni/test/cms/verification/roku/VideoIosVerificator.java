package com.nbcuni.test.cms.verification.roku;

import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.video.VideoJson;
import com.nbcuni.test.cms.verification.Verificator;
import com.nbcuni.test.webdriver.Utilities;

/**
 * Created by Dzianis_Kulesh on 5/20/2016.
 */
public class VideoIosVerificator implements Verificator {

    private String uuidPattern = "[a-z,0-9]{8}-[a-z,0-9]{4}-[a-z,0-9]{4}-[a-z,0-9]{4}-[a-z,0-9]{12}";

    @Override
    public boolean verify(Object expectedObj, Object actualObj) {
        //The soft assertions is used to verify pattern for list of elements and do not use custom flag
        SoftAssert softAssert = new SoftAssert();
        Utilities.logWarningMessage("Verify objects started");

        VideoJson expected = (VideoJson) expectedObj;
        VideoJson actual = (VideoJson) actualObj;

        // validation of available dates
        Long expAvailableDate = expected.getMpxMetadata().getAvailableDate();
        Long actAvailableDate = actual.getMpxMetadata().getAvailableDate();
        softAssert.assertTrue(Math.abs(expAvailableDate - actAvailableDate) < 3600000, "Available dates are differ more than 1 hour");

        // validation of expiration dates
        Long expExpirationDate = expected.getMpxMetadata().getExpirationDate();
        Long actExpirationDate = actual.getMpxMetadata().getExpirationDate();
        softAssert.assertTrue(Math.abs(expExpirationDate - actExpirationDate) < 3600000, "Expiration dates are differ more than 1 hour");

        // validation of air dates
        Long expAirDate = expected.getMpxMetadata().getAirDate();
        Long actAirDate = actual.getMpxMetadata().getAirDate();
        softAssert.assertTrue(Math.abs(expAirDate - actAirDate) < 3600000, "Air dates are differ more than 1 hour");


        VideoJson clone = (VideoJson) actual.clone();
        clone.setCategories(expected.getCategories());
        clone.setTags(expected.getTags());
        clone.getMpxMetadata().setAvailableDate(expAvailableDate);
        clone.getMpxMetadata().setExpirationDate(expExpirationDate);
        clone.getMpxMetadata().setAirDate(expAirDate);
        return softAssert.getTempStatus() && clone.verifyObject(expected);
    }

    @Override
    public boolean verify(Object o) {
        throw new UnsupportedOperationException("This type of validation is not supported in current implementation");
    }


}
