package com.nbcuni.test.cms.backend.chiller.pages.contenttype;

import com.nbcuni.test.cms.backend.chiller.block.contenttype.*;
import com.nbcuni.test.cms.backend.chiller.block.contenttype.castcredit.CastAndCredit;
import com.nbcuni.test.cms.backend.chiller.block.contenttype.externallinks.ExternalLinksBlock;
import com.nbcuni.test.cms.backend.chiller.block.contenttype.media.MediaBlock;
import com.nbcuni.test.cms.backend.tvecms.block.ActionBlock;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.ContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Slug;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.Associations;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.Promotional;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.links.ExternalLinksInfo;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.media.MediaImage;
import com.nbcuni.test.cms.elements.Link;
import com.nbcuni.test.cms.pageobjectutils.chiller.ActionButtons;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.ContentTabs;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.castcredit.CastEntity;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.support.FindBy;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by Ivan_Karnilau on 06-Apr-16.
 */
public abstract class ContentTypePage extends MainRokuAdminPage {

    @FindBy(id = "edit-group_basic")
    protected GeneralInfoBlock generalInfoBlock;

    @FindBy(xpath = "//*[contains(@id,'edit-group_association')]")
    protected AssociationsBlock associationsBlock;

    @FindBy(id = "edit-group_promotional")
    protected PromotionalBlock promotionalBlock;

    @FindBy(id = "edit-group_promotional")
    protected PublishingOptionBlock publishingOptionBlock;

    @FindBy(id = "edit-group_media")
    protected MediaTab mediaTab;

    @FindBy(id = "edit-group_links")
    protected ExternalLinksBlock externalLinksBlock;

    @FindBy(id = "edit-path")
    protected UrlPathSettingsBlock slugBlock;

    @FindBy(id = "edit-field-cast-collection-und")
    private CastAndCredit castAndCredit;

    @FindBy(xpath = "//fieldset//a[@class='fieldset-title']")
    private List<Link> links;

    @FindBy(id = "edit-actions")
    private ActionBlock actionBlock;

    public ContentTypePage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public AssociationsBlock onAssociationsTab() {
        associationsBlock.expandTab();
        return associationsBlock;
    }

    public PromotionalBlock onPromotionalTab() {
        promotionalBlock.expandTab();
        return promotionalBlock;
    }

    public MediaTab onMediaTab() {
        mediaTab.expandTab();
        return mediaTab;
    }

    public UrlPathSettingsBlock onSlugTab() {
        slugBlock.expandTab();
        return slugBlock;
    }

    public PublishingOptionBlock onPublishingOptionsTab() {
        publishingOptionBlock.expandTab();
        return publishingOptionBlock;
    }

    public ContentTypePage saveAsDraft() {
        WebDriverUtil.getInstance(webDriver).scrollPageUp();
        this.actionBlock.save();
        return this;
    }

    public ContentTypePage publish() {
        this.actionBlock.publish();
        return this;
    }

    public ContentTypePage publish(String endPoint) {
        this.actionBlock.publish(endPoint);
        return this;
    }

    public ContentTypePage cancelPublish() {
        this.actionBlock.cancelPublishing();
        return this;
    }

    public ContentPage cancel() {
        this.actionBlock.cancel();
        return new ContentPage(webDriver, aid);
    }

    public ActionBlock getActionBlock() {
        return actionBlock;
    }

    public abstract ContentTypePage enterContentTypeData(Content content);

    public abstract ContentTypePage create(Content content);

    public abstract ContentTypePage createAndPublish(Content content);

    public abstract Content getPageData();

    public ContentTypePage enterPromotionalData(Promotional promotional) {
        if (!promotional.isObjectNull()) {
            onPromotionalTab().enterPromotional(promotional);
        }
        return this;
    }

    public ContentTypePage enterSlugData(Slug slug) {
        if (!slug.isObjectNull()) {
            onSlugTab().enterSlug(slug);
        }
        return this;
    }

    public ContentTypePage enterAssociationData(Associations associations) {
        if (!associations.isObjectNull()) {
            onAssociationsTab().enterAssociations(associations);
        }
        return this;
    }

    public ContentTypePage enterMediaData(int count) {
        if (count > 0) {
            MediaBlock mediaBlock = onMediaTab().onMediaBlock();
            mediaBlock.removeAllElements();
            mediaBlock.addImagesFromLibrary(count);
            mediaBlock.selectRandomUsageForAllImages();
        }
        return this;
    }

    public ContentTypePage enterMediaData(List<MediaImage> mediaImages) {
        if (!mediaImages.isEmpty()) {
            MediaBlock mediaBlock = onMediaTab().onMediaBlock();
            mediaBlock.removeAllElements();
            mediaBlock.addImagesFromLibrary(mediaImages.size());
            mediaBlock.setUsageByImageIndex(mediaImages);
        }
        return this;
    }

    protected ContentTypePage enterCastCredit(List<CastEntity> castCreditEntities) {
        if (!castCreditEntities.isEmpty()) {
            castAndCredit.fillCast(castCreditEntities);
        }
        return this;
    }

    public ContentTypePage enterExternalLinksData(List<ExternalLinksInfo> linksInfos) {
        if (CollectionUtils.isEmpty(linksInfos)) {
            return this;
        }
        externalLinksBlock.expandTab();
        externalLinksBlock.setLinkPairs(linksInfos);
        return this;
    }

    public List<ExternalLinksInfo> getExternalLinksData() {
        externalLinksBlock.expandTab();
        return externalLinksBlock.getLinkPairs();
    }

    public CastAndCredit onCastAndCreditBlock() {
        generalInfoBlock.expandTab();
        return castAndCredit;
    }

    public Associations getAssociations() {
        return onAssociationsTab().getAssociationsInfo();
    }

    public Promotional getPromotional() {
        return onPromotionalTab().getPromotional();
    }

    public Slug getSlugInfo() {
        return onSlugTab().getSlug();
    }

    public boolean isTabPresent(ContentTabs tab) {
        for (Link link : links) {
            if (link.getText().equalsIgnoreCase(tab.getName())) {
                return true;
            }
        }
        return false;
    }

    public boolean isActionButtonsPresent(List<ActionButtons> buttons, boolean isPresent) {
        return actionBlock.isActionButtonsPresent(buttons, isPresent);
    }
}
