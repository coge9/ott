package com.nbcuni.test.cms.backend.chiller.block.contenttype;


import com.nbcuni.test.cms.backend.chiller.pages.assetlibrary.AddFilePage;
import com.nbcuni.test.cms.backend.chiller.pages.assetlibrary.AssetLibraryPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.media.MediaImage;
import com.nbcuni.test.cms.elements.Button;
import com.nbcuni.test.cms.elements.Image;
import com.nbcuni.test.cms.utils.ImageUtil;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author Aliaksei_Dzmitrenka
 */

public class CoverMediaBlock extends AbstractContainer {

    private static final String IMG = ".//img";
    @FindBy(xpath = IMG)
    private Image imageItem;
    @FindBy(xpath = ".//label[@class='media-filename']")
    private WebElement fileName;

    @FindBy(xpath = ".//*[@value='Remove']")
    private Button removeButton;

    @FindBy(xpath = ".//*[contains(@id, 'browse-button')]")
    private Button browseButton;

    @FindBy(id = "edit-field-cover-media-und-0-library-button")
    private Button addFromLibraryButton;

    @FindBy(xpath = "//iframe")
    private WebElement iframe;

    public MediaImage getCoverMediaImage() {
        MediaImage mediaImage = new MediaImage();
        mediaImage.setUrl(imageItem.getSource());
        mediaImage.setName(fileName.getText());
        mediaImage.setWidth(imageItem.getWidth());
        mediaImage.setWidth(imageItem.getHeight());
        return mediaImage;
    }

    private AddFilePage clickUploadfromYourComputerButton() {
        browseButton.click();
        webDriver.switchTo().frame(iframe);
        return new AddFilePage(webDriver, null);
    }

    private AssetLibraryPage clickAddFromLibraryButton() {
        addFromLibraryButton.click();
        webDriver.switchTo().frame(iframe);
        return new AssetLibraryPage(webDriver, null);
    }

    public void addImagesFromLibrary() {
        AssetLibraryPage assetLibraryPage = clickAddFromLibraryButton();
        assetLibraryPage.getFiles().get(0).divSelect();
        assetLibraryPage.clickSubmitButton();
    }

    public void uploadImage() {
        clickUploadfromYourComputerButton().uploadCoverImage(ImageUtil.createRandomImages(1920, 1080, 1).get(0));
        webDriver.switchTo().defaultContent();
    }

    public void isImagePresentInLibrary(MediaImage image, SoftAssert softAssert) {
        AssetLibraryPage assetLibraryPage = clickAddFromLibraryButton();
        softAssert.assertTrue(assetLibraryPage.isImageInLibrary(image.getName()), "Image [" + image.getName() + "] is not present in library", "Image [" + image.getName() + "] is present in library", webDriver);
    }

    public void removeCoverImage() {
        removeButton.clickWithAjaxWait();
    }

    public boolean isCoverImagePresent() {
        return WebDriverUtil.getInstance(webDriver).isElementPresent(IMG);
    }
}
