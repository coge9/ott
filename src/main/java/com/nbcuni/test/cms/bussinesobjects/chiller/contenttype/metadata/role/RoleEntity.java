package com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata.role;

import com.nbcuni.test.cms.backend.chiller.pages.contenttype.RolePage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.metadata.MetaDataEntity;
import com.nbcuni.test.cms.pageobjectutils.Page;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;

/**
 * Created by Aleksandra_Lishaeva on 4/11/16.
 */
public class RoleEntity extends MetaDataEntity {

    @Override
    public Class<? extends Page> getPage() {
        return RolePage.class;
    }

    @Override
    public ItemTypes getType() {
        return ItemTypes.ROLE;
    }

}
