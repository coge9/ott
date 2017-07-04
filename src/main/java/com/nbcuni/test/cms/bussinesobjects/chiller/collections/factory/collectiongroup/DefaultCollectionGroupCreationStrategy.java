package com.nbcuni.test.cms.bussinesobjects.chiller.collections.factory.collectiongroup;

import com.nbcuni.test.cms.bussinesobjects.chiller.collections.Collection;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.CollectionCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.CollectionGroup;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.factory.curatedcollection.CollectionData;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.generalinfo.CollectionGroupGeneralInfo;
import com.nbcuni.test.cms.utils.SimpleUtils;
import org.springframework.stereotype.Component;

@Component("defaultCollectionGroup")
public class DefaultCollectionGroupCreationStrategy implements CollectionCreationStrategy {

    @Override
    public Collection createCollection() {
        String postfix = SimpleUtils.getRandomString(6);
        String title = String.format(CollectionData.TITLE, postfix);
        CollectionGroup collectionGroup = new CollectionGroup();
        collectionGroup.getGeneralInfo().setTitle(title)
                .setShortDescription(String.format(CollectionData.SHORT_DESCRIPTION, postfix))
                .setMediumDescription(String.format(CollectionData.MEDIUM_DESCRIPTION, postfix))
                .setLongDescription(String.format(CollectionData.LONG_DESCRIPTION, postfix));
        collectionGroup.getSlugInfo().setSlugValue(title.replaceAll(" ", "-").toLowerCase());
        collectionGroup.getCollectionInfo().setItemsCount(2);
        ((CollectionGroupGeneralInfo)collectionGroup.getGeneralInfo()).setMachineName(title.replaceAll(" ", "_").toLowerCase());

        return collectionGroup;
    }

}
