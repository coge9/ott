package com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.programsources.firetvsources;

import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.Source;
import com.nbcuni.test.cms.pageobjectutils.tvecms.ImageUsage;

import java.util.Arrays;
import java.util.List;

import static com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles.landscape_1284x1080_x1;

/**
 * Created by Aliaksei_Dzmitrenka on 2/8/2017.
 */
public class ProgramFireTV2088x1576 implements Source {

    private static final String CONCERTO_USAGE = ImageUsage.FEATURED.getUsage();


    private List<ImageStyles> imageStyles = Arrays.asList(
            landscape_1284x1080_x1);

    @Override
    public List<ImageStyles> getImageStyles() {
        return imageStyles;
    }

    @Override
    public String getConcertoUsage() {
        return CONCERTO_USAGE;
    }
}
