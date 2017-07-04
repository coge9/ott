package com.nbcuni.test.cms.bussinesobjects.chiller.collections;

import com.nbcuni.test.cms.backend.chiller.pages.collections.CuratedCollectionPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.generalinfo.CollectionGeneralInfo;
import com.nbcuni.test.cms.pageobjectutils.Page;

import static com.nbcuni.test.cms.bussinesobjects.chiller.collections.CollectionInfo.CollectionType.CURATED_COLLECTION;

public class CuratedCollection extends Collection {

    public CuratedCollection() {
        getCollectionInfo().setType(CURATED_COLLECTION);
        generalInfo = new CollectionGeneralInfo();
    }

    @Override
    public Class<? extends Page> getPage() {
        return CuratedCollectionPage.class;
    }

    @Override
    public String getTitle() {
        return getGeneralInfo().getTitle();
    }

}
