package com.nbcuni.test.cms.backend.tvecms.pages.content;

import com.nbcuni.test.cms.backend.chiller.block.contenttype.UrlPathSettingsBlock;
import com.nbcuni.test.cms.backend.chiller.block.contenttype.video.MPXInfoTab;
import com.nbcuni.test.cms.backend.tvecms.block.content.BasicMediaBlock;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ottvideo.PublishingOptionsTab;
import com.nbcuni.test.cms.elements.*;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.ContentTabs;
import com.nbcuni.test.cms.pageobjectutils.html.HtmlAttributes;
import com.nbcuni.test.cms.pageobjectutils.tvecms.TemplateStyle;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.RegexUtil;
import com.nbcuni.test.cms.utils.logging.TestRuntimeException;
import com.nbcuni.test.cms.utils.mpx.objects.MpxAsset;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Ivan_Karnilau on 13-Nov-15.
 */
public class MediaContentPage extends MainRokuAdminPage {

    protected String nodeUpdatedDate;
    protected Boolean publishState;
    protected WebDriverUtil driverUtil;
    @FindBy(id = "edit-group_additional_info")
    protected MPXInfoTab mpxInfoTab;
    @FindBy(xpath = "(.//ul[@class='vertical-tabs-list'])[1]")
    protected TabsGroup tabsGroup;
    @FindBy(xpath = ".//a[contains(@type,'video/mpx')]")
    private Link fileLink;
    @FindBy(id = "edit-submit")
    private Button save;
    @FindBy(id = "edit-cancel")
    private Button cancel;
    @FindBy(id = "edit-delete")
    private Button delete;
    @FindBy(id = "edit-preview-changes")
    private Button viewChanges;
    @FindBy(id = "edit-group_basic")
    private BasicMediaBlock basicBlock;
    @FindBy(xpath = "//strong[text()='Publishing options']")
    private PublishingOptionsTab publishingOptionsTab;
    @FindBy(id = "edit-path")
    private UrlPathSettingsBlock slugBlock;
    private PublishBlock publishBlock = new PublishBlock(webDriver);

