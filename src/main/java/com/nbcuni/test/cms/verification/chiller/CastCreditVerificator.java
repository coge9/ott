package com.nbcuni.test.cms.verification.chiller;

import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.castcredit.CastCreditJson;
import com.nbcuni.test.cms.verification.Verificator;
import com.nbcuni.test.webdriver.Utilities;

/**
 * Created by Dzianis_Kulesh on 5/20/2016.
 */
public class CastCreditVerificator extends BaseVerificatorForPublishing implements Verificator {

    @Override
    public boolean verify(Object expectedObj, Object actualObj) {
        //The soft assertions is used to verify pattern for list of elements and do not use custom flag
        SoftAssert softAssert = new SoftAssert();
        Utilities.logWarningMessage("Verify objects started");

        CastCreditJson expected = (CastCreditJson) expectedObj;
        CastCreditJson actual = (CastCreditJson) actualObj;
        verifyUuid(softAssert, actual.getUuid(), "cast and credit");
        verifyRevision(softAssert, String.valueOf(actual.getRevision()));
        softAssert.assertTrue(expected.getProgram().equals(actual.getProgram()), "The Channel reference checking fail",
                "The Channel reference checking is past");

        CastCreditJson clone = (CastCreditJson) actual.clone();
        clone.setRevision(expected.getRevision());
        clone.setUuid(expected.getUuid());
        return softAssert.getTempStatus() && clone.verifyObject(expected);
    }

    @Override
    public boolean verify(Object o) {
        throw new UnsupportedOperationException("This type of validation is not supported in current implementation");
    }
}
