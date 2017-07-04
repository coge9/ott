package com.nbcuni.test.cms.utils.transformers;

import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.Source;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.SourceMatcher;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ImageSource;
import com.nbcuni.test.cms.pageobjectutils.tve.ContentType;
import com.nbcuni.test.cms.pageobjectutils.tvecms.CmsPlatforms;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.images.ImagesJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.Image;
import com.nbcuni.test.cms.utils.thumbnails.ImageStylesUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Dzianis_Kulesh on 2/9/2017.
 */
public class ImageTransformer {

    public static synchronized List<Image> getSerialApiVideo(List<ImageSource> sources) {
        return getCommonForSerialApi(sources, ContentType.TVE_VIDEO);
    }

    public static synchronized List<Image> getSerialApiProgram(List<ImageSource> sources) {
        return getCommonForSerialApi(sources, ContentType.TVE_PROGRAM);
    }

    private static synchronized List<Image> getCommonForSerialApi(List<ImageSource> sources, ContentType type) {
        List<Image> images = new ArrayList<Image>();
        for (ImageSource source : sources) {
            // Only ROKU and Android images are published. Skipping other sources.
            if (source.getPlatform() == null || !(source.getPlatform().equals(CmsPlatforms.ROKU) || source.getPlatform().equals(CmsPlatforms.ANDROID))) {
                if (!type.equals(ContentType.TVE_VIDEO)) {
                    continue;
                }
            }
            Class<Source> clazz = null;
            if (!StringUtils.isEmpty(source.getMachineName())) {
                clazz = SourceMatcher.getSourceByMachibeName(source.getMachineName(), type).getSourceClass();
            } else {
                clazz = SourceMatcher.getSource(source.getName(), type, source.getPlatform()).getSourceClass();
            }
            if (clazz != null) {
                try {
                    Source sorceJavaObject = clazz.newInstance();
                    images.addAll(ImageStylesUtils.imposeImageStyles(sorceJavaObject, source));
                } catch (Throwable e) {
                    e.printStackTrace();
                    throw new RuntimeException("Unable to get images for serial API publishing");
                }
            }
        }
        return images;
    }

    public static synchronized List<ImagesJson> getConcertoApiForProgram(List<ImageSource> sources, String brand) {
        List<ImagesJson> imagesJsons = new ArrayList<>();
        Map<String, ImagesJson> imagesJsonMap = new HashMap<>();
        for (ImageSource source : sources) {
            if (source.getPlatform().isConcerto() || source.getMachineName().equals("field_3_tile_source")) {
                imagesJsonMap.putIfAbsent(source.getUuid(), ImageStylesUtils.getConcertoImageJson(source, brand));
            }
        }
        imagesJsons.addAll(imagesJsonMap.values());
        return imagesJsons;
    }

    public static synchronized List<ImagesJson> getConcertoApiForVideo(List<ImageSource> sources, String brand) {
        List<ImagesJson> imagesJsons = new ArrayList<>();
        Map<String, ImagesJson> imagesJsonMap = new HashMap<>();
        for (ImageSource source : sources) {
            if (source.getPlatform().isConcerto()) {
                imagesJsonMap.putIfAbsent(source.getUuid(), ImageStylesUtils.getConcertoImageJson(source, brand));
            }
        }
        imagesJsons.addAll(imagesJsonMap.values());
        return imagesJsons;
    }

    public static synchronized ImagesJson getConcertoApiForPromo(ImageSource source, String brand) {
        if (source != null) {
            return ImageStylesUtils.getConcertoImageJson(source, brand);
        }
        return null;
    }
}
