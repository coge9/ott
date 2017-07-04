package com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.programsources.iossources;

import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.Source;
import com.nbcuni.test.cms.pageobjectutils.tvecms.ImageUsage;

import java.util.Arrays;
import java.util.List;

import static com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles.*;

/**
 * Created by Aliaksei_Dzmitrenka on 2/8/2017.
 */
public class ProgramIOS1965x1108 implements Source {

    private static final String CONCERTO_USAGE = ImageUsage.PRIMARY.getUsage();

    private List<ImageStyles> imageStyles = Arrays.asList(landscape_widescreen_size1024_x1, landscape_widescreen_size640_x2, landscape_widescreen_size350_x2);

    @Override
    public List<ImageStyles> getImageStyles() {
        return imageStyles;
    }

    @Override
    public String getConcertoUsage() {
        return CONCERTO_USAGE;
    }
}
