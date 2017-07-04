package com.nbcuni.test.cms.backend.chiller.pages.collections;

import com.nbcuni.test.cms.backend.chiller.block.collection.BaseGeneralCollectionInfoBlock;
import com.nbcuni.test.cms.backend.chiller.block.contenttype.AssociationsBlock;
import com.nbcuni.test.cms.backend.chiller.block.contenttype.UrlPathSettingsBlock;
import com.nbcuni.test.cms.backend.tvecms.block.ActionBlock;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.collections.Collection;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.Associations;
import com.nbcuni.test.cms.elements.Link;
import com.nbcuni.test.cms.pageobjectutils.chiller.ActionButtons;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by Alena_Aukhukova on 5/4/2016.
 */
public abstract class CollectionAbstractPage extends MainRokuAdminPage {

    @FindBy(xpath = ".//a[text()='Edit']")
    protected Link edit;
    @FindBy(id = "edit-actions")
    private ActionBlock actionBlock;
    @FindBy(id = "edit-group_associations")
    private AssociationsBlock associationsBlock;
    @FindBy(id = "edit-path")
    private UrlPathSettingsBlock slugBlock;
    @FindBy(id = "edit-group_basic")
    private BaseGeneralCollectionInfoBlock generalInfoBlock;

    public CollectionAbstractPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public BaseGeneralCollectionInfoBlock getGeneralInfoBlock() {
        onBasicTab();
        return generalInfoBlock;
    }

    public AssociationsBlock getAssociationsBlock() {
        onAssociationsTab();
        return associationsBlock;
    }

    public UrlPathSettingsBlock getSlugBlock() {
        onSlugTab();
        return slugBlock;
    }

    public ActionBlock getActionBlock() {
        return actionBlock;
    }

    public BaseGeneralCollectionInfoBlock onBasicTab() {
        generalInfoBlock.expandTab();
        return generalInfoBlock;
    }

    public AssociationsBlock onAssociationsTab() {
        associationsBlock.expandTab();
        return associationsBlock;
    }

    public UrlPathSettingsBlock onSlugTab() {
        slugBlock.expandTab();

        return slugBlock;
    }

    public CollectionAbstractPage saveAsDraft() {
        this.actionBlock.save();
        return this;
    }

    public CollectionAbstractPage publish() {
        this.actionBlock.publish();
        return this;
    }

    public CollectionAbstractPage publish(String endPoint) {
        this.actionBlock.publish(endPoint);
        return this;
    }

    public CollectionAbstractPage cancelPublish() {
        this.actionBlock.cancelPublishing();
        return this;
    }

    public CollectionsContentPage cancel() {
        this.actionBlock.cancel();
        return new CollectionsContentPage(webDriver, aid);
    }

    public CollectionAbstractPage enterAssociationData(Associations associations) {
        if (!associations.isObjectNull()) {
            getAssociationsBlock().enterAssociations(associations);
        }
        return this;
    }

    public void enterCollectionData(Collection collection) {
        getGeneralInfoBlock().enterTitle(collection.getTitle())
                .enterShortDescription(collection.getGeneralInfo().getShortDescription())
                .enterMediumDescription(collection.getGeneralInfo().getMediumDescription())
                .enterLongDescription(collection.getGeneralInfo().getLongDescription());
        setSpecificFields(collection);
        generalInfoBlock.enterItems(collection);
    }

    public Collection editCollection(Collection collection) {
        enterCollectionData(collection);
        generalInfoBlock.getItemsBlock().dragAndDropFirstToLast();
        actionBlock.save();
        collection.setSlugInfo(getSlugBlock().getSlug());
        collection.setAssociations(getAssociationsBlock().getAssociationsInfo());
        collection.getCollectionInfo().setItems(getBasicCollection().getCollectionInfo().getItems());
        return collection;
    }

    private void enterData(Collection collection) {
        enterCollectionData(collection);
        enterAssociationData(collection.getAssociations());
        getSlugBlock().enterSlug(collection.getSlugInfo());
    }

    public void createCollection(Collection collection) {
        enterData(collection);
        WebDriverUtil.getInstance(webDriver).scrollPageUp();
        actionBlock.save();
    }

    public void createAndPublishCollection(Collection collection) {
        enterData(collection);
        actionBlock.save();
        actionBlock.publish();
    }

    protected abstract Collection getBasicCollection();

    protected abstract CollectionAbstractPage setSpecificFields(Collection collection);

    protected Collection setBasicInfoFromPage(Collection collection) {
        collection.getGeneralInfo().setTitle(getGeneralInfoBlock().getTitle());
        collection.getGeneralInfo().setShortDescription(getGeneralInfoBlock().getShortDescription());
        collection.getGeneralInfo().setMediumDescription(getGeneralInfoBlock().getMediumDescription());
        collection.getGeneralInfo().setLongDescription(getGeneralInfoBlock().getLongDescription());
        collection.getCollectionInfo().setItems(getGeneralInfoBlock().getItemsBlock().getItems());
        collection.getCollectionInfo().setItemsCount(getGeneralInfoBlock().getItemsBlock().getItems().size());

        return collection;
    }

    public Collection getPageInfo() {
        Collection actualCollection = getBasicCollection();
        actualCollection.setAssociations(getAssociationsBlock().getAssociationsInfo());
        actualCollection.setSlugInfo(getSlugBlock().getSlug());
        return actualCollection;
    }

    public boolean isActionButtonsPresent(List<ActionButtons> buttons, boolean isPresent) {
        return actionBlock.isActionButtonsPresent(buttons, isPresent);
    }

}
