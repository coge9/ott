package com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.programsources.rokusources;

import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.Source;
import com.nbcuni.test.cms.pageobjectutils.tvecms.ImageUsage;

import java.util.Arrays;
import java.util.List;

import static com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles.program_source_withlogo;
import static com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles.three_tile_program_one;

/**
 * Created by Aliaksei_Dzmitrenka on 2/8/2017.
 */
public class Program3TileSource implements Source {

    private static final String CONCERTO_USAGE = ImageUsage.ROKU_SMALL.getUsage();

    private List<ImageStyles> imageStyles = Arrays.asList(three_tile_program_one, program_source_withlogo);

    @Override
    public List<ImageStyles> getImageStyles() {
        return imageStyles;
    }

    @Override
    public String getConcertoUsage() {
        return CONCERTO_USAGE;
    }
}
