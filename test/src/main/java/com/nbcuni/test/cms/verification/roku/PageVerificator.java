package com.nbcuni.test.cms.verification.roku;

import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuPageJson;
import com.nbcuni.test.cms.verification.Verificator;
import com.nbcuni.test.cms.verification.chiller.BaseVerificatorForPublishing;
import com.nbcuni.test.webdriver.Utilities;

/**
 * Created by Aleksandra Lishaeva on 2/7/2017.
 */
public class PageVerificator extends BaseVerificatorForPublishing implements Verificator<RokuPageJson, RokuPageJson> {

    @Override
    public boolean verify(RokuPageJson expected, RokuPageJson actual) {
        SoftAssert softAssert = new SoftAssert();
        Utilities.logInfoMessage("Verify Page objects started");

        RokuPageJson actualClone = (RokuPageJson) actual.clone();
        actualClone.setDateModified(expected.getDateModified());
        return softAssert.getTempStatus() && actualClone.verifyObject(expected);
    }


    @Override
    public boolean verify(RokuPageJson o) {
        throw new UnsupportedOperationException("This type of validation is not supported in current implementation");
    }
}
