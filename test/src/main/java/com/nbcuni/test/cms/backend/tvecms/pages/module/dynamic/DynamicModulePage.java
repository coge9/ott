package com.nbcuni.test.cms.backend.tvecms.pages.module.dynamic;

import com.nbcuni.test.cms.backend.tvecms.block.modules.contentlist.ModulesContentList;
import com.nbcuni.test.cms.backend.tvecms.pages.module.MainModulePage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Slug;
import com.nbcuni.test.cms.bussinesobjects.tvecms.module.dynamic.Dynamic;
import com.nbcuni.test.cms.elements.*;
import com.nbcuni.test.cms.pageobjectutils.entities.rules.OrderType;
import com.nbcuni.test.cms.pageobjectutils.entities.rules.SortingType;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.shelf.dynamic.Programs;
import com.nbcuni.test.cms.pageobjectutils.tvecms.shelf.dynamic.VideoType;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.logging.TestRuntimeException;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;


/**
 * Created by Ivan_Karnilau on 5/11/2017.
 */
public class DynamicModulePage extends MainModulePage {

    private PublishBlock publishBlock = new PublishBlock(webDriver);

    @FindBy(xpath = ".//*[contains(@id,'edit-title')]")
    private TextField title;

    @FindBy(xpath = ".//*[contains(@id,'edit-display-title')]")
    private CheckBox displayTitle;

    @FindBy(xpath = ".//input[contains(@id,'edit-actions-submit')]|//input[contains(@id,'edit-submit')]")
    private Button saveModule;

    @FindBy(xpath = ".//div[@id='ott-list-table-wrapper']")
    private ModulesContentList contentList;

    @FindBy(xpath = ".//*[contains(@id,'edit-field-ott-module-content-type')]")
    private RadioButtonsGroup contentType;

    @FindBy(xpath = ".//*[contains(@id,'edit-field-ott-modue-video-type-und')]")
    private DropDownList videoType;

    @FindBy(xpath = ".//input[contains(@id,'edit-field-ott-module-max-cont-items')]")
    private TextField maxContentItems;

    @FindBy(xpath = ".//*[contains(@id,'edit-field-ott-module-sort-by-und')]")
    private DropDownList sortBy;

    @FindBy(xpath = ".//*[contains(@id,'edit-field-ott-module-order-und')]")
    private DropDownList order;

    @FindBy(xpath = ".//*[contains(@id,'edit-field-ott-module-programs')]")
    private RadioButtonsGroup programs;

    @FindBy(xpath = ".//*[contains(@id,'edit-advanced-options')]")
    private ExpanderWithLink advancedOptions;

    @FindBy(xpath = ".//*[contains(@id,'edit-alias')]")
    private TextField alias;

    public DynamicModulePage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public void typeTitle(String title) {
        this.title.enterText(title);
    }

    public String getTitle() {
        return this.title.getValue();
    }

    public boolean getDisplayTitle() {
        return this.displayTitle.isSelected();
    }

    public void setDisplayTitle(Boolean displayTitle) {
        this.displayTitle.selectStatus(displayTitle);
    }

    private void typeMaxContentItems(Integer maxContentItems) {
        this.maxContentItems.enterText(String.valueOf(maxContentItems));
    }

    private Integer getMaxContentItems() {
        return Integer.valueOf(this.maxContentItems.getValue());
    }

    private void selectOrder(OrderType order) {
        if (order == null) {
            return;
        }
        this.order.selectFromDropDown(order.getOrderValue());
    }

    private OrderType getOrder() {
        return OrderType.get(this.order.getSelectedValue());
    }

    private void selectContentType(ContentType contentType) {
        if (contentType == null) {
            return;
        }
        String type;
        switch (contentType) {
            case TVE_VIDEO:
                type = "Video";
                break;
            case TVE_PROGRAM:
                type = "Program";
                break;
            default:
                throw new TestRuntimeException("Impossible to select content type \"" + contentType);
        }
        this.contentType.selectRadioButtonByName(type);
    }

    private ContentType getContentType() {
        return ContentType.getContentByText("TVE " + this.contentType.getSelectedRadioButton());
    }

    private void selectSortBy(SortingType sortBy) {
        if (sortBy == null) {
            return;
        }
        this.sortBy.selectFromDropDown(sortBy.getSortingValue());
    }

    private SortingType getSortBy() {
        return SortingType.get(this.sortBy.getSelectedValue());
    }

    private void selectVideoType(VideoType videoType) {
        if (videoType == null) {
            return;
        }
        this.videoType.selectFromDropDown(videoType.getType());
    }

    private VideoType getVideoType() {
        return VideoType.getVideoTypeByText(this.videoType.getSelectedValue());
    }

    public void fillAlias(Slug slugInfo) {
        advancedOptions.expand();
        pauseInMilliseconds(200);
        alias.enterText(slugInfo.getSlugValue());
    }

    public Slug getAlias() {
        Slug slug = new Slug().setAutoSlug(false);
        slug.setSlugValue(alias.getValue());
        return slug;
    }

    public void create(Dynamic dynamic) {
        typeTitle(dynamic.getTitle());
        setDisplayTitle(dynamic.getDisplayTitle());
        typeMaxContentItems(dynamic.getMaxContentItem());
        selectOrder(dynamic.getOrder());
        selectContentType(dynamic.getContentType());
        if (dynamic.getContentType() == null || dynamic.getContentType().equals(ContentType.TVE_VIDEO)) {
            createVideoType(dynamic);
        }
        fillAlias(dynamic.getSlug());
        saveModule.click();
    }

    private void createVideoType(Dynamic dynamic) {
        selectVideoType(dynamic.getVideoType());
        selectSortBy(dynamic.getSortBy());
        selectPrograms(dynamic.getPrograms());
        setAssets(dynamic.getAssets());
    }

    private void selectPrograms(Programs programs) {
        if (programs == null) {
            return;
        }
        this.programs.selectRadioButtonByName(programs.getPrograms());
        if (programs.equals(Programs.CUSTOMIZE)) {
            addItems();
        }
    }

    private Programs getPrograms() {
        return Programs.getProgramsByText(this.programs.getSelectedRadioButton());
    }

    private void setAssets(List<String> programs) {
        if (programs == null || programs.isEmpty()) {
            return;
        }
        contentList.addContentAssets(programs);
    }

    private void addItems() {
       // created for future needs.
    }

    public void publish() {
        publishBlock.publishByTabName();
    }

    public Dynamic getData() {
        Dynamic dynamic = new Dynamic();
        dynamic.setTitle(getTitle());
        dynamic.setUuid(getUuidEntity());
        dynamic.setRevision(getRevisionEntity());
        dynamic.setSlug(getAlias());
        dynamic.setDisplayTitle(getDisplayTitle());
        dynamic.setContentType(getContentType());
        dynamic.setOrder(getOrder());
        dynamic.setMaxContentItem(getMaxContentItems());
        if (dynamic.getContentType().equals(ContentType.TVE_VIDEO)) {
            dynamic.setVideoType(getVideoType());
            dynamic.setSortBy(getSortBy());
            dynamic.setPrograms(getPrograms());
            if (dynamic.getPrograms().equals(Programs.CUSTOMIZE)) {
                dynamic.setAssets(this.contentList.getAssets());
            }
        }

        return dynamic;
    }
}
