package com.nbcuni.test.cms.backend.tvecms.pages.module.tabs;

import com.nbcuni.test.cms.backend.tvecms.block.modules.contentlist.ModulesContentList;
import com.nbcuni.test.cms.backend.tvecms.pages.content.MediaContentPage;
import com.nbcuni.test.cms.backend.tvecms.pages.module.MainModulePage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Slug;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featurecarousel.FeatureCarouselForm;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.featureshow.FeatureShowModule;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.shelf.Shelf;
import com.nbcuni.test.cms.elements.*;
import com.nbcuni.test.cms.pageobjectutils.MessageConstants;
import com.nbcuni.test.cms.pageobjectutils.entities.content.PublishState;
import com.nbcuni.test.cms.pageobjectutils.tvecms.VoidShelf;
import com.nbcuni.test.cms.pageobjectutils.tvecms.shelf.ShelfType;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.DateUtil;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.jsonclasses.GlobalNodeJson;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DraftModuleTab extends MainModulePage {

    private PublishBlock publishBlock;

    @FindBy(xpath = ".//*[contains(@id,'edit-title')]")
    private TextField title;

    @FindBy(xpath = ".//*[contains(@id,'edit-display-title')]")
    private CheckBox displayTitle;

    @FindBy(xpath = ".//*[contains(@id,'edit-field-ott-module-tile-variant')]")
    private RadioButtonsGroup tileVariant;

    @FindBy(xpath = "//a[@title='Add items']")
    private Link addAnotherItem;

    @FindBy(xpath = ".//input[contains(@id,'edit-actions-submit')]|//input[contains(@id,'edit-submit')]")
    private Button saveModule;

    @FindBy(xpath = "//div[@id='ott-list-table-wrapper']")
    private ModulesContentList contentList;

    @FindBy(xpath = "//*[@id='edit-field-ott-module-data']//textarea")
    private TextField dataField;

    @FindBy(xpath = ".//*[@id='edit-assignment-info']")
    private Label assignmentBlock;

    @FindBy(xpath = "//div[@id='ott-list-table-wrapper']")
    private DragAndDrop dragAndDrop;

    @FindBy(id = "edit-advanced-options")
    private ExpanderWithLink advancedOptions;

    @FindBy(xpath = ".//*[contains(@id,'edit-alias')]")
    private TextField alias;

    private WaitUtils waitUtils;

    public DraftModuleTab(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
        waitUtils = WaitUtils.perform(webDriver);
        publishBlock = new PublishBlock(webDriver);
    }

    public ModulesContentList elementContentList() {
        return contentList;
    }

    public Shelf createShelf(Shelf shelf, PublishState... state) {
        updateShelf(shelf, state);
        clickSave();
        shelf.setId(getShelfId());
        shelf.setListId(getShelfId());
        shelf.setRevision(getRevisionEntity());
        shelf.setUuid(getUuidEntity());
        return shelf;
    }

    public Shelf updateShelf(Shelf shelf, PublishState... state) {
        Utilities.logInfoMessage("Set Shelf " + shelf.getTitle());
        if (shelf.getTitle() != null) {
            fillTitle(shelf.getTitle());
        }
        if (shelf.isDisplayTitle() != null) {
            if (shelf.getDisplayTitle()) {
                setDisplayTitle(shelf.isDisplayTitle());
            }
        } else {
            shelf.setDisplayTitle(isDisplayTitle());
        }
        if (!shelf.getSlug().isObjectNull()) {
            fillAlias(shelf.getSlug());
        }

        if (shelf.getTileVariant() != null) {
            checkTileVariant(shelf.getTileVariant());
        }
        if (shelf.getAssets() != null) {
            if (shelf.getAssets().isEmpty()) {
                try {
                    addRandomAssets(shelf.getAssetsCount());
                    shelf.setAssets(getAssets());
                } catch (Exception e) {
                    Utilities.logSevereMessage("The setting an random file method doesn't work");
                }
            } else {
                addAssets(shelf.getAssets());
                if (shelf.getContentEnabled() != null) {
                    setEnabledStatus(shelf.getContentEnabled());
                }
            }

        }
        shelf.setType(ShelfType.SHELF.getName());
        shelf.setCreatedDate(DateUtil.getCurrentDateInUtcString());
        shelf.setModifiedDate(DateUtil.getCurrentDateInUtcString());
        return shelf;
    }

    /**
     * Method fills existing object or create and fills new
     * @param shelfs - shelfs
     * @return shelf info
     */
    public Shelf getShelfInfo(Shelf... shelfs) {
        waitUtils.waitForThrobberNotPresent(15);
        Shelf shelf = new Shelf();
        if (shelfs.length > 0) {
            shelf = shelfs[0];
        }
        shelf.setTitle(getTitle());
        shelf.setDisplayTitle(isDisplayTitle());
        shelf.setSlug(getAlias());
        shelf.setTitleVariant(getCheckedTitleVariant());
        shelf.setAssets(getAssets());
        shelf.setAssetsCount(shelf.getAssets().size());
        shelf.setId(getShelfId());
        shelf.setRevision(getRevisionEntity());
        shelf.setUuid(getUuidEntity());
        return shelf;
    }

    public void createVoidShelf(VoidShelf shelf) {
        Utilities.logInfoMessage("Create Void Shelf " + shelf.getTitle());
        if (shelf.getTitle() != null) {
            fillTitle(shelf.getTitle());
        }
        if (shelf.getAlias() != null) {
            // void shelf do not use
        }
        if (shelf.getSettings() != null) {
            fillSettings(shelf.getSettings());
        }
        clickSave();
        shelf.setType(ShelfType.PLACEHOLDER.getName());
        shelf.setId(getShelfId());
    }

    public VoidShelf getVoidShelfInfo() {
        VoidShelf shelf = new VoidShelf();
        shelf.setTitle(getTitle());
        shelf.setAlias(getAlias());
        shelf.setSettings(getSettings());
        return shelf;
    }

    public FeatureCarouselForm createFeatureCarousel(FeatureCarouselForm featureCarousel) {
        Utilities.logInfoMessage("Create Feature Carousel " + featureCarousel.getTitle());
        if (featureCarousel.getTitle() != null) {
            fillTitle(featureCarousel.getTitle());
        }
        featureCarousel.setDisplayTitle(false);

        if (!featureCarousel.getSlug().isObjectNull()) {
            fillAlias(featureCarousel.getSlug());
        }

        if (featureCarousel.getAssets() != null) {
            if (!featureCarousel.getAssets().isEmpty()) {
                addAssets(featureCarousel.getAssets());
                if (featureCarousel.getContentEnabled() != null) {
                    setEnabledStatus(featureCarousel.getContentEnabled());
                }
            } else {
                try {
                    featureCarousel.setAssets(Arrays.asList(addRandomAsset()));
                } catch (Exception e) {
                    Utilities.logSevereMessage("The setting an random file method doesn't work");
                    Utilities.logSevereMessageThenFail(Utilities.convertStackTraceToString(e));
                }
            }
        }
        clickSave();
        WaitUtils.perform(webDriver).waitForPageLoad();
        featureCarousel.setAssetsCount(featureCarousel.getAssets().size());
        featureCarousel.setCreatedDate(DateUtil.getCurrentDateInUtcString());
        featureCarousel.setModifiedDate(DateUtil.getCurrentDateInUtcString());
        featureCarousel.setId(getShelfId());
        featureCarousel.setType(ShelfType.FEATURE_CAROUSEL.getName());
        featureCarousel.setRevision(getRevisionEntity());
        featureCarousel.setUuid(getUuidEntity());
        featureCarousel.setSlug(getAlias());
        return featureCarousel;
    }

    /**
     * Method fills existing object or create and fills new
     * @param featureCarouselForm - info of featurecarousel
     * @return feature carousel form - featureCarouselForm
     */
    public FeatureCarouselForm getFeatureCarouselInfo(FeatureCarouselForm... featureCarouselForm) {
        FeatureCarouselForm featureCarousel = new FeatureCarouselForm();
        if (featureCarouselForm.length > 0) {
            featureCarousel = featureCarouselForm[0];
        }
        featureCarousel.setTitle(getTitle());
        featureCarousel.setSlug(getAlias());
        featureCarousel.setAssets(getAssets());
        featureCarousel.setAssetsCount(featureCarousel.getAssets().size());
        featureCarousel.setId(getShelfId());
        featureCarousel.setType(ShelfType.FEATURE_CAROUSEL.getName());
        featureCarousel.setRevision(getRevisionEntity());
        featureCarousel.setUuid(getUuidEntity());
        return featureCarousel;
    }

    public FeatureShowModule createFeatureShowModule(FeatureShowModule featureShowModule) {
        List<String> assetValues = featureShowModule.getAssets();
        title.updateValue(featureShowModule.getTitle());
        displayTitle.selectStatus(featureShowModule.isDisplayTitle());
        if (!featureShowModule.getSlug().isObjectNull()) {
            fillAlias(featureShowModule.getSlug());
        }
        if (CollectionUtils.isNotEmpty(assetValues)) {
            if ("random".equals(assetValues.get(0))) {
                String randomAssetTitle = addRandomAsset();
                assetValues.set(0, randomAssetTitle);
            } else {
                addAssets(assetValues);
                if (featureShowModule.getContentEnabled() != null) {
                    setEnabledStatus(featureShowModule.getContentEnabled());
                }
            }
        }
        clickSave();
        if (getShelfId() != null) {
            featureShowModule.setId(getShelfId());
        }
        featureShowModule.setRevision(getRevisionEntity());
        featureShowModule.setUuid(getUuidEntity());
        return featureShowModule;
    }

    public FeatureShowModule getFeatureShowModuleInfo() {
        FeatureShowModule featureShowModule = new FeatureShowModule();
        featureShowModule.setTitle(getTitle())
                .setDisplayTitle(isDisplayTitle())
                .setId(getShelfId());
        featureShowModule.setSlug(getAlias());
        if (elementContentList().elementTable().elementHeader().isPresent()) {
            featureShowModule.setAssets(getAssets()).setAssetsCount(featureShowModule.getAssets().size());
        } else {
            featureShowModule.setAssetsCount(0);
        }
        return featureShowModule;
    }

    public Map<String, Integer> getMaxCounts(Shelf shelf) {
        Map<String, Integer> maxCounts = new HashedMap();
        for (String asset : shelf.getAssets()) {
            if (isMaxCountPresent(asset)) {
                maxCounts.put(asset, Integer.valueOf(getMaxCount(asset)));
            }
        }
        return maxCounts;
    }

    public String getShelfId() {
        try {
            String url = webDriver.getCurrentUrl();
            return url.split("\\D+")[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            Utilities.logSevereMessage("Module hasn't id");
        }
        return null;
    }

    public void fillTitle(String textToEnter) {
        title.enterText(textToEnter);
    }

    public String getTitle() {
        return title.getValue();
    }

    public PublishBlock elementPublishBlock() {
        return publishBlock;
    }

    public void fillSettings(String textToEnter) {
        dataField.enterText(textToEnter);
    }

    public String getSettings() {
        return dataField.getValue();
    }

    public boolean isDisplayTitle() {
        return displayTitle.isSelected();
    }

    public void setDisplayTitle(boolean check) {
        if (check) {
            displayTitle.check();
        } else {
            displayTitle.uncheck();
        }
    }

    public void fillAlias(Slug slugInfo) {
        advancedOptions.expand();
        pause(1);
        alias.enterText(slugInfo.getSlugValue());
    }

    public Slug getAlias() {
        Slug slug = new Slug().setAutoSlug(false);
        slug.setSlugValue(alias.getValue());
        return slug;
    }

    public void checkTileVariant(String valueToSelect) {
        tileVariant.selectRadioButtonByName(valueToSelect);
    }

    public String getCheckedTitleVariant() {
        return tileVariant.getSelectedRadioButton();
    }

    public List<String> getAssets() {
        return contentList.getAssets();
    }

    public boolean isMaxCountPresent(String assetTitle) {
        return contentList.isMaxCountPresent(assetTitle);
    }

    public String getMaxCount(String assetTitle) {
        return contentList.getMaxCount(assetTitle);
    }

    public void setMaxCount(String assetTitle, String value) {
        contentList.setMaxCount(assetTitle, value);
    }

    public MediaContentPage clickByAssetTitle(String assetTitle) {
        contentList.clickOnAssetTitle(assetTitle);
        WaitUtils.perform(webDriver).waitForPageLoad();
        return new MediaContentPage(webDriver, aid);
    }

    public void clickAddAnotherItem() {
        addAnotherItem.setName("Add another Item");
        addAnotherItem.clickWithAjaxWait();
    }

    public void addAssets(List<String> assetsList) {
        contentList.addContentAssetsWithOrder(assetsList);
    }

    public void setEnabledStatus(Map<Content, Boolean> contentEnabled) {
        contentEnabled.forEach((content, status) -> setEnableItemStatus(content.getTitle(), status));
    }

    private void setEnableItemStatus(String title, Boolean status) {
        contentList.setEnabledItemStatusByName(title, status);
    }

    public String addRandomAsset() {
        return contentList.addRandomAsset();
    }

    public void removeAsset(String asset) {
        contentList.removeAsset(asset);
    }

    public DraftModuleTab removeAllAsset() {
        contentList.removeAllAsset();
        return this;
    }

    public boolean isAddAnotherItemPresent() {
        return addAnotherItem.isPresent();
    }

    public DraftModuleTab checkLatestEpisodeByName(String name) {
        contentList.checkLatestEpisodeByName(name);
        return this;
    }

    public DraftModuleTab checkLatestEpisodeByName(List<String> names) {
        for (String name : names) {
            contentList.checkLatestEpisodeByName(name);
        }
        return this;
    }

    public DraftModuleTab checkLatestEpisodesByName(List<String> names, int numberLatest) {
        for (String name : names) {
            contentList.checkLatestEpisodeByName(name);
            contentList.setMaxCount(name, String.valueOf(numberLatest));
        }
        return this;
    }

    public void uncheckLatestEpisodeByName(String name) {
        contentList.uncheckLatestEpisodeByName(name);
    }

    public boolean isDisplayTitleEnable() {
        return displayTitle.isEnable();
    }

    public boolean isAliasEnable() {
        return alias.isEnable();
    }

    public boolean isTileVariantDsiplayed() {
        return tileVariant.isVisible();
    }

    public boolean isTileVariantEnabled() {
        return isTileVariantDsiplayed() && tileVariant.getRadioButtonList().get(0).isEnable();
    }

    public List<String> addRandomAssets(int number) {
        return contentList.addRandomAssets(number);
    }

    public void removeRandomAsset() {
        contentList.removeRandomAsset();
    }

    public void dragAndDrop(int whatAssetNumber, int targetAssetNumber) {
        dragAndDrop.perform(whatAssetNumber, targetAssetNumber);
    }

    public DraftModuleTab clickSave() {
        saveModule.click();
        WaitUtils.perform(webDriver).waitForPageLoad();
        return new DraftModuleTab(webDriver, aid);
    }

    public String getExpectedTitle(String moduleName, ShelfType moduleType) {
        return String.format(MessageConstants.TITLE_EDIT_OTT_MODULE, moduleName, moduleType.getName());
    }

    /**
     * Method allowed to get UUIDs of content which was added to the module.
     *
     *@param brand - brnad on which test is executed.
     *@return list of UUIDs
     *
     * */
    public List<String> getUuidsOfContent(String brand) {
        return contentList.getUuids(brand);
    }

    /**
     * Method allowed to get content entities of content which was added to the module using content API.
     *
     *@param brand - brnad on which test is executed.
     *@return list of GlobalNodeJson
     * */
    public List<GlobalNodeJson> getContentEntitiesFromApi(String brand) {
        return contentList.getContentEntitiesFromApi(brand);
    }

    @Override
    public List<String> verifyPage() {
        return new ArrayList<>();
    }

    public Boolean isAssignmentPresent() {
        return assignmentBlock.isPresent();
    }

    /**
     * This method is for double click save (TC11555)
     */
    public void customSave() {
        this.clickSave();
        this.pause(3);
        this.clickSave();
        this.pause(3);
    }

}
