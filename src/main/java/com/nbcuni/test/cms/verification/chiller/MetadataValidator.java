package com.nbcuni.test.cms.verification.chiller;

import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.metadata.MetadataJson;
import com.nbcuni.test.cms.verification.Verificator;
import com.nbcuni.test.webdriver.Utilities;

public class MetadataValidator extends BaseVerificatorForPublishing implements Verificator<MetadataJson, MetadataJson> {

    @Override
    public boolean verify(MetadataJson expected, MetadataJson actual) {
        SoftAssert softAssert = new SoftAssert();
        Utilities.logWarningMessage("Verify objects started");
        verifyUuids(softAssert, actual.getImages(), "media image");

        return softAssert.getTempStatus() && expected.verifyObject(expected);
    }

    @Override
    public boolean verify(MetadataJson o) {
        throw new UnsupportedOperationException("This type of validation is not supported in current implementation");
    }
}
