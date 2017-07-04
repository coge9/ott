package com.nbcuni.test.cms.utils.transformers;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Slug;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.Associations;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.ChannelReference;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.Promotional;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.video.GlobalVideoEntity;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.Source;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.SourceMatcher;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ImageSource;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.GlobalConstants;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.ChannelReferencesJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.MediaJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.video.MpxInfoJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.video.VideoJson;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.jsonclasses.GlobalNodeJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuVideoJson;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Dzianis_Kulesh on 2/8/2017.
 * <p>
 * Class which is going to convert GlobalVideoEntity to different objects used in
 * publishing.
 */
public class VideoJsonTransformer {

    private VideoJsonTransformer() {
    }

    // Logic for Chiller brand using in Concerto API publishing.
    public static synchronized VideoJson forConcertoApiChiller(GlobalVideoEntity video) {
        VideoJson videoJson = getCommonConcertoData(video);
        videoJson.getMpxMetadata().setAdvertisingGenre(null);
        videoJson.getMpxMetadata().setTmsId(null);
        videoJson.getMpxMetadata().setShowId(null);
        // Convert dates to seconds.
        Long availableDate = videoJson.getMpxMetadata().getAvailableDate();
        Long expirationDate = videoJson.getMpxMetadata().getExpirationDate();
        Long airDate = videoJson.getMpxMetadata().getAirDate();
        if (availableDate != null) {
            videoJson.getMpxMetadata().setAvailableDate(availableDate);
        }
        if (expirationDate != null) {
            videoJson.getMpxMetadata().setExpirationDate(expirationDate);
        }
        if (airDate != null) {
            videoJson.getMpxMetadata().setAirDate(airDate);
        }
        List<MediaJson> medias = new ArrayList<>();
        for (ImageSource imageSource : video.getImageSources()) {
            medias.add(MediaJsonTransformer.getMediaJsonForConcerto(imageSource));
        }
        videoJson.setImages(medias);
        return videoJson;
    }

    // Logic for NON-Chiller brands using in Concerto API publishing.
    public static synchronized VideoJson forConcertoApiAll(GlobalVideoEntity video) {
        VideoJson videoJson = getCommonConcertoData(video);
        videoJson.setMdsuuid(null);
        List<MediaJson> medias = new ArrayList<>();
        for (ImageSource imageSource : video.getImageSources()) {
            Source source = SourceMatcher.getSourceClassInstanceByMachineName(imageSource.getMachineName(), ContentType.TVE_VIDEO);
            if (source.getConcertoUsage() != null) {
                medias.add(MediaJsonTransformer.getMediaJsonForConcertoVideo(imageSource));
            }
        }
        // add empty list of media
        videoJson.setImages(removeMediaDuplicates(medias));
        return videoJson;
    }

    private static synchronized List<MediaJson> removeMediaDuplicates(List<MediaJson> medias) {
        List<MediaJson> mediasCopy = new ArrayList<>(medias);
        for (MediaJson media : medias) {
            List<MediaJson> sameUuids = mediasCopy.stream().filter(mediaJson -> mediaJson.getUuid().equals(media.getUuid()) && mediaJson.getUsage().equals(media.getUsage())).collect(Collectors.toList());
            if (sameUuids.size() > 1) {
                mediasCopy.remove(media);
            }
        }
        return mediasCopy;
    }


