package com.nbcuni.test.cms.utils.transformers;

import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.Slug;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.Associations;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.ChannelReference;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.associations.Promotional;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.links.ExternalLinksInfo;
import com.nbcuni.test.cms.bussinesobjects.chiller.contenttype.series.GlobalProgramEntity;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.Source;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.SourceMatcher;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ImageSource;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Genre;
import com.nbcuni.test.cms.pageobjectutils.chiller.contenttype.Status;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.utils.Config;
import com.nbcuni.test.cms.utils.GlobalConstants;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.ExternalLinksJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.MediaJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.series.SeriesJson;
import com.nbcuni.test.cms.utils.jsonparsing.contentapi.jsonclasses.GlobalNodeJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.concerto.MpxMetadata;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.RokuProgramJson;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Dzianis_Kulesh on 2/8/2017.
 * <p>
 * Class which is going to convert GlobalProgramEntity to different objects used in
 * publishing.
 */
public class ProgramJsonTransformer {

    private ProgramJsonTransformer() {
    }

    // Logic for Concerto API publishing.
    public static synchronized SeriesJson forConcertoApi(GlobalProgramEntity program) {
        return getProgram(program);
    }


    // Logic for Serial API publishing.
    public static synchronized RokuProgramJson forSerialApi(GlobalProgramEntity program) {
        RokuProgramJson programJson = new RokuProgramJson();
        programJson.setType("program");
        programJson.setSeriesType(StringUtils.isEmpty(program.getMpxAsset().getSeriesType()) ? "" : program.getMpxAsset().getSeriesType());
        programJson.setSeriesCategory(StringUtils.isEmpty(program.getMpxAsset().getSeriesCategory()) ? null : program.getMpxAsset().getSeriesCategory());
        programJson.setShowColor("");
        programJson.setId(program.getMpxAsset().getGuid());
        programJson.setMpxId(program.getMpxAsset().getId());
        programJson.setTitle(program.getTitle());
        programJson.setDescription(program.getMpxAsset().getDescription());
        programJson.setDateModified(program.getUpdatedDate().format(DateTimeFormatter.ofPattern(GlobalConstants.SERIAL_API_DATE_FORMAT)));
        programJson.setImages(ImageTransformer.getSerialApiProgram(program.getImageSources()));
        programJson.setShowColor(program.getShowColor());
        return programJson;
    }

    private static synchronized SeriesJson getProgram(GlobalProgramEntity program) {
        SeriesJson seriesJson = new SeriesJson();
        seriesJson.setUuid(program.getGeneralInfo().getUuid());
        seriesJson.setItemType(ItemTypes.SERIES.getItemType());
        seriesJson.setRevision(program.getGeneralInfo().getRevision());
        seriesJson.setTitle(program.getTitle());
        seriesJson.setSlug(program.getSlugInfo().getSlugValue());
        seriesJson.setSubhead(program.getGeneralInfo().getSubhead());
        seriesJson.setSortTitle(program.getGeneralInfo().getSortTitle());
        setMedia(program, seriesJson);
        seriesJson.setTags(new ArrayList<>());
        seriesJson.setPublished(program.getPublished());
        List<ExternalLinksJson> externalLinksJsons = new ArrayList<>();
        if (!CollectionUtils.isEmpty(program.getExternalLinksInfo())) {
            for (ExternalLinksInfo info : program.getExternalLinksInfo()) {
                ExternalLinksJson externalLinksJson = new ExternalLinksJson();
                externalLinksJson.setObject(info);
                externalLinksJsons.add(externalLinksJson);
            }
        }
        seriesJson.setLinks(externalLinksJsons);
        setSeriesInfo(seriesJson, program);
        setPromotionalInfo(seriesJson, program);
        setAssociationInfo(seriesJson, program);
        seriesJson.setCollection(null);
        setMPXInfo(seriesJson, program);
        return seriesJson;
    }

    private static SeriesJson setSeriesInfo(SeriesJson seriesJson, GlobalProgramEntity program) {
        seriesJson.setGenre(program.getSeriesInfo().getGenre() != null ? Arrays.asList(program.getSeriesInfo().getGenre().getValue()) :
                new ArrayList<>());
        seriesJson.setUnscripted(program.getSeriesInfo().getUnscripted() != null ? program.getSeriesInfo().getUnscripted() : false);
        seriesJson.setSyndicated(program.getSeriesInfo().getSyndicated() != null ? program.getSeriesInfo().getSyndicated() : false);
        seriesJson.setProgramStatus(program.getSeriesInfo().getStatus() != null ? program.getSeriesInfo().getStatus().getValue() : null);
        seriesJson.setRelatedSeries(program.getSeriesInfo().getRelatedSeries() != null ? Arrays.asList(program.getSeriesInfo().getRelatedSeries()) : new ArrayList<String>());
        seriesJson.setContentRating(program.getSeriesInfo().getRating() != null ? program.getSeriesInfo().getRating().getValue() : "");
        if (program.getSeriesInfo().getType() != null) {
            seriesJson.setSeriesType(program.getSeriesInfo().getType().getValue());
        }
        return seriesJson;
    }

