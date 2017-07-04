package com.nbcuni.test.cms.bussinesobjects.chiller.collections;

import com.nbcuni.test.cms.backend.chiller.pages.collections.CollectionGroupPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.generalinfo.CollectionGroupGeneralInfo;
import com.nbcuni.test.cms.pageobjectutils.Page;

import static com.nbcuni.test.cms.bussinesobjects.chiller.collections.CollectionInfo.CollectionType.COLLECTION_GROUP;

public class CollectionGroup extends Collection {

    public CollectionGroup() {
        getCollectionInfo().setType(COLLECTION_GROUP);
        generalInfo = new CollectionGroupGeneralInfo();
    }

    @Override
    public Class<? extends Page> getPage() {
        return CollectionGroupPage.class;
    }

    @Override
    public String getTitle() {
        return getGeneralInfo().getTitle();
    }

}
