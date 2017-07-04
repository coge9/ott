package com.nbcuni.test.cms.backend.chiller.pages.collections;

import com.nbcuni.test.cms.bussinesobjects.chiller.collections.Collection;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.CuratedCollection;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.generalinfo.CollectionGeneralInfo;
import com.nbcuni.test.cms.elements.CheckBox;
import com.nbcuni.test.cms.elements.DropDownList;
import com.nbcuni.test.cms.pageobjectutils.chiller.collection.TileType;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.support.FindBy;

import static com.nbcuni.test.cms.bussinesobjects.chiller.collections.CollectionInfo.CollectionType.CURATED_COLLECTION;

public class CuratedCollectionPage extends CollectionAbstractPage {

    @FindBy(id = "edit-field-tile-type-und")
    private DropDownList tileType;

    @FindBy(id = "edit-field-display-title-und")
    private CheckBox displayTitle;


    public CuratedCollectionPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    @Override
    protected Collection getBasicCollection() {
        CuratedCollection collection = new CuratedCollection();
        collection.getCollectionInfo().setType(CURATED_COLLECTION);
        ((CollectionGeneralInfo)collection.getGeneralInfo()).setTileType(getTileType());
        ((CollectionGeneralInfo)collection.getGeneralInfo()).setDisplayTitle(getDisplayTitle());
        return setBasicInfoFromPage(collection);
    }

    @Override
    protected CollectionAbstractPage setSpecificFields(Collection collection) {
        CuratedCollection curatedCollection = (CuratedCollection) collection;
        tileType.selectFromDropDown(((CollectionGeneralInfo)curatedCollection.getGeneralInfo()).getTileType().getName());
        displayTitle.selectStatus(((CollectionGeneralInfo)curatedCollection.getGeneralInfo()).getDisplayTitle());
        return this;
    }

    private TileType getTileType() {
        onBasicTab();
        return TileType.getTileTypeByName(tileType.getSelectedValue());
    }

    private boolean getDisplayTitle() {
        onBasicTab();
        return displayTitle.isSelected();
    }
}
