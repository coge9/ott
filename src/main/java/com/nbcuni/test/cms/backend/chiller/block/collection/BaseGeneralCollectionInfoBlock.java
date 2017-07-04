package com.nbcuni.test.cms.backend.chiller.block.collection;

import com.nbcuni.test.cms.backend.chiller.block.contenttype.BaseGeneralInfoBlock;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.Collection;
import org.apache.commons.collections.CollectionUtils;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class BaseGeneralCollectionInfoBlock extends BaseGeneralInfoBlock {
    @FindBy(id = "edit-field-qt-curated-collection")
    private ItemsBlock items;

    public ItemsBlock getItemsBlock() {
        return items;
    }

    public BaseGeneralCollectionInfoBlock enterItems(Collection collection) {
        List<String> itemsForAdding = collection.getCollectionInfo().getItems();
        Integer itemsCount = collection.getCollectionInfo().getItemsCount();
        if (!CollectionUtils.isEmpty(itemsForAdding)) {
            items.addItems(itemsForAdding);
        } else {
            if (itemsCount != null) {
                items.addItems(itemsCount);
            }
        }
        return this;
    }

}
