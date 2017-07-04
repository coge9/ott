package com.nbcuni.test.cms.verification.roku;

import com.nbcuni.test.cms.utils.DateUtil;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuShelfJson;
import com.nbcuni.test.cms.verification.Verificator;
import com.nbcuni.test.webdriver.Utilities;

import java.util.Date;

/**
 * Created by Alena_Aukhukova on 6/1/2016.
 */
public class ModuleSerialVerificator implements Verificator<RokuShelfJson, RokuShelfJson> {

    @Override
    public boolean verify(RokuShelfJson expected, RokuShelfJson actual) {
        //The soft assertions is used to verify pattern for list of elements and do not use custom flag
        SoftAssert softAssert = new SoftAssert();
        Utilities.logInfoMessage("Verify objects started");
        Date actualModifiedShelf = DateUtil.parseStringToUtcDate(actual.getDateModified());
        Date actualCreatedShelf = DateUtil.parseStringToDate(actual.getDateModified(), "yyyy-MM-dd'T'HH:mm:ss");
        Date actualPublishedShelf = DateUtil.parseStringToDate(actual.getDateModified(), "yyyy-MM-dd'T'HH:mm:ss");
        softAssert.assertTrue(actualModifiedShelf.getTime() - DateUtil.getCurrentDateInUtc().getTime() < 10000, "Data Modified field value is not correct. ", "Data Modified field value is correct");
        softAssert.assertTrue(actualPublishedShelf.getTime() - DateUtil.getCurrentDateInUtc().getTime() < 10000, "Data Published field value is not correct. ", "Data Published field value is correct");
        softAssert.assertTrue(actualCreatedShelf.getTime() - DateUtil.getCurrentDateInUtc().getTime() < 10000, "Data Created field value is not correct. ", "Data Created field value is correct");

        RokuShelfJson actualClone = (RokuShelfJson) actual.clone();
        actualClone.setDateCreated(expected.getDateCreated());
        actualClone.setDateModified(expected.getDateModified());
        actualClone.setDatePublished(expected.getDatePublished());
        return softAssert.getTempStatus() && actualClone.verifyObject(expected);
    }


    @Override
    public boolean verify(RokuShelfJson o1) {
        throw new UnsupportedOperationException("This type of validation is not supported in current implementation");
    }

}