    public MediaContentPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
        driverUtil = WebDriverUtil.getInstance(webDriver);
    }

    public RadioButtonsGroup elementTemplateStyle() {
        return onBasicBlock().getTemplateStyle();
    }

    public BasicMediaBlock onBasicBlock() {
        tabsGroup.openTabByName(ContentTabs.BASIC.getName());
        return basicBlock;
    }

    public UrlPathSettingsBlock onSlugTab() {
        tabsGroup.openTabByName(ContentTabs.URL_PATH_SETTINGS.getName());
        return slugBlock;
    }

    public MPXInfoTab onMPXInfoTab() {
        tabsGroup.openTabByName(ContentTabs.MPX_INFO.getName());
        return mpxInfoTab;
    }

    public PublishingOptionsTab onPublishingOptionsTab() {
        tabsGroup.openTabByName(ContentTabs.PUBLISHING_OPTIONS.getName());
        return publishingOptionsTab;
    }

    public UrlPathSettingsBlock onSlugBlock() {
        tabsGroup.openTabByName(ContentTabs.URL_PATH_SETTINGS.getName());
        return slugBlock;
    }

    public String getTitle() {
        return onBasicBlock().getTitle().getValue();
    }

    public boolean isTitlePresent() {
        return onBasicBlock().getTitle().isVisible();
    }

    public boolean isFileLinkPresent() {
        return fileLink.isPresent();
    }

    public String getFileLinkHref() {
        return fileLink.element().getAttribute(HtmlAttributes.HREF.get());
    }

    public EditFilePage clickFileLink() {
        fileLink.click();
        WaitUtils.perform(webDriver).waitForPageLoad();
        return new EditFilePage(webDriver, aid);
    }

    public EditFilePage openEditFilePage() {
        webDriver.get(getFileLinkHref() + "/edit");
        WaitUtils.perform(webDriver).waitForPageLoad();
        return new EditFilePage(webDriver, aid);
    }

    public void clickCancel() {
        cancel.click();
    }

    public void clickSave() {
        save.setName("Save");
        save.click();
        WaitUtils.perform(webDriver).waitForPageLoad();
    }

    public PublishBlock elementPublishBlock() {
        return publishBlock;
    }

    public String getNodeUpdatedDate() {
        return nodeUpdatedDate;
    }

    public void setNodeUpdatedDate(String nodeUpdatedDate) {
        this.nodeUpdatedDate = nodeUpdatedDate;
    }

    public Boolean getNodePublishState() {
        return publishState;
    }

    public TabsGroup elementTabsGroup() {
        return tabsGroup;
    }

    public void setPublishState(String publishState) {
        if (publishState.equalsIgnoreCase("Published")) {
            this.publishState = true;
        } else if (publishState.equalsIgnoreCase("Not published")) {
            this.publishState = false;
        }
    }

    public TextField elementFeaturedCarouselCta() {
        return onBasicBlock().getFeatureCarouselCta();
    }

    public TextField elementShowPageCta() {
        return onBasicBlock().getShowPageCta();
    }

    public TextField elementFeaturedCarouselHeadLine() {
        return onBasicBlock().getFeatureCarouselHeadline();
    }

    public boolean isTemplateStyleEnable() {
        return onBasicBlock().getTemplateStyle().isEnable();
    }

    public boolean isTemplateStylePresent() {
        return onBasicBlock().getTemplateStyle().isPresent();
    }

    public boolean isTemplateStyleSelected(TemplateStyle templateStyle) {
        return elementTemplateStyle().getSelectedRadioButton().equalsIgnoreCase(templateStyle.getStyle());
    }

    public void typeCTA(String text) {
        onBasicBlock().getFeatureCarouselCta().enterText(text);
    }

    public void typeShowPageCTA(String text) {
        onBasicBlock().getShowPageCta().enterText(text);
    }

    public void typeHeadline(String text) {
        onBasicBlock().getFeatureCarouselHeadline().enterText(text);
    }

    public boolean isHeadlinePresent() {
        return onBasicBlock().getFeatureCarouselHeadline().isPresent();
    }

    public boolean isFeatureCarouselCTAEnable() {
        return onBasicBlock().getFeatureCarouselCta().isEnable();
    }

    public boolean isFeatureCarouselCTAPresent() {
        return onBasicBlock().getFeatureCarouselCta().isPresent();
    }

    public boolean isFeatureCarouselHeadlineEnable() {
        return onBasicBlock().getFeatureCarouselHeadline().isEnable();
    }

    public void fillForm(String cta, String headline) {
        this.typeCTA(cta);
        this.typeHeadline(headline);
    }

    public void selectTemplateStyle(TemplateStyle templateStyle) {
        elementTemplateStyle().selectRadioButtonByName(templateStyle.getStyle());
    }

    @Override
    public String getPageTitle() {
        return onBasicBlock().getPageTitle().getText();
    }

    public boolean isTabPresent(ContentTabs tab) {
        return tabsGroup.isTabPresent(tab.getName());
    }

    public MpxAsset getMpxInfo() {
        MpxAsset mpxAsset = onMPXInfoTab().getMetadata();
        mpxAsset.setTitle(getTitle());
        return mpxAsset;
    }

    public String createSortTitle(String title) {
        String pattern = "^(?i)((an |the |a |episode [one|two|three|four|five|six|seven|eight|nine|ten" +
                "|eleven|twelve|thirteen|fourteen|fifteen|sixteen|seventeen|eighteen|nineteen|twenty|thirty" +
                "|forty|fifty|sixty|seventy|eighty|ninety]) .*)";
        if (!RegexUtil.isMatch(title.trim(), pattern)) {
            return title;
        }
        throw new TestRuntimeException("Unable to create Sort Title");
    }

}
