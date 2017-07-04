package com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.programsources.roqusqssources;

import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.Source;
import com.nbcuni.test.cms.pageobjectutils.tvecms.ImageUsage;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Dzianis_Kulesh on 4/12/2017.
 */
public class ProgramRokuSqsLogo implements Source {

    private static final String CONCERTO_USAGE = ImageUsage.LOGO.getUsage();

    private List<ImageStyles> imageStyles = Arrays.asList(ImageStyles.logo_source);

    @Override
    public List<ImageStyles> getImageStyles() {
        return imageStyles;
    }

    @Override
    public String getConcertoUsage() {
        return CONCERTO_USAGE;
    }
}
