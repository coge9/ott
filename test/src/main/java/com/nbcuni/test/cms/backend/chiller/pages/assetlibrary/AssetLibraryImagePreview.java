package com.nbcuni.test.cms.backend.chiller.pages.assetlibrary;

import com.nbcuni.test.cms.backend.chiller.block.assetlibrary.ImagePreview;
import com.nbcuni.test.cms.backend.tvecms.pages.MainRokuAdminPage;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.thumbnails.chiller.ChillerImageEntity;
import com.nbcuni.test.cms.utils.thumbnails.chiller.ChillerThumbnails;
import com.nbcuni.test.cms.utils.webdriver.WaitUtils;
import com.nbcuni.test.cms.utils.webdriver.WebDriverUtil;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Aleksandra_Lishaeva on 4/25/16.
 */
public class AssetLibraryImagePreview extends MainRokuAdminPage {

    private static final String IMAGES_XPATH = ".//h2[text()='%s']/following-sibling::*[1]";
    @FindBy(xpath = ".//div[@class='focal_point_examples']/div")
    private List<ImagePreview> images;
    @FindBy(tagName = "body")
    private ImagePreview originalImage;

    private WebDriverUtil util = WebDriverUtil.getInstance(webDriver);

    public AssetLibraryImagePreview(CustomWebDriver webDriver, AppLib aid) {
        super(webDriver, aid);
        WaitUtils.perform(webDriver).waitForPageLoad();
    }

    public List<String> getImagesNames() {
        List<String> names = new ArrayList<>();
        for (ImagePreview image : images) {
            names.add(image.getImageName());
        }
        return names;
    }

    public boolean isChillerCustomImagesPresent(SoftAssert softAssert, List<ChillerThumbnails> list) {

        for (ChillerThumbnails type : list) {
            softAssert.assertTrue(isChillerImagePresent(type), "Image " + type.name() + " is not present", "Image " + type.name() + "is present");
        }
        webDriver.close();
        util.switchToWindowByNumber(1);
        return softAssert.getTempStatus();
    }

    public Map<ChillerThumbnails, ChillerImageEntity> getChillerImages(List<ChillerThumbnails> list) {
        Map<ChillerThumbnails, ChillerImageEntity> map = new HashMap<ChillerThumbnails, ChillerImageEntity>();
        try {
            for (ChillerThumbnails type : list) {
                if (!type.equals(ChillerThumbnails.ORIGINAL)) {
                    map.put(type, getChillerImageInfo(type));
                }
            }
            webDriver.close();
            util.switchToWindowByNumber(1);
        } catch (Exception e) {
            WebDriverUtil.getInstance(webDriver).attachScreenshot();
            Utilities.logSevereMessageThenFail(Utilities.convertStackTraceToString(e));
        }
        return map;
    }

    public ChillerImageEntity getOriginalChillerImage() {
        ChillerImageEntity image = null;
        try {
            image = getOriginalImageInfo();
            webDriver.close();
            util.switchToWindowByNumber(1);
        } catch (Exception e) {
            WebDriverUtil.getInstance(webDriver).attachScreenshot();
            Utilities.logSevereMessageThenFail(Utilities.convertStackTraceToString(e));
        }
        return image;
    }

    private boolean isChillerImagePresent(ChillerThumbnails type) {
        for (ImagePreview image : images) {
            if (image.getImageName().equalsIgnoreCase(type.getAssetTypes())) {
                return true;
            }
        }
        return false;
    }

    private ChillerImageEntity getChillerImageInfo(ChillerThumbnails type) {
        ChillerImageEntity imageEntity = new ChillerImageEntity();
        ImagePreview image = new ImagePreview(webDriver.findElementByXPath(String.format(IMAGES_XPATH, type.getAssetTypes())));
        imageEntity.setSrc(image.getImageSrc());
        imageEntity.setHeight(image.getHeight());
        imageEntity.setWidth(image.getWidth());
        return imageEntity;
    }

    private ChillerImageEntity getOriginalImageInfo() {
        ChillerImageEntity chillerImageEntity = new ChillerImageEntity();
        for (ImagePreview imagePreview : images) {
            if (imagePreview.getImageName().equalsIgnoreCase(ChillerThumbnails.ORIGINAL.getAssetTypes())) {
                chillerImageEntity.setSrc(originalImage.getImageSrc());
                chillerImageEntity.setWidth(originalImage.getWidth());
                chillerImageEntity.setHeight(originalImage.getHeight());
                break;
            }
        }
        return chillerImageEntity;
    }

}
