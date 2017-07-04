package com.nbcuni.test.cms.backend.chiller.block.contenttype.media;

import com.nbcuni.test.cms.backend.chiller.pages.assetlibrary.AddFilePage;
import com.nbcuni.test.cms.backend.chiller.pages.assetlibrary.AssetLibraryPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.assetlibrary.FilesMetadata;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.media.MediaImage;
import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Usage;
import com.nbcuni.test.cms.utils.ImageUtil;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Aliaksei_Dzmitrenka
 */

public class MediaBlock extends AbstractContainer {

    private static final String MEDIA_FIELDS_XPATH = ".//*[contains(@id,'edit-field-media')]/tbody/tr";
    @FindBy(xpath = ".//*[contains(@id, 'browse-button')]")
    private Button browseButton;
    @FindBy(xpath = ".//*[contains(@id, 'library-button')]")
    private Button addFromLibraryButton;
    @FindBy(xpath = "//iframe[contains(@class, 'media-modal-frame')]")
    private WebElement iframe;
    @FindBy(xpath = ".//*[contains(@id,'edit-field-media')]/tbody/tr")
    private List<MediaField> mediaFields;

    public List<MediaImage> getMediaImages(FilesMetadata... metaData) {
        List<MediaImage> toReturn = new ArrayList<>();
        for (MediaField field : mediaFields) {
            MediaImage mediaImage = new MediaImage();
            mediaImage.setUrl(field.getUrl());
            mediaImage.setName(field.getName());
            if (metaData.length > 0) {
                for (FilesMetadata oneImage : metaData) {
                    if (oneImage.getImageGeneralInfo().getTitle().equals(field.getName())) {
                        Utilities.logInfoMessage(oneImage.getImageGeneralInfo().getCaption());
                        mediaImage.setUsage(oneImage.getImageGeneralInfo().getCaption());
                        break;
                    }
                }
            } else {
                mediaImage.setUsage(field.getUsage());
            }
            toReturn.add(mediaImage);
        }
        return toReturn;
    }

    private AddFilePage clickUploadfromYourComputerButton() {
        browseButton.click();
        webDriver.switchTo().frame(iframe);
        return new AddFilePage(webDriver, null);
    }

    private AssetLibraryPage clickAddFromLibraryButton() {
        addFromLibraryButton.click();
        waitFor().waitForPageLoad();
        webDriver.switchTo().frame(iframe);
        pause(1);
        return new AssetLibraryPage(webDriver, null);
    }

    public void addImagesFromLibrary(int count) {
        AssetLibraryPage assetLibraryPage = clickAddFromLibraryButton();
        for (int i = 0; i < count; i++) {
            assetLibraryPage.getFiles().get(i).divSelect();
        }
        assetLibraryPage.clickSubmitButton();
    }

    public void addImagesFromLibrary(List<FilesMetadata> expectedImages) {
        for (FilesMetadata image : expectedImages) {
            AssetLibraryPage assetLibraryPage = clickAddFromLibraryButton();
            assetLibraryPage.clickAsset(image.getImageGeneralInfo().getTitle());
            assetLibraryPage.clickSubmitButton();
            WaitUtils.perform(webDriver).waitElementPresent(MEDIA_FIELDS_XPATH, 7);
        }
        selectRandomUsageForAllImages();
    }

    public void addImagesFromLibrary(FilesMetadata file) {
        addImagesFromLibrary(file.getImageGeneralInfo().getTitle());
    }

    public void addImagesFromLibrary(String imageName) {
        AssetLibraryPage assetLibraryPage = clickAddFromLibraryButton();
        assetLibraryPage.clickAsset(imageName);
        assetLibraryPage.clickSubmitButton();
        selectRandomUsageForAllImages();
    }

    public void uploadImages(int count) {
        uploadImages(ImageUtil.createRandomImages(1920, 1080, count));
    }

    public void uploadImages(List<File> files) {
        AddFilePage addFilePage = clickUploadfromYourComputerButton();
        addFilePage.uploadFiles(files);
        addFilePage.pause(20);
    }

    public void isImagesPresentInLibrary(List<MediaImage> images, SoftAssert softAssert) {
        AssetLibraryPage assetLibraryPage = clickAddFromLibraryButton();
        for (MediaImage image : images) {
            softAssert.assertTrue(assetLibraryPage.isImageInLibrary(image.getName()), "Image [" + image.getName() + "] is not present in library", "Image [" + image.getName() + "] is present in library", webDriver);
        }
    }

    public void removeRandomElement() {
        int i = new Random().nextInt(mediaFields.size());
        mediaFields.get(i).delete();
    }

    public void removeAllElements() {
        for (MediaField mediaField : mediaFields) {
            mediaFields.get(0).delete();
        }
    }

    public void selectRandomUsageForAllImages() {
        for (MediaField field : mediaFields) {
            field.selectUsage(Usage.getRandomUsage().getUsage());
        }
    }

    public void removeUsageForAllImages() {
        for (MediaField field : mediaFields) {
            field.selectUsage("");
        }
    }

    public void selectUsageForImage(String name) {
        for (MediaField field : mediaFields) {
            if (field.getName().equalsIgnoreCase(name)) {
                field.selectUsage(Usage.getRandomUsage().getUsage());
                return;
            }
        }
    }

    public void setImageItem(List<MediaImage> mediaImages) {
        AssetLibraryPage assetLibraryPage = clickAddFromLibraryButton();
        addImagesFromLibrary(mediaImages.size());
        assetLibraryPage.clickSubmitButton();
        setUsageByImageIndex(mediaImages);
    }

    public void setUsageByImageIndex(List<MediaImage> mediaImages) {
        for (int index = 0; index < mediaImages.size(); index++) {
            if (mediaImages.get(index).getUsage() != null) {
                mediaFields.get(index).selectUsage(mediaImages.get(index).getUsage());
            }
        }
    }

}
