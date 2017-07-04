package com.nbcuni.test.cms.verification.chiller;

import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.ContentTypeDeleteJson;
import com.nbcuni.test.cms.verification.Verificator;
import com.nbcuni.test.webdriver.Utilities;

/**
 * Created by Ivan_Karnilau on 12-Jul-16.
 */
public class CollectionDeleteJsonVerificator extends BaseVerificatorForPublishing implements Verificator<ContentTypeDeleteJson, ContentTypeDeleteJson> {

    boolean isCollectionGroup = false;

    @Override
    public boolean verify(ContentTypeDeleteJson expected, ContentTypeDeleteJson actual) {


        SoftAssert softAssert = new SoftAssert();
        Utilities.logInfoMessage("Verify objects started");

        verifyUuid(softAssert, actual.getUuid(), "uuid");

        ContentTypeDeleteJson actualClone = (ContentTypeDeleteJson) actual.clone();
        expected.setUuid(actual.getUuid());

        return softAssert.getTempStatus() && actualClone.verifyObject(expected);
    }

    @Override
    public boolean verify(ContentTypeDeleteJson o) {
        throw new UnsupportedOperationException("This type of validation is not supported in current implementation");
    }
}
