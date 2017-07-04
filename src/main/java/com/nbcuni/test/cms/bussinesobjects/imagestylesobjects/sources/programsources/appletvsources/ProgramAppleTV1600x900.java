package com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.programsources.appletvsources;

import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.Source;
import com.nbcuni.test.cms.pageobjectutils.tvecms.ImageUsage;

import java.util.Arrays;
import java.util.List;

import static com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles.*;

/**
 * Created by Aliaksei_Dzmitrenka on 2/8/2017.
 */
public class ProgramAppleTV1600x900 implements Source {

    private static final String CONCERTO_USAGE = ImageUsage.SECONDARY.getUsage();


    private List<ImageStyles> imageStyles = Arrays.asList(landscape_127x71_x1, landscape_127x71_x1_5, landscape_255x143_x1, landscape_255x143_x1_5, landscape_540x304_x1, landscape_540x304_x1_5);

    @Override
    public List<ImageStyles> getImageStyles() {
        return imageStyles;
    }

    @Override
    public String getConcertoUsage() {
        return CONCERTO_USAGE;
    }
}
