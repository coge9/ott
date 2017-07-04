package com.nbcuni.test.cms.verification.roku;

import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.collection.CollectionJson;
import com.nbcuni.test.cms.verification.Verificator;
import com.nbcuni.test.cms.verification.chiller.BaseVerificatorForPublishing;
import com.nbcuni.test.webdriver.Utilities;

import java.util.List;

/**
 * Created by Alena_Aukhukova on 6/1/2016.
 */
public class ModuleIosVerificator extends BaseVerificatorForPublishing implements Verificator<CollectionJson, CollectionJson> {
    boolean isCollectionGroup = false;

    @Override
    public boolean verify(CollectionJson expected, CollectionJson actual) {
        //The soft assertions is used to verify pattern for list of elements and do not use custom flag
        SoftAssert softAssert = new SoftAssert();
        Utilities.logInfoMessage("Verify objects started");

        List<String> categoryUuids = actual.getCategories();
        verifyUuids(softAssert, categoryUuids, "category");

        List<String> tagsUuid = actual.getTags();
        verifyUuids(softAssert, tagsUuid, "tag");

        CollectionJson actualClone = (CollectionJson) actual.clone();
        actualClone.setCategories(expected.getCategories());
        actualClone.setTags(expected.getTags());
        return softAssert.getTempStatus() && actualClone.verifyObject(expected);
    }


    @Override
    public boolean verify(CollectionJson o) {
        throw new UnsupportedOperationException("This type of validation is not supported in current implementation");
    }
}
