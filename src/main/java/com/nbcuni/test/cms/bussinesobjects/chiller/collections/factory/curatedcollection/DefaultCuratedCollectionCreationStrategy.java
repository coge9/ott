package com.nbcuni.test.cms.bussinesobjects.chiller.collections.factory.curatedcollection;

import com.nbcuni.test.cms.bussinesobjects.chiller.collections.Collection;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.CollectionCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.CuratedCollection;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.generalinfo.CollectionGeneralInfo;
import com.nbcuni.test.cms.pageobjectutils.chiller.collection.TileType;
import com.nbcuni.test.cms.utils.SimpleUtils;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component("defaultCuratedCollection")
public class DefaultCuratedCollectionCreationStrategy implements CollectionCreationStrategy {

    @Override
    public Collection createCollection() {
        String postfix = SimpleUtils.getRandomString(6);
        CuratedCollection curatedCollection = new CuratedCollection();
        String title = String.format(CollectionData.TITLE, postfix);
        curatedCollection.getGeneralInfo().setTitle(title)
                .setShortDescription(String.format(CollectionData.SHORT_DESCRIPTION, postfix))
                .setMediumDescription(String.format(CollectionData.MEDIUM_DESCRIPTION, postfix))
                .setLongDescription(String.format(CollectionData.LONG_DESCRIPTION, postfix));
        curatedCollection.getCollectionInfo().setItemsCount(2);
        curatedCollection.getSlugInfo().setSlugValue(title.toLowerCase().replaceAll(" ", "-"));
        ((CollectionGeneralInfo) curatedCollection.getGeneralInfo()).setTileType(TileType.getRandom());
        ((CollectionGeneralInfo) curatedCollection.getGeneralInfo()).setDisplayTitle(new Random().nextBoolean());
        return curatedCollection;
    }
}
