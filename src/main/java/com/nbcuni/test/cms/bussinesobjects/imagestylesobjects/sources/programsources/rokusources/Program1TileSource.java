package com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.programsources.rokusources;

import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.Source;

import java.util.Arrays;
import java.util.List;

import static com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles.one_tile_program_one;

/**
 * Created by Aliaksei_Dzmitrenka on 2/8/2017.
 */
public class Program1TileSource implements Source {

    private List<ImageStyles> imageStyles = Arrays.asList(one_tile_program_one);

    @Override
    public List<ImageStyles> getImageStyles() {
        return imageStyles;
    }
}
