package com.nbcuni.test.cms.bussinesobjects.chiller.collections.factory.curatedcollection;

import com.nbcuni.test.cms.bussinesobjects.chiller.collections.Collection;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.CollectionCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.CuratedCollection;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.ChannelReference;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.ChannelReferenceAssociations;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.TagsCreator;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.generalinfo.CollectionGeneralInfo;
import com.nbcuni.test.cms.pageobjectutils.chiller.collection.TileType;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Category;
import com.nbcuni.test.cms.utils.SimpleUtils;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Created by Alena_Aukhukova on 6/2/2016.
 */
@Component("fullCuratedCollection")
public class FullCuratedCollectionCreationStrategy implements CollectionCreationStrategy {
    @Override
    public Collection createCollection() {
        String postfix = SimpleUtils.getRandomString(6);
        String title = String.format(CollectionData.TITLE, postfix);
        CuratedCollection curatedCollection = new CuratedCollection();
        //set basic info
        curatedCollection.getGeneralInfo().setTitle(title)
                .setShortDescription(String.format(CollectionData.SHORT_DESCRIPTION, postfix))
                .setMediumDescription(String.format(CollectionData.MEDIUM_DESCRIPTION, postfix))
                .setLongDescription(String.format(CollectionData.LONG_DESCRIPTION, postfix));
        //set slug info
        curatedCollection.getSlugInfo().setSlugValue(title.toLowerCase().replaceAll(" ", "-"));
        //set data for association tab
        curatedCollection.getAssociations().addCategories(Category.randomValue().getCategory()).setTags(TagsCreator.getRandomTags(1));
        curatedCollection.getAssociations().setChannelReferenceAssociations(new ChannelReferenceAssociations().setChannelReference(new ChannelReference()));
        ((CollectionGeneralInfo)curatedCollection.getGeneralInfo()).setTileType(TileType.getRandom());
        ((CollectionGeneralInfo)curatedCollection.getGeneralInfo()).setDisplayTitle(new Random().nextBoolean());
        return curatedCollection;
    }
}
