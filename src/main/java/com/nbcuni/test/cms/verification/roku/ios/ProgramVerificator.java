package com.nbcuni.test.cms.verification.roku.ios;

import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuProgramJson;
import com.nbcuni.test.cms.verification.Verificator;
import com.nbcuni.test.cms.verification.chiller.BaseVerificatorForPublishing;
import com.nbcuni.test.webdriver.Utilities;

public class ProgramVerificator extends BaseVerificatorForPublishing implements Verificator<RokuProgramJson, RokuProgramJson> {

    @Override
    public boolean verify(RokuProgramJson expected, RokuProgramJson actual) {
        //The soft assertions is used to verify pattern for list of elements and do not use custom flag
        SoftAssert softAssert = new SoftAssert();
        Utilities.logInfoMessage("Verify objects started");

        RokuProgramJson actualClone = (RokuProgramJson) actual.clone();

        softAssert.assertContainsAll(expected.getImages(), actual.getImages(), "The image styles are not equals",
                "The image styles are equals");
        actualClone.setImages(expected.getImages());
        return softAssert.assertEquals(expected, actualClone,
                "actual result is not matched with expected",
                "actual result is matched with expected").getTempStatus();
    }

    @Override
    public boolean verify(RokuProgramJson o) {
        throw new UnsupportedOperationException("This type of validation is not supported in current implementation");
    }
}