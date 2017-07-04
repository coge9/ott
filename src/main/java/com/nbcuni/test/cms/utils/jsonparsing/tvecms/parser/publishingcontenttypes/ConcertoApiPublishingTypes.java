package com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.publishingcontenttypes;

import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.castcredit.CastCreditJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.collection.CollectionJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.episode.EpisodeJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.event.EventJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.mediagallery.MediaGalleryJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.season.SeasonJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.series.SeriesJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.contenttype.video.VideoJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.images.ImagesJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.metadata.MetadataJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.post.PostJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.concerto.PromoJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.concerto.platform.PlatformJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.TaxonomyTermJson;


/**
 * Enum represent items types which can be published to Concerto API.
 * <p>
 * Created by Dzianis_Kulesh on 1/23/2017.
 */
public enum ConcertoApiPublishingTypes implements PublishingContentType {

    VIDEO("Node (TVE Video)", VideoJson.class),
    PROGRAM("Node (TVE Program)", SeriesJson.class),
    FILE_IMAGE("File (Image)", ImagesJson.class),
    COLLECTIONS("Queue (C", CollectionJson.class),
    PAGE("TVE Page", CollectionJson.class),
    MODULE("Module", CollectionJson.class),
    TAXONOMY_TERM("Taxonomy term", TaxonomyTermJson.class),
    EPISODE("Node (TVE Episode)", EpisodeJson.class),
    EVENT("Node (TVE Event)", EventJson.class),
    SERIES("Node (TVE Series)", SeriesJson.class),
    SEASON("Node (TVE Season)", SeasonJson.class),
    POST("Node (TVE Post)", PostJson.class),
    MEDIA_GALLERY("Node (TVE Media Gallery)", MediaGalleryJson.class),
    // for role and person there is common JSON object MetadataJson.class
    ROLE("Node (TVE Role)", MetadataJson.class),
    PERSON("Node (TVE Person)", MetadataJson.class),
    PROMO("Node (TVE Promo)", PromoJson.class),
    PLATFORM("Platform", PlatformJson.class),
    CAST_CREDIT("Node (TVE Cast Collection)", CastCreditJson.class);

    private final String objectLabel;
    private final Class clazz;
    ConcertoApiPublishingTypes(String objectLabel, Class clazz) {
        this.objectLabel = objectLabel;
        this.clazz = clazz;
    }

    @Override
    public Class getBelongObjectClass() {
        return clazz;
    }

    @Override
    public String getObjectLabel() {
        return objectLabel;
    }
}
