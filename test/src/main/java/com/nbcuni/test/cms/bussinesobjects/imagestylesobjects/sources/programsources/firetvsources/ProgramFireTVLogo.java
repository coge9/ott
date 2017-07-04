package com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.programsources.firetvsources;

import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.Source;
import com.nbcuni.test.cms.pageobjectutils.tvecms.ImageUsage;

import java.util.Arrays;
import java.util.List;

import static com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles.*;

/**
 * Created by Aliaksei_Dzmitrenka on 2/8/2017.
 */
public class ProgramFireTVLogo implements Source {

    private static final String CONCERTO_USAGE = ImageUsage.LOGO_SECONDARY.getUsage();


    private List<ImageStyles> imageStyles = Arrays.asList(
            logo_400x117,
            logo_198x58_x1,
            logo_259x76_x1,
            logo_1600x468_x1,
            logo_1200x351_x1,
            logo_800x234_x1,
            logo_600x176_x1);

    @Override
    public List<ImageStyles> getImageStyles() {
        return imageStyles;
    }

    @Override
    public String getConcertoUsage() {
        return CONCERTO_USAGE;
    }
}
