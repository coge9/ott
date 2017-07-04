package com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.programsources.androidsources;

import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles;
import com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.sources.Source;

import java.util.Arrays;
import java.util.List;

import static com.nbcuni.test.cms.bussinesobjects.imagestylesobjects.ImageStyles.program_landscape_1284_1080;

/**
 * Created by Aliaksei_Dzmitrenka on 2/8/2017.
 */
public class ProgramAndroid2088x1576 implements Source {

    private List<ImageStyles> imageStyles = Arrays.asList(program_landscape_1284_1080);

    @Override
    public List<ImageStyles> getImageStyles() {
        return imageStyles;
    }
}
