package com.nbcuni.test.cms.elements;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.media.MediaImage;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.Source;
import com.nbcuni.test.cms.pageobjectutils.html.HtmlAttributes;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CustomImageTypes;
import com.nbcuni.test.cms.utils.ImageUtil;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.Image;
import com.nbcuni.test.cms.utils.thumbnails.android.program.AndroidProgramThumbnails;
import com.nbcuni.test.cms.utils.thumbnails.android.video.AndroidVideoThumbnails;
import com.nbcuni.test.cms.utils.thumbnails.chiller.ThumbnailsCutInterface;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class VideoContentImage extends AbstractContainer {

    /**
     * Created by: Dzianis Kulesh
     * <p/>
     * Date: 19/10/2015
     * <p/>
     * Description: Block represent uploaded image source in video/program page
     * <p/>
     * Preview URL:
     * https://dl.dropboxusercontent.com/s/64k2gggk3ed4b22/VideoContentImage.png
     */


    private static final String IMAGE_PRIVIEW = ".//div[@class='image-preview']//img";
    private static final String SOURCE_IMAGE_LINK = ".//span[@class='file']/a";
    private static final String LINK_TO_CUSTOM_IMAGE = "//h2[text()='%s']//following-sibling::div[1]//img";
    private static final String LINKS_TO_CUSTOM_IMAGE = "//h2//following-sibling::div[1]//img";
    @FindBy(id = "edit-field-3-tile-source")
    private FileUploader THREE_TILE_SOURCE_UPLOADER;
    @FindBy(xpath = ".//div[contains(@class,'form-type-managed-file')]")
    private FileUploader fileOverrider;

    @FindBy(xpath = ".//input[@type='checkbox']")
    private CheckBox overrideCheckBox;

    @FindBy(xpath = ".//label[contains(@for,'edit-field')]")
    private Label titleLabel;

    @FindBy(xpath = ".//div[@class='image-preview']//a")
    private Link imagePreview;

    public void overrideImage(String imagePath) {
        overrideCheckBox.check();
        fileOverrider.removeFile();
        fileOverrider.uploadFile(imagePath);
    }

    public String getLinkToSourceImage() {
        String sourceImageLink = currentElement.element().findElement(
                By.xpath(SOURCE_IMAGE_LINK)).getAttribute(
                HtmlAttributes.HREF.get());
        return sourceImageLink;
    }

    public boolean isSourcePresent() {
        Boolean status = false;
        status = WebDriverUtil.getInstance(webDriver).isElementPresent(currentElement.element(), SOURCE_IMAGE_LINK, 1);
        return status;
    }

    public String getImageSourceName() {
        return currentElement.element().findElement(By.xpath(SOURCE_IMAGE_LINK)).getText();
    }

    public int getSourceWidth() {
        return ImageUtil.getWidth(getLinkToSourceImage());
    }

    public int getSourceHeight() {
        return ImageUtil.getHeight(getLinkToSourceImage());
    }

    public int getWidth(ThumbnailsCutInterface type) {
        return ImageUtil.getWidth(getLinkToImage(type));
    }

    public int getHeight(ThumbnailsCutInterface type) {
        return ImageUtil.getHeight(getLinkToImage(type));
    }

    public int getWidth(CustomImageTypes type) {
        return ImageUtil.getWidth(getLinkToCustomImage(type));
    }

    public int getHeight(CustomImageTypes type) {
        return ImageUtil.getHeight(getLinkToCustomImage(type));
    }

    private int getNumberLinks() {
        return webDriver.findElementsByXPath(LINKS_TO_CUSTOM_IMAGE).size();
    }

    private String getLinkToCustomImage(CustomImageTypes type) {
        return getLinkToCustomImage(type.getName());
    }

    private String getLinkToCustomImage(String typeName) {
        WebElement link = webDriver.findElement(By.xpath(String.format(LINK_TO_CUSTOM_IMAGE, typeName)));
        return link.getAttribute(HtmlAttributes.SRC.get());
    }

    private String getLinkToImage(ThumbnailsCutInterface type) {
        WebElement link = webDriver.findElement(By.xpath(String.format(LINK_TO_CUSTOM_IMAGE, type.getImageName())));
        return link.getAttribute(HtmlAttributes.SRC.get());
    }

    private boolean isImagePresent(CustomImageTypes type) {
        return WebDriverUtil.getInstance(webDriver).isElementPresent(String.format(LINK_TO_CUSTOM_IMAGE, type.getName()));
    }

    private boolean isAndroidProgramImagePresent(AndroidProgramThumbnails type) {
        return WebDriverUtil.getInstance(webDriver).isElementPresent(String.format(LINK_TO_CUSTOM_IMAGE, type.name()));
    }

    private boolean isIosProgramImagePresent(ThumbnailsCutInterface type) {
        return WebDriverUtil.getInstance(webDriver).isElementPresent(String.format(LINK_TO_CUSTOM_IMAGE, type.getImageName()));
    }

    private boolean isAndroidVideoImagePresent(AndroidVideoThumbnails type) {
        return WebDriverUtil.getInstance(webDriver).isElementPresent(String.format(LINK_TO_CUSTOM_IMAGE, type.name()));
    }

    private boolean isImagePresent(ThumbnailsCutInterface type) {
        return WebDriverUtil.getInstance(webDriver).isElementPresent(String.format(LINK_TO_CUSTOM_IMAGE, type.getImageName()));
    }

    private boolean isImagePresent(String name) {
        return WebDriverUtil.getInstance(webDriver).isElementPresent(String.format(LINK_TO_CUSTOM_IMAGE, name));
    }

    public Map<CustomImageTypes, String> getCustomImages(
            List<CustomImageTypes> list) {

        if (!imagePreview.isPresent()) {
            Utilities.logSevereMessage("Source " + this.getName() + " is not present");
            return new HashMap<>();
        }

        imagePreview.clickJS();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
        util().switchToWindowByNumber(2);
        waitFor().waitForPageLoad();

        Map<CustomImageTypes, String> map = new HashMap<CustomImageTypes, String>();
        for (CustomImageTypes type : list) {
            map.put(type, getLinkToCustomImage(type));
        }
        webDriver.close();
        util().switchToWindowByNumber(1);
        return map;
    }

    public Map<ThumbnailsCutInterface, String> getThumbnails(
            List<ThumbnailsCutInterface> list) {

        if (!imagePreview.isPresent()) {
            Utilities.logSevereMessage("Source " + this.getName() + " is not present");
            return new HashMap<>();
        }

        imagePreview.clickJS();
        util().switchToWindowByNumber(2);
        waitFor().waitForPageLoad();

        Map<ThumbnailsCutInterface, String> map = new HashMap<>();
        for (ThumbnailsCutInterface type : list) {
            map.put(type, getLinkToImage(type));
        }
        webDriver.close();
        util().switchToWindowByNumber(1);
        return map;
    }

    public boolean isImageStylesPresent(SoftAssert softAssert, List<ThumbnailsCutInterface> list) {
        List<String> names = new ArrayList<String>();
        list.forEach(item -> names.add(item.getImageName()));
        return verifyImagePrecence(softAssert, names);
    }

    public boolean isImageStylesPresent(List<ImageStyles> list, SoftAssert softAssert) {
        List<String> names = new ArrayList<String>();
        list.forEach(item -> names.add(item.getName()));
        return verifyImagePrecence(softAssert, names);
    }

    private boolean verifyImagePrecence(SoftAssert softAssert, List<String> list) {
        if (!imagePreview.isPresent()) {
            Utilities.logWarningMessage("Source " + this.getName() + " is not present");
            return softAssert.getTempStatus();
        }
        imagePreview.clickJS();
        util().switchToWindowByNumber(2);
        waitFor().waitForPageLoad();

        for (String name : list) {
            softAssert.assertTrue(isImagePresent(name), "Image " + name + "is not present", "Image " + name + "is present");
        }
        webDriver.close();
        util().switchToWindowByNumber(1);
        return softAssert.getTempStatus();
    }

    public boolean verifyThumbnailsPresent(
            Source source, SoftAssert softAssert) {
        try {
            imagePreview.clickJS();
            util().switchToWindowByNumber(2);
            waitFor().waitForPageLoad();

            for (ImageStyles imageStyle : source.getImageStyles()) {
                softAssert.assertTrue(isImagePresent(imageStyle.getName()), "Image " + imageStyle.getName() + " is not present", "Image " + imageStyle.getName() + " is present");
            }
            softAssert.assertEquals(source.getImageStyles().size(), this.getNumberLinks() - 1, "Number is not corrected", "Number is corrected");
            webDriver.close();
            util().switchToWindowByNumber(1);
        } catch (NoSuchElementException e) {
            WebDriverUtil.getInstance(webDriver).attachScreenshot();
            Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
        }
        return softAssert.getTempStatus();
    }

    public boolean isThreeTileSourceUploaderPresent() {
        return this.THREE_TILE_SOURCE_UPLOADER.isVisible();
    }

    public Image getImage(AndroidVideoThumbnails type) {
        Image image = new Image();
        try {
            imagePreview.clickJS();
            util().switchToWindowByNumber(2);
            waitFor().waitForPageLoad();
            image.setName(type.getImageName());
            image.setImageUrl(getLinkToImage(type));
            image.setWidth(getWidth(type));
            image.setHeight(getHeight(type));
            webDriver.close();
            util().switchToWindowByNumber(1);
        } catch (NoSuchElementException e) {
            WebDriverUtil.getInstance(webDriver).attachScreenshot();
            Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
        }
        return image;
    }

    public List<Image> getImages(List<CustomImageTypes> types) {
        List<Image> toReturn = new ArrayList<>();
        try {
            imagePreview.clickJS();
            util().switchToWindowByNumber(2);
            waitFor().waitForPageLoad();
            for (CustomImageTypes type : types) {
                Image image = new Image();
                image.setName(type.getName());
                image.setImageUrl(getLinkToCustomImage(type));
                image.setWidth(getWidth(type));
                image.setHeight(getHeight(type));
                toReturn.add(image);
            }
            webDriver.close();
            util().switchToWindowByNumber(1);
        } catch (NoSuchElementException e) {
            WebDriverUtil.getInstance(webDriver).attachScreenshot();
            Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
        }
        return toReturn;
    }

    public List<Image> getAndroidImages(List<ThumbnailsCutInterface> types) {
        List<Image> toReturn = new ArrayList<>();
        try {
            imagePreview.clickJS();
            util().switchToWindowByNumber(2);
            waitFor().waitForPageLoad();
            for (ThumbnailsCutInterface type : types) {
                Image image = new Image();
                image.setName(type.getImageName());
                image.setImageUrl(getLinkToCustomImage(type.getImageName()));
                image.setWidth(getWidth(type));
                image.setHeight(getHeight(type));
                toReturn.add(image);
            }
            webDriver.close();
            util().switchToWindowByNumber(1);
        } catch (NoSuchElementException e) {
            WebDriverUtil.getInstance(webDriver).attachScreenshot();
            Utilities.logSevereMessage(Utilities.convertStackTraceToString(e));
        }
        return toReturn;
    }

    public void uploadImage(String path) {
        if (path == null) {
            return;
        }
        if (fileOverrider.isFileUploaded()) {
            WebDriverUtil.getInstance(webDriver).scrollPageDown();
            fileOverrider.removeFile();
        }
        fileOverrider.uploadFile(path);
    }

    public MediaImage getImage() {
        MediaImage mediaImage = new MediaImage();

        if (fileOverrider.isFileUploaded()) {
            mediaImage.setName(fileOverrider.getUploadedFileName());
            mediaImage.setUrl(fileOverrider.getURLUploadedFile());
            mediaImage.setUsage("Primary");
        }
        return mediaImage;
    }

    public Boolean isOverriden() {
        Boolean status = false;
        webDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        if (overrideCheckBox.isPresent()) {
            status = overrideCheckBox.isSelected();
        }
        webDriver.manage().timeouts().implicitlyWait(WaitUtils.DEFAULT_IMPLICITY_WAIT, TimeUnit.SECONDS);
        return status;
    }


    public String getTitleLabel() {
        return titleLabel.getText();
    }

}
