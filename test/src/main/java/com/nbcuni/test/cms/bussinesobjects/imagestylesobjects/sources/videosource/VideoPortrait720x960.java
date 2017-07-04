package com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.videosource;

import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.Source;

import java.util.Arrays;
import java.util.List;

import static com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles.video_portrait_720_960;

/**
 * Created by Aliaksei_Dzmitrenka on 2/8/2017.
 */
public class VideoPortrait720x960 implements Source {

    private List<ImageStyles> imageStyles = Arrays.asList(video_portrait_720_960);

    @Override
    public List<ImageStyles> getImageStyles() {
        return imageStyles;
    }
}
