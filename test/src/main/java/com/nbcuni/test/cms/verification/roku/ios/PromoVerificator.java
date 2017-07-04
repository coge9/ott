package com.nbcuni.test.cms.verification.roku.ios;

import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.concerto.PromoJson;
import com.nbcuni.test.cms.verification.Verificator;
import com.nbcuni.test.cms.verification.chiller.BaseVerificatorForPublishing;
import com.nbcuni.test.webdriver.Utilities;

/**
 * Created by Ivan_Karnilau on 8/31/2016.
 */
public class PromoVerificator extends BaseVerificatorForPublishing implements Verificator<PromoJson, PromoJson> {
    @Override
    public boolean verify(PromoJson expected, PromoJson actual) {
        //The soft assertions is used to verify pattern for list of elements and do not use custom flag
        SoftAssert softAssert = new SoftAssert();
        Utilities.logInfoMessage("Verify objects started");
        return softAssert.getTempStatus() && actual.verifyObject(expected);
    }

    @Override
    public boolean verify(PromoJson o) {
        throw new UnsupportedOperationException("This type of validation is not supported in current implementation");
    }
}