    public static synchronized RokuVideoJson forSerialApi(GlobalVideoEntity video) {
        RokuVideoJson videoJson = new RokuVideoJson();
        videoJson.setTitle(video.getTitle());
        videoJson.setType("video");
        videoJson.setShowName(video.getMpxAsset().getSeriesTitle() == null ? "" : video.getMpxAsset().getSeriesTitle());
        videoJson.setPublishState(video.getPublished());
        videoJson.setSeriesCategories(video.getMpxAsset().getSeriesCategory() == null ? "" : video.getMpxAsset().getSeriesCategory());
        videoJson.setSeriesType(video.getMpxAsset().getSeriesType() == null ? "" : video.getMpxAsset().getSeriesType());
        videoJson.setDayPart(video.getMpxAsset().getDayPart());
        videoJson.setId(video.getMpxAsset().getGuid());
        videoJson.setMpxId(video.getMpxAsset().getId());
        videoJson.setFullEpisode(video.getMpxAsset().getFullEpisode());
        videoJson.setDateModified(video.getUpdatedDate().format(DateTimeFormatter.ofPattern(GlobalConstants.SERIAL_API_DATE_FORMAT)));
        videoJson.setEpisodeNumber(video.getMpxAsset().getEpisodeNumber() == null ? "" : String.valueOf(video.getMpxAsset().getEpisodeNumber()));
        videoJson.setSeasonNumber(video.getMpxAsset().getSeasonNumber() == null ? "" : String.valueOf(video.getMpxAsset().getSeasonNumber()));
        videoJson.setExpirationDate(formatDateForSerialApi(getConvertedExpirationDate(video.getMpxAsset().getExpirationDate())));
        videoJson.setAirDate(formatDateForSerialApi(getConvertedDate(video.getMpxAsset().getPubDate(), video.getUpdatedDate())));
        videoJson.setAvailableDate(formatDateForSerialApi(getConvertedDate(video.getMpxAsset().getAvailableDate(), video.getUpdatedDate())));
        videoJson.setDuration(video.getMpxAsset().getDuration());
        videoJson.setDescription(video.getMpxAsset().getDescription());
        videoJson.setExternalAdvertiserId(video.getMpxAsset().getAdvertisingId());
        videoJson.setKeywords(video.getMpxAsset().getKeywords());
        videoJson.setEntitlement(video.getMpxAsset().getEntitlement());
        videoJson.setClosedCaptionFlag(video.getMpxAsset().getClosedCaptions());
        videoJson.setRatings(video.getMpxAsset().getRatings());
        videoJson.setMediaCategories(video.getMpxAsset().getMediaCategories());
        String showId = video.getRelatedShowGuid();
        videoJson.setShowId(StringUtils.isEmpty(showId) ? "false" : showId);
        videoJson.setImages(ImageTransformer.getSerialApiVideo(video.getImageSources()));
        return videoJson;
    }

    // Common logic for Concerto API publishing.
    private static synchronized VideoJson getCommonConcertoData(GlobalVideoEntity video) {
        VideoJson videoJson = new VideoJson();
        videoJson.setUuid(video.getMpxAsset().getGuid());
        videoJson.setTitle(video.getTitle());
        videoJson.setSlug(video.getSlugInfo().getSlugValue());
        videoJson.setItemType(ItemTypes.VIDEO.getItemType());
        videoJson.setRevision(video.getGeneralInfo().getRevision());
        videoJson.setPublished(video.getPublished());
        videoJson.setFullEpisode(video.getMpxAsset().getFullEpisode());
        videoJson.setProgram(new ChannelReferencesJson().getObject(video.getAssociations().getChannelReferenceAssociations().getChannelReference()));
        videoJson.setPromoKicker("".equals(video.getPromotional().getPromotionalKicker()) ? null : video.getPromotional().getPromotionalKicker());
        videoJson.setPromoDescription("".equals(video.getPromotional().getPromotionalDescription()) ? null : video.getPromotional().getPromotionalDescription());
        videoJson.setPromoTitle("".equals(video.getPromotional().getPromotionalTitle()) ? null : video.getPromotional().getPromotionalTitle());

        MpxInfoJson mpxInfo = new MpxInfoJson().getObject(video.getMpxAsset());
        if (StringUtils.isEmpty(mpxInfo.getExternalAdvertiserid())) {
            mpxInfo.setExternalAdvertiserid(null);
        }
        if (mpxInfo.getKeywords() == null) {
            mpxInfo.setKeywords("");
        }
        if (mpxInfo.getDescription() == null) {
            mpxInfo.setDescription("");
        }
        if (mpxInfo.getDuration() == null) {
            mpxInfo.setDuration(0);
        }
        mpxInfo.setShowId(video.getAssociations().getChannelReferenceAssociations().getChannelReference().getSeries());
        mpxInfo.setExpirationDate(formatDateForConcertoApi(getConvertedExpirationDate(mpxInfo.getExpirationDate())));
        mpxInfo.setAvailableDate(formatDateForConcertoApi(getConvertedDate(video.getMpxAsset().getAvailableDate(), video.getUpdatedDate())));
        mpxInfo.setAirDate(formatDateForConcertoApi(getConvertedDate(video.getMpxAsset().getPubDate(), video.getUpdatedDate())));
        videoJson.setMpxMetadata(mpxInfo);
        videoJson.setTags(new ArrayList<>());
        videoJson.setCategories(new ArrayList<>());
        videoJson.setGradient(video.getGradient());
        return videoJson;
    }


