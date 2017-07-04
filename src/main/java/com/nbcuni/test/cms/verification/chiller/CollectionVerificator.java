package com.nbcuni.test.cms.verification.chiller;

import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.collection.CollectionJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.collection.ListItemsJson;
import com.nbcuni.test.cms.verification.Verificator;
import com.nbcuni.test.webdriver.Utilities;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Created by Alena_Aukhukova on 6/1/2016.
 */
public class CollectionVerificator extends BaseVerificatorForPublishing implements Verificator<CollectionJson, CollectionJson> {
    boolean isCollectionGroup = false;

    @Override
    public boolean verify(CollectionJson expected, CollectionJson actual) {
        //The soft assertions is used to verify pattern for list of elements and do not use custom flag
        SoftAssert softAssert = new SoftAssert();
        Utilities.logInfoMessage("Verify objects started");

        verifyUuid(softAssert, actual.getUuid(), "uuid");

        List<String> categoryUuids = actual.getCategories();
        verifyUuids(softAssert, categoryUuids, "category");

        List<String> tagsUuid = actual.getTags();
        verifyUuids(softAssert, tagsUuid, "tag");

        List<ListItemsJson> expectedListItems = expected.getListItems();
        if (CollectionUtils.isNotEmpty(expectedListItems) && expectedListItems.get(0).getUuid() == null) {
            isCollectionGroup = true;
        }

        CollectionJson actualClone = (CollectionJson) actual.clone();
        actualClone.setCategories(expected.getCategories());
        actualClone.setTags(expected.getTags());
        //user isn't able get uuid and revision number from UI for collections
        expected.setUuid(actual.getUuid());
        expected.setRevision(actual.getRevision());

        if (isCollectionGroup) {
            List<ListItemsJson> actualCloneItems = actualClone.getListItems();
            for (ListItemsJson itemJson : actualCloneItems) {
                verifyUuid(softAssert, itemJson.getUuid(), "uuid for list items");
                itemJson.setUuid(null);
            }
        }

        return softAssert.getTempStatus() && actualClone.verifyObject(expected);
    }


    @Override
    public boolean verify(CollectionJson o) {
        throw new UnsupportedOperationException("This type of validation is not supported in current implementation");
    }
}
