package com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.videosource;

import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.Source;
import com.nbcuni.test.cms.pageobjectutils.tvecms.ImageUsage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles.*;

public class VideoOriginalImage implements Source {


    private static final String CONCERTO_USAGE = ImageUsage.PRIMARY.getUsage();
    /**
     * imageStyles it is a list for VideoOriginal Source preview. It contains all concerto images
     * and due object creation we add all serial images to that list too
     */

    private List<ImageStyles> imageStyles = new ArrayList<>(Arrays.asList(landscape_widescreen_size1024_x1, landscape_widescreen_size350_x2, landscape_widescreen_size640_x2, landscape_132x74_x1,
            landscape_132x74_x1_5, landscape_216x122_x1, landscape_216x122_x1_5, landscape_255x143_x1, landscape_255x143_x1_5, landscape_393x221_x1, landscape_393x221_x1_5, landscape_390x219_x1,
            landscape_720x405_x1, landscape_702x394_x1, landscape_494x278_x1, landscape_390x220_x1, landscape_1920x915_x1, landscape_340x191_x1, landscape_1290x608_x1, landscape_517x292_x1,
            landscape_1920x1080_x1
    ));
    /**
     * serialImageStyles it is a list with all Serial image styles under this source
     */

    private List<ImageStyles> serialImageStyles = Arrays.asList(one_tile_video_one, three_tile_video_one, three_tile_video_one_resume, three_tile_video_one_now_playing, three_tile_video_two,
            three_tile_video_two_resume, three_tile_video_two_now_playing, three_tile_video_four, three_tile_video_five, three_tile_video_six, three_tile_video_six_resume,
            three_tile_video_seven, three_tile_video_eight, video_landscape_440_247, video_landscape_533_300, video_landscape_670_375, video_portrait_720_406, video_portrait_646_363,
            video_landscape_323_182, video_landscape_439_247, video_landscape_554_312, video_portrait_390_220, video_portrait_540_304, video_landscape_390_220,
            video_landscape_494_278, video_landscape_702_394, video_landscape_1280_435, video_landscape_1920_912, video_portrait_1200_1134, video_landscape_1920_915,
            video_source);

    public VideoOriginalImage() {
        imageStyles.addAll(serialImageStyles);
    }

    @Override
    public List<ImageStyles> getImageStyles() {
        return imageStyles;
    }

    public List<ImageStyles> getSerialStyles() {
        return serialImageStyles;
    }

    @Override
    public String getConcertoUsage() {
        return CONCERTO_USAGE;
    }

}