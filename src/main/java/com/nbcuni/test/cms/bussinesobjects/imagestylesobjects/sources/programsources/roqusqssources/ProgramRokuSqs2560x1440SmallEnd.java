package com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.programsources.roqusqssources;

import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.Source;
import com.nbcuni.test.cms.pageobjectutils.tvecms.ImageUsage;

import java.util.Arrays;
import java.util.List;

import static com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles.landscape_340x191_x1;

/**
 * Created by Dzianis_Kulesh on 4/12/2017.
 */
public class ProgramRokuSqs2560x1440SmallEnd implements Source {

    private static final String CONCERTO_USAGE = ImageUsage.KEY_ART2.getUsage();

    private List<ImageStyles> imageStyles = Arrays.asList(landscape_340x191_x1);

    @Override
    public List<ImageStyles> getImageStyles() {
        return imageStyles;
    }

    @Override
    public String getConcertoUsage() {
        return CONCERTO_USAGE;
    }
}
