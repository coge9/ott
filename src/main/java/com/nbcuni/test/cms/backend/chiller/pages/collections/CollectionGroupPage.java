package com.nbcuni.test.cms.backend.chiller.pages.collections;

import com.nbcuni.test.cms.bussinesobjects.chiller.collections.Collection;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.CollectionGroup;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.generalinfo.CollectionGroupGeneralInfo;
import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.TextField;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.support.FindBy;

import static com.nbcuni.test.cms.bussinesobjects.chiller.collections.CollectionInfo.CollectionType.COLLECTION_GROUP;

public class CollectionGroupPage extends CollectionAbstractPage {

    @FindBy(className = "machine-name-value")
    private TextField machineName;

    @FindBy(id = "edit-machine-name")
    private TextField machineNameEditField;

    @FindBy(className = "admin-link")
    private Button editMachineName;

    public CollectionGroupPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    @Override
    protected Collection getBasicCollection() {
        CollectionGroup collection = new CollectionGroup();
        collection.getCollectionInfo().setType(COLLECTION_GROUP);
        ((CollectionGroupGeneralInfo) collection.getGeneralInfo()).setMachineName(getMachineName());
        return setBasicInfoFromPage(collection);
    }

    @Override
    protected CollectionAbstractPage setSpecificFields(Collection collection) {
        CollectionGroup collectionGroup = (CollectionGroup) collection;
        editMachineName.click();
        machineNameEditField.enterText(((CollectionGroupGeneralInfo) collectionGroup.getGeneralInfo()).getMachineName());
        return this;
    }

    private String getMachineName() {
        onBasicTab();
        return machineName.getValue();
    }
}
