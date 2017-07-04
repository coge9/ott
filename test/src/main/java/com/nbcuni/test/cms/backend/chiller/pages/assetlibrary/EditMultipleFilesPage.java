package com.nbcuni.test.cms.backend.chiller.pages.assetlibrary;

import com.nbcuni.test.cms.backend.chiller.block.assetlibrary.editpage.ImageGeneralInfoBlock;
import com.nbcuni.test.cms.backend.chiller.block.contenttype.AssociationsBlock;
import com.nbcuni.test.cms.backend.chiller.pages.contenttype.DevelPage;
import com.nbcuni.test.cms.backend.tvecms.block.ActionBlock;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.assetlibrary.FilesMetadata;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.assetlibrary.ImageGeneralInfo;
import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.TabsGroup;
import com.nbcuni.test.cms.pageobjectutils.MessageConstants;
import com.nbcuni.test.cms.pageobjectutils.chiller.ActionButtons;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.RegexUtil;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.images.ImageStyleJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.images.ImagesJson;
import com.nbcuni.test.cms.utils.thumbnails.chiller.ChillerImageEntity;
import com.nbcuni.test.cms.utils.thumbnails.chiller.ChillerThumbnails;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Ivan_Karnilau on 18-Apr-16.
 */
public class EditMultipleFilesPage extends MainRokuAdminPage {

    @FindBy(xpath = ".//ul[@class='vertical-tabs-list']")
    protected TabsGroup tabsGroup;
    @FindBy(id = "edit-group_association")
    private AssociationsBlock associations;
    @FindBy(id = "edit-group_general_info")
    private ImageGeneralInfoBlock generalInfo;
    @FindBy(xpath = "//div[@id='edit-actions']|//form[@id='multiform']/div")
    private ActionBlock actionBlock;
    @FindBy(xpath = "//*[@id='edit-publish']|//*[@id='edit-submit']|//*[contains(@id,'edit-publish')]")
    private Button saveButton;

    public EditMultipleFilesPage(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
    }

    public ActionBlock getActionBlock() {
        return actionBlock;
    }

    public void save() {
        this.saveButton.click();
    }

    public void cancel() {
        actionBlock.cancel();
        WaitUtils.perform(webDriver).waitForPageLoad();
    }

    public void publish() {
        actionBlock.publish();
        WaitUtils.perform(webDriver).waitForPageLoad();
    }

    public EditMultipleFilesPage cancelPublish() {
        this.actionBlock.cancelPublishing();
        return this;
    }

    public ImageGeneralInfoBlock getGeneralInfoBlock() {
        generalInfo.expandTab();
        return generalInfo;
    }

    public AssociationsBlock getAssociations() {
        associations.expandTab();
        return associations;
    }

    public AssetLibraryImagePreview clickImagePreview() {
        WebDriverUtil.getInstance(webDriver).scrollPageUp();
        getGeneralInfoBlock().clickImagePreview();
        WebDriverUtil.getInstance(webDriver).switchToWindowByNumber(2);
        return new AssetLibraryImagePreview(webDriver, aid);
    }

    public boolean isAllChillerImagesArePresent(SoftAssert softAssert) {
        return clickImagePreview().isChillerCustomImagesPresent(softAssert, ChillerThumbnails.getAllTumbnails());
    }

    public Map<ChillerThumbnails, ChillerImageEntity> getChillerImagesOnUI() {
        return clickImagePreview().getChillerImages(ChillerThumbnails.getAllTumbnails());
    }

    public List<ImageStyleJson> getImageStylesForPublishingFromPage() {
        List<ImageStyleJson> imageStyles = new ArrayList<>();
        Map<ChillerThumbnails, ChillerImageEntity> chillerImages = getChillerImagesOnUI();
        for (Map.Entry<ChillerThumbnails, ChillerImageEntity> chillerImageStyleEntry : chillerImages.entrySet()) {
            String imageHref = RegexUtil.getFirstSubstringByRegex(chillerImageStyleEntry.getValue().getSrc(), MessageConstants.CHILLER_IMAGE_STYLE_FOR_PUBLISHING);
            String imageType = chillerImageStyleEntry.getKey().getAssetTypes().toLowerCase();
            Integer imageHeight = chillerImageStyleEntry.getValue().getHeight();
            Integer imageWidth = chillerImageStyleEntry.getValue().getWidth();
            ImageStyleJson imageStyleJson = new ImageStyleJson().setHref(imageHref).setType(imageType)
                    .setHeight(imageHeight).setWidth(imageWidth);
            imageStyles.add(imageStyleJson);
        }
        webDriver.switchTo().defaultContent();
        return imageStyles;
    }

    public ChillerImageEntity getOriginalChiller() {
        return clickImagePreview().getOriginalChillerImage();
    }

    public void enterFilesMetadata(FilesMetadata filesMetadata) {
        getGeneralInfoBlock().enterImageGeneralInfo(filesMetadata.getImageGeneralInfo());
        getAssociations().enterAssociations(filesMetadata.getAssociations());
    }

    public FilesMetadata getPageData() {
        FilesMetadata filesMetadata = new FilesMetadata();
        filesMetadata.setImageGeneralInfo(this.getGeneralInfoBlock().getImageGeneralInfo());
        if (getAssociations().getAssociationsInfo() != null) {
            filesMetadata.setAssociations(getAssociations().getAssociationsInfo());
        }

        return filesMetadata;
    }

    public ImagesJson updateMetadataForPublishing(FilesMetadata filesMetadata) {
        ImageGeneralInfo imageGeneralInfo = getGeneralInfoBlock().getImageGeneralInfo();
        filesMetadata.getImageGeneralInfo().setHighResolution(imageGeneralInfo.getHighResolution());
        filesMetadata.getImageGeneralInfo().setTitle(imageGeneralInfo.getTitle());
        filesMetadata.getImageGeneralInfo().setImageHref(getOriginalChiller().getSrc());
        List<ImageStyleJson> imageStyleJsons = getImageStylesForPublishingFromPage();
        getInfoFromDevelPage(filesMetadata);
        ImagesJson imagesJson = new ImagesJson(filesMetadata);
        imagesJson.setImageStyles(imageStyleJsons);
        return imagesJson;
    }

    public FilesMetadata getInfoFromDevelPage(FilesMetadata filesMetadata) {
        DevelPage develPage = openDevelPage();
        if (develPage == null) {
            return filesMetadata;
        }
        develPage.getDataForImage(filesMetadata);
        return filesMetadata;
    }

    public boolean isActionButtonsPresent(List<ActionButtons> actionButtons, boolean isPresent) {
        return this.actionBlock.isActionButtonsPresent(actionButtons, isPresent);
    }
}
