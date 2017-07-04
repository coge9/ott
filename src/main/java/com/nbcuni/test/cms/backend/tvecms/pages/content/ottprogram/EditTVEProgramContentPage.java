package com.nbcuni.test.cms.backend.tvecms.pages.content.ottprogram;

import com.google.common.base.Strings;
import com.nbcuni.test.cms.backend.chiller.pages.contenttype.DevelPage;
import com.nbcuni.test.cms.backend.tvecms.pages.content.MediaContentPage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.assetlibrary.FilesMetadata;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.iosstyles.factory.FilesMetadataProgramConcertoCreationStrategy;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.media.MediaImage;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.Series;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ImageSource;
import com.nbcuni.test.cms.elements.PublishBlock;
import com.nbcuni.test.cms.elements.VideoContentImage;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.ContentTabs;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.ProgrammingTimeframe;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Type;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CustomImageTypes;
import com.nbcuni.test.cms.pageobjectutils.tvecms.concerto.ProgramConcertoImageSourceType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.mpxdata.MpxInfoFields;
import com.nbcuni.test.cms.utils.AppLib;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.images.ImagesJson;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.PlatformApi;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuProgramJson;
import com.nbcuni.test.cms.utils.thumbnails.chiller.ThumbnailsCutInterface;
import com.nbcuni.test.webdriver.CustomWebDriver;
import com.nbcuni.test.webdriver.Utilities;
import org.openqa.selenium.support.FindBy;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditTVEProgramContentPage extends MediaContentPage {

    public static final String PAGE_TITLE = "Edit TVE Program";
    @FindBy(id = "edit-group_roku_images")
    private ProgramRokuImagesTab rokuImagesTab;
    @FindBy(id = "edit-group_roku_sqs_images")
    private ProgramRokuSqsImagesTab rokuSqsImagesTab;
    @FindBy(id = "edit-group_android_images")
    private ProgramAndroidImagesTab androidImagesTab;
    @FindBy(id = "edit-group_ios_images")
    private ProgramIosImagesTab iosImagesTab;
    @FindBy(id = "edit-group_appletv_images")
    private ProgramAppleTVImagesTab appleTVImagesTab;
    @FindBy(id = "edit-group_roku_images")
    private ProgramRokuImagesTab newRokuImagesTab;
    @FindBy(id = "edit-group_xboxone_images")
    private ProgramXboxOneImagesTab xboxOneImagesTab;
    @FindBy(id = "edit-group_firetv_images")
    private ProgramFireTvImagesTab fireTvImagesTab;
    private PublishBlock publishBlock;

    public EditTVEProgramContentPage(final CustomWebDriver webDriver, final AppLib aid) {
        super(webDriver, aid);
        publishBlock = new PublishBlock(webDriver);
    }

    @Override
    public PublishBlock elementPublishBlock() {
        return publishBlock;
    }

    @Override
    public List<String> verifyPage() {
        Utilities.logInfoMessage("Verify page " + getPageUrl() + " for missed elements");
        final ArrayList<String> missedElements = new ArrayList<String>();
        final Class<?> thisClass = this.getClass();
        final Field[] fields = thisClass.getDeclaredFields();
        for (final Field field : fields) {
            verifySingleField(missedElements, field);
        }
        return missedElements;
    }

    private void verifySingleField(ArrayList<String> missedElements, Field field) {
        try {
            if (field.getType().equals(String.class)
                    && !"PAGE_TITLE".equals(field.getName())) {
                final String fieldLocator = field.get(this).toString();
                if (!webDriver.isVisible(String.format(fieldLocator))) {
                    missedElements.add("Element:  " + field.getName()
                            + " , Locator: " + fieldLocator);
                    missedElements.trimToSize();
                }
            }
        } catch (final IllegalArgumentException e) {
            Utilities.logSevereMessageThenFail(e.getClass().getName() + ": " + Utilities.convertStackTraceToString(e));
        } catch (final IllegalAccessException e) {
            Utilities.logSevereMessageThenFail(e.getClass().getName() + ": " + Utilities.convertStackTraceToString(e));
        }
    }

    public Map<String, String> collectMPXData() {
        final Map<String, String> episodeData = new HashMap<String, String>();
        episodeData.put("Title", this.getTitle());
        this.onMPXInfoTab();
        episodeData.putAll(mpxInfoTab.collectMetaData());
        return episodeData;
    }

    public RokuProgramJson getProgramObjectFromNodeMetadata() {
        RokuProgramJson programJson = new RokuProgramJson();
        programJson.setTitle(this.getTitle());
        this.onMPXInfoTab();
        programJson = mpxInfoTab
                .setMetaDataToProgramObject(programJson);
        return programJson;
    }

    public RokuProgramJson getProgramObjectForDevelopmentInstance() {
        RokuProgramJson programJson = getProgramObjectFromNodeMetadata();
        programJson.addImages(onRokuImagesTab().getAllImages());
        programJson.addImages(onAndroidImagesTab().getAllImages());
        return programJson;
    }

    public ProgramRokuImagesTab onRokuImagesTab() {
        tabsGroup.openTabByName(ContentTabs.ROKU_IMAGES.getName());
        return rokuImagesTab;
    }

    public ProgramRokuSqsImagesTab onRokuSqsImagesTab() {
        tabsGroup.openTabByName(ContentTabs.ROKU_IMAGES.getName());
        return rokuSqsImagesTab;
    }

    public ProgramAndroidImagesTab onAndroidImagesTab() {
        tabsGroup.openTabByName(ContentTabs.ANDROID_IMAGES.getName());
        return androidImagesTab;
    }

    public ProgramIosImagesTab onIosImagesTab() {
        tabsGroup.openTabByName(ContentTabs.IOS_IMAGES.getName());
        return iosImagesTab;
    }

    public ProgramAppleTVImagesTab onAppleTVImagesTab() {
        tabsGroup.openTabByName(ContentTabs.APPLETV_IMAGES.getName());
        return appleTVImagesTab;
    }

    public ProgramFireTvImagesTab onFireTVImagesTab() {
        tabsGroup.openTabByName(ContentTabs.FIRETV_IMAGES.getName());
        return fireTvImagesTab;
    }

    public ProgramXboxOneImagesTab onXboxOneImagesTab() {
        tabsGroup.openTabByName(ContentTabs.XBOXONE_IMAGES.getName());
        return xboxOneImagesTab;
    }

    public List<FilesMetadata> updateMetadataFoIos(List<FilesMetadata> metadata) {
        Map<String, VideoContentImage> imagesSourceMap = onIosImagesTab().getIosSourceImages();
        Map<ThumbnailsCutInterface, String> imageStyles = onIosImagesTab().getActualIosImages();
        for (FilesMetadata filesMetadata : metadata) {
            List<MediaImage> styles = new ArrayList<>();
            String styleTypeValue = filesMetadata.getImageGeneralInfo().getImageSourceType().getType();
            for (Map.Entry<ThumbnailsCutInterface, String> entry : imageStyles.entrySet()) {
                String styleTypeName = entry.getKey().getMpxSourceSize();
                if (styleTypeName.equals(styleTypeValue)) {
                    MediaImage mediaImage = new MediaImage();
                    mediaImage.setUrl(entry.getValue().substring(0, entry.getValue().indexOf("itok="))).setName(entry.getKey().getImageName());
                    mediaImage.setWidth(entry.getKey().getRequiredAppWidth());
                    mediaImage.setHeight(entry.getKey().getRequiredAppHeight());
                    styles.add(mediaImage);
                }
            }

            filesMetadata.getImageGeneralInfo().setTitle(imagesSourceMap.get(styleTypeValue).getImageSourceName());
            filesMetadata.getImageGeneralInfo().setImageHref(imagesSourceMap.get(styleTypeValue).getLinkToSourceImage());
            filesMetadata.getImageGeneralInfo().setImageStyles(styles);
        }
        return metadata;
    }

    public List<FilesMetadata> updateMetadataForAppleTV(List<FilesMetadata> metadata) {
        Map<String, VideoContentImage> imagesSourceMap = onAppleTVImagesTab().getAppleTVSourceImages();
        Map<ThumbnailsCutInterface, String> imageStyles = onAppleTVImagesTab().getActualAppleTVImages();
        for (FilesMetadata filesMetadata : metadata) {
            List<MediaImage> styles = new ArrayList<>();
            String styleTypeValue = filesMetadata.getImageGeneralInfo().getImageSourceType().getType();
            for (Map.Entry<ThumbnailsCutInterface, String> entry : imageStyles.entrySet()) {
                String styleTypeName = entry.getKey().getMpxSourceSize();
                if (styleTypeName.equals(styleTypeValue)) {
                    MediaImage mediaImage = new MediaImage();
                    mediaImage.setUrl(entry.getValue().substring(0, entry.getValue().indexOf("itok="))).setName(entry.getKey().getImageName());
                    mediaImage.setWidth(entry.getKey().getRequiredAppWidth());
                    mediaImage.setHeight(entry.getKey().getRequiredAppHeight());
                    styles.add(mediaImage);
                }
            }

            filesMetadata.getImageGeneralInfo().setTitle(imagesSourceMap.get(styleTypeValue).getImageSourceName());
            filesMetadata.getImageGeneralInfo().setImageHref(imagesSourceMap.get(styleTypeValue).getLinkToSourceImage());
            filesMetadata.getImageGeneralInfo().setImageStyles(styles);
        }
        return metadata;
    }

    public List<FilesMetadata> updateMetadataFoRoku(List<FilesMetadata> metadata) {
        Map<String, VideoContentImage> imagesSourceMap = onRokuImagesTab().getRokuSourceImages();

        for (FilesMetadata filesMetadata : metadata) {
            String styleTypeValue = filesMetadata.getImageGeneralInfo().getImageSourceType().getType();
            filesMetadata.getImageGeneralInfo().setTitle(imagesSourceMap.get(styleTypeValue).getImageSourceName());
            filesMetadata.getImageGeneralInfo().setImageHref(imagesSourceMap.get(styleTypeValue).getLinkToSourceImage());

            String hrefTemplate = filesMetadata.getImageGeneralInfo().getImageHref().replaceFirst("files/", "files/styles/%s/public/");
            StringBuilder hrefBuilder = new StringBuilder(hrefTemplate);
            hrefBuilder.append("?");
            List<CustomImageTypes> customImageTypes = CustomImageTypes.getStyleTypes(ProgramConcertoImageSourceType.getConcertoImageSourceType(styleTypeValue));
            List<MediaImage> styles = new ArrayList<>();
            for (CustomImageTypes imageStyle : customImageTypes) {
                MediaImage mediaImage = new MediaImage();
                mediaImage
                        .setUrl(String.format(hrefBuilder.toString(), imageStyle.getName()))
                        .setName(imageStyle.getName())
                        .setWidth(imageStyle.getWidth())
                        .setHeight(imageStyle.getHeight());
                styles.add(mediaImage);
            }
            filesMetadata.getImageGeneralInfo().setImageStyles(styles);
        }
        return metadata;
    }

    public List<ImagesJson> getImageJson() {
        List<ImagesJson> expectedImagesJsons = new ArrayList<>();

        FilesMetadataProgramConcertoCreationStrategy metadataCreationStrategy = new FilesMetadataProgramConcertoCreationStrategy();

        List<FilesMetadata> filesMetadataListIos = metadataCreationStrategy.getListOfSourcesMetadaObject(
                ProgramConcertoImageSourceType.SOURCE_1600x900,
                ProgramConcertoImageSourceType.SOURCE_1965x1108
        );
        List<FilesMetadata> filesMetadataListRoku = metadataCreationStrategy.getListOfSourcesMetadaObject(
                ProgramConcertoImageSourceType.SOURCE_3TILE
        );
        List<FilesMetadata> filesMetadataListAppleTV = metadataCreationStrategy.getListOfSourcesMetadaObject(
                ProgramConcertoImageSourceType.SOURCE_600x600,
                ProgramConcertoImageSourceType.SOURCE_771x292,
                ProgramConcertoImageSourceType.SOURCE_1600x900_APPLETV,
                ProgramConcertoImageSourceType.SOURCE_1704x440,
                ProgramConcertoImageSourceType.SOURCE_1920x1080,
                ProgramConcertoImageSourceType.SOURCE_1920x486
        );

        List<FilesMetadata> filesMetadataListUpdated = this.updateMetadataFoIos(filesMetadataListIos); //set styles, href source, title source
        filesMetadataListUpdated.addAll(this.updateMetadataFoRoku(filesMetadataListRoku));
        filesMetadataListUpdated.addAll(this.updateMetadataForAppleTV(filesMetadataListAppleTV));
        DevelPage develPage = this.openDevelPage();
        for (FilesMetadata filesMetadata : filesMetadataListUpdated) {
            filesMetadata = develPage.getDataForConcertoImage(filesMetadata);
            ImagesJson imagesJson = new ImagesJson(filesMetadata);
            expectedImagesJsons.add(imagesJson);
        }
        develPage.openEditPage();
        return expectedImagesJsons;
    }

    public void updateBasicTabForIos(Series series) {
        onBasicBlock().fillValuesForProgram(series);
        clickSave();
    }

    public Series updateSeriesForIos(Series series) {
        Map<String, String> metadataMap = onMPXInfoTab().collectMetaData();
        String shortDescription = metadataMap.get(MpxInfoFields.SHORT_DESCRIPTION.getName());
        series.getSeriesInfo().setGiud(metadataMap.get(MpxInfoFields.GUID.getName()));
        series.getSeriesInfo().setTmsId(metadataMap.get(MpxInfoFields.TMS_ID.getName()));
        series.getSeriesInfo().setRegularlyScheduledDuration(Integer.valueOf(metadataMap.get(MpxInfoFields.VIDEOLENGTH.getName())));
        series.getGeneralInfo().setShortDescription(Strings.emptyToNull(shortDescription));
        String mediumDescription = metadataMap.get(MpxInfoFields.DESCRIPTION.getName());
        series.getGeneralInfo().setMediumDescription(Strings.emptyToNull(mediumDescription));
        series.getGeneralInfo().setTitle(onBasicBlock().getTitle().getValue());
        series.getGeneralInfo().setSortTitle(createSortTitle(series.getGeneralInfo().getTitle()));
        series.getSlugInfo().setSlugValue(onSlugBlock().getSlug().getSlugValue());

        String dayPart = metadataMap.get(MpxInfoFields.DAYPART.getName());
        series.getSeriesInfo().setProgrammingTimeframe(ProgrammingTimeframe.getValueByName(dayPart));

        String seriesType = metadataMap.get(MpxInfoFields.SERIES_TYPE.getName());
        series.getSeriesInfo().setType(Type.getValueByName(seriesType));
        Series basic = onBasicBlock().getData();
        series.getGeneralInfo()
                .setLongDescription(Strings.emptyToNull(basic.getGeneralInfo().getLongDescription()))
                .setSubhead(basic.getGeneralInfo().getSubhead());
        series.setPromotional(basic.getPromotional());
        series.getSeriesInfo()
                .setStatus(basic.getSeriesInfo().getStatus())
                .setGenre(basic.getSeriesInfo().getGenre());
        series.setPublished(onPublishingOptionsTab().getPublishedState());
        List<MediaImage> mediaImages = onIosImagesTab().getMediaImages();
        DevelPage develPage = openDevelPage();
        series.getGeneralInfo().setUuid(develPage.getUuid());
        series.getGeneralInfo().setRevision(Integer.parseInt(develPage.getVid()));
        mediaImages = develPage.getMediaUuidsByMachineName(mediaImages);
        openEditPage();
        series.setMediaImages(mediaImages);
        return series;
    }


    // Collect all image sources from UI.
    public List<ImageSource> getImageSources(String brand) {
        List<ImageSource> imageSources = new ArrayList<ImageSource>();
        if (this.isTabPresent(ContentTabs.ANDROID_IMAGES)) {
            imageSources.addAll(getSourcesForPlatform(onAndroidImagesTab().getAllSourcesBlocks(), CmsPlatforms.ANDROID));
        }
        if (this.isTabPresent(ContentTabs.APPLETV_IMAGES)) {
            imageSources.addAll(getSourcesForPlatform(onAppleTVImagesTab().getAllSourcesBlocks(), CmsPlatforms.APPLETV));
        }
        if (this.isTabPresent(ContentTabs.IOS_IMAGES)) {
            imageSources.addAll(getSourcesForPlatform(onIosImagesTab().getAllSourcesBlocks(), CmsPlatforms.IOS));
        }
        if (this.isTabPresent(ContentTabs.ROKU_IMAGES)) {
            boolean isRokuPublishedToConcerto = new PlatformApi(brand).isRokuPublishedToConcerto();
            if (isRokuPublishedToConcerto) {
                imageSources.addAll(getSourcesForPlatform(onRokuSqsImagesTab().getAllSourcesBlocks(), CmsPlatforms.ROKU_SQS));
            } else {
                imageSources.addAll(getSourcesForPlatform(onRokuImagesTab().getAllSourcesBlocks(), CmsPlatforms.ROKU));
            }
        }
        if (this.isTabPresent(ContentTabs.FIRETV_IMAGES)) {
            imageSources.addAll(getSourcesForPlatform(onFireTVImagesTab().getAllSourcesBlocks(), CmsPlatforms.FIRETV));
        }
        if (this.isTabPresent(ContentTabs.XBOXONE_IMAGES)) {
            imageSources.addAll(getSourcesForPlatform(onXboxOneImagesTab().getAllSourcesBlocks(), CmsPlatforms.XBOXONE));
        }
        return imageSources;
    }

    private List<ImageSource> getSourcesForPlatform(List<VideoContentImage> blocks, CmsPlatforms platform) {
        List<ImageSource> imageSources = new ArrayList<ImageSource>();
        for (VideoContentImage block : blocks) {
            if (block.isSourcePresent()) {
                ImageSource source = new ImageSource();
                source.setName(block.getTitleLabel());
                source.setImageUrl(block.getLinkToSourceImage());
                source.setImageName(block.getImageSourceName());
                source.setOverriden(block.isOverriden());
                source.setPlatform(platform);
                imageSources.add(source);
            }
        }
        return imageSources;
    }

}
