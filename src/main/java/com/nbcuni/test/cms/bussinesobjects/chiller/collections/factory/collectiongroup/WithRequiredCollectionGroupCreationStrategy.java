package com.nbcuni.test.cms.bussinesobjects.chiller.collections.factory.collectiongroup;

import com.nbcuni.test.cms.bussinesobjects.chiller.collections.Collection;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.CollectionCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.CollectionGroup;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.generalinfo.CollectionGroupGeneralInfo;
import com.nbcuni.test.cms.utils.SimpleUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component("collectionGroupWithRequiredFields")
public class WithRequiredCollectionGroupCreationStrategy implements CollectionCreationStrategy {

    @Override
    public Collection createCollection() {
        String postfix = SimpleUtils.getRandomString(6);
        CollectionGroup collectionGroup = new CollectionGroup();
        String title = String.format(CollectionGroupData.TITLE, postfix);
        collectionGroup.getGeneralInfo().setTitle(title)
                .setLongDescription("")
                .setMediumDescription("")
                .setShortDescription("");
        collectionGroup.getSlugInfo().setSlugValue(title.replaceAll(" ", "-").toLowerCase());
        ((CollectionGroupGeneralInfo) collectionGroup.getGeneralInfo()).setMachineName(title.replaceAll(" ", "_").toLowerCase());
        collectionGroup.getAssociations().setCategories(new ArrayList<>()).setTags(new ArrayList<>());
        collectionGroup.getCollectionInfo().setItems(new ArrayList<>());
        return collectionGroup;
    }

}
