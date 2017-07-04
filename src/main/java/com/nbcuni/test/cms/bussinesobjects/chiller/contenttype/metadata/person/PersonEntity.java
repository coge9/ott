package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata.person;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.PersonPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata.MetaDataEntity;
import com.nbcuni.test.cms.pageobjectutils.Page;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;

/**
 * Created by Aleksandra_Lishaeva on 4/11/16.
 */
public class PersonEntity extends MetaDataEntity {

    @Override
    public Class<? extends Page> getPage() {
        return PersonPage.class;
    }

    @Override
    public ItemTypes getType() {
        return ItemTypes.PERSON;
    }

}