    private static synchronized ZonedDateTime getConvertedExpirationDate(Long timestamp) {
        ZonedDateTime date;
        if (timestamp != null && timestamp != 0) {
            date = ZonedDateTime.ofInstant(Instant.ofEpochSecond(timestamp), GlobalConstants.NY_ZONE);

        } else {
            date = ZonedDateTime.now(GlobalConstants.NY_ZONE);
            date = date.plusYears(10);
        }
        return date;
    }

    private static synchronized ZonedDateTime getConvertedDate(Long timestamp, ZonedDateTime nodeUpdated) {
        ZonedDateTime date;
        if (timestamp != null && timestamp != 0) {
            date = ZonedDateTime.ofInstant(Instant.ofEpochSecond(timestamp), GlobalConstants.NY_ZONE);
        } else {
            date = nodeUpdated;
        }
        return date;
    }

    private static synchronized String formatDateForSerialApi(ZonedDateTime date) {
        return date.format(DateTimeFormatter.ofPattern(GlobalConstants.SERIAL_API_DATE_FORMAT));
    }

    private static synchronized Long formatDateForConcertoApi(ZonedDateTime date) {
        return date.toInstant().toEpochMilli();
    }

    public static GlobalVideoEntity getGlobalVideoEntity(GlobalNodeJson globalNodeJson, String brand) {
        GlobalVideoEntity globalVideoEntity = new GlobalVideoEntity();
        globalVideoEntity.setTitle(globalNodeJson.getTitle());
        globalVideoEntity.setMpxAsset(globalNodeJson.getMpxAsset());
        globalVideoEntity.setUpdatedDate(globalNodeJson.getUpdatedDate());
        globalVideoEntity.setRelatedShowGuid(globalNodeJson.getRelatedShowId());
        Promotional promotional = new Promotional();
        promotional.setPromotionalKicker(globalNodeJson.getFeatureCarouselCta());
        promotional.setPromotionalTitle(globalNodeJson.getFeatureCarouselHeadline());
        promotional.setPromotionalDescription(null);
        globalVideoEntity.setPromotional(promotional);
        Slug slug = new Slug();
        slug.setAutoSlug(globalNodeJson.getUrlPath());
        slug.setSlugValue(globalNodeJson.getSlug());
        globalVideoEntity.setSlugInfo(slug);
        globalVideoEntity.setImageSources(urlTransformer(globalNodeJson.getImageSources(), brand));
        Associations associations = new Associations();
        associations.getChannelReferenceAssociations().setChannelReference(new ChannelReference().setSeries(globalNodeJson.getRelatedShowId())
                .setItemType("series"));
        globalVideoEntity.setAssociations(associations);
        globalVideoEntity.getGeneralInfo().setRevision(Integer.parseInt(globalNodeJson.getVid()));
        globalVideoEntity.getGeneralInfo().setUuid(globalNodeJson.getUuid());
        globalVideoEntity.setPublished(globalNodeJson.getPublished());
        globalVideoEntity.setGradient(globalNodeJson.getGradient());
        return globalVideoEntity;
    }

    private static synchronized List<ImageSource> urlTransformer(List<ImageSource> imageSources, String brand) {
        String urlPart = String.format("%ssites/%s/files/", Config.getInstance().getRokuHomePage(brand), brand);
        Config.getInstance().getRokuHomePage(brand);
        for (ImageSource source : imageSources) {
            String url = source.getImageUrl();
            url = url.replace("public://", urlPart);
            source.setImageUrl(url);
        }
        return imageSources;

    }

}
