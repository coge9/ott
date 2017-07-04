package com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.programsources.appletvsources;

import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.Source;
import com.nbcuni.test.cms.pageobjectutils.tvecms.ImageUsage;

import java.util.Arrays;
import java.util.List;

import static com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles.landscape_1704x440_x1;

/**
 * Created by Aliaksei_Dzmitrenka on 2/8/2017.
 */
public class ProgramAppleTV1704x440 implements Source {

    private static final String CONCERTO_USAGE = ImageUsage.FEATURED_4.getUsage();

    private List<ImageStyles> imageStyles = Arrays.asList(landscape_1704x440_x1);

    @Override
    public List<ImageStyles> getImageStyles() {
        return imageStyles;
    }

    @Override
    public String getConcertoUsage() {
        return CONCERTO_USAGE;
    }
}
