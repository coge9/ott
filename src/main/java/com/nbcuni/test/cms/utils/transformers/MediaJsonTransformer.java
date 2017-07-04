package com.nbcuni.test.cms.utils.transformers;

import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.Source;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.SourceMatcher;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ImageSource;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.MediaJson;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Dzianis_Kulesh on 2/8/2017.
 * <p>
 * Class which is going to convert diferent objects to MediaJson
 */
public class MediaJsonTransformer {

    private static final String ITEM_TYPE = "image";

    private MediaJsonTransformer(){
        super();
    }

    public static synchronized MediaJson getMediaJsonForConcerto(ImageSource imageSource) {
        MediaJson mediaJson = new MediaJson();
        mediaJson.setItemType(ITEM_TYPE);
        mediaJson.setUsage(imageSource.getUsage());
        mediaJson.setUuid(imageSource.getUuid());
        return mediaJson;
    }


    public static synchronized MediaJson getMediaJsonForConcertoProgram(ImageSource imageSource) {
        return getMediaCommonConcerto(imageSource, ContentType.TVE_PROGRAM);
    }

    public static synchronized MediaJson getMediaJsonForConcertoVideo(ImageSource imageSource) {
        return getMediaCommonConcerto(imageSource, ContentType.TVE_VIDEO);
    }

    public static synchronized MediaJson getMediaJsonForConcertoPromo(ImageSource imageSource) {
        return getMediaCommonConcerto(imageSource, ContentType.TVE_PROMO);
    }

    private static synchronized MediaJson getMediaCommonConcerto(ImageSource imageSource, ContentType type) {
        MediaJson mediaJson = new MediaJson();
        mediaJson.setItemType(ITEM_TYPE);
        if (StringUtils.isEmpty(imageSource.getUsage())) {
            mediaJson.setUsage(getConcertoUsage(imageSource, type));
        } else {
            mediaJson.setUsage(imageSource.getUsage());
        }
        mediaJson.setUuid(imageSource.getUuid());
        return mediaJson;
    }

    private static String getConcertoUsage(ImageSource imageSource, ContentType type) {
        Source sourceClassInstance = SourceMatcher.getSourceClassInstanceByMachineName(imageSource.getMachineName(), type);
        return sourceClassInstance.getConcertoUsage();
    }
}
