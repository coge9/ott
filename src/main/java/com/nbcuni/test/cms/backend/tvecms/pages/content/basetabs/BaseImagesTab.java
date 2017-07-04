package com.nbcuni.test.cms.backend.tvecms.pages.content.basetabs;

import com.nbcuni.test.cms.elements.CheckBox;
import com.nbcuni.test.cms.elements.FileUploader;
import com.nbcuni.test.cms.elements.VideoContentImage;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CustomImageTypes;
import com.nbcuni.test.cms.utils.fielddecorator.AbstractContainer;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.util.Map;

public class BaseImagesTab extends AbstractContainer {

    private static final String THREE_TILE_LOCATOR =
            "//div[contains(@class,'form-item-field-3-tile-source-und-0') and contains(@class,'form-type-managed-file')]";
    private static final String ONE_TILE_LOCATOR =
            "//div[contains(@class,'form-item-field-1-tile-source-und-0') and contains(@class,'form-type-managed-file')]";

    protected FileUploader oneTileSource = new FileUploader(webDriver, By.id("edit-field-1-tile-source"));

    @FindBy(xpath = THREE_TILE_LOCATOR)
    protected VideoContentImage threeTitleArea;

    @FindBy(xpath = ONE_TILE_LOCATOR)
    protected VideoContentImage oneTitleArea;

    @FindBy(xpath = ".//div[contains(@class,'1-tile-source-und-0-ott-media-override-image')]")
    protected CheckBox overrideOneTileImageSource;

    public VideoContentImage elementThreeTitleArea() {
        return threeTitleArea;
    }

    public Map<CustomImageTypes, String> getThreeTileCustomLinks() {
        return threeTitleArea.getCustomImages(CustomImageTypes.getProgramThreeTileThreeTypes());
    }

    public VideoContentImage elementOneTileArea() {
        return oneTitleArea;
    }

    public FileUploader elementFeatureCarouselHero() {
        return oneTileSource;
    }

    public String uploadOneTileSource(String... path) {
        if (path.length != 0) {
            return oneTileSource.uploadFile(path[0]);
        } else {
            return oneTileSource.uploadRandomImageFile(200, 150);
        }
    }

    public CheckBox elementOverrideImage() {
        return overrideOneTileImageSource;
    }

    public boolean getOverrideOneTileImageSourceImageStatus() {
        return overrideOneTileImageSource.isSelected();
    }

    public void setOverrideOneTileImageSourceImageStatus(boolean status) {
        overrideOneTileImageSource.selectStatus(status);
    }

    public void overrideOneTileImageSourceImage(String... path) {
        this.setOverrideOneTileImageSourceImageStatus(true);
        this.elementFeatureCarouselHero().removeFile();
        this.uploadOneTileSource(path);
    }

}
