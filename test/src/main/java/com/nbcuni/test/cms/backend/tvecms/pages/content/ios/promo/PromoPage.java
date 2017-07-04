package com.nbcuni.test.cms.backend.tvecms.pages.content.ios.promo;

import com.nbcuni.test.cms.backend.chiller.block.contenttype.UrlPathSettingsBlock;
import com.nbcuni.test.cms.backend.chiller.pages.contenttype.ContentTypePage;
import com.nbcuni.test.cms.backend.chiller.pages.contenttype.DevelPage;
import com.nbcuni.test.cms.backend.tvecms.block.content.ios.promo.BasicTab;
import com.nbcuni.test.cms.backend.tvecms.block.content.ios.promo.LinksTab;
import com.nbcuni.test.cms.backend.tvecms.block.content.ios.promo.MediaTab;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Content;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.assetlibrary.FilesMetadata;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.media.MediaImage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.Promo;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.basic.Basic;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ios.promo.links.Links;
import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.PublishBlock;
import com.nbcuni.test.cms.elements.TabsGroup;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.ContentTabs;
import com.nbcuni.test.cms.pageobjectutils.tvecms.concerto.promo.PromoIosSource;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Ivan_Karnilau on 23-Aug-16.
 */
public class PromoPage extends ContentTypePage {

    @FindBy(id = "edit-group_basic")
    private BasicTab basicTab;

    @FindBy(id = "edit-group_links")
    private LinksTab linksTab;

    @FindBy(id = "edit-group_media")
    private MediaTab mediaTab;

    @FindBy(id = "edit-submit")
    private Button save;

    @FindBy(xpath = "(.//ul[@class='vertical-tabs-list'])[1]")
    private TabsGroup tabsGroup;

    private PublishBlock publishBlock = new PublishBlock(webDriver);

    public PromoPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public BasicTab onBasicTab() {
        tabsGroup.openTabByName(ContentTabs.BASIC.getName());
        return basicTab;
    }

    public LinksTab onLinksTab() {
        tabsGroup.openTabByName(ContentTabs.LINKS.getName());
        return linksTab;
    }

    public MediaTab onPromoMediaTab() {
        tabsGroup.openTabByName(ContentTabs.MEDIA.getName());
        return mediaTab;
    }

    @Override
    public UrlPathSettingsBlock onSlugTab() {
        tabsGroup.openTabByName(ContentTabs.URL_PATH_SETTINGS.getName());
        return slugBlock;
    }

    private void enterBasic(Basic basic) {
        onBasicTab().enterData(basic);
    }

    private void enterLinks(Links links) {
        onLinksTab().enterData(links);
    }

    private void uploadImage(MediaImage image) {
        this.onPromoMediaTab().uploadImage(image.getUrl());
    }

    private void clickSave() {
        this.save.click();
    }

    @Override
    public ContentTypePage publish() {
        this.publishBlock.publishByTabName();
        return this;
    }

    @Override
    public ContentTypePage enterContentTypeData(Content content) {
        Promo promo = (Promo) content;
        this.enterBasic(promo.getBasic());
        this.enterLinks(promo.getLinks());
        this.uploadImage(promo.getImage());
        return this;
    }

    @Override
    public ContentTypePage create(Content content) {
        Promo promo = (Promo) content;
        this.enterContentTypeData(promo);
        this.clickSave();
        return this;
    }

    @Override
    public ContentTypePage createAndPublish(Content content) {
        this.create(content);
        this.publish();
        return this;
    }

    public FilesMetadata getFilesMetadata() {
        Promo promo = (Promo) getPageData();
        FilesMetadata filesMetadata = new FilesMetadata();
        filesMetadata.getImageGeneralInfo()
                .setTitle(promo.getImage().getName())
                .setUuid(promo.getImage().getUuid())
                .setRevision(promo.getGeneralInfo().getRevision())
                .setImageHref(promo.getImage().getUrl())
                .setHighResolution(false)
                .setPublished(true)
                .setImageStyles(onPromoMediaTab().getPromoThumbnails());
        return filesMetadata;
    }

    @Override
    public Content getPageData() {
        Promo promo = new Promo();

        promo.setBasic(onBasicTab().getData());
        promo.setLinks(onLinksTab().getData());
        promo.setImage(onPromoMediaTab().getImage());
        promo.setSlugInfo(onSlugTab().getSlug());
        DevelPage develPage = this.openDevelPage();
        promo.getGeneralInfo().setRevision(Integer.parseInt(develPage.getVid()));
        promo.getGeneralInfo().setUuid(develPage.getUuid());
        if (promo.getImage().getName() != null) {
            promo.getImage().setUuid(develPage.getMediaUuidByMachineName(PromoIosSource.PROMO_MEDIA.getMachineName()));
        }
        this.openEditPage();

        return promo;
    }
}
