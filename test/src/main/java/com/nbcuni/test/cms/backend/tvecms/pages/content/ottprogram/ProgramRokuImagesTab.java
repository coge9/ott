package com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram;

import com.nbcuni.test.cms.backend.tvecms.pages.content.basetabs.BaseImagesTab;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.programsources.rokusources.Program1TileShowPageSource;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.programsources.rokusources.Program1TileSource;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.programsources.rokusources.Program3TileProgram3Source;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.programsources.rokusources.Program3TileSource;
import com.nbcuni.test.cms.elements.FileUploader;
import com.nbcuni.test.cms.elements.TextField;
import com.nbcuni.test.cms.elements.VideoContentImage;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CustomImageTypes;
import com.nbcuni.test.cms.utils.SoftAssert;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.Image;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.nbcuni.test.cms.pageobjectutils.tvecms.concerto.ProgramConcertoImageSourceType.SOURCE_3TILE;

public class ProgramRokuImagesTab extends BaseImagesTab {

    private static final String ONE_TILE_LOCATOR_SHOW_PAGE =
            "//div[contains(@class,'form-item-field-1-tile-show-page-source-und-0') and contains(@class,'form-type-managed-file')]";
    private static final String THREE_TILE_LOCATOR_SHOW_PAGE =
            "//div[contains(@class,'form-item-field-3-tile-program-3-source-und-0') and contains(@class,'form-type-managed-file')]";

    @FindBy(xpath = ONE_TILE_LOCATOR_SHOW_PAGE)
    private VideoContentImage oneTitleShowPageArea;

    @FindBy(xpath = THREE_TILE_LOCATOR_SHOW_PAGE)
    private VideoContentImage threeTileShowPageArea;

    @FindBy(xpath = ".//*[contains(@class,'form-type-managed-file')]")
    private List<VideoContentImage> allsources;

    private TextField programHeroCTA = new TextField(webDriver, By.id("edit-field-program-hero-cta-und-0-value"));

    public VideoContentImage elementOneTitleArea() {
        return oneTitleShowPageArea;
    }

    public Map<CustomImageTypes, String> getOneTileProgramOneCustomLinks() {
        return oneTitleArea.getCustomImages(CustomImageTypes.getProgramOneTileProgramOneTypes());
    }

    public VideoContentImage elementThreeTitleProgramThreeArea() {
        return threeTileShowPageArea;
    }

    public Map<CustomImageTypes, String> getOneTileProgramTwoCustomLinks() {
        return oneTitleShowPageArea.getCustomImages(CustomImageTypes.getProgramOneTileProgramTwoTypes());
    }

    @Override
    public Map<CustomImageTypes, String> getThreeTileCustomLinks() {
        Map<CustomImageTypes, String> map = new HashMap<>();
        map.putAll(threeTitleArea.getCustomImages(CustomImageTypes.getProgramThreeTileOneTypes()));
        map.putAll(threeTileShowPageArea.getCustomImages(CustomImageTypes.getProgramThreeTileThreeTypes()));
        return map;
    }

    public Map<CustomImageTypes, String> getThreeTileProgramCustomLinks() {
        Map<CustomImageTypes, String> map = new HashMap<>();
        map.putAll(threeTitleArea.getCustomImages(CustomImageTypes.getProgramThreeTileOneTypes()));
        return map;
    }

    public void isOneTileProgramOneCustomLinkPresent(SoftAssert softAssert) {
        oneTitleArea.verifyThumbnailsPresent(new Program1TileSource(), softAssert);
    }

    public void isOneTileProgramTwoCustomLinkPresent(SoftAssert softAssert) {
        oneTitleShowPageArea.verifyThumbnailsPresent(new Program1TileShowPageSource(), softAssert);
    }

    public void isThreeTileOneCustomLinkPresent(SoftAssert softAssert) {
        threeTitleArea.verifyThumbnailsPresent(new Program3TileSource(), softAssert);
    }

    public void isThreeTileThreeCustomLinkPresent(SoftAssert softAssert) {
        threeTileShowPageArea.verifyThumbnailsPresent(new Program3TileProgram3Source(), softAssert);
    }

    public Map<CustomImageTypes, String> getThreeTileProgramThreeCustomLinks() {
        return threeTileShowPageArea.getCustomImages(CustomImageTypes.getProgramThreeTileThreeTypes());
    }

    public void typeProgramHeroCTA(String ctaText) {
        programHeroCTA.enterText(ctaText);
    }

    public String getProgramHeroCTA() {
        return programHeroCTA.getValue();
    }

    @Override
    public VideoContentImage elementOneTileArea() {
        return oneTitleArea;
    }

    @Override
    public FileUploader elementFeatureCarouselHero() {
        return oneTileSource;
    }

    public Map<String, VideoContentImage> getRokuSourceImages() {
        Map<String, VideoContentImage> toReturn = new HashMap<>();
        toReturn.put(SOURCE_3TILE.getType(), threeTitleArea);
        return toReturn;
    }

    public List<Image> getAllImages() {
        List<Image> toReturn = new ArrayList<>();
        toReturn.addAll(threeTileShowPageArea.getImages(CustomImageTypes.getProgramThreeTileThreeTypes()));
        toReturn.addAll(oneTitleShowPageArea.getImages(CustomImageTypes.getProgramOneTileProgramTwoTypes()));
        toReturn.addAll(oneTitleArea.getImages(CustomImageTypes.getProgramOneTileProgramOneTypes()));
        toReturn.addAll(threeTitleArea.getImages(CustomImageTypes.getProgramThreeTileOneTypes()));
        return toReturn;
    }

    public List<VideoContentImage> getAllSourcesBlocks() {
        return allsources;
    }


    public void verifyThumbnails(SoftAssert softAssert) {
        softAssert.assertTrue(oneTitleArea.isSourcePresent(), "Image source for " + oneTitleArea.getName() + " is not present");
        if (oneTitleArea.isSourcePresent()) {
            isOneTileProgramOneCustomLinkPresent(softAssert);
        }
        softAssert.assertTrue(oneTitleShowPageArea.isSourcePresent(), "Image source for " + oneTitleShowPageArea.getName() + " is not present");
        if (oneTitleShowPageArea.isSourcePresent()) {
            isOneTileProgramTwoCustomLinkPresent(softAssert);
        }
        softAssert.assertTrue(threeTitleArea.isSourcePresent(), "Image source for " + threeTitleArea.getName() + " is not present");
        if (threeTitleArea.isSourcePresent()) {
            isThreeTileOneCustomLinkPresent(softAssert);
        }
        softAssert.assertTrue(threeTileShowPageArea.isSourcePresent(), "Image source for " + threeTileShowPageArea.getName() + " is not present");
        if (threeTileShowPageArea.isSourcePresent()) {
            isThreeTileThreeCustomLinkPresent(softAssert);
        }
    }

}
