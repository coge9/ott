package com.nbcuni.test.cms.bussinesobjects.chiller.collections.factory.collectiongroup;

import com.nbcuni.test.cms.bussinesobjects.chiller.collections.Collection;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.CollectionCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.CollectionGroup;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.factory.curatedcollection.CollectionData;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.ChannelReference;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.ChannelReferenceAssociations;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.TagsCreator;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.generalinfo.CollectionGroupGeneralInfo;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Category;
import com.nbcuni.test.cms.utils.SimpleUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created by Alena_Aukhukova on 6/2/2016.
 */
@Component("fullCollectionGroup")
public class FullCollectionGroupCreationStrategy implements CollectionCreationStrategy {
    @Override
    public Collection createCollection() {
        String postfix = SimpleUtils.getRandomString(6);
        String title = String.format(CollectionData.TITLE, postfix);
        CollectionGroup collectionGroup = new CollectionGroup();
        collectionGroup.getGeneralInfo().setTitle(title)
                .setShortDescription(String.format(CollectionData.SHORT_DESCRIPTION, postfix))
                .setMediumDescription(String.format(CollectionData.MEDIUM_DESCRIPTION, postfix))
                .setLongDescription(String.format(CollectionData.LONG_DESCRIPTION, postfix));
        collectionGroup.getSlugInfo().setSlugValue(title.toLowerCase().replaceAll(" ", "-"));
        ((CollectionGroupGeneralInfo)collectionGroup.getGeneralInfo()).setMachineName(title.replaceAll(" ", "_").toLowerCase());
        collectionGroup.getCollectionInfo().setItems(new ArrayList<String>());
        collectionGroup.getAssociations().addCategories(Category.randomValue().getCategory()).setTags(TagsCreator.getRandomTags(1));
        collectionGroup.getAssociations().setChannelReferenceAssociations(new ChannelReferenceAssociations().setChannelReference(new ChannelReference()));
        return collectionGroup;
    }
}
