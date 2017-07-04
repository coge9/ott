package com.nbcuni.test.cms.utils.thumbnails;

import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.Source;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.videosource.VideoOriginalImage;
import com.nbcuni.test.cms.bussinesobjects.tvecms.ImageSource;
import com.nbcuni.test.cms.pageobjectutils.chiller.ItemTypes;
import com.nbcuni.test.cms.utils.ImageUtil;
import com.nbcuni.test.cms.utils.RegexUtil;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.images.ImageStyleJson;
import com.nbcuni.test.cms.utils.jsonparsing.chiller.parser.images.ImagesJson;
import com.nbcuni.test.cms.utils.jsonparsing.tvecms.parser.Image;
import com.nbcuni.test.cms.utils.thumbnails.imagestyles.*;

import java.util.*;

/**
 * Created by Aliaksei_Dzmitrenka on 2/8/2017.
 */
public class ImageStylesUtils {

    private static final String URL_PART = "/styles/%s/public";

    private ImageStylesUtils(){
        super();
    }

    public static List<Image> imposeImageStyles(Source source, ImageSource imageSource) {
        List<Image> toReturn = new ArrayList<>();
        List<ImageStyles> styles = source.getImageStyles();
        if (source instanceof VideoOriginalImage) {
            VideoOriginalImage s = (VideoOriginalImage) source;
            styles = s.getSerialStyles();
        }
        for (ImageStyles style : styles) {
            toReturn.add(getImageObject(style, imageSource));
        }
        return toReturn;
    }

    private static String insert(String url, String name) {
        int index = RegexUtil.getFirstMatcherIndex(url, "/\\d{4}");
        String bagBegin = url.substring(0, index);
        String bagEnd = url.substring(index);
        return bagBegin + String.format(URL_PART, name) + bagEnd;
    }

    private static Image getImageObject(ImageStyles style, ImageSource imageSource) {
        String url = imageSource.getImageUrl();
        if (style.equals(ImageStyles.video_source) || style.equals(ImageStyles.program_source_withlogo) || style.equals(ImageStyles.logo_source)) {
            if (imageSource.getWidth() != 0) {
                return new Image(style.getName(), insert(url, style.getName()), imageSource.getWidth(), imageSource.getHeight());
            } else {
                String resolution = ImageUtil.getImageResolution(url);
                int width = Integer.parseInt(resolution.split("x")[0]);
                int height = Integer.parseInt(resolution.split("x")[1]);
                return new Image(style.getName(), insert(url, style.getName()), width, height);
            }
        }
        return new Image(style.getName(), insert(url, style.getName()), style.getWidth(), style.getHeight());
    }

    public static ImagesJson getConcertoImageJson(List<ImageStyles> styles, ImageSource source) {
        ImagesJson imagesJson = new ImagesJson();
        int height = ImageUtil.getHeight(source.getImageUrl());
        int width = ImageUtil.getWidth(source.getImageUrl());
        imagesJson
                .setRevision(Integer.parseInt(source.getVid()))
                .setItemType(ItemTypes.IMAGE.getItemType())
                .setTitle(source.getImageName())
                .setHref(source.getImageUrl())
                .setUuid(source.getUuid())
                .setPrograms(new ArrayList<>())
                .setPublished(true)
                .setCategories(new ArrayList<>())
                .setTags(new ArrayList<>());

        List<ImageStyleJson> imageStyleJsons = new ArrayList<>();
        for (ImageStyles imageStyle : styles) {
            ImageStyleJson imageStyleJson = new ImageStyleJson();
            imageStyleJson
                    .setType(imageStyle.getName())
                    .setHref(insert(source.getImageUrl(), imageStyle.getName().replace(".", "_")))
                    .setWidth(imageStyle.getWidth() != 0 ? imageStyle.getWidth() : width)
                    .setHeight(imageStyle.getHeight() != 0 ? imageStyle.getHeight() : height);
            imageStyleJsons.add(imageStyleJson);

        }
        imagesJson.setImageStyles(imageStyleJsons);

        return imagesJson;
    }

    public static ImagesJson getConcertoImageJson(ImageSource source, String brand) {
        return getConcertoImageJson(new ArrayList<>(getConcertoStyles(brand)), source);
    }

    private static List<ImageStyles> getChillerStyles() {
        List<ImageStyles> styles = Arrays.asList(
                ImageStyles.w1024_focalpoint_16_9,
                ImageStyles.w1024_focalpoint_4_1,
                ImageStyles.w1024_focalpoint_4_3,
                ImageStyles.w1200_focalpoint_16_9,
                ImageStyles.w1200_focalpoint_4_1,
                ImageStyles.w1200_focalpoint_4_3,
                ImageStyles.w1600_focalpoint_16_9,
                ImageStyles.w1600_focalpoint_4_1,
                ImageStyles.w1600_focalpoint_4_3,
                ImageStyles.w1920_focalpoint_16_9,
                ImageStyles.w1920_focalpoint_4_1,
                ImageStyles.w1920_focalpoint_4_3,
                ImageStyles.w200_focalpoint_compressive_5_8,
                ImageStyles.w2560_focalpoint_16_9,
                ImageStyles.w2560_focalpoint_4_1,
                ImageStyles.w400_focalpoint_compressive_16_9,
                ImageStyles.w400_focalpoint_compressive_1_1,
                ImageStyles.w400_focalpoint_compressive_2_1,
                ImageStyles.w400_focalpoint_compressive_4_5,
                ImageStyles.w50_focalpoint_compressive_1_1,
                ImageStyles.w600_focalpoint_compressive_1_1,
                ImageStyles.w600_focalpoint_compressive_2_1,
                ImageStyles.w600_focalpoint_compressive_3_1,
                ImageStyles.w600_focalpoint_compressive_4_1,
                ImageStyles.w600_focalpoint_compressive_4_3,
                ImageStyles.w600_focalpoint_compressive_5_3,
                ImageStyles.w800_focalpoint_1_1,
                ImageStyles.w800_focalpoint_compressive_16_9,
                ImageStyles.w800_focalpoint_compressive_2_1,
                ImageStyles.w800_focalpoint_compressive_3_1,
                ImageStyles.w800_focalpoint_compressive_4_1,
                ImageStyles.w800_focalpoint_compressive_4_3,
                ImageStyles.w800_focalpoint_compressive_5_3,
                ImageStyles.w900_focalpoint_5_8,
                ImageStyles.wmax1200_hmax800_scale,
                ImageStyles.wmax1600_hmax900_scale,
                ImageStyles.wmax1920_hmax1080_scale,
                ImageStyles.wmax400_hmax200_scale,
                ImageStyles.wmax600_hmax300_scale_compressive);
        return styles;
    }

    public static Set<ImageStyles> getConcertoStyles(String brand) {
        Set<ImageStyles> imageStyles = new HashSet<>();
        imageStyles.addAll(new RokuSQSStylesMatcher().getImageStyles(brand));
        imageStyles.addAll(new IOSStylesMatcher().getImageStyles(brand));
        imageStyles.addAll(new AppleTVStylesMatcher().getImageStyles(brand));
        imageStyles.addAll(new FireTVStylesMatcher().getImageStyles(brand));
        imageStyles.addAll(new XboxOneStylesMatcher().getImageStyles(brand));
        imageStyles.addAll(new UniversalSearchStylesMatcher().getImageStyles(brand));

        return imageStyles;
    }

}