    private static SeriesJson setPromotionalInfo(SeriesJson seriesJson, GlobalProgramEntity program) {
        seriesJson.setLongDescription(StringUtils.isEmpty(program.getGeneralInfo().getLongDescription()) ? null : program.getGeneralInfo().getLongDescription());
        seriesJson.setPromoKicker(program.getPromotional().getPromotionalKicker() != null ? program.getPromotional().getPromotionalKicker() : "");
        seriesJson.setPromoDescription(StringUtils.isEmpty(program.getPromotional().getPromotionalDescription()) ? null : program.getPromotional().getPromotionalDescription());
        seriesJson.setPromoTitle(program.getPromotional().getPromotionalTitle());
        return seriesJson;
    }

    private static SeriesJson setAssociationInfo(SeriesJson seriesJson, GlobalProgramEntity program) {
        seriesJson.setCurrentSeason(program.getAssociations().getSeason() != null ? program.getAssociations().getSeason().getGeneralInfo().getUuid() : null);
        seriesJson.setCategories(program.getAssociations().getCategories());
        return seriesJson;
    }

    private static SeriesJson setMPXInfo(SeriesJson seriesJson, GlobalProgramEntity program) {
        seriesJson.setDaypart(StringUtils.isEmpty(program.getMpxAsset().getDayPart()) ? null : program.getMpxAsset().getDayPart());
        seriesJson.setSeriesType(StringUtils.isEmpty(program.getMpxAsset().getSeriesType()) ? null : program.getMpxAsset().getSeriesType());
        // creation of MPX metadata
        MpxMetadata mpxMetadata = new MpxMetadata();
        mpxMetadata.setMpxGuid(program.getMpxAsset().getGuid());
        mpxMetadata.setTmsId(StringUtils.isEmpty(program.getMpxAsset().getSeriesTmsId()) ? null : program.getMpxAsset().getSeriesTmsId());
        seriesJson.setMpxMetadata(mpxMetadata);
        seriesJson.setShortDescription(StringUtils.isEmpty(program.getMpxAsset().getShortDescription()) ? null : program.getMpxAsset().getShortDescription());
        seriesJson.setMediumDescription(program.getMpxAsset().getDescription());
        seriesJson.setContentRating(null);
        seriesJson.setSlug(program.getSlugInfo().getSlugValue());
        seriesJson.setShowColor(program.getShowColor());
        seriesJson.setSeriesEpisodeLength(program.getMpxAsset().getDuration());
        return seriesJson;
    }

    // set media info for concerto API publishing
    private static synchronized void setMedia(GlobalProgramEntity program, SeriesJson seriesJson) {
        List<MediaJson> medias = new ArrayList<>();
        for (ImageSource imageSource : program.getImageSources()) {
            Source source = SourceMatcher.getSourceClassInstanceByMachineName(imageSource.getMachineName(), ContentType.TVE_PROGRAM);
            if (source.getConcertoUsage() != null) {
                medias.add(MediaJsonTransformer.getMediaJsonForConcertoProgram(imageSource));
            }
        }
        seriesJson.setMedia(removeMediaDuplicates(medias));
    }

    // delete items with same UUID if any.
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

    public static GlobalProgramEntity getGlobalProgramEntity(GlobalNodeJson globalNodeJson, String brand) {
        GlobalProgramEntity globalProgramEntity = new GlobalProgramEntity();
        globalProgramEntity.setTitle(globalNodeJson.getTitle());
        globalProgramEntity.setMpxAsset(globalNodeJson.getMpxAsset());
        globalProgramEntity.setUpdatedDate(globalNodeJson.getUpdatedDate());
        globalProgramEntity.setSubhead(globalNodeJson.getShowPageCta());
        globalProgramEntity.getSeriesInfo().setStatus(Status.getValue(globalNodeJson.getProgramStatus()));
        globalProgramEntity.getSeriesInfo().setGenre(Genre.getValue(globalNodeJson.getGenre()));
        globalProgramEntity.getGeneralInfo().setLongDescription(globalNodeJson.getLongDescription());

        Promotional promotional = new Promotional();
        promotional.setPromotionalKicker(globalNodeJson.getFeatureCarouselCta());
        promotional.setPromotionalTitle(globalNodeJson.getFeatureCarouselHeadline());
        promotional.setPromotionalDescription(null);
        globalProgramEntity.setPromotional(promotional);
        Slug slug = new Slug();
        slug.setAutoSlug(globalNodeJson.getUrlPath());
        slug.setSlugValue(globalNodeJson.getSlug());
        globalProgramEntity.setSlugInfo(slug);
        globalProgramEntity.setImageSources(urlTransformer(globalNodeJson.getImageSources(), brand));
        Associations associations = new Associations();
        associations.getChannelReferenceAssociations().setChannelReference(new ChannelReference().setSeries(null)
                .setItemType("series"));
        globalProgramEntity.setAssociations(associations);
        globalProgramEntity.getGeneralInfo().setRevision(Integer.parseInt(globalNodeJson.getVid()));
        globalProgramEntity.getGeneralInfo().setUuid(globalNodeJson.getUuid());
        globalProgramEntity.setPublished(globalNodeJson.getPublished());
        globalProgramEntity.setGradient(globalNodeJson.getGradient());
        globalProgramEntity.setShowColor(globalNodeJson.getShowColor());
        return globalProgramEntity;
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
