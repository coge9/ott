package com.nbcuni.test.cms.bussinesobjects.chiller.collections.factory.curatedcollection;

import com.nbcuni.test.cms.bussinesobjects.chiller.collections.Collection;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.CollectionCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.CuratedCollection;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.generalinfo.CollectionGeneralInfo;
import com.nbcuni.test.cms.pageobjectutils.chiller.collection.TileType;
import com.nbcuni.test.cms.utils.SimpleUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component("curatedCollectionWithRequiredFields")
public class WithRequiredCuratedCollectionCreationStrategy implements CollectionCreationStrategy {

    @Override
    public Collection createCollection() {
        String postfix = SimpleUtils.getRandomString(6);
        CuratedCollection curatedCollection = new CuratedCollection();
        String title = String.format(CollectionData.TITLE, postfix);
        curatedCollection.getGeneralInfo().setTitle(title)
                .setLongDescription("")
                .setMediumDescription("")
                .setShortDescription("");
        curatedCollection.getSlugInfo().setSlugValue(title.replaceAll(" ", "-").toLowerCase());
        curatedCollection.getAssociations().setCategories(new ArrayList<>()).setTags(new ArrayList<>());
        curatedCollection.getCollectionInfo().setItems(new ArrayList<>());
        ((CollectionGeneralInfo)curatedCollection.getGeneralInfo()).setTileType(TileType.getRandom());
        return curatedCollection;
    }

}
