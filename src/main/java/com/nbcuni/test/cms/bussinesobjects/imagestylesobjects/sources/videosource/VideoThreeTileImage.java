package com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.videosource;

import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.Source;

import java.util.Arrays;
import java.util.List;

import static com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles.*;

/**
 * Created by Aliaksei_Dzmitrenka on 2/8/2017.
 */
public class VideoThreeTileImage implements Source {

    private List<ImageStyles> imageStyles = Arrays.asList(three_tile_video_three, three_tile_video_three_resume, three_tile_video_three_a, three_tile_video_three_a_resume);

    @Override
    public List<ImageStyles> getImageStyles() {
        return imageStyles;
    }
}
